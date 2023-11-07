(ns bybit-clj.market
  "Public and private endpoint functions and websocket feed . In all function signatures, `client` is a map with the following keys:
- `:key` - optional - your Bybit API key.
- `:secret` - optional - your Bybit API secret.
- `:recv-window` - optional - how long an HTTP request is valid, in milliseconds.
  
  The library is design to easily swap out testnet for mainnet via `url` in `client`

  Remember not to store your `key` and `secret` in a public repo."
  (:gen-class)
  (:require
   [bybit-clj.client :as client]))

(set! *warn-on-reflection* true)

(def url-market
  "The rest URL for Bybit V5 market mainnet"
  "https://api.bybit.com/v5/market")

(def url-market-testnet
  "The rest URL for Bybit V5 market mainnet"
  "https://api-testnet.bybit.com/v5/market")

(defn get-time
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/time)"
  [client]
  (->> (client/build-get-request client "/time")
       (client/send-request)))

(defn get-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/kline)"
  [client category symbol interval opts]
  (->> (client/build-get-request client "/kline")
       (client/append-query-params
        (merge {:category category :symbol symbol :interval interval} opts))
       client/send-request))

(defn get-mark-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/mark-kline)"
  [client category symbol interval opts]
  (->> (client/build-get-request client "/mark-price-kline")
       (client/append-query-params
        (merge {:category category :symbol symbol :interval interval} opts))
       client/send-request))

(defn get-index-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/index-kline)"
  [client category symbol interval opts]
  (->> (client/build-get-request client "/index-price-kline")
       (client/append-query-params
        (merge {:category category :symbol symbol :interval interval} opts))
       client/send-request))

(defn get-premium-index-price-kline
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/preimum-index-kline)"
  [client category symbol interval opts]
  (->> (client/build-get-request client "/premium-index-price-kline")
       (client/append-query-params
        (merge {:category category :symbol symbol :interval interval} opts))
       (client/send-request)))

(defn get-instruments-info
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/instrument)"
  [client category opts]
  (->> (client/build-get-request client "/instruments-info")
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-orderbook
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/orderbook)"
  [client category symbol opts]
  (->> (client/build-get-request client "/orderbook")
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-tickers
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/tickers)"
  [client category symbol opts]
  (->> (client/build-get-request client "/tickers")
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-funding-history
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/history-fund-rate)"
  [client category symbol opts]
  (->> (client/build-get-request client "/funding/history")
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-recent-trades
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/recent-trade)"
  [client category symbol opts]
  (->> (client/build-get-request client "/recent-trade")
       (client/append-query-params
        (merge {:category category :symbol symbol} opts))
       (client/send-request)))

(defn get-open-interest
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/open-interest)"
  [client category symbol interval-time opts]
  (->> (client/build-get-request client "/open-interest")
       (client/append-query-params
        (merge {:category category :symbol symbol :intervalTime interval-time} opts))
       (client/send-request)))

(defn get-risk-limit
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/risk-limit)"
  ([client category]
   (->> (client/build-get-request client "/risk-limit")
        (client/append-query-params {:category category})
        (client/send-request)))
  ([client category symbol]
   (->> (client/build-get-request client "/risk-limit")
        (client/append-query-params {:category category :symbol symbol})
        (client/send-request))))

(defn get-delivery-price
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/market/delivery-price)"
  [client category opts]
  (->> (client/build-get-request client "/delivery-price")
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
   (->> (client/build-get-request client "/account-ratio")
        (client/append-query-params
         (merge {:category category :symbol symbol :period period :limit limit}))
        (client/send-request))))