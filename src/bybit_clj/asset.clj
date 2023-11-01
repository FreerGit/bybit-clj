(ns bybit-clj.asset
  (:require [bybit-clj.client :as client]))

(set! *warn-on-reflection* true)

(def asset-rest-url
  "The rest URL for Bybit V5 asset"
  "https://api.bybit.com/v5/asset")

(defn get-delivery-record
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/asset/delivery)"
  ([client category]
   (get-delivery-record client category {}))
  ([client category opts]
   (->> (client/build-get-request (str asset-rest-url "/delivery-record"))
        (client/append-query-params (merge {:category category} opts))
        (client/send-signed-request client))))

(defn get-usdc-session-settlement
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/asset/settlement)"
  ([client category]
   (get-usdc-session-settlement client category {}))
  ([client category opts]
   (->> (client/build-get-request (str asset-rest-url "/settlement-record"))
        (client/append-query-params (merge {:category category} opts))
        (client/send-signed-request client))))

(defn get-asset-info
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/asset/asset-info)
   For now, it can query SPOT only."
  ([client account-type]
   (->> (client/build-get-request (str asset-rest-url "/transfer/query-asset-info"))
        (client/append-query-params {:accountType account-type})
        (client/send-signed-request client)))
  ([client account-type coin]
   (->> (client/build-get-request (str asset-rest-url "/transfer/query-asset-info"))
        (client/append-query-params (merge {:accountType account-type :coin coin}))
        (client/send-signed-request client))))

(defn get-all-coins-balance
  "[API DOCS](https://bybit-exchange.github.io/docs/v5/asset/all-balance)
   It is not allowed to get master account coin balance via sub account api key."
  ([client account-type]
   (get-usdc-session-settlement client account-type {}))
  ([client account-type opts]
   (->> (client/build-get-request (str asset-rest-url "/transfer/query-account-coins-balance"))
        (client/append-query-params (merge {:accountType account-type} opts))
        (client/send-signed-request client))))
