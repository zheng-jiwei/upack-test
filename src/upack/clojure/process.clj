(ns upack.clojure.process
    (:gen-class)
  (:require [ring.util.codec :as codec]
            [cheshire.core :as cjson]
            )
    )

(def ^:dynamic area_map (atom {}))

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

(defn process-delivery-price [request]
  (let [data (:params request)]
    {:status 200
     :body
     (cjson/generate-string {:data (calculate_delivery_price (codec/url-decode (:from data))
                                                               (codec/url-decode (:to data))
                                                               (codec/url-decode (:size data))
                                                               )
                             })}
    )
  )
