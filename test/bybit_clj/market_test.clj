(ns bybit-clj.market-test
  (:require [clojure.test :refer :all]
            [bybit-clj.market :as core]
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

(defn default-kline-params
  []
  {:category "inverse" :symbol "BTCUSDT" :interval 60 :start 1 :end 2 :limit 20})

(defn default-kline-params-no-opt
  []
  {:category "inverse" :symbol "BTCUSDT" :interval 60})

(defn result-list-has-count
  [body size]
  (-> body
      :result
      :list
      (#(is (count %) size))))

(deftest get-kline
  (testing "Get kline REST"
    (let [body
          (core/get-kline
           {:url core/rest-url} (default-kline-params))
          body-without-opt
          (core/get-kline
           {:url core/rest-url}
           (default-kline-params-no-opt))]
      (request-is-ok body)
      (request-is-ok body-without-opt)
      (result-list-has-count body 20))))

(deftest get-mark-price-kline
  (testing "Get mark kline REST"
    (let [body
          (core/get-mark-price-kline
           {:url core/rest-url} (default-kline-params))
          body-without-opt
          (core/get-mark-price-kline
           {:url core/rest-url}
           (default-kline-params-no-opt))]
      (request-is-ok body)
      (request-is-ok body-without-opt)
      (result-list-has-count body 20))))

(deftest get-index-price-kline
  (testing "Get index kline REST"
    (let [body
          (core/get-index-price-kline
           {:url core/rest-url} (default-kline-params))
          body-without-opt
          (core/get-index-price-kline
           {:url core/rest-url}
           (default-kline-params-no-opt))]
      (request-is-ok body)
      (request-is-ok body-without-opt)
      (result-list-has-count body 20))))

(deftest get-premium-index-price-kline
  (testing "Get premium kline REST"
    (let [body
          (core/get-premium-index-price-kline
           {:url core/rest-url} (default-kline-params))
          body-without-opt
          (core/get-premium-index-price-kline
           {:url core/rest-url}
           (default-kline-params-no-opt))]
      (request-is-ok body)
      (request-is-ok body-without-opt)
      (result-list-has-count body 20))))

(deftest get-instruments-info
  (testing "Get premium kline REST"
    (let [body
          (core/get-instruments-info
           {:url core/rest-url}
           {:category "spot" :symbol "BTCUSDT"})]
      (request-is-ok body)
      (result-list-has-count body 1))))

(defn orderbook-levels-has-count
  [body size]
  (-> body
      :result
      :a
      (#(is (count %) size))))

(deftest get-orderbook
  (testing "Get orderbook REST"
    (let [body
          (core/get-orderbook
           {:url core/rest-url}
           {:category "spot" :symbol "BTCUSDT" :limit 1})
          body-200
          (core/get-orderbook
           {:url core/rest-url}
           {:category "spot" :symbol "BTCUSDT" :limit 200})]
      (request-is-ok body)
      (orderbook-levels-has-count body 1)
      (orderbook-levels-has-count body-200 200))))

(deftest get-tickers
  (testing "Get tickers REST"
    (let [body
          (core/get-tickers
           {:url core/rest-url}
           {:category "spot" :symbol "BTCUSDT"})]
      (request-is-ok body)
      (result-list-has-count body 1))))
