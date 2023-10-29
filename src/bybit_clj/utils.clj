(ns bybit-clj.utils
  (:require [pjson.core :as json]
            [aleph.http :as http]
            [clj-commons.byte-streams :as bs]))

(defn get-request
  ([url query-params]
   (->> @(http/get url {:query-params query-params})
        :body
        bs/to-string
        json/read-str))
  ;; ([url category symbol interval start end]
  ;;  (get-request url category symbol interval start end nil))
  ;; ([url category symbol interval]
  ;;  (get-request url category symbol interval nil nil nil))
  )


