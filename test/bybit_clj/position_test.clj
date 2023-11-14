(ns bybit-clj.position-test
  (:require [aleph.http :as http]
            [bybit-clj.position :as position]
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

(def test-client {:url position/url-position
                  :key "key"
                  :secret "secret"})

(deftest get-position-info
  (is (= {} @(position/get-position-info test-client "linear" {})))
  (is (= @last-request
         {:request-method :get,
          :url "https://api.bybit.com/v5/position/list?category=linear",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "d20e0d92192d803cf8494e4f2ec5ceb0b11c9e8351a9c1a9aeaf4dd176b83eca",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest set-leverage
  (is (= {} @(position/set-leverage test-client "linear" "BTCUSDT" "1" "1")))
  (is (= @last-request
         {:request-method :post,
          :url "https://api.bybit.com/v5/position/set-leverage",
          :body "{\"category\":\"linear\",\"symbol\":\"BTCUSDT\",\"buyLeverage\":\"1\",\"sellLeverage\":\"1\"}",
          :content-type :json,
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "5c34ff2b0d4ec9dc10df4c56a92b04c54df4dfe53bc01665a9d232b287df064b",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))

(deftest get-closed-pnl
  (is (= {} @(position/get-closed-pnl test-client "linear" {})))
  (is (= @last-request
         {:request-method :get,
          :url "https://api.bybit.com/v5/position/closed-pnl?category=linear",
          :headers {"Content-Type" "application/json",
                    "X-BAPI-SIGN" "d20e0d92192d803cf8494e4f2ec5ceb0b11c9e8351a9c1a9aeaf4dd176b83eca",
                    "X-BAPI-API-KEY" "key",
                    "X-BAPI-TIMESTAMP" "1698680853",
                    "X-BAPI-RECV-WINDOW" "5000"}})))