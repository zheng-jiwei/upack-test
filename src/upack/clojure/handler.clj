(ns upack.clojure.handler
    (:require [compojure.core :refer :all]
      [compojure.route :as route]
      [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
      [clojure.data.json :as json]
      [upack.clojure.process :as ucp]
      [clojure.java.io :as io]
      [ring.util.response :as response]
      )
    )

(defn get-store-config [handle]
	(fn[request]
		(when (empty? @ucp/area_map)
			(reset! ucp/area_map (json/read-str (slurp "index.json")))
			)
		(handle request)
		)
)

(defroutes app-routes
  (GET "/provinces" request (ucp/get-all-province request))
  (GET "/box/sizes" request (ucp/get-all-box-sizes request))
  (GET "/*" request (response/response (io/file (str "resources" (:uri request)))))
  (POST "/calculate" request (ucp/process-delivery-price request))
  (route/not-found "Not Found :(")
 )

(def app
  (-> app-routes
       get-store-config
      (wrap-defaults (-> site-defaults
                         (assoc-in [:params :multipart] true)
                         (assoc-in [:security :anti-forgery] false))))
  )
