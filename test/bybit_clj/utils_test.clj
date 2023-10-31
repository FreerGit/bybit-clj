(ns bybit-clj.utils-test
  (:require
   [clojure.test :refer :all]
   [bybit-clj.utils :as utils]))

(deftest parse-request-params
  (is (= "example=onetwo" (utils/parse-request-params "https://example.com/hello?example=onetwo")))
  (is (= nil (utils/parse-request-params "https://example.com")))
  (is (= "" (str (utils/parse-request-params "https://example.com")))))