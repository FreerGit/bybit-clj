(ns bybit-clj.utils
  (:require [pjson.core :as json]
            [aleph.http :as http]
            [clj-commons.byte-streams :as bs]))

(defn get-request
  [url]
  (->> @(http/get url {})
       :body
       bs/to-string
       json/read-str))




