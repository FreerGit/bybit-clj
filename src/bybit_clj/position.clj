(ns bybit-clj.position
  "Public and private endpoint functions and websocket feed . In all function signatures, `client` is a map with the following keys:
      - `:key` - optional - your Bybit API key.
      - `:secret` - optional - your Bybit API secret.
      - `:recv-window` - optional - how long an HTTP request is valid, in milliseconds.
        
        The library is design to easily swap out testnet for mainnet via `url` in `client`
      
        Remember not to store your `key` and `secret` in a public repo."
  (:require [bybit-clj.client :as client]))

(set! *warn-on-reflection* true)

(def url-position
  "The rest URL for Bybit V5 position"
  "https://api.bybit.com/v5")

(def url-position-testnet
  "The rest URL for Bybit V5 position testnet"
  "https://api-testnet.bybit.com/v5")

(defn get-position-info
  "[API DOCS](https:https://bybit-exchange.github.io/docs/v5/position)"
  [client category opts]
  (->> (client/build-get-request client "/position/list")
       (client/append-query-params (merge {:category category} opts))
       (client/send-signed-request client)))

(defn set-leverage
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/position/leverage)"
  [client category symbol buy-leverage sell-leverage]
  (->> (client/build-post-request client "/position/set-leverage"
                                  {:category category :symbol symbol :buyLeverage buy-leverage :sellLeverage sell-leverage})
       (client/send-signed-request client)))

(defn get-closed-pnl
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/position/close-pnl)"
  [client category opts]
  (->> (client/build-get-request client "/position/closed-pnl")
       (client/append-query-params (merge {:category category} opts))
       (client/send-signed-request client)))
