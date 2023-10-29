(ns bybit-clj.market
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
  (utils/get-request (str (:url client) "/market/time") nil))

(defn get-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/kline)"
  [client {:keys [category symbol interval start end limit]}]
  (->>
   {:category category :symbol symbol :interval interval :start start :end end :limit limit}
   (utils/get-request (str (:url client) "/market/kline"))))

(defn get-mark-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/mark-kline)"
  [client {:keys [category symbol interval start end limit]}]
  (->>
   {:category category :symbol symbol :interval interval :start start :end end :limit limit}
   (utils/get-request (str (:url client) "/market/mark-price-kline"))))

(defn get-index-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/index-kline)"
  [client {:keys [category symbol interval start end limit]}]
  (->>
   {:category category :symbol symbol :interval interval :start start :end end :limit limit}
   (utils/get-request (str (:url client) "/market/index-price-kline"))))

(defn get-premium-index-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/preimum-index-kline)"
  [client {:keys [category symbol interval start end limit]}]
  (->>
   {:category category :symbol symbol :interval interval :start start :end end :limit limit}
   (utils/get-request (str (:url client) "/market/premium-index-price-kline"))))

(defn get-instruments-info
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/instrument)"
  [client {:keys [category symbol status base-coin limit cursor]}]
  (->>
   {:category category :symbol symbol :status status :baseCoin base-coin :limit limit :cursor cursor}
   (utils/get-request (str (:url client) "/market/instruments-info"))))
