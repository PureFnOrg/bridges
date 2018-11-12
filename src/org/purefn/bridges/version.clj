(ns org.purefn.bridges.version
  (:gen-class))

(defn -main
  []
  (println (System/getProperty "bridges.version")))
