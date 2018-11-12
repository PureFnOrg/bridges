(ns org.purefn.bridges.cache.api
  "Caching behaviors for key-values stores."
  (:require [org.purefn.bridges.api :as bridges]))

(defn swap-in
  "Swaps the Clojure value encoded in a document using the supplied function, setting
   the expiration to `ttl` seconds.

   - Returns the final value if successful, `nil` if not.`"
  [couch ns k f ttl]
  (when-let [stored (bridges/swap-in couch ns k f)]
    (bridges/expire couch ns k ttl)
    stored))
