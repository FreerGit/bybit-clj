(ns bybit-clj.account
  "Public and private endpoint functions and websocket feed . In all function signatures, `client` is a map with the following keys:
  - `:url` - rest URL.
  - `:key` - optional - your Bybit API key.
  - `:secret` - optional - your Bybit API secret.
  - `:recv-window` - optional - how long an HTTP request is valid, in milliseconds.
    
    The library is design to easily swap out testnet for mainnet via `url` in `client`
  
    Remember not to store your `key` and `secret` in a public repo."
  (:require [bybit-clj.auth :as auth]
            [bybit-clj.client :as client]
            [bybit-clj.utils :as utils]))

;; TODO build a request instead of using `get` directly. 
;; TODO auth

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


