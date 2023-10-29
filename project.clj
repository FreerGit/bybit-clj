(defproject bybit-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [pjson "1.0.0"]
                 [aleph "0.7.0-alpha2"]]
  :main ^:skip-aot bybit-clj.core
  :target-path "target/%s"
  :aliases {:dev {:extra-paths ["test"]}}
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}
  :plugins [[lein-codox "0.10.8"]]
  :codox {:doc-paths ["docs/"] :output-path "docs/"})
