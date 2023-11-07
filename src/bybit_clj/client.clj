(ns bybit-clj.client
  (:require [aleph.http :as http]
            [clj-commons.byte-streams :as bs]
            [clojure.string :as str]
            [manifold.deferred :as d]
            [pjson.core :as json]
            [ring.util.codec :refer [form-encode]]
            [bybit-clj.auth :as auth]
            [bybit-clj.utils :as utils]))

(defn- build-base-request
  [method url]
  {:request-method method
   :url url})

(defn build-get-request
  [client endpoint]
  (build-base-request :get (utils/create-full-url client endpoint)))

(defn build-post-request
  ([client endpoint body]
   (build-post-request client endpoint body {}))
  ([client endpoint body opts]
   (merge (build-base-request :post (utils/create-full-url client endpoint))
          {:body (json/write-str body) :content-type :json}
          opts)))

(defn send-request
  "Takes in a request, sends the http request, and returns the body of the response."
  [request]
  (d/chain (http/request request)
           :body
           bs/to-string
           json/read-str))

(defn send-signed-request
  "Wrapper for sign-request with send-request"
  [client request]
  (->> (auth/sign-request client request)
       (#(d/chain (http/request %)
                  :body
                  bs/to-string
                  json/read-str))))

(defn append-query-params
  [query-params request]
  (if (empty? query-params)
    request
    (update-in request [:url]
               #(str %
                     (if (str/includes? % "?") "&" "?")
                     (form-encode query-params)))))