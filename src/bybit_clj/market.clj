(ns bybit-clj.market
  "Public and private endpoint functions and websocket feed . In all function signatures, `client` is a map with the following keys:
- `:url` - rest URL
- `:key` - optional - your Bybit API key
- `:secret` - optional - your Bybit API key
  
  The library is design to easily swap out testnet for mainnet via `url` in `client`

  Remember not to store your `key` and `secret` in a public repo."
  (:gen-class)
  (:require [bybit-clj.utils :as utils]
            [bybit-clj.client :as client]))

(set! *warn-on-reflection* true)

(def market-rest-url
  "The rest URL for Bybit V5"
  "https://api.bybit.com/v5/market")

(defn get-time
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/time)"
  [client]
  (-> (str (:url client) "/time")
      client/build-get-request
      client/send-request))

(defn get-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/kline)"
  [client category symbol interval opts]
  (->> (client/build-get-request (str (:url client) "/kline"))
       (client/append-query-params
        (merge {:category category :symbol symbol :interval interval} opts))
       client/send-request))

(defn get-mark-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/mark-kline)"
  [client category symbol interval opts]
  (->> (client/build-get-request (str (:url client) "/mark-price-kline"))
       (client/append-query-params
        (merge {:category category :symbol symbol :interval interval} opts))
       client/send-request))

(defn get-index-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/index-kline)"
  [client category symbol interval opts]
  (->> (client/build-get-request (str (:url client) "/index-price-kline"))
       (client/append-query-params
        (merge {:category category :symbol symbol :interval interval} opts))
       client/send-request))

(defn get-premium-index-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/preimum-index-kline)"
  [client category symbol interval opts]
  (->> (client/build-get-request (str (:url client) "/premium-index-price-kline"))
       (client/append-query-params
        (merge {:category category :symbol symbol :interval interval} opts))
       (client/send-request)))

(defn get-instruments-info
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/instrument)"
  [client category opts]
  (->> (client/build-get-request (str (:url client) "/instruments-info"))
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-orderbook
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/orderbook)"
  [client category symbol opts]
  (->> (client/build-get-request (str (:url client) "/orderbook"))
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-tickers
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/tickers)"
  [client category symbol opts]
  (->> (client/build-get-request (str (:url client) "/tickers"))
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-funding-history
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/history-fund-rate)"
  [client category symbol opts]
  (->> (client/build-get-request (str (:url client) "/funding/history"))
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-recent-trades
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/recent-trade)"
  [client category symbol opts]
  (->> (client/build-get-request (str (:url client) "/recent-trade"))
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-open-interest
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/open-interest)"
  [client category symbol interval-time opts]
  (->> (client/build-get-request (str (:url client) "/open-interest"))
       (client/append-query-params
        (merge {:category category :symbol symbol :intervalTime interval-time} opts))
       (client/send-request)))

(defn get-risk-limit
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/risk-limit)"
  ([client category]
   (->> (client/build-get-request (str (:url client) "/risk-limit"))
        (client/append-query-params
         (merge {:category category}))
        (client/send-request)))
  ([client category symbol]
   (->> (client/build-get-request (str (:url client) "/risk-limit"))
        (client/append-query-params
         (merge {:category category :symbol symbol}))
        (client/send-request))))

(defn get-delivery-price
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/delivery-price)"
  [client category opts]
  (->> (client/build-get-request (str (:url client) "/delivery-price"))
       (client/append-query-params
        (merge {:category category} opts))
       (client/send-request)))

(defn get-long-short-ratio
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/long-short-ratio)
   Linear only has USDT perps
   "
  ([client category symbol period]
   (get-long-short-ratio client category symbol period 50))
  ([client category symbol period limit]
   (->> (client/build-get-request (str (:url client) "/account-ratio"))
        (client/append-query-params
         (merge {:category category :symbol symbol :period period :limit limit}))
        (client/send-request))))