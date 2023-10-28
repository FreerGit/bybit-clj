(ns bybit-clj.core-test
  (:require [clojure.test :refer :all]
            [bybit-clj.core :as core]
            [aleph.http :as http]))


(defn request-is-ok [body]
  (is (= (get body "retCode") 0))
  (is (= (get body "retMsg") "OK"))
  (is (instance? Long (get body "retCode")))
  (is (not= (get body "result") {})))

(deftest get-time
  (testing "Get time REST"
    (let [body (core/get-time {:url core/rest-url})]
      (request-is-ok body))))

(deftest get-kline
  (testing "Get kline REST"
    (let [body
          (core/get-kline
           {:url core/rest-url}
           {:category "inverse" :symbol "BTCUSDT" :interval 60 :start 1 :end 2 :limit 20})]
      (request-is-ok body)
      (-> body
          :result
          :list
          (#(is (count %) 20))))))

