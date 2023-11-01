(ns bybit-clj.market
  "Public and private endpoint functions and websocket feed . In all function signatures, `client` is a map with the following keys:
- `:key` - optional - your Bybit API key.
- `:secret` - optional - your Bybit API secret.
- `:recv-window` - optional - how long an HTTP request is valid, in milliseconds.
  
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
  []
  (-> (str market-rest-url "/time")
      client/build-get-request
      client/send-request))

(defn get-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/kline)"
  [category symbol interval opts]
  (->> (client/build-get-request (str market-rest-url "/kline"))
       (client/append-query-params
        (merge {:category category :symbol symbol :interval interval} opts))
       client/send-request))

(defn get-mark-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/mark-kline)"
  [category symbol interval opts]
  (->> (client/build-get-request (str market-rest-url "/mark-price-kline"))
       (client/append-query-params
        (merge {:category category :symbol symbol :interval interval} opts))
       client/send-request))

(defn get-index-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/index-kline)"
  [category symbol interval opts]
  (->> (client/build-get-request (str market-rest-url "/index-price-kline"))
       (client/append-query-params
        (merge {:category category :symbol symbol :interval interval} opts))
       client/send-request))

(defn get-premium-index-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/preimum-index-kline)"
  [category symbol interval opts]
  (->> (client/build-get-request (str market-rest-url "/premium-index-price-kline"))
       (client/append-query-params
        (merge {:category category :symbol symbol :interval interval} opts))
       (client/send-request)))

(defn get-instruments-info
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/instrument)"
  [category opts]
  (->> (client/build-get-request (str market-rest-url "/instruments-info"))
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-orderbook
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/orderbook)"
  [category symbol opts]
  (->> (client/build-get-request (str market-rest-url "/orderbook"))
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-tickers
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/tickers)"
  [category symbol opts]
  (->> (client/build-get-request (str market-rest-url "/tickers"))
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-funding-history
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/history-fund-rate)"
  [category symbol opts]
  (->> (client/build-get-request (str market-rest-url "/funding/history"))
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-recent-trades
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/recent-trade)"
  [category symbol opts]
  (->> (client/build-get-request (str market-rest-url "/recent-trade"))
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-open-interest
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/open-interest)"
  [category symbol interval-time opts]
  (->> (client/build-get-request (str market-rest-url "/open-interest"))
       (client/append-query-params
        (merge {:category category :symbol symbol :intervalTime interval-time} opts))
       (client/send-request)))

(defn get-risk-limit
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/risk-limit)"
  ([category]
   (->> (client/build-get-request (str market-rest-url "/risk-limit"))
        (client/append-query-params
         (merge {:category category}))
        (client/send-request)))
  ([category symbol]
   (->> (client/build-get-request (str market-rest-url "/risk-limit"))
        (client/append-query-params
         (merge {:category category :symbol symbol}))
        (client/send-request))))

(defn get-delivery-price
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/delivery-price)"
  [category opts]
  (->> (client/build-get-request (str market-rest-url "/delivery-price"))
       (client/append-query-params
        (merge {:category category} opts))
       (client/send-request)))

(defn get-long-short-ratio
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/long-short-ratio)
   Linear only has USDT perps
   "
  ([category symbol period]
   (get-long-short-ratio category symbol period 50))
  ([category symbol period limit]
   (->> (client/build-get-request (str market-rest-url "/account-ratio"))
        (client/append-query-params
         (merge {:category category :symbol symbol :period period :limit limit}))
        (client/send-request))))