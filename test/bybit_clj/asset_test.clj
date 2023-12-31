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

(deftest get-single-coin-balance
  (is (= {} @(asset/get-single-coin-balance test-client "CONTRACT")))
  (is (= @last-request
         {:request-method :get,
          :url "https://api.bybit.com/v5/asset/transfer/query-account-coins-balance?accountType=CONTRACT",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "5b7590b8b6d494a3ca64924fafadec66b3b64d6c382ec15d61d0868a4160dbeb",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest get-transferable-coin
  (is (= {} @(asset/get-transferable-coin test-client "UNIFIED" "CONTRACT")))
  (is (= @last-request
         {:request-method :get,
          :url "https://api.bybit.com/v5/asset/transfer/query-transfer-coin-list?fromAccountType=UNIFIED&toAccountType=CONTRACT",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "7225f2bb6c4630025e435b5a57ea3dea09c869fb1fec78505df7af51bb6552e8",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest create-internal-transfer
  (is (= {} @(asset/create-internal-transfer test-client
                                             "42c0cfb0-6bca-c242-bc76-4e6df6cbcb16"
                                             "BTC"
                                             "0.05"
                                             "UNIFIED"
                                             "CONTRACT")))
  (is (= @last-request
         {:request-method :post,
          :url "https://api.bybit.com/v5/asset/transfer/inter-transfer",
          :body "{\"transferId\":\"42c0cfb0-6bca-c242-bc76-4e6df6cbcb16\",\"coin\":\"BTC\",\"amount\":\"0.05\",\"fromAccountType\":\"UNIFIED\",\"toAccountType\":\"CONTRACT\"}",
          :content-type :json,
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "9a1dadd0d3f9a1de98636830053c8a29dabec407f9c7c3fea7d52b8a8b8330ed",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest get-transfer-records
  (is (= {} @(asset/get-transfer-records test-client)))
  (is (= @last-request
         {:request-method :get,
          :url "https://api.bybit.com/v5/asset/transfer/query-account-coins-balance",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "e47095f2ac81449cea89c774ab5aa067aca0de296cb09d1cbe9ace308ce51157",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest get-sub-uid
  (is (= {} @(asset/get-sub-uid test-client)))
  (is (= @last-request
         {:request-method :get,
          :url "https://api.bybit.com/v5/asset/transfer/query-sub-member-list",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "e47095f2ac81449cea89c774ab5aa067aca0de296cb09d1cbe9ace308ce51157",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest create-universal-transfer
  (is (= {} @(asset/create-universal-transfer
              test-client
              {:transferId "42c0cfb0-6bca-c242-bc76-4e6df6cbcb16"
               :coin "BTC"
               :amount "0.05"
               :fromMemberId 592334
               :toMemberId 691355
               :fromAccountType "CONTRACT"
               :toAccountType "UNIFIED"})))
  (is (= @last-request
         {:request-method :post,
          :url "https://api.bybit.com/v5/asset/transfer/universal-transfer",
          :body "{\"transferId\":\"42c0cfb0-6bca-c242-bc76-4e6df6cbcb16\",\"coin\":\"BTC\",\"amount\":\"0.05\",\"fromMemberId\":592334,\"toMemberId\":691355,\"fromAccountType\":\"CONTRACT\",\"toAccountType\":\"UNIFIED\"}",
          :content-type :json,
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "f00a523ccea172e90987724f4c8feed09a47418a2766de36a383d7d9c2441cbb",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest get-universal-transfer-records
  (is (= {} @(asset/get-universal-transfer-records test-client)))
  (is (= @last-request
         {:request-method :get,
          :url "https://api.bybit.com/v5/asset/transfer/query-universal-transfer-list",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "e47095f2ac81449cea89c774ab5aa067aca0de296cb09d1cbe9ace308ce51157",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest get-allowed-deposit-coin
  (is (= {} @(asset/get-allowed-deposit-coin test-client)))
  (is (= @last-request
         {:request-method :get,
          :url "https://api.bybit.com/v5/asset/deposit/query-allowed-list",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "e47095f2ac81449cea89c774ab5aa067aca0de296cb09d1cbe9ace308ce51157",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest set-deposit-account
  (is (= {} @(asset/set-deposit-account
              test-client "UNIFIED")))
  (is (= @last-request
         {:request-method :post,
          :url "https://api.bybit.com/v5/asset/deposit/deposit-to-account",
          :body "{\"account-type\":\"UNIFIED\"}",
          :content-type :json,
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "3439a7e6c1d94e66bf8c335546feaab684f6f99825fa91ff72ff1a697338986d",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest get-deposit-records
  (is (= {} @(asset/get-deposit-records test-client)))
  (is (= @last-request
         {:request-method :get,
          :url "https://api.bybit.com/v5/asset/deposit/query-record",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "e47095f2ac81449cea89c774ab5aa067aca0de296cb09d1cbe9ace308ce51157",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest get-coin-info
  (is (= {} @(asset/get-coin-info test-client)))
  (is (= @last-request
         {:request-method :get,
          :url "https://api.bybit.com/v5/asset/coin/query-info",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "e47095f2ac81449cea89c774ab5aa067aca0de296cb09d1cbe9ace308ce51157",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

