(defproject org.purefn/bridges "1.13.1-SNAPSHOT"
  :description "A Kurosawa library for interacting with Key-Value stores."
  :url "https://github.com/purefnorg/bridges"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.7.1"
  :global-vars {*warn-on-reflection* true}
  :dependencies [[org.clojure/clojure "1.9.0-alpha16"]
                 
                 [org.purefn/kurosawa.core "2.0.5"]
                 
                 [org.clojure/test.check "0.9.0"]
                 [com.gfredericks/test.chuck "0.2.7"
                  :exclusions [clj-time]]]
  :profiles
  {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
         :jvm-opts ["-Xmx2g"]
         :source-paths ["dev"]
         :codeina {:sources ["src"]
                   :exclude [org.purefn.bridges.version]
                   :reader :clojure
                   :target "doc/dist/latest/api"
                   :src-uri "http://github.com/purefnorg/bridges/blob/master/"
                   :src-uri-prefix "#L"}
         :plugins [[funcool/codeina "0.4.0"
                    :exclusions [org.clojure/clojure]]
                   [lein-ancient "0.6.10"]]}}
  :aliases {"project-version" ["run" "-m" "org.purefn.bridges.version"]})
