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

(set! *warn-on-reflection* true)

(def rest-url
  "The rest URL for Bybit V5"
  "https://api.bybit.com/v5")


(defn get-time
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/time)"
  [client]
  (utils/get-request (str (:url client) "/market/time") nil))

(defn get-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/kline)"
  [client category symbol interval opts]
  (->>
   (merge {:category category :symbol symbol :interval interval} opts)
   (utils/get-request (str (:url client) "/market/kline"))))

(defn get-mark-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/mark-kline)"
  [client category symbol interval opts]
  (->>
   (merge {:category category :symbol symbol :interval interval} opts)
   (utils/get-request (str (:url client) "/market/mark-price-kline"))))

(defn get-index-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/index-kline)"
  [client category symbol interval opts]
  (->>
   (merge {:category category :symbol symbol :interval interval} opts)
   (utils/get-request (str (:url client) "/market/index-price-kline"))))

(defn get-premium-index-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/preimum-index-kline)"
  [client category symbol interval opts]
  (->>
   (merge {:category category :symbol symbol :interval interval} opts)
   (utils/get-request (str (:url client) "/market/premium-index-price-kline"))))

(defn get-instruments-info
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/instrument)"
  [client category opts]
  (->>
   (merge {:category category} opts)
   (utils/get-request (str (:url client) "/market/instruments-info"))))

(defn get-orderbook
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/orderbook)"
  [client category symbol opts]
  (->>
   (merge {:category category :symbol symbol} opts)
   (utils/get-request (str (:url client) "/market/orderbook"))))

(defn get-tickers
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/tickers)"
  [client category symbol opts]
  (->>
   (merge {:category category :symbol symbol} opts)
   (utils/get-request (str (:url client) "/market/tickers"))))

(defn get-funding-history
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/history-fund-rate)"
  [client category symbol opts]
  (->>
   (merge {:category category :symbol symbol} opts)
   (utils/get-request (str (:url client) "/market/funding/history"))))


