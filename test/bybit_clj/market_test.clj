(ns bybit-clj.market-test
  (:require [clojure.test :refer :all]
            [bybit-clj.market :as market]
            [manifold.deferred :as d]
            [bybit-clj.utils :as utils]
            [aleph.http :as http]))

(defn request-is-ok [body]
  (is (= (get body "retCode") 0))
  (is (= (get body "retMsg") "OK"))
  (is (instance? Long (get body "retCode")))
  (is (not= (get body "result") {})))

(defn request-is-ok-option [body]
  (is (= (get body "retCode") 0))
  (is (= (get body "retMsg") "success"))
  (is (instance? Long (get body "retCode")))
  (is (not= (get body "result") {}))
  (is (= (get-in body ["result" "category"]) "option")))

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
    (d/chain (market/get-time {:url market/market-rest-url})
             request-is-ok)))

(deftest get-kline
  (testing "Get kline REST"
    (let [body
          @(market/get-kline
            {:url market/market-rest-url} "inverse" "BTCUSDT" 60 {:limit 20})
          body-without-opt
          @(market/get-kline
            {:url market/market-rest-url}
            "inverse" "BTCUSDT" 60 {})]
      (request-is-ok body)
      (request-is-ok body-without-opt)
      (result-list-has-count body 20))))

(deftest get-mark-price-kline
  (testing "Get mark kline REST"
    (let [body
          @(market/get-mark-price-kline
            {:url market/market-rest-url} "inverse" "BTCUSDT" 60 {:limit 20})
          body-without-opt
          @(market/get-mark-price-kline
            {:url market/market-rest-url}
            "inverse" "BTCUSDT" 60 {})]
      (request-is-ok body)
      (request-is-ok body-without-opt)
      (result-list-has-count body 20))))

(deftest get-index-price-kline
  (testing "Get index kline REST"
    (let [body
          @(market/get-index-price-kline
            {:url market/market-rest-url} "inverse" "BTCUSDT" 60 {:limit 20})
          body-without-opt
          @(market/get-index-price-kline
            {:url market/market-rest-url}
            "inverse" "BTCUSDT" 60 {})]
      (request-is-ok body)
      (request-is-ok body-without-opt)
      (result-list-has-count body 20))))

(deftest get-premium-index-price-kline
  (testing "Get premium kline REST"
    (let [body
          @(market/get-premium-index-price-kline
            {:url market/market-rest-url} "inverse" "BTCUSDT" 60 {:limit 20})
          body-without-opt
          @(market/get-premium-index-price-kline
            {:url market/market-rest-url}
            "inverse" "BTCUSDT" 60 {})]
      (request-is-ok body)
      (request-is-ok body-without-opt)
      (result-list-has-count body 20))))

(deftest get-instruments-info
  (testing "Get premium kline REST"
    (let [body
          @(market/get-instruments-info
            {:url market/market-rest-url}
            "inverse" {:symbol "BTCUSDT" :limit 20})]
      (request-is-ok body)
      (result-list-has-count body 1))))

(deftest get-orderbook
  (testing "Get orderbook REST"
    (let [body
          @(market/get-orderbook
            {:url market/market-rest-url}
            "inverse" "BTCUSDT" {:limit 20})
          body-200
          @(market/get-orderbook
            {:url market/market-rest-url}
            "inverse" "BTCUSDT" {})]
      (request-is-ok body)
      (orderbook-levels-has-count body 1)
      (orderbook-levels-has-count body-200 200))))

(deftest get-tickers
  (testing "Get tickers REST"
    (let [body
          @(market/get-tickers
            {:url market/market-rest-url}
            "spot" "BTCUSDT" {})]
      (request-is-ok body)
      (result-list-has-count body 1))))

(deftest get-funding-history
  (testing "Get funding history REST"
    (let [body
          @(market/get-funding-history
            {:url market/market-rest-url}
            "linear" "ETHPERP" {:limit 1})]
      (request-is-ok body)
      (result-list-has-count body 1))))

(deftest get-recent-trades
  (testing "Get recent trades REST"
    (let [body
          @(market/get-recent-trades
            {:url market/market-rest-url}
            "linear" "BTCUSDT" {:limit 1})]
      (request-is-ok body)
      (result-list-has-count body 1))))

(deftest get-open-interest
  (testing "Get open interest REST"
    (let [body
          @(market/get-open-interest
            {:url market/market-rest-url}
            "linear" "BTCUSDT" "1d" {:limit 50})]
      (request-is-ok body)
      (result-list-has-count body 50))))

(deftest get-risk-limit
  (testing "Get risk limit REST"
    (let [body
          @(market/get-risk-limit
            {:url market/market-rest-url}
            "linear" "BTCUSDT")
          body-all
          @(market/get-risk-limit
            {:url market/market-rest-url}
            "linear")]
      (request-is-ok body)
      (request-is-ok body-all)
      (result-list-has-count body 1))))

(deftest get-delivery-price
  (testing "Get delivery price REST"
    (let [body
          @(market/get-delivery-price
            {:url market/market-rest-url}
            "linear" {})
          body-option
          @(market/get-delivery-price
            {:url market/market-rest-url}
            "option" {})]
      (request-is-ok body)
      (request-is-ok-option body-option)
      (result-list-has-count body-option 50)
      (result-list-has-count body 50))))

(deftest get-long-short-ratio
  (testing "Get long short ratio REST"
    (let [body
          @(market/get-long-short-ratio
            {:url market/market-rest-url}
            "linear" "BTCUSDT" "4h" 25)
          body-default
          @(market/get-long-short-ratio
            {:url market/market-rest-url}
            "inverse" "BTCUSD" "1h")]
      (request-is-ok body)
      (request-is-ok body-default)
      (result-list-has-count body 25)
      (result-list-has-count body-default 50))))