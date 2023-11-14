(ns bybit-clj.auth
  (:require [bybit-clj.utils :as utils]
            [buddy.core.mac :as mac]
            [buddy.core.codecs :as codecs]))

(defn- create-prehash-string
  [client timestamp request]
  (str timestamp
       (:key client)
       (if (:recv-window client) (:recv-window client) "5000")
       (utils/parse-request-params (:url request)) (:body request)))

(defn- create-http-signature
  [client timestamp request]
  (let [secret-decoded (:secret client)
        prehash-string (create-prehash-string client timestamp request)
        hmac (mac/hash prehash-string {:key secret-decoded :alg :hmac+sha256})]
    (codecs/bytes->hex hmac)))

(defn sign-request
  [client request]
  (let [timestamp (str (utils/get-timestamp))
        recv-w (if (:recv-window client) (:recv-window client) "5000")]
    (update-in request [:headers] merge {"Content-Type" "application/json"
                                         "X-BAPI-SIGN" (create-http-signature client timestamp request)
                                         "X-BAPI-API-KEY" (:key client)
                                         "X-BAPI-TIMESTAMP" timestamp
                                         "X-BAPI-RECV-WINDOW" recv-w})))

(defn- create-websocket-signature
  [secret timestamp]
  (let [prehash-string (str "GET/realtime" (timestamp + 5000))
        hmac (mac/hash prehash-string {:key secret :alg :hmac+sha256})]
    (codecs/bytes->hex hmac)))

(defn sign-message
  [message {:keys [key secret]}]
  (let [timestamp (utils/get-timestamp)]
    (merge message
           {:key key
            :timestamp timestamp
            :signature (create-websocket-signature secret timestamp)})))