(ns bybit-clj.utils
  (:require [pjson.core :as json]
            [aleph.http :as http]
            [clj-commons.byte-streams :as bs]))

(defn get-request
  ([url query-params]
   (->> @(http/get url {:query-params query-params})
        :body
        bs/to-string
        json/read-str)))


