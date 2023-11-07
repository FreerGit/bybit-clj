(ns bybit-clj.utils
  (:require [clojure.string :as string]))

(set! *warn-on-reflection* true)

(defn get-timestamp
  []
  (System/currentTimeMillis))

(defn parse-request-params
  [request-url]
  (second (string/split request-url #"\?")))

(defn create-full-url
  [client endpoint]
  (str (:url client) endpoint))