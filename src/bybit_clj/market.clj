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

(def market-rest-url
  "The rest URL for Bybit V5"
  "https://api.bybit.com/v5/market")

(defn get-time
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/time)"
  [client]
  (utils/get-request (str (:url client) "/time") nil))

(defn get-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/kline)"
  [client category symbol interval opts]
  (->>
   (merge {:category category :symbol symbol :interval interval} opts)
   (utils/get-request (str (:url client) "/kline"))))

(defn get-mark-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/mark-kline)"
  [client category symbol interval opts]
  (->>
   (merge {:category category :symbol symbol :interval interval} opts)
   (utils/get-request (str (:url client) "/mark-price-kline"))))

(defn get-index-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/index-kline)"
  [client category symbol interval opts]
  (->>
   (merge {:category category :symbol symbol :interval interval} opts)
   (utils/get-request (str (:url client) "/index-price-kline"))))

(defn get-premium-index-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/preimum-index-kline)"
  [client category symbol interval opts]
  (->>
   (merge {:category category :symbol symbol :interval interval} opts)
   (utils/get-request (str (:url client) "/premium-index-price-kline"))))

(defn get-instruments-info
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/instrument)"
  [client category opts]
  (->>
   (merge {:category category} opts)
   (utils/get-request (str (:url client) "/instruments-info"))))

(defn get-orderbook
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/orderbook)"
  [client category symbol opts]
  (->>
   (merge {:category category :symbol symbol} opts)
   (utils/get-request (str (:url client) "/orderbook"))))

(defn get-tickers
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/tickers)"
  [client category symbol opts]
  (->>
   (merge {:category category :symbol symbol} opts)
   (utils/get-request (str (:url client) "/tickers"))))

(defn get-funding-history
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/history-fund-rate)"
  [client category symbol opts]
  (->>
   (merge {:category category :symbol symbol} opts)
   (utils/get-request (str (:url client) "/funding/history"))))

(defn get-recent-trades
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/recent-trade)"
  [client category symbol opts]
  (->>
   (merge {:category category :symbol symbol} opts)
   (utils/get-request (str (:url client) "/recent-trade"))))

(defn get-open-interest
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/open-interest)"
  [client category symbol interval-time opts]
  (->>
   (merge {:category category :symbol symbol :intervalTime interval-time} opts)
   (utils/get-request (str (:url client) "/open-interest"))))

(defn get-risk-limit
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/risk-limit)"
  ([client category]
   (utils/get-request (str (:url client) "/risk-limit") {:category category}))
  ([client category symbol]
   (utils/get-request (str (:url client) "/risk-limit") {:category category :symbol symbol})))

(defn get-delivery-price
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/delivery-price)"
  [client category opts]
  (->> (merge {:category category} opts)
       (utils/get-request (str (:url client) "/delivery-price"))))

(defn get-long-short-ratio
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/long-short-ratio)
   Linear only has USDT perps
   "
  ([client category symbol period]
   (get-long-short-ratio client category symbol period 50))
  ([client category symbol period limit]
   (->>
    {:category category :symbol symbol :period period :limit limit}
    (utils/get-request (str (:url client) "/account-ratio")))))