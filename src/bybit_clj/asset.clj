(ns bybit-clj.asset
  "Public and private endpoint functions and websocket feed . In all function signatures, `client` is a map with the following keys:
    - `:key` - optional - your Bybit API key.
    - `:secret` - optional - your Bybit API secret.
    - `:recv-window` - optional - how long an HTTP request is valid, in milliseconds.
      
      The library is design to easily swap out testnet for mainnet via `url` in `client`
    
      Remember not to store your `key` and `secret` in a public repo."
  (:require [bybit-clj.client :as client]
            [bybit-clj.asset :as asset]))

(set! *warn-on-reflection* true)

(def url-asset
  "The rest URL for Bybit V5 asset"
  "https://api.bybit.com/v5/asset")

(def url-asset-testnet
  "The rest URL for Bybit V5 asset testnet"
  "https://api-testnet.bybit.com/v5/asset")

(defn get-delivery-record
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/asset/delivery)"
  ([client category]
   (get-delivery-record client category {}))
  ([client category opts]
   (->> (client/build-get-request (str (:url client) "/delivery-record"))
        (client/append-query-params (merge {:category category} opts))
        (client/send-signed-request client))))

(defn get-usdc-session-settlement
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/asset/settlement)"
  ([client category]
   (get-usdc-session-settlement client category {}))
  ([client category opts]
   (->> (client/build-get-request (str (:url client) "/settlement-record"))
        (client/append-query-params (merge {:category category} opts))
        (client/send-signed-request client))))

(defn get-asset-info
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/asset/asset-info)
   For now, it can query SPOT only."
  ([client account-type]
   (->> (client/build-get-request (str (:url client) "/transfer/query-asset-info"))
        (client/append-query-params {:accountType account-type})
        (client/send-signed-request client)))
  ([client account-type coin]
   (->> (client/build-get-request (str (:url client) "/transfer/query-asset-info"))
        (client/append-query-params (merge {:accountType account-type :coin coin}))
        (client/send-signed-request client))))

(defn get-all-coins-balance
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/asset/all-balance)
   It is not allowed to get master account coin balance via sub account api key."
  ([client account-type]
   (get-usdc-session-settlement client account-type {}))
  ([client account-type opts]
   (->> (client/build-get-request (str (:url client) "/transfer/query-account-coins-balance"))
        (client/append-query-params (merge {:accountType account-type} opts))
        (client/send-signed-request client))))

(defn get-single-coin-balance
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/asset/account-coin-balance)
   Sub account _cannot_ query master account balance
   Sub account can oly check its own balance
   Master account can check its own and its sub UIDs balance"
  ([client account-type]
   (get-single-coin-balance client account-type {}))
  ([client account-type opts]
   (->> (client/build-get-request (str (:url client) "/transfer/query-account-coins-balance"))
        (client/append-query-params (merge {:accountType account-type} opts))
        (client/send-signed-request client))))

(defn get-transferable-coin
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/asset/transferable-coin)"
  [client from-account-type to-account-type]
  (->> (client/build-get-request (str (:url client) "/transfer/query-transfer-coin-list"))
       (client/append-query-params {:fromAccountType from-account-type :toAccountType to-account-type})
       (client/send-signed-request client)))

(defn create-internal-transfer
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/asset/create-inter-transfer)"
  [client transfer-id coin amount from-account-type to-account-type]
  (->> (client/build-post-request
        (str (:url client) "/transfer/inter-transfer")
        {:transferId transfer-id :coin coin :amount amount
         :fromAccountType from-account-type :toAccountType to-account-type})
       (client/send-signed-request client)))

(defn get-transfer-records
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/asset/inter-transfer-list)"
  ([client]
   (get-transfer-records client {}))
  ([client opts]
   (->> (client/build-get-request (str (:url client) "/transfer/query-account-coins-balance"))
        (client/append-query-params opts)
        (client/send-signed-request client))))

(defn get-sub-uid
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/asset/sub-uid-list)"
  [client]
  (->> (client/build-get-request (str (:url client) "/transfer/query-sub-member-list"))
       (client/send-signed-request client)))

