(ns bybit-clj.core
  "Public and private endpoint functions and websocket feed . In all function signatures, `client` is a map with the following keys:
- `:url` - rest URL
- `:key` - optional - your Bybit API key
- `:secret` - optional - your Bybit API key
  
  The library is design to easily swap out testnet for mainnet via `url` in `client`

  Remember not to store your `key` and `secret` in a public repo."
  (:gen-class)
  (:require [bybit-clj.utils :as utils]
            [aleph.http :as http]))

(def rest-url
  "The rest URL for Bybit V5"
  "https://api.bybit.com/v5")


(defn get-time
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/time)"
  [client]
  (-> (str (:url client) "/market/time")
      (utils/get-request)))

(defn get-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/kline)
   "
  [client {:keys [category symbol interval start end limit]}]
  (->>
   (str (:url client) "/market/kline?" "category=" category "&symbol=" symbol "&interval=" interval)
   (#(when (and start end) (str % "&start=" start "&start=" end)))
   (#(when limit (str % "&limit=" limit)))
   (utils/get-request)))

