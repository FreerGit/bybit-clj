(ns bybit-clj.trade-test
  (:require [aleph.http :as http]
            [bybit-clj.trade :as trade]
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

(def test-client {:url trade/url-trade
                  :key "key"
                  :secret "secret"})

(deftest place-order
  (is (= {} @(trade/place-order test-client "spot" "BTCUSDT" "Buy" "Limit" "0.001" {:price "30000"}))
      (is (= @last-request
             {:request-method :post,
              :url "https://api.bybit.com/v5/order/create",
              :body "{\"category\":\"spot\",\"symbol\":\"BTCUSDT\",\"side\":\"Buy\",\"orderType\":\"Limit\",\"qty\":\"0.001\",\"price\":\"30000\"}",
              :content-type :json,
              :headers {"Content-Type" "application/json",
                        "X-BAPI-SIGN" "f6b43453529435cb824ba4b36dfd75cfab196ada86a1e1db5ff840c906ebbb55",
                        "X-BAPI-API-KEY" "key",
                        "X-BAPI-TIMESTAMP" "1698680853",
                        "X-BAPI-RECV-WINDOW" "5000"}}))))

(deftest amend-order
  (is (= {} @(trade/amend-order test-client "spot" "BTCUSDT" {:price "31000" :orderLinkId "11111"}))
      (is (= @last-request
             {:request-method :post,
              :url "https://api.bybit.com/v5/order/amend",
              :body "{\"category\":\"spot\",\"symbol\":\"BTCUSDT\",\"price\":\"31000\",\"orderLinkId\":\"11111\"}",
              :content-type :json,
              :headers {"Content-Type" "application/json",
                        "X-BAPI-SIGN" "a30f008ebf58d7fd37efc9f44d21828770a86ca25a72501b96a22581279ca43e",
                        "X-BAPI-API-KEY" "key",
                        "X-BAPI-TIMESTAMP" "1698680853",
                        "X-BAPI-RECV-WINDOW" "5000"}}))))

(deftest cancel-all-order
  (is (= {} @(trade/cancel-all-orders test-client "spot" {}))
      (is (= @last-request
             {:request-method :post,
              :url "https://api.bybit.com/v5/order/cancel-all",
              :body "{\"category\":\"spot\"}",
              :content-type :json,
              :headers {"Content-Type" "application/json",
                        "X-BAPI-SIGN" "57f08f7b3f90ba457567f26bdc5749572f1c021c94eceac7a235168c9e5a2c9c",
                        "X-BAPI-API-KEY" "key",
                        "X-BAPI-TIMESTAMP" "1698680853",
                        "X-BAPI-RECV-WINDOW" "5000"}}))))

(deftest cancel-order
  (is (= {} @(trade/cancel-order test-client "spot" "BTCUSDT" {:orderLinkId "11111"}))
      (is (= @last-request
             {:request-method :post, :url "https://api.bybit.com/v5/order/cancel",
              :body "{\"category\":\"spot\",\"symbol\":\"BTCUSDT\",\"orderLinkId\":\"11111\"}",
              :content-type :json,
              :headers {"Content-Type" "application/json",
                        "X-BAPI-SIGN" "399c6481b4772c81357340f4c4dafaad8f8953902e6cd494f262ac12b8099bbd",
                        "X-BAPI-API-KEY" "key",
                        "X-BAPI-TIMESTAMP" "1698680853",
                        "X-BAPI-RECV-WINDOW" "5000"}}))))

(deftest get-open-orders
  (is (= {} @(trade/get-open-orders test-client "spot" {}))
      (is (= @last-request
             {:request-method :get,
              :url "https://api.bybit.com/v5/order/realtime?category=spot",
              :headers {"Content-Type" "application/json",
                        "X-BAPI-SIGN" "bb7d991b08db33132aae6fe2b4b677e6f3d84e8c63206a8028ddb02fc7c06e9e",
                        "X-BAPI-API-KEY" "key",
                        "X-BAPI-TIMESTAMP" "1698680853",
                        "X-BAPI-RECV-WINDOW" "5000"}}))))

