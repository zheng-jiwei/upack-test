(ns upack.clojure.process
    (:gen-class)
    (:require [ring.util.codec :as codec]
      [cheshire.core :as cjson]
      )
    )

(def ^:dynamic area_map (atom {}))
(def ^:dynamic area_map_2 (atom {}))

(def added_height (atom 0))
(def exist_box (atom {:l 0 :w 0 :h 0}))
(def result_boxs (atom []))
(def input_boxs (atom []))

(defn str-to-int [str]
      (int (Float/parseFloat str))
      )

(defn get-all-province [request]
      {:status 200 :body (cjson/generate-string {:data (keys (sort-by val < (get @area_map "index")))})}
      )

(defn get-all-box-sizes [request]
      {:status 200 :body (cjson/generate-string {:data (keys (get @area_map "price"))})}
      )

(defn- get-max-size [size]
       (try
         (let [i_size (str-to-int (str size))]
              (cond
                (<= (- i_size 60) 0) 60
                (<= (- i_size 80) 0) 80
                (<= (- i_size 100) 0) 100
                (<= (- i_size 120) 0) 120
                (<= (- i_size 140) 0) 140
                (<= (- i_size 160) 0) 160
                (<= (- i_size 170) 0) 170
                :else {:error "荷物のサイズは170cmまで受付できます。"}
                )
              )
         (catch Exception e
           {:error "サイズの入力は数字のみ、または数字のみあるの文字列ができます。"}
           ))
       )

(defn calculate_delivery_price[from to size]
      (let [size (get-max-size size)]
           (if (:error size)
             size
             (if (or (= from "沖縄") (= to "沖縄"))
               (let [index_map (get @area_map_2 "index")
                     index_from (get index_map from)
                     index_to  (get index_map to)
                     ]
                    (or
                      (-> @area_map_2 (get (str index_from)) (get (str index_to)) (get (str size)))
                      (-> @area_map_2 (get (str index_to)) (get (str index_from)) (get (str size)))
                      )
                    )
               (let [index_map (get @area_map "index")
                     index_diff (- (get index_map from) (get index_map to))
                     index_diff (int (Math/ceil(max index_diff (- 0 index_diff))))
                     index_diff (if (= from to) -1 index_diff)
                     index_diff (if (or (= from "北海道") (= to "北海道"))
                                  (cond
                                    (> index_diff 5) "5"
                                    (= index_diff 5) "5-h"
                                    :else index_diff
                                    )
                                  index_diff
                                  )
                     ]
                    (-> @area_map (get "price") (get (str size)) (get (str index_diff)))
                    )
               )
             )
           )
      )

(defn process-delivery-price [request]
      (let [data (:params request)]
           {:status 200
            :body
            (cjson/generate-string
              {:data
               (calculate_delivery_price (codec/url-decode (:from data))
                                         (codec/url-decode (:to data))
                                         (codec/url-decode (:size data)))})
            }
           )
      )

(defn remove-one-element-from-array [elem array]
       (if (= (-> array first type) clojure.lang.PersistentVector)
         (let [total (count array)
               result (remove #{elem} array)
               count2 (- total (count result))]
              (if (= 1 count2)
                result
                (reduce #(conj %1 elem) result (range (- count2 1)))
                )
              )
         (reduce (fn [m iNum]
                     (let [total (count m)
                           result (remove #{iNum} m)
                           count2 (- total (count result))
                           ]
                          (if (= 1 count2)
                            result
                            (reduce (fn [r i] (conj r iNum)) result (range (- count2 1)))
                            )
                          )
                     )
                 array elem)
         )
       )

(defn- select_max_plane_packet [limit_box]
       (if (= 0 (:l limit_box))
         (reduce #(if (< (* (:l %1) (:w %1)) (* (:l %2) (:w %2))) %2 %1) {:l 0 :w 0 :h 0} @input_boxs)
         (reduce #(if (and (< (* (:l %1) (:w %1)) (* (:l %2) (:w %2)))
                           (<= (:l %2) (:l limit_box)) (<= (:w %2) (:w limit_box))
                           )
                    %2 %1) {:l 0 :w 0 :h 0} @input_boxs)
         )
       )
(defn- select_max_length_packet [limit_box]
       (if (= 0 (:l limit_box))
         (reduce #(if (< (:l %1) (:l %2)) %2 %1) {:l 0 :w 0 :h 0} @input_boxs)
         (reduce #(if (and (< (:l %1) (:l %2))
                           (<= (:l %2) (:l limit_box))
                           )
                    %2 %1) {:l 0 :w 0 :h 0} @input_boxs)
         )
       )
(declare combine_packets_in_layer)
(defn- get_next_packet [area1 area2]
       (let [target_box (reduce (fn [m in]
                                    (if (and
                                          (> (:l in) (:l m))
                                          (<= (:w in) (:w area1))
                                          (<= (:l in) (:l area1)))
                                      in m)
                                    ) {:l 0 :w 0 :h 0} @input_boxs)]
            (if (and (> (:l target_box) 0) (<= (+ (:l @exist_box) (:w @exist_box) (+ (max (:h target_box) @added_height) (:h @exist_box))) 170))
                  (do
                    (reset! input_boxs (remove-one-element-from-array [target_box] @input_boxs))
                    (reset! added_height (max (:h target_box) @added_height))
                    (get_next_packet {:l (max (:l target_box) (- (:w area1) (:w target_box)))
                                      :w (min (:l target_box) (- (:w area1) (:w target_box)))
                                      :h (max (:h area1) (:h target_box))}
                                     {:l (max (- (:l area1) (:l target_box)) (:w area1))
                                      :w (min (- (:l area1) (:l target_box)) (:w area1))
                                      :h (max (:h area1) (:h target_box))}
                                     )
                    )
                  (let [target_box_2 (reduce (fn [m in]
                                                 (if (and
                                                       (> (:l in) (:l m))
                                                       (<= (:w in) (:w area2))
                                                       (<= (:l in) (:l area2)))
                                                   in m)
                                                 ) {:l 0 :w 0 :h 0} @input_boxs)]
                       (if (and (> (:l target_box_2) 0) (<= (+ (:l @exist_box) (:w @exist_box) (+ (max (:h target_box_2) @added_height) (:h @exist_box))) 170))
                         (do
                           (reset! input_boxs (remove-one-element-from-array [target_box_2] @input_boxs))
                           (reset! added_height (max (:h target_box_2) @added_height))
                           (get_next_packet {:l (max (:l target_box_2) (- (:w area2) (:w target_box_2)))
                                             :w (min (:l target_box_2) (- (:w area2) (:w target_box_2)))
                                             :h (max (:h area2) (:h target_box_2))}
                                            {:l (max (- (:l area2) (:l target_box_2)) (:w area2))
                                             :w (min (- (:l area2) (:l target_box_2)) (:w area2))
                                             :h (max (:h area2) (:h target_box_2))}
                                            )
                           )
                         (if (> (+ (:l @exist_box) (:w @exist_box) (+ (max (:h target_box) @added_height) (:h @exist_box))) 170)
                           (do
                             (reset! result_boxs (conj @result_boxs (assoc @exist_box :h (+ @added_height (:h @exist_box)))))
                             (reset! exist_box {:l 0 :w 0 :h 0})
                             ;(combine_packets_in_layer @exist_box)
                             )
                           )
                         )
                       )
                  )
            )
       )

(defn combine_packets_in_layer [limit_box]
      (reset! added_height 0)
      (let [first_packet (select_max_plane_packet limit_box)
            left_packets_count (count @input_boxs)]
           (if (not= 0 (:l first_packet))
             (do
               (if (= 0 (:l limit_box))
                 (reset! exist_box first_packet)
                 (reset! exist_box (assoc limit_box :h (+ (:h limit_box) (:h first_packet))))
                 )
               (reset! input_boxs (remove-one-element-from-array [first_packet] @input_boxs))
               (get_next_packet first_packet {:l 0 :w 0 :h 0})
               (if (not-empty @input_boxs)
                 (if (not= left_packets_count (count @input_boxs))
                   ; recalculate l w h of exist box
                   (let [l (max (:l @exist_box) (:w @exist_box) (+ (:h @exist_box) @added_height))
                         h (min (:l @exist_box) (:w @exist_box) (+ (:h @exist_box) @added_height))
                         w (first (remove-one-element-from-array [l h] [(:l @exist_box) (:w @exist_box) (+ (:h @exist_box) @added_height)]))]
                        (reset! exist_box {:l l :h h :w w})
                        (combine_packets_in_layer @exist_box)
                        )
                   )
                 (do
                   (reset! result_boxs (conj @result_boxs (assoc @exist_box :h (+ @added_height (:h @exist_box)))))
                   )
                 )
               )
             (if (not-empty @input_boxs)
               ;特別長さの荷物がある
               (let [max_length_packet (select_max_length_packet {:l 0})]
                    (if (> (+ (:w @exist_box) (:h @exist_box) (:l max_length_packet)) 170 )
                      (do
                        (reset! result_boxs (conj @result_boxs @exist_box))
                        (combine_packets_in_layer {:l 0 :w 0 :h 0})
                        )
                      (do
                        (reset! exist_box (assoc @exist_box :l (:l max_length_packet)))
                        (reset! input_boxs (remove-one-element-from-array [max_length_packet] @input_boxs))
                        (combine_packets_in_layer @exist_box)
                        )
                      )
                    )
               (do
                 (reset! result_boxs (conj @result_boxs (assoc @exist_box :h (+ @added_height (:h @exist_box)))))
                 )
               )
             )
           )
      )

(defn calculate_best_price [from to size_array]
      (reset! result_boxs [])
      (reset! exist_box [])
      (reset! added_height 0)
      (reset! input_boxs (map (fn [box]
                                  (let [l (apply max box)
                                        h (apply min box)
                                        w (first (remove-one-element-from-array [l h] box))]
                                       {:l l :w w :h h}
                                       )
                                  ) size_array))
      (combine_packets_in_layer {:l 0 :w 0 :h 0})
      (prn "### result=" @result_boxs "### input=" size_array)
      (apply + (map (fn [box]
                        (let [size (apply + (vals box))]
                             (prn "### size=" from to size)
                             (calculate_delivery_price from to size)
                             )
                        ) @result_boxs))
      )

(defn process-best-price [request]
      (let [data (:params request)
            ]
           {:status 2000
            :body
            (cjson/generate-string
              {:data
               (calculate_best_price (codec/url-decode (:from data))
                                     (codec/url-decode (:to data))
                                     (codec/url-decode (:size data)))})
            }
           )
      )
