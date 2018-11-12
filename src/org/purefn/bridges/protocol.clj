(ns org.purefn.bridges.protocol
  "Protocol definitions.")

(defprotocol UnsafeStringSetStore
   "A set of Strings backed by a key-value store.

    These low-level functions do minimal error handling and return a `Result`."
   (set-create* [this name])
   (set-destroy* [this name])
   (set-refresh* [this name])
   (set-add* [this name value])
   (set-remove* [this name value])
   (set-contents* [this name]))
     
(defprotocol StringSetStore
   "A set of Strings backed by a key-value store."
   (set-exists? [this name])
   (set-create [this name])
   (set-destroy [this name])
   (set-refresh [this name])
   (set-add [this name value])
   (set-remove [this name value])
   (set-contents [this name]))
     
(defprotocol UnsafeCheckAndSetStore
  "A key-value store in which access to documents is protected by 
   check-and-set (CAS) semantics.
   
   These low-level functions do minimal error handling and return a `Result`."
  (create* [this namespace key value])  
  (delete* [this namespace key cas]) 
  (lookup* [this namespace key]) 
  (store* [this namespace key value cas]))

(defprotocol CheckAndSetStore
  "A low-level key-value store in which access to documents is protected by 
   check-and-set (CAS) semantics."
  (exists? [this namespace key])
  (create [this namespace key value])
  (delete [this namespace key cas])
  (lookup [this namespace key])
  (store [this namespace key value cas]))

(defprotocol UnsafeKeyValueStore
  "A high-level key-value store which hides the mechanism of access 
   protection.
   
   These low-level functions do minimal error handling and return a `Result`."
  (fetch* [this namespace key])
  (destroy* [this namespace key])
  (write* [this namespace key value])
  (swap-in* [this namespace key f]))

(defprotocol KeyValueStore
  "A high-level key-value store which hides the mechanism of access 
   protection."
  (fetch [this namespace key])
  (destroy [this namespace key])
  (write [this namespace key value])
  (swap-in [this namespace key f]))

(defprotocol Cache
  "Caching behaviors for a key-value store."
  (expire [this namespace key ttl]))
