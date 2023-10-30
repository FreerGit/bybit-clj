(ns bybit-clj.account-test
  (:require [aleph.http :as http]
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

;; TODO https://stackoverflow.com/questions/42536178/strategy-for-stubbing-http-requests-in-clojure-tests
