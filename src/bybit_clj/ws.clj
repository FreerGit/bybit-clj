(ns bybit-clj.ws
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/ws/connect)  
   Module for websocket connections such as trade feeds, orderbook updates, liqudation feeds and so on."
  (:gen-class)
  (:require [bybit-clj.auth :as auth]
            [bybit-clj.utils :as utils]
            [pjson.core :as json]))

(set! *warn-on-reflection* true)

(def sub "{
    \"op\": \"subscribe\",
    \"args\": [\"orderbook.50.BTCUSDT\"]}")

(defn parse-message [^String message]
  (json/read-str message))

;; (defn idk []
;;   (let [conn @(http/websocket-client "wss://stream.bybit.com/v5/public/spot")]
;;     (s/put! conn sub)
;;     (prn "hh")
;;     (while true
;;       (do
;;         @(s/consume parse-message conn)))))

  ;;  Refer to [API DOCS](https://bybit-exchange.github.io/docs/v5/ws/connect) to construct channels

  ;;  - channels is a list of (string) streams to subscribe to, e.g `orderbook.50.BTCUSDT`

(defn- create-subscribe-message
  "Creates the subscribe message and signs the message if key and secret are provided.
   "
  [opts]

  (let [message {:op "subscribe" :args (:channels opts)}]
    (if (utils/contains-many? opts :key :secret)
      (auth/sign-message message opts)
      message)))
;; (defn- create-unsubscribe-message
;;   "Creates the unsubscribe message."
;;   [opts]
;;   (merge opts {:type "unsubscribe"}))

;; (defn subscribe
;;   "[API docs](https://docs.pro.coinbase.com/#subscribe)

;; - `connection` is created with [[create-websocket-connection]]
;; - `opts` is a map with the following keys:
;;     - `:product_ids` - either this or `:channels` or both must be provided (see the Coinbase Pro API docs) - a vector of strings
;;     - `:channels` - either this or `:product_ids` or both must be provided (see the Coinbase Pro API docs) - a vector of strings or maps with `:name` (string) and `:product_ids` (vector of strings)
;;     - `:key` - optional - your Coinbase Pro API key
;;     - `:secret` - optional - your Coinbase Pro API key
;;     - `:passphrase` - optional - your Coinbase Pro API key

;; ```clojure
;; (subscribe connection {:product_ids [\"BTC-USD\"]})
;; ```"
;;   [connection opts]
;;   (->> (create-subscribe-message opts)
;;        edn->json
;;        (ws/send-msg connection)))

;; (defn unsubscribe
;;   "[API docs](https://docs.pro.coinbase.com/#subscribe)

;; - `connection` is created with [[create-websocket-connection]].
;; - `opts` takes the equivalent shape as [[subscribe]].

;; ```clojure
;; (unsubscribe connection {:product_ids [\"BTC-USD\"]})
;; ```"
;;   [connection opts]
;;   (->> (create-unsubscribe-message opts)
;;        edn->json
;;        (ws/send-msg connection)))

;; (defn close
;;   "`connection` is created with [[create-websocket-connection]]."
;;   [connection]
;;   (ws/close connection))

;; (defn- create-on-receive
;;   "Returns a function that returns nil if user-on-receive is nil, otherwise returns a function 
;;   that takes the received message, converts it to edn, then passes it to the user-on-receive function"
;;   [user-on-receive]
;;   (if (nil? user-on-receive)
;;     (constantly nil)
;;     (fn [msg]
;;       (-> msg
;;           json->edn ; convert to edn before passing to user defined on-receive
;;           user-on-receive))))

;; (defn create-websocket-connection
;;   "[API docs](https://docs.pro.coinbase.com/#websocket-feed)

;; `opts` is a map that takes the following keys:

;; - `:url` - the websocket URL
;; - `:product_ids` - either this or `:channels` or both must be provided (see the Coinbase Pro API docs) - a vector of strings 
;; - `:channels` - either this or `:product_ids` or both must be provided (see the Coinbase Pro API docs) - a vector of strings or maps with `:name` (string) and `:product_ids` (vector of strings)
;; - `:key` - optional - your Coinbase Pro API key 
;; - `:secret` - optional - your Coinbase Pro API secret
;; - `:passphrase` - optional - your Coinbase Pro API passphrase
;; - `:on-receive` - optional - A unary function called when a message is received. The argument is received as edn.
;; - `:on-connect` - optional - A unary function called after the connection has been established. The argument is a [WebSocketSession](https://www.eclipse.org/jetty/javadoc/9.4.8.v20171121/org/eclipse/jetty/websocket/common/WebSocketSession.html).
;; - `:on-error` - optional - A unary function called in case of errors. The argument is a `Throwable` describing the error.
;; - `:on-close` - optional - A binary function called when the connection is closed. Arguments are an `int` status code and a `String` description of reason.    

;; ```clojure
;; (def conn (create-websocket-connection {:product_ids [\"BTC-USD\"]
;;                                         :url \"wss://ws-feed.pro.coinbase.com\"
;;                                         :on-receive (fn [x] (prn 'received x))}))
;; ```"
;;   [opts]
;;   (let [connection (get-socket-connection opts)]
;;     ;; subscribe immediately so the connection isn't lost
;;     (subscribe connection opts)
;;     connection))