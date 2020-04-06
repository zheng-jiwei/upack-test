(ns upack.clojure.process-test
    (:require
      [clojure.string :as str]
      [clojure.test :refer :all]
      [clojure.data.json :as json]
      [upack.clojure.process :as ucp]
      )
    (:import
      (java.util Date TimeZone Locale Calendar)
      )
    )

;Scenario-1: 地域毎の通常運賃
(deftest ^:user001 test-delivery-price-by-Scenario-1
         (binding [ucp/area_map (atom (json/read-str (slurp "index.json")))
                   ucp/area_map_2 (atom (json/read-str (slurp "table.json")))]
                  (testing "'北海道'からの通常運賃\n"
                           (testing "Given 地域毎にサイズ60cmの荷物を'北海道'から運送運賃を計算する\n"
                                    (testing " When 北海道まで荷物を発送する; Then 運賃は 810 円\n"
                                             (is (= 810 (ucp/calculate_delivery_price "北海道" "北海道" 60)))
                                             )
                                    (testing " When 青森まで荷物を発送する; Then 運賃は 1100 円\n"
                                             (is (= 1100 (ucp/calculate_delivery_price "北海道" "青森" 60)))
                                             )
                                    (testing " When 東京まで荷物を発送する; Then 運賃は 1300 円\n"
                                             (is (= 1300 (ucp/calculate_delivery_price "北海道" "東京" 60)))
                                             )
                                    (testing " When 新潟まで荷物を発送する; Then 運賃は 1300 円\n"
                                             (is (= 1300 (ucp/calculate_delivery_price "北海道" "新潟" 60)))
                                             )
                                    (testing " When 愛知まで荷物を発送する; Then 運賃は 1430 円\n"
                                             (is (= 1430 (ucp/calculate_delivery_price "北海道" "愛知" 60)))
                                             )
                                    (testing " When 富山まで荷物を発送する; Then 運賃は 1430 円\n"
                                             (is (= 1430 (ucp/calculate_delivery_price "北海道" "富山" 60)))
                                             )
                                    (testing " When 大阪まで荷物を発送する; Then 運賃は 1540 円\n"
                                             (is (= 1540 (ucp/calculate_delivery_price "北海道" "大阪" 60)))
                                             )
                                    (testing " When 徳島まで荷物を発送する; Then 運賃は 1540 円\n"
                                             (is (= 1540 (ucp/calculate_delivery_price "北海道" "徳島" 60)))
                                             )
                                    (testing " When 山口まで荷物を発送する; Then 運賃は 1540 円\n"
                                             (is (= 1540 (ucp/calculate_delivery_price "北海道" "山口" 60)))
                                             )
                                    (testing " When 大分まで荷物を発送する; Then 運賃は 1540 円\n"
                                             (is (= 1540 (ucp/calculate_delivery_price "北海道" "大分" 60)))
                                             )
                                    (testing " When 沖縄まで荷物を発送する; Then 運賃は 1550 円\n"
                                             (is (= 1550 (ucp/calculate_delivery_price "北海道" "沖縄" 60)))
                                             )
                                    )
                           )
                  (testing "'岩手'からの通常運賃\n"
                           (testing "Given 地域毎にサイズ80cmの荷物を'岩手'から運送運賃を計算する\n"
                                    (testing " When 岩手まで荷物を発送する; Then 運賃は 1030 円\n"
                                             (is (= 1030 (ucp/calculate_delivery_price "岩手" "岩手" 80)))
                                             )
                                    (testing " When 青森まで荷物を発送する; Then 運賃は 1100 円\n"
                                             (is (= 1100 (ucp/calculate_delivery_price "岩手" "青森" 80)))
                                             )
                                    (testing " When 北海道まで荷物を発送する; Then 運賃は 1310 円\n"
                                             (is (= 1310 (ucp/calculate_delivery_price "岩手" "北海道" 80)))
                                             )
                                    (testing " When 東京まで荷物を発送する; Then 運賃は 1100 円\n"
                                             (is (= 1100 (ucp/calculate_delivery_price "岩手" "東京" 80)))
                                             )
                                    (testing " When 新潟まで荷物を発送する; Then 運賃は 1100 円\n"
                                             (is (= 1100 (ucp/calculate_delivery_price "岩手" "新潟" 80)))
                                             )
                                    (testing " When 愛知まで荷物を発送する; Then 運賃は 1200 円\n"
                                             (is (= 1200 (ucp/calculate_delivery_price "岩手" "愛知" 80)))
                                             )
                                    (testing " When 富山まで荷物を発送する; Then 運賃は 1200 円\n"
                                             (is (= 1200 (ucp/calculate_delivery_price "岩手" "富山" 80)))
                                             )
                                    (testing " When 大阪まで荷物を発送する; Then 運賃は 1310 円\n"
                                             (is (= 1310 (ucp/calculate_delivery_price "岩手" "大阪" 80)))
                                             )
                                    (testing " When 徳島まで荷物を発送する; Then 運賃は 1530 円\n"
                                             (is (= 1530 (ucp/calculate_delivery_price "岩手" "徳島" 80)))
                                             )
                                    (testing " When 山口まで荷物を発送する; Then 運賃は 1530 円\n"
                                             (is (= 1530 (ucp/calculate_delivery_price "岩手" "山口" 80)))
                                             )
                                    (testing " When 大分まで荷物を発送する; Then 運賃は 1750 円\n"
                                             (is (= 1750 (ucp/calculate_delivery_price "岩手" "大分" 80)))
                                             )
                                    (testing " When 沖縄まで荷物を発送する; Then 運賃は 1760 円\n"
                                             (is (= 1760 (ucp/calculate_delivery_price "岩手" "沖縄" 80)))
                                             )
                                    )
                           )
                  (testing "'千葉'からの通常運賃\n"
                           (testing "Given 地域毎にサイズ100cmの荷物を'千葉'から運送運賃を計算する\n"
                                    (testing " When 千葉まで荷物を発送する; Then 運賃は 1280 円\n"
                                             (is (= 1280 (ucp/calculate_delivery_price "千葉" "千葉" 100)))
                                             )
                                    (testing " When 青森まで荷物を発送する; Then 運賃は 1330 円\n"
                                             (is (= 1330 (ucp/calculate_delivery_price "千葉" "青森" 100)))
                                             )
                                    (testing " When 北海道まで荷物を発送する; Then 運賃は 1760 円\n"
                                             (is (= 1760 (ucp/calculate_delivery_price "千葉" "北海道" 100)))
                                             )
                                    (testing " When 東京まで荷物を発送する; Then 運賃は 1330 円\n"
                                             (is (= 1330 (ucp/calculate_delivery_price "千葉" "東京" 100)))
                                             )
                                    (testing " When 新潟まで荷物を発送する; Then 運賃は 1330 円\n"
                                             (is (= 1330 (ucp/calculate_delivery_price "千葉" "新潟" 100)))
                                             )
                                    (testing " When 愛知まで荷物を発送する; Then 運賃は 1330 円\n"
                                             (is (= 1330 (ucp/calculate_delivery_price "千葉" "愛知" 100)))
                                             )
                                    (testing " When 富山まで荷物を発送する; Then 運賃は 1330 円\n"
                                             (is (= 1330 (ucp/calculate_delivery_price "千葉" "富山" 100)))
                                             )
                                    (testing " When 大阪まで荷物を発送する; Then 運賃は 1440 円\n"
                                             (is (= 1440 (ucp/calculate_delivery_price "千葉" "大阪" 100)))
                                             )
                                    (testing " When 徳島まで荷物を発送する; Then 運賃は 1560 円\n"
                                             (is (= 1560 (ucp/calculate_delivery_price "千葉" "徳島" 100)))
                                             )
                                    (testing " When 山口まで荷物を発送する; Then 運賃は 1560 円\n"
                                             (is (= 1560 (ucp/calculate_delivery_price "千葉" "山口" 100)))
                                             )
                                    (testing " When 大分まで荷物を発送する; Then 運賃は 1760 円\n"
                                             (is (= 1760 (ucp/calculate_delivery_price "千葉" "大分" 100)))
                                             )
                                    (testing " When 沖縄まで荷物を発送する; Then 運賃は 1900 円\n"
                                             (is (= 1900 (ucp/calculate_delivery_price "千葉" "沖縄" 100)))
                                             )
                                    )
                           )
                  (testing "'長野'からの通常運賃\n"
                           (testing "Given 地域毎にサイズ120cmの荷物を'長野'から運送運賃を計算する\n"
                                    (testing " When 長野まで荷物を発送する; Then 運賃は 1530 円\n"
                                             (is (= 1530 (ucp/calculate_delivery_price "長野" "長野" 120)))
                                             )
                                    (testing " When 青森まで荷物を発送する; Then 運賃は 1590 円\n"
                                             (is (= 1590 (ucp/calculate_delivery_price "長野" "青森" 120)))
                                             )
                                    (testing " When 北海道まで荷物を発送する; Then 運賃は 2020 円\n"
                                             (is (= 2020 (ucp/calculate_delivery_price "長野" "北海道" 120)))
                                             )
                                    (testing " When 東京まで荷物を発送する; Then 運賃は 1590 円\n"
                                             (is (= 1590 (ucp/calculate_delivery_price "長野" "東京" 120)))
                                             )
                                    (testing " When 新潟まで荷物を発送する; Then 運賃は 1590 円\n"
                                             (is (= 1590 (ucp/calculate_delivery_price "長野" "新潟" 120)))
                                             )
                                    (testing " When 愛知まで荷物を発送する; Then 運賃は 1590 円\n"
                                             (is (= 1590 (ucp/calculate_delivery_price "長野" "愛知" 120)))
                                             )
                                    (testing " When 富山まで荷物を発送する; Then 運賃は 1590 円\n"
                                             (is (= 1590 (ucp/calculate_delivery_price "長野" "富山" 120)))
                                             )
                                    (testing " When 大阪まで荷物を発送する; Then 運賃は 1690 円\n"
                                             (is (= 1690 (ucp/calculate_delivery_price "長野" "大阪" 120)))
                                             )
                                    (testing " When 徳島まで荷物を発送する; Then 運賃は 1800 円\n"
                                             (is (= 1800 (ucp/calculate_delivery_price "長野" "徳島" 120)))
                                             )
                                    (testing " When 山口まで荷物を発送する; Then 運賃は 1800 円\n"
                                             (is (= 1800 (ucp/calculate_delivery_price "長野" "山口" 120)))
                                             )
                                    (testing " When 大分まで荷物を発送する; Then 運賃は 2020 円\n"
                                             (is (= 2020 (ucp/calculate_delivery_price "長野" "大分" 120)))
                                             )
                                    (testing " When 沖縄まで荷物を発送する; Then 運賃は 2270 円\n"
                                             (is (= 2270 (ucp/calculate_delivery_price "長野" "沖縄" 120)))
                                             )
                                    )
                           )
                  (testing "'三重'からの通常運賃\n"
                           (testing "Given 地域毎にサイズ140cmの荷物を'三重'から運送運賃を計算する\n"
                                    (testing " When 三重まで荷物を発送する; Then 運賃は 1780 円\n"
                                             (is (= 1780 (ucp/calculate_delivery_price "三重" "三重" 140)))
                                             )
                                    (testing " When 青森まで荷物を発送する; Then 運賃は 1950 円\n"
                                             (is (= 1950 (ucp/calculate_delivery_price "三重" "青森" 140)))
                                             )
                                    (testing " When 北海道まで荷物を発送する; Then 運賃は 2390 円\n"
                                             (is (= 2390 (ucp/calculate_delivery_price "三重" "北海道" 140)))
                                             )
                                    (testing " When 東京まで荷物を発送する; Then 運賃は 1830 円\n"
                                             (is (= 1830 (ucp/calculate_delivery_price "三重" "東京" 140)))
                                             )
                                    (testing " When 新潟まで荷物を発送する; Then 運賃は 1830 円\n"
                                             (is (= 1830 (ucp/calculate_delivery_price "三重" "新潟" 140)))
                                             )
                                    (testing " When 愛知まで荷物を発送する; Then 運賃は 1830 円\n"
                                             (is (= 1830 (ucp/calculate_delivery_price "三重" "愛知" 140)))
                                             )
                                    (testing " When 富山まで荷物を発送する; Then 運賃は 1830 円\n"
                                             (is (= 1830 (ucp/calculate_delivery_price "三重" "富山" 140)))
                                             )
                                    (testing " When 大阪まで荷物を発送する; Then 運賃は 1830 円\n"
                                             (is (= 1830 (ucp/calculate_delivery_price "三重" "大阪" 140)))
                                             )
                                    (testing " When 徳島まで荷物を発送する; Then 運賃は 1950 円\n"
                                             (is (= 1950 (ucp/calculate_delivery_price "三重" "徳島" 140)))
                                             )
                                    (testing " When 山口まで荷物を発送する; Then 運賃は 1950 円\n"
                                             (is (= 1950 (ucp/calculate_delivery_price "三重" "山口" 140)))
                                             )
                                    (testing " When 大分まで荷物を発送する; Then 運賃は 2060 円\n"
                                             (is (= 2060 (ucp/calculate_delivery_price "三重" "大分" 140)))
                                             )
                                    (testing " When 沖縄まで荷物を発送する; Then 運賃は 2440 円\n"
                                             (is (= 2440 (ucp/calculate_delivery_price "三重" "沖縄" 140)))
                                             )
                                    )
                           )
                  (testing "'福井'からの通常運賃\n"
                           (testing "Given 地域毎にサイズ160cmの荷物を'福井'から運送運賃を計算する\n"
                                    (testing " When 福井まで荷物を発送する; Then 運賃は 2010 円\n"
                                             (is (= 2010 (ucp/calculate_delivery_price "福井" "福井" 160)))
                                             )
                                    (testing " When 青森まで荷物を発送する; Then 運賃は 2160 円\n"
                                             (is (= 2160 (ucp/calculate_delivery_price "福井" "青森" 160)))
                                             )
                                    (testing " When 北海道まで荷物を発送する; Then 運賃は 2610 円\n"
                                             (is (= 2610 (ucp/calculate_delivery_price "福井" "北海道" 160)))
                                             )
                                    (testing " When 東京まで荷物を発送する; Then 運賃は 2060 円\n"
                                             (is (= 2060 (ucp/calculate_delivery_price "福井" "東京" 160)))
                                             )
                                    (testing " When 新潟まで荷物を発送する; Then 運賃は 2060 円\n"
                                             (is (= 2060 (ucp/calculate_delivery_price "福井" "新潟" 160)))
                                             )
                                    (testing " When 愛知まで荷物を発送する; Then 運賃は 2060 円\n"
                                             (is (= 2060 (ucp/calculate_delivery_price "福井" "愛知" 160)))
                                             )
                                    (testing " When 富山まで荷物を発送する; Then 運賃は 2060 円\n"
                                             (is (= 2060 (ucp/calculate_delivery_price "福井" "富山" 160)))
                                             )
                                    (testing " When 大阪まで荷物を発送する; Then 運賃は 2060 円\n"
                                             (is (= 2060 (ucp/calculate_delivery_price "福井" "大阪" 160)))
                                             )
                                    (testing " When 徳島まで荷物を発送する; Then 運賃は 2160 円\n"
                                             (is (= 2160 (ucp/calculate_delivery_price "福井" "徳島" 160)))
                                             )
                                    (testing " When 山口まで荷物を発送する; Then 運賃は 2160 円\n"
                                             (is (= 2160 (ucp/calculate_delivery_price "福井" "山口" 160)))
                                             )
                                    (testing " When 大分まで荷物を発送する; Then 運賃は 2270 円\n"
                                             (is (= 2270 (ucp/calculate_delivery_price "福井" "大分" 160)))
                                             )
                                    (testing " When 沖縄まで荷物を発送する; Then 運賃は 2770 円\n"
                                             (is (= 2770 (ucp/calculate_delivery_price "福井" "沖縄" 160)))
                                             )
                                    )
                           )
                  (testing "'兵庫'からの通常運賃\n"
                           (testing "Given 地域毎にサイズ170cmの荷物を'兵庫'から運送運賃を計算する\n"
                                    (testing " When 兵庫まで荷物を発送する; Then 運賃は 2340 円\n"
                                             (is (= 2340 (ucp/calculate_delivery_price "兵庫" "兵庫" 170)))
                                             )
                                    (testing " When 青森まで荷物を発送する; Then 運賃は 2640 円\n"
                                             (is (= 2640 (ucp/calculate_delivery_price "兵庫" "青森" 170)))
                                             )
                                    (testing " When 北海道まで荷物を発送する; Then 運賃は 3100 円\n"
                                             (is (= 3100 (ucp/calculate_delivery_price "兵庫" "北海道" 170)))
                                             )
                                    (testing " When 東京まで荷物を発送する; Then 運賃は 2530 円\n"
                                             (is (= 2530 (ucp/calculate_delivery_price "兵庫" "東京" 170)))
                                             )
                                    (testing " When 新潟まで荷物を発送する; Then 運賃は 2530 円\n"
                                             (is (= 2530 (ucp/calculate_delivery_price "兵庫" "新潟" 170)))
                                             )
                                    (testing " When 愛知まで荷物を発送する; Then 運賃は 2410 円\n"
                                             (is (= 2410 (ucp/calculate_delivery_price "兵庫" "愛知" 170)))
                                             )
                                    (testing " When 富山まで荷物を発送する; Then 運賃は 2410 円\n"
                                             (is (= 2410 (ucp/calculate_delivery_price "兵庫" "富山" 170)))
                                             )
                                    (testing " When 大阪まで荷物を発送する; Then 運賃は 2410 円\n"
                                             (is (= 2410 (ucp/calculate_delivery_price "兵庫" "大阪" 170)))
                                             )
                                    (testing " When 徳島まで荷物を発送する; Then 運賃は 2410 円\n"
                                             (is (= 2410 (ucp/calculate_delivery_price "兵庫" "徳島" 170)))
                                             )
                                    (testing " When 山口まで荷物を発送する; Then 運賃は 2410 円\n"
                                             (is (= 2410 (ucp/calculate_delivery_price "兵庫" "山口" 170)))
                                             )
                                    (testing " When 大分まで荷物を発送する; Then 運賃は 2530 円\n"
                                             (is (= 2530 (ucp/calculate_delivery_price "兵庫" "大分" 170)))
                                             )
                                    (testing " When 沖縄まで荷物を発送する; Then 運賃は 3060 円\n"
                                             (is (= 3060 (ucp/calculate_delivery_price "兵庫" "沖縄" 170)))
                                             )
                                    )
                           )
                  (testing "'熊本'からの通常運賃\n"
                           (testing "Given 地域毎にサイズ60cmの荷物を'熊本'から運送運賃を計算する\n"
                                    (testing " When 熊本まで荷物を発送する; Then 運賃は 810 円\n"
                                             (is (= 810 (ucp/calculate_delivery_price "熊本" "熊本" 60)))
                                             )
                                    (testing " When 青森まで荷物を発送する; Then 運賃は 1540 円\n"
                                             (is (= 1540 (ucp/calculate_delivery_price "熊本" "青森" 60)))
                                             )
                                    (testing " When 北海道まで荷物を発送する; Then 運賃は 1540 円\n"
                                             (is (= 1540 (ucp/calculate_delivery_price "熊本" "北海道" 60)))
                                             )
                                    (testing " When 東京まで荷物を発送する; Then 運賃は 1300 円\n"
                                             (is (= 1300 (ucp/calculate_delivery_price "熊本" "東京" 60)))
                                             )
                                    (testing " When 新潟まで荷物を発送する; Then 運賃は 1300 円\n"
                                             (is (= 1300 (ucp/calculate_delivery_price "熊本" "新潟" 60)))
                                             )
                                    (testing " When 愛知まで荷物を発送する; Then 運賃は 1100 円\n"
                                             (is (= 1100 (ucp/calculate_delivery_price "熊本" "愛知" 60)))
                                             )
                                    (testing " When 富山まで荷物を発送する; Then 運賃は 1100 円\n"
                                             (is (= 1100 (ucp/calculate_delivery_price "熊本" "富山" 60)))
                                             )
                                    (testing " When 大阪まで荷物を発送する; Then 運賃は 970 円\n"
                                             (is (= 970 (ucp/calculate_delivery_price "熊本" "大阪" 60)))
                                             )
                                    (testing " When 徳島まで荷物を発送する; Then 運賃は 970 円\n"
                                             (is (= 970 (ucp/calculate_delivery_price "熊本" "徳島" 60)))
                                             )
                                    (testing " When 山口まで荷物を発送する; Then 運賃は 870 円\n"
                                             (is (= 870 (ucp/calculate_delivery_price "熊本" "山口" 60)))
                                             )
                                    (testing " When 大分まで荷物を発送する; Then 運賃は 870 円\n"
                                             (is (= 870 (ucp/calculate_delivery_price "熊本" "大分" 60)))
                                             )
                                    (testing " When 沖縄まで荷物を発送する; Then 運賃は 1030 円\n"
                                             (is (= 1030 (ucp/calculate_delivery_price "熊本" "沖縄" 60)))
                                             )
                                    )
                           )
                  )
         )


(deftest ^:user001 test-delivery-price-by-Scenario-2
         (testing "Scenario-2 サイズエラーの確認\n"
                  (binding [ucp/area_map (atom (json/read-str (slurp "index.json")))
                            ucp/area_map_2 (atom (json/read-str (slurp "table.json")))]
                           (testing " When 180cm以上の荷物を東京から熊本まで発送する; Then エラーメッセージは'荷物のサイズは170cmまで受付できます。'\n"
                                    (is (= "荷物のサイズは170cmまで受付できます。" (:error (ucp/calculate_delivery_price "東京" "熊本" 180))))
                                    )
                           (testing " When サイズにcmが付いたら; Then エラーメッセージは'サイズの入力は数字のみ、または数字のみあるの文字列ができます。'\n"
                                    (is (= "サイズの入力は数字のみ、または数字のみあるの文字列ができます。" (:error (ucp/calculate_delivery_price "東京" "熊本" "120cm"))))
                                    )
                           )
                  )
         )

(deftest ^:user001 test-delivery-price-by-Scenario-3
         (testing "Scenario-3 サイズの入力は文字列も可能の確認\n"
                  (binding [ucp/area_map (atom (json/read-str (slurp "index.json")))
                            ucp/area_map_2 (atom (json/read-str (slurp "table.json")))]
                           (testing " When '60cm'のサイズを入力されて、東京から熊本まで発送する; Then 運賃は1300円'\n"
                                    (is (= 1300 (ucp/calculate_delivery_price "東京" "熊本" "60")))
                                    )
                           )
                  )
         )

(deftest ^:user002 test-best-price-for-multiple-packet
         (testing "複数荷物を同梱して一番安い料金の確認\n"
                  (binding [ucp/area_map (atom (json/read-str (slurp "index.json")))
                            ucp/area_map_2 (atom (json/read-str (slurp "table.json")))]
                           (testing "荷物は 30x20x20と20x10x18を東京から熊本へ; Then 運賃は(80cm)1530円'\n"
                                    (is (= 1530 (ucp/calculate_best_price "東京" "熊本" [[30 20 20] [20 10 18]])))
                                    )
                           (testing "荷物は 30x20x20と10x10x18と10x3x20を東京から熊本へ; Then 運賃は(100cm)1760円'\n"
                                    (is (= 1760 (ucp/calculate_best_price "東京" "熊本" [[30 20 20] [10 10 18] [10 3 20]])))
                                    )
                           (testing "荷物は 30x20x20と10x10x18と10x3x20と60x5x5を東京から熊本へ; Then 運賃は(120cm) 2260 円'\n"
                                    (is (= 2020 (ucp/calculate_best_price "東京" "熊本" [[30 20 20] [10 10 18] [10 3 20] [60 5 5]])))
                                    )
                           (testing "荷物は 30x20x20と20x10x20と10x8x8、8x8x12を東京から熊本へ; Then 運賃は(80cm)1530円'\n"
                                    (is (= 1530 (ucp/calculate_best_price "東京" "熊本" [[30 20 20] [10 10 18] [10 3 20] [60 5 5]])))
                                    )
                           )
                  )
         )

(deftest ^:user002 test-best-price-for-big-packet
         (testing "同梱サイズ170cm以上なら複数へ分けるの確認\n"
                  (binding [ucp/area_map (atom (json/read-str (slurp "index.json")))
                            ucp/area_map_2 (atom (json/read-str (slurp "table.json")))]
                           (testing "荷物は 90x50x15と80x50x20を東京から熊本へ; Then 運賃は(160+160cm) 4980円'\n"
                                    (is (= 4980 (ucp/calculate_best_price "東京" "熊本" [[90 50 15] [80 50 20]])))
                                    )
                           (testing "荷物は 40x40x40と100x5x5を東京から熊本へ; Then 運賃は(160cm+120) 4510 円'\n"
                                    (is (= 4510 (ucp/calculate_best_price "東京" "熊本" [[40 40 40] [100 5 5]])))
                                    )
                           (testing "荷物は 80x40x20と80x40x20と80x40x20を東京から熊本へ; Then 運賃は(160+140cm) 4750 円'\n"
                                    (is (= 4750 (ucp/calculate_best_price "東京" "熊本" [[30 20 20] [10 10 18] [10 3 20] [10 3 20]])))
                                    )
                           )
                  )
         )
