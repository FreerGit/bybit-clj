(ns bybit-clj.market-test
  (:require [clojure.test :refer :all]
            [bybit-clj.market :as core]))


(defn request-is-ok [body]
  (is (= (get body "retCode") 0))
  (is (= (get body "retMsg") "OK"))
  (is (instance? Long (get body "retCode")))
  (is (not= (get body "result") {})))

(defn result-list-has-count
  [body size]
  (-> body
      :result
      :list
      (#(is (count %) size))))

(defn orderbook-levels-has-count
  [body size]
  (-> body
      :result
      :a
      (#(is (count %) size))))

(deftest get-time
  (testing "Get time REST"
    (let [body (core/get-time {:url core/rest-url})]
      (request-is-ok body))))

(deftest get-kline
  (testing "Get kline REST"
    (let [body
          (core/get-kline
           {:url core/rest-url} "inverse" "BTCUSDT" 60 {:limit 20})
          body-without-opt
          (core/get-kline
           {:url core/rest-url}
           "inverse" "BTCUSDT" 60 {})]
      (request-is-ok body)
      (request-is-ok body-without-opt)
      (result-list-has-count body 20))))

(deftest get-mark-price-kline
  (testing "Get mark kline REST"
    (let [body
          (core/get-mark-price-kline
           {:url core/rest-url} "inverse" "BTCUSDT" 60 {:limit 20})
          body-without-opt
          (core/get-mark-price-kline
           {:url core/rest-url}
           "inverse" "BTCUSDT" 60 {})]
      (request-is-ok body)
      (request-is-ok body-without-opt)
      (result-list-has-count body 20))))

(deftest get-index-price-kline
  (testing "Get index kline REST"
    (let [body
          (core/get-index-price-kline
           {:url core/rest-url} "inverse" "BTCUSDT" 60 {:limit 20})
          body-without-opt
          (core/get-index-price-kline
           {:url core/rest-url}
           "inverse" "BTCUSDT" 60 {})]
      (request-is-ok body)
      (request-is-ok body-without-opt)
      (result-list-has-count body 20))))

(deftest get-premium-index-price-kline
  (testing "Get premium kline REST"
    (let [body
          (core/get-premium-index-price-kline
           {:url core/rest-url} "inverse" "BTCUSDT" 60 {:limit 20})
          body-without-opt
          (core/get-premium-index-price-kline
           {:url core/rest-url}
           "inverse" "BTCUSDT" 60 {})]
      (request-is-ok body)
      (request-is-ok body-without-opt)
      (result-list-has-count body 20))))

(deftest get-instruments-info
  (testing "Get premium kline REST"
    (let [body
          (core/get-instruments-info
           {:url core/rest-url}
           "inverse" {:symbol "BTCUSDT" :limit 20})]
      (request-is-ok body)
      (result-list-has-count body 1))))

(deftest get-orderbook
  (testing "Get orderbook REST"
    (let [body
          (core/get-orderbook
           {:url core/rest-url}
           "inverse" "BTCUSDT" {:limit 20})
          body-200
          (core/get-orderbook
           {:url core/rest-url}
           "inverse" "BTCUSDT" {})]
      (request-is-ok body)
      (orderbook-levels-has-count body 1)
      (orderbook-levels-has-count body-200 200))))

(deftest get-tickers
  (testing "Get tickers REST"
    (let [body
          (core/get-tickers
           {:url core/rest-url}
           "spot" "BTCUSDT" {})]
      (request-is-ok body)
      (result-list-has-count body 1))))

(deftest get-funding-history
  (testing "Get funding history REST"
    (let [body
          (core/get-funding-history
           {:url core/rest-url}
           "linear" "ETHPERP" {:limit 1})]
      (request-is-ok body)
      (result-list-has-count body 1))))


