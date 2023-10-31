(ns bybit-clj.account-test
  (:require [aleph.http :as http]
            [bybit-clj.account :as account]
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

(def test-client {:url "https://example.com"
                  :key "key"
                  :secret "secret"})

;; https://stackoverflow.com/questions/42536178/strategy-for-stubbing-http-requests-in-clojure-tests

(deftest get-wallet-balance
  (is (= {} @(account/get-wallet-balance test-client "UNIFIED")))
  (is (= @last-request
         {:request-method :get,
          :url "https://example.com/wallet-balance?accountType=UNIFIED",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "9be3546ea7ec879995a0cdbff926d754035e8ef0091c466519254ef6efbc6f02",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest get-borrow-history
  (is (= {} @(account/get-borrow-history test-client)))
  (is (= @last-request
         {:request-method :get,
          :url "https://example.com/borrow-history"
          :headers {"Content-Type" "application/json"
                    "X-BAPI-SIGN" "e47095f2ac81449cea89c774ab5aa067aca0de296cb09d1cbe9ace308ce51157"
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest set-collateral-switch
  (is (= {} @(account/set-collateral-switch test-client "BTC" "ON")))
  (is (= @last-request
         {:request-method :post,
          :url "https://example.com/set-collateral-switch",
          :body "{\"coin\":\"BTC\",\"collateralSwitch\":\"ON\"}",
          :content-type :json,
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "f3125232723f4cb60fc1ba9b277faff3504b714af046fe0a327f317c2f73dfe6",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest get-collateral-info
  (is (= {} @(account/get-collateral-info test-client)))
  (is (= @last-request
         {:request-method :get,
          :url "https://example.com/collateral-info",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "e47095f2ac81449cea89c774ab5aa067aca0de296cb09d1cbe9ace308ce51157",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))