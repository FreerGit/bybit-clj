(defproject org.clojars.sven_11/bybit-clj "1.0-SNAPSHOT"
  :description "A mostly complete wrapper for the Bybit API."
  :url "https://github.com/FreerGit/bybit-clj"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [pjson "1.0.0"]
                 [aleph "0.7.0-alpha2"]
                 [ring "1.10.0"]
                 [buddy/buddy-core "1.7.1"]
                 [stylefruits/gniazdo "1.2.2"]]
  :target-path "target/%s"
  :aliases {:dev {:extra-paths ["test"]}}
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :test {:jvm-opts ["-Dorg.slf4j.simpleLogger.defaultLogLevel=debug"]}}
  :plugins [[lein-codox "0.10.8"]]
  :codox {:doc-paths ["docs/"] :output-path "docs/"})

