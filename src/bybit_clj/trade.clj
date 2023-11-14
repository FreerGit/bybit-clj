(ns bybit-clj.trade
  "Public and private endpoint functions and websocket feed . In all function signatures, `client` is a map with the following keys:
        - `:key` - optional - your Bybit API key.
        - `:secret` - optional - your Bybit API secret.
        - `:recv-window` - optional - how long an HTTP request is valid, in milliseconds.
          
          The library is design to easily swap out testnet for mainnet via `url` in `client`
        
          Remember not to store your `key` and `secret` in a public repo."
  (:require [bybit-clj.client :as client]
            [bybit-clj.trade :as trade]))

(set! *warn-on-reflection* true)

(def url-trade
  "The rest URL for Bybit V5 trade"
  "https://api.bybit.com/v5")

(def url-trade-testnet
  "The rest URL for Bybit V5 trade testnet"
  "https://api-testnet.bybit.com/v5")

(defn place-order
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/order/create-order)
   
   Read the docs, otherwise this function will make no sense!"
  [client category symbol side order-type qty opts]
  (->> (client/build-post-request client "/order/create"
                                  (merge {:category category :symbol symbol :side side
                                          :orderType order-type :qty qty}
                                         opts))
       (client/send-signed-request client)))

(defn amend-order
  "[API DOCS](https:https://bybit-exchange.github.io/docs/v5/order/amend-order)
   
   Read the docs, otherwise this function will make no sense!"
  [client category symbol opts]
  (->> (client/build-post-request client "/order/amend"
                                  (merge {:category category :symbol symbol}
                                         opts))
       (client/send-signed-request client)))

(defn cancel-order
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/order/cancel-order)
   
   Read the docs, otherwise this function will make no sense!"
  [client category symbol opts]
  (->> (client/build-post-request client "/order/cancel"
                                  (merge {:category category :symbol symbol}
                                         opts))
       (client/send-signed-request client)))

(defn get-open-orders
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/order/open-order)
   
   Read the docs, otherwise this function will make no sense!"
  [client category opts]
  (->> (client/build-get-request client "/order/realtime")
       (client/append-query-params (merge {:category category} opts))
       (client/send-signed-request client)))

(defn cancel-all-orders
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/order/cancel-all)
   
   Read the docs, otherwise this function will make no sense!"
  [client category opts]
  (->> (client/build-post-request client "/order/cancel-all"
                                  (merge {:category category}
                                         opts))
       (client/send-signed-request client)))
