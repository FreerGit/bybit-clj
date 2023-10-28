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
