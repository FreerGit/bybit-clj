(ns bybit-clj.asset_test
  (:require [aleph.http :as http]
            [bybit-clj.asset :as asset]
            [bybit-clj.utils :as utils]
            [clojure.test :refer :all]))

(def last-request (atom {}))

(defn mock-request
  [request]
  (reset! last-request request)
  {:body "{}"})

(defn market-fixture
  [test-function]
  (with-redefs [http/request mock-request
                utils/get-timestamp (constantly 1698680853)]
    (test-function)))

(use-fixtures :each market-fixture)

(utils/get-timestamp)

(def test-client {:url asset/url-asset
                  :key "key"
                  :secret "secret"})

(deftest get-delivery-record
  (is (= {} @(asset/get-delivery-record test-client "option")))
  (is (= @last-request
         {:request-method :get,
          :url "https://api.bybit.com/v5/asset/delivery-record?category=option",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "22648c47a04c875940232d25244b2cf6fe8543ff8140bda7d8b17636d72d37e6",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest get-usdc-session-settlement
  (is (= {} @(asset/get-usdc-session-settlement test-client "linear")))
  (is (= @last-request
         {:request-method :get,
          :url "https://api.bybit.com/v5/asset/settlement-record?category=linear",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "d20e0d92192d803cf8494e4f2ec5ceb0b11c9e8351a9c1a9aeaf4dd176b83eca"
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest get-asset-info
  (is (= {} @(asset/get-asset-info test-client "SPOT")))
  (is (= @last-request
         {:request-method :get, :url "https://api.bybit.com/v5/asset/transfer/query-asset-info?accountType=SPOT",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "5edc237e16651873c3a161e3a64bc3a5785cbf46184ed33c8de527fcdf7a3ab0",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest get-all-coins-balance
  (is (= {} @(asset/get-all-coins-balance test-client "linear")))
  (is (= @last-request
         {:request-method :get,
          :url "https://api.bybit.com/v5/asset/settlement-record?category=linear",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "d20e0d92192d803cf8494e4f2ec5ceb0b11c9e8351a9c1a9aeaf4dd176b83eca",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

