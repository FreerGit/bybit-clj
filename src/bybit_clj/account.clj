(ns bybit-clj.account
  "Public and private endpoint functions and websocket feed . In all function signatures, `client` is a map with the following keys:
  - `:key` - optional - your Bybit API key.
  - `:secret` - optional - your Bybit API secret.
  - `:recv-window` - optional - how long an HTTP request is valid, in milliseconds.
    
    The library is design to easily swap out testnet for mainnet via `url` in `client`
  
    Remember not to store your `key` and `secret` in a public repo."
  (:require [bybit-clj.client :as client]))

(set! *warn-on-reflection* true)

(def url-account
  "The rest URL for Bybit V5 account mainnet"
  "https://api.bybit.com/v5/account")

(def url-account-testnet
  "The rest URL for Bybit V5 account testnet"
  "https://api-testnet.bybit.com/v5/account")

(def url-asset
  "The rest URL for Bybit V5 asset mainnet"
  "https://api.bybit.com/v5/asset")

(def url-asset-testnet
  "The rest URL for Bybit V5 asset testnet"
  "https://api-testnet.bybit.com/v5/account")

(defn get-wallet-balance
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/account/wallet-balance)"
  ([client account-type]
   (get-wallet-balance client account-type {}))
  ([client account-type opts]
   (->> (client/build-get-request (str (:url client) "/wallet-balance"))
        (client/append-query-params
         (merge {:accountType account-type} opts))
        (client/send-signed-request client))))

(defn get-borrow-history
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/account/borrow-history)
   Note this requries unified account
  "
  ([client]
   (get-borrow-history client {}))
  ([client opts]
   (->> (client/build-get-request (str (:url client) "/borrow-history"))
        (client/append-query-params opts)
        (client/send-signed-request client))))

(defn set-collateral-switch
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/account/set-collateral)"
  [client coin collateral-switch]
  (->> (client/build-post-request (str (:url client) "/set-collateral-switch")
                                  {:coin coin :collateralSwitch collateral-switch})
       (client/send-signed-request client)))

(defn get-collateral-info
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/account/collateral-info)"
  ([client]
   (->> (client/build-get-request (str (:url client) "/collateral-info"))
        (client/send-signed-request client)))
  ([client currency]
   (->> (client/build-get-request (str (:url client) "/collateral-info"))
        (client/append-query-params {:currency currency})
        (client/send-signed-request client))))

(defn get-coin-greeks
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/account/coin-greeks)
  
   The url in client is under `/asset` you could use `url-asset` or `url-asset-testnet` for this."
  ([client]
   (->> (client/build-get-request (str (:url client) "/coin-greeks"))
        (client/send-signed-request client)))
  ([client base-coin]
   (->> (client/build-get-request (str (:url client) "/coin-greeks"))
        (client/append-query-params {:baseCoin base-coin})
        (client/send-signed-request client))))

(defn get-fee-rate
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/account/fee-rate)"
  ([client category]
   (get-fee-rate client category {}))
  ([client category opts]
   (->> (client/build-get-request (str (:url client) "/fee-rate"))
        (client/append-query-params (merge {:category category} opts))
        (client/send-signed-request client))))

(defn get-account-info
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/account/account-info)"
  [client]
  (->> (client/build-get-request (str (:url client) "/info"))
       (client/send-signed-request client)))

(defn get-transaction-log
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/account/transaction-log)"
  ([client]
   (get-transaction-log client {}))
  ([client opts]
   (->> (client/build-get-request (str (:url client) "/transaction-log"))
        (client/append-query-params opts)
        (client/send-signed-request client)))) 
