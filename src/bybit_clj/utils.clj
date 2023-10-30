(ns bybit-clj.utils)

(set! *warn-on-reflection* true)

(defn get-timestamp
  []
  (quot (System/currentTimeMillis) 1000))


