(defproject net.vemv.autonewline "0.1.0"
  :description "A tiny, smart formatter which semantically places newlines in your Clojure forms."
  :url "https://github.com/reducecombine/autonewline"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :global-vars {*warn-on-reflection* true}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [rewrite-clj "0.6.1"]])
