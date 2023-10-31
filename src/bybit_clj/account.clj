(ns bybit-clj.account
  "Public and private endpoint functions and websocket feed . In all function signatures, `client` is a map with the following keys:
  - `:url` - rest URL.
  - `:key` - optional - your Bybit API key.
  - `:secret` - optional - your Bybit API secret.
  - `:recv-window` - optional - how long an HTTP request is valid, in milliseconds.
    
    The library is design to easily swap out testnet for mainnet via `url` in `client`
  
    Remember not to store your `key` and `secret` in a public repo."
  (:require [bybit-clj.auth :as auth]
            [bybit-clj.client :as client]))

(set! *warn-on-reflection* true)

(def account-rest-url
  "The rest URL for Bybit V5 account"
  "https://api.bybit.com/v5/account")

(defn get-wallet-balance
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/account/wallet-balance)"
  ([client account-type]
   (get-wallet-balance client account-type {}))
  ([client account-type opts]
   (->> (client/build-get-request (str (:url client) "/wallet-balance"))
        (client/append-query-params
         (merge {:accountType account-type} opts))
        (auth/sign-request client)
        client/send-request)))

(defn get-borrow-history
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/account/borrow-history)
   Note this requries unified account
  "
  ([client]
   (get-borrow-history client {}))
  ([client opts]
   (->> (client/build-get-request (str (:url client) "/borrow-history"))
        (client/append-query-params opts)
        (auth/sign-request client)
        client/send-request)))

(defn set-collateral-switch
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/account/set-collateral)"
  [client coin collateral-switch]
  (->> (client/build-post-request (str (:url client) "/set-collateral-switch")
                                  {:coin coin :collateralSwitch collateral-switch})
       (auth/sign-request client)
       (client/send-request)))

(defn get-collateral-info
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/account/collateral-info)"
  ([client]
   (->> (client/build-get-request (str (:url client) "/collateral-info"))
        (auth/sign-request client)
        (client/send-request)))
  ([client currency]
   (->> (client/build-get-request (str (:url client) "/collateral-info"))
        (client/append-query-params {:currency currency})
        (auth/sign-request client)
        (client/send-request))))
