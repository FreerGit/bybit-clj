(ns bybit-clj.ws
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/ws/connect)  
   Module for websocket connections such as trade feeds, orderbook updates, liqudation feeds and so on."
  (:gen-class)
  (:require
   [bybit-clj.auth :as auth]
   [bybit-clj.utils :as utils]
   [pjson.core :as json]
   [gniazdo.core :as ws])
  (:import [org.eclipse.jetty.util.ssl SslContextFactory]
           [org.eclipse.jetty.websocket.client WebSocketClient]))

(set! *warn-on-reflection* true)

(defn- create-on-receive
  "Returns a function that returns nil if user-on-receive is nil, otherwise returns a function 
  that takes the received message, parses the json and turns it into a map, then passes it to the user-on-receive function"
  [user-on-receive]
  (if (nil? user-on-receive)
    (constantly nil)
    (fn [msg]
      (-> msg
          json/read-str
          user-on-receive))))

(defn- get-socket-connection
  "Creates the socket client using the Java WebSocketClient and SslContextFactory, starts the client,
  then connects it to the websocket URL."
  [opts]
  (let [client (WebSocketClient. (SslContextFactory.))]
    (.setMaxTextMessageSize (.getPolicy client) (* 1024 1024))
    (.start client)
    (ws/connect
     (:url opts)
     :client client
     :on-connect (or (:on-connect opts) (constantly nil))
     :on-receive (create-on-receive (:on-receive opts))
     :on-close (or (:on-close opts) (constantly nil))
     :on-error (or (:on-error opts) (constantly nil)))))

(defn- create-subscribe-message
  "Creates the subscribe message
   "
  [opts]
  (let [message {:op "subscribe" :args (:channels opts)}]
    message))

(defn- create-unsubscribe-message
  "Creates the unsubscribe message."
  [opts]
  (let [message {:op "unsubscribe" :args (:channels opts)}]
    message))

(defn subscribe
  "[API docs](https://bybit-exchange.github.io/docs/v5/ws/connect)

- `connection` is created with [[create-websocket-connection]]
- `opts` is a map with the following keys:
    - `:channels` - vector of subscription strings eg. `orderbook.50.BTCUSDT`
    - `:key` - optional - your Bybit API key
    - `:secret` - optional - your Bybit API key

```clojure
(subscribe connection {:channels [\"orderbook.50.BTCUSDT\"]})
```"
  [connection opts]
  (->> (create-subscribe-message opts)
       json/write-str
       (ws/send-msg connection)))

(defn- send-auth
  [connection message]
  (->> (json/write-str message)
       (ws/send-msg connection)))

(defn unsubscribe
  "[API docs](https://bybit-exchange.github.io/docs/v5/ws/connect)

- `connection` is created with [[create-websocket-connection]].
- `opts` takes the equivalent shape as [[subscribe]].

```clojure
(unsubscribe connection {:channels [\"orderbook.50.BTCUSDT\"]})
```"
  [connection opts]
  (->> (create-unsubscribe-message opts)
       json/write-str
       (ws/send-msg connection)))

(defn close
  "`connection` is created with [[create-websocket-connection]]."
  [connection]
  (ws/close connection))

(defn create-websocket-connection
  "[API docs](https://bybit-exchange.github.io/docs/v5/ws/connect)

`opts` is a map that takes the following keys:

- `:url` - the websocket URL
- `:channels` -  vector of subscription strings eg. `orderbook.50.BTCUSDT`
- `:key` - optional - your Bybit API key
- `:secret` - optional - your Bybit API secret
- `:on-connect` - optional - A unary function called after the connection has been established. The argument is a [WebSocketSession](https://www.eclipse.org/jetty/javadoc/9.4.8.v20171121/org/eclipse/jetty/websocket/common/WebSocketSession.html).
- `:on-error` - optional - A unary function called in case of errors. The argument is a `Throwable` describing the error.
- `:on-close` - optional - A binary function called when the connection is closed. Arguments are an `int` status code and a `String` description of reason.    

```clojure
(def conn (create-websocket-connection 
   {:url \"wss://stream.bybit.com/v5/public/spot\"
    :channels [\"orderbook.50.BTCUSDT\" \"publicTrade.BTCUSDT\"]
    :on-receive (fn [x] (prn x))}))

(def conn (create-websocket-connection 
   {:url \"wss://stream.bybit.com/v5/private\"
    :key \"invaluable-secret-key\"
    :secret \"my-super-secret-secret\"
    :channels [\"execution\"]
    :on-receive (fn [x] (prn x))}))
```"
  [opts]
  (let [connection (get-socket-connection opts)]
    (when (utils/contains-many? opts :key :secret)
      (->>
       (auth/create-auth-message opts)
       (send-auth connection)))
    (subscribe connection opts)
    connection)) 