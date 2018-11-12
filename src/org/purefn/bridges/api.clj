(ns org.purefn.bridges.api
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.test.check.generators :refer [recursive-gen]]
            [com.gfredericks.test.chuck.generators :refer [string-from-regex]]
            [org.purefn.kurosawa.result :refer :all]
            [org.purefn.bridges.protocol :as proto]))

;;------------------------------------------------------------------------------
;; UnsafeStringSetStore API 
;;------------------------------------------------------------------------------

(defn set-create*
  "Attempt to create a new named set of strings.

   - Returns `true` wrapped in a `Success` if successful, a `Failure` if not."
  [couch name]
  (proto/set-create* couch name))

(defn set-destroy*
  "Attempt to unsafely delete an existing named set of strings.

   - Returns `true` wrapped in a `Success` if successful, a `Failure` if not."
  [couch name]
  (proto/set-destroy* couch name))

(defn set-refresh*
  "Attempt to rebuild the entire contents of the set using the Couchbase view
   of all keys in the bucket.   
   
   WARNING: This will overwrite any existing members of the set.

   - Returns `true` wrapped in a `Success` if successful, a `Failure` if not."
  [couch name]
  (proto/set-refresh* couch name))

(defn set-add*
  "Attempt to add a string to the named set.

   - Returns `true` wrapped in a `Success` if successful, a `Failure` if not."
  [couch name value]
  (proto/set-add* couch name value))

(defn set-remove*
  "Attempt to remove a string from the named set.

   - Returns `true` wrapped in a `Success` if successful, a `Failure` if not."
  [couch name value]
  (proto/set-remove* couch name value))

(defn set-contents*
  "Attempt to get all of the strings in the named set.

   - Returns the set wrapped in `Success` if successful, a `Failure` if not."
  [couch name]
  (proto/set-contents* couch name))


;;------------------------------------------------------------------------------
;; StringSetStore API 
;;------------------------------------------------------------------------------

(defn set-exists?
  "Tests for the existance of a named set of strings. 

   - Returns `true` if the set is found, `false` if not."
  [couch namespace]
  (proto/set-exists? couch namespace))

(defn set-create
  "Create a new named set of strings.

   - Returns `true` if successful, `false` if not."
  [couch name]
  (proto/set-create couch name))

(defn set-destroy
  "Unsafely delete an existing named set of strings.

   - Returns `true` if successful, `false` if not."
  [couch name]
  (proto/set-destroy couch name))

(defn set-refresh
  "Rebuild the entire contents of the set using the Couchbase view of all keys 
   in the bucket.   
   
   WARNING: This will overwrite any existing members of the set.

   - Returns `true` if successful, `false` if not."
  [couch name]
  (proto/set-refresh couch name))

(defn set-add
  "Add a string to the named set.

   - Returns `true` if successful, `false` if not."
  [couch name value]
  (proto/set-add couch name value))

(defn set-remove
  "Remove a string from the named set.

   - Returns `true` if successful, `false` if not."
  [couch name value]
  (proto/set-remove couch name value))

(defn set-contents
  "Get all of the strings in the named set.

   - Returns the set if successful, `nil` if not."
  [couch name]
  (proto/set-contents couch name))


;;------------------------------------------------------------------------------
;; UnsafeCheckAndSetStore API
;;------------------------------------------------------------------------------

(defn create*
  "Attempt to create a new document containing an encoded Clojure value in the
   namespace.

   - The namespace must already exist.
   - The document must NOT already exist for the key.
   - If successful, the value passed in will be returned along with an updated
     check-and-set (CAS) token wrapped in a `Success`.
   - If unable to create the document, a `Failure` will be returned."
  [couch namespace key value]
  (proto/create* couch namespace key value))

(defn delete*
  "Attempt to safely delete a document in the namespace. 

   - A check-and-set (CAS) token must be supplied which matches the existing 
     token for the document.
   - The CAS token can be obtained by performing a `lookup` operation.   
   - Returns `true` wrapped in a `Success` if successful, a `Failure` if not."
  [couch namespace key cas]
  (proto/delete* couch namespace key cas))

(defn lookup*
  "Attempt to retrieve the Clojure value encoded in a document in the namespace.

   - If successful, returns the value along with a check-and-set (CAS) token
   wrapped in a `Success`.
   - If unable to find the document, a `Failure` will be returned."
  [couch namespace key]
  (proto/lookup* couch namespace key))

(defn store*
  "Attempt to store a Clojure value endoded in a document in the namespace.

   - The namespace must already exist.
   - A document must already exist for the key.
   - To prevent concurrent updates, a check-and-set (CAS) token must be supplied
     which matches that of the existing token for the document.  If the CAS 
     tokens do not match, the store will fail.
   - CAS tokens can be obtained from previous `create`, `lookup` or `store`
     operations.  If the store failed because of CAS token mismatch, you should
     obtain a new CAS token and the current existing value for the key by 
     calling `lookup`.
   - If successful, the value passed in will be returned with an updated CAS 
     token wrapped in a `Success`.
   - If unable to find the document, a `Failure` will be returned."
  [couch namespace key value cas]
  (proto/store* couch namespace key value cas))


;;------------------------------------------------------------------------------
;; CheckAndSetStore API
;;------------------------------------------------------------------------------

(defn exists?
  "Tests for the existance of a document in the namespace.

   - Returns `true` if the document is found, `false` if not."
  [couch namespace key]
  (proto/exists? couch namespace key))

(defn create
  "Create a new document containing an encoded Clojure value in the namespace.

   - The namespace must already exist.
   - The document must NOT already exist for the key.
   - If successful, the value passed in will be returned along with an updated
     check-and-set (CAS) token.
   - If unable to create the document, `nil` will be returned."
  [couch namespace key value]
  (proto/create couch namespace key value))

(defn delete
  "Safely delete a document in the namespace. 

   - A check-and-set (CAS) token must be supplied which matches the existing 
     token for the document.
   - The CAS token can be obtained by performing a `lookup` operation.   
   - Returns `true` if successful, `false` if not."
  [couch namespace key cas]
  (proto/delete couch namespace key cas))

(defn lookup
  "Retrieve the Clojure value encoded in a document in the namespace.

   - If successful, returns the value along with a check-and-set (CAS) token.
   - If unable to find the document, `nil` will be returned."
  [couch namespace key]
  (proto/lookup couch namespace key))

(defn store
  "Store a Clojure value endoded in a document in the namespace.

   - The namespace must already exist.
   - A document must already exist for the key.
   - To prevent concurrent updates, a check-and-set (CAS) token must be supplied
     which matches that of the existing token for the document.  If the CAS 
     tokens do not match, the store will fail.
   - CAS tokens can be obtained from previous `create`, `lookup` or `store`
     operations.  If the store failed because of CAS token mismatch, you should
     obtain a new CAS token and the current existing value for the key by 
     calling `lookup`.
   - If successful, the value passed in will be returned with an updated CAS 
     token.
   - If the CAS token did not match, `false` will be returned.
   - If unable to find the document, `nil` will be returned."
  [couch namespace key value cas]
  (proto/store couch namespace key value cas))


;;------------------------------------------------------------------------------
;; UnsafeKeyValueStore API 
;;------------------------------------------------------------------------------

(defn fetch*
  "Attempt to retrieve the Clojure value encoded in a document in the namespace.

   - If successful, returns the value alone wrapped in a `Success`. The 
   check-and-set (CAS) token is discarded.
   - If unable to find the document, a `Failure` will be returned."
  [couch namespace key]
  (proto/fetch* couch namespace key))

(defn destroy*
  "Attempt to unsafely delete a document in the namespace. 

   Because this operation is not protected by a check-and-set (CAS) token,
   there is a potential to silently lose changes stored simultaneously by 
   another client or thread.

   - Returns `true` wrapped in a `Success` if successful, a `Failure` if not."
  [couch namespace key]
  (proto/destroy* couch namespace key))

(defn write*
  "Stores the Clojure value encoded in a document to the namespace.

  Internally this function may call `swap-in` with `constantly`, or stomp on the
  current value (if there is one) through some other mechanism.  It is preferable
  to call `swap-in` if it is available.
  - If successful, returns the stored value wrapped in a Success.
    If unable to store the value, a Failure will be returned."
  [kv namespace key value]
  (proto/write* kv namespace key value))

(defn swap-in*
  "Atomically swaps the Clojure value encoded in a document using the supplied
   function.

   - If the document does not already exist, it will be created.
   - The new value encode in the document will be determined by the result of 
     applying the supplied function `f` to the previous value.  Note that `f`
     may be called several times (in the case of concurrent conflicts) and
     therefore should be free of side effects and be efficient to call.
   - Returns the final value wrapped in a `Success` if successful, a `Failure` 
     if not."
  [couch namespace key f]
  (proto/swap-in* couch namespace key f))


;;------------------------------------------------------------------------------
;; KeyValueStore API 
;;------------------------------------------------------------------------------

(defn fetch
  "Retrieve the Clojure value encoded in a document in the namespace.

   - If successful, returns the value alone. The check-and-set (CAS) token is 
   discarded.
   - If unable to find the document, `nil` will be returned."
  [couch namespace key]
  (proto/fetch couch namespace key))

(defn destroy
  "Unsafely delete a document in the namespace. 

   Because this operation is not protected by a check-and-set (CAS) token,
   there is a potential to silently lose changes stored simultaneously by 
  another client or thread.
  
   - Returns `true` if successful, `false` if not."
  [couch namespace key]
  (proto/destroy couch namespace key))

(defn write
  "Stores the Clojure value encoded in a document to the namespace.

  Internally this function may call `swap-in` with `constantly`, or stomp on the
  current value (if there is one) through some other mechanism.  It is preferable
  to call `swap-in` if it is available.
  - Returns the stored value if successful, `nil` if not."
  [kv namespace key value]
  (proto/write kv namespace key value))
  
(defn swap-in
  "Atomically swaps the Clojure value encoded in a document using the supplied
   function.

   - If the document does not already exist, it will be created.
   - The new value encode in the document will be determined by the result of 
     applying the supplied function `f` to the previous value.  Note that `f`
     may be called several times (in the case of concurrent conflicts) and
     therefore should be free of side effects and be efficient to call.
   - Returns the final value if successful, `nil, if not."
  [couch namespace key f]
  (proto/swap-in couch namespace key f))

(defn expire
  "Sets the expiration for the document with the specified `key` to `ttl` seconds
   in the future.

   - Returns `true` if successful, false if not."
  [kv namespace key ttl]
  (proto/expire kv namespace key ttl))

;;------------------------------------------------------------------------------
;; Data Specs 
;;------------------------------------------------------------------------------

;; Protocols.
(def string-set-store?
  (partial satisfies? proto/StringSetStore))

(def unsafe-string-set-store?
  (partial satisfies? proto/UnsafeStringSetStore))


(def check-and-set-store?
  (partial satisfies? proto/CheckAndSetStore))

(def unsafe-check-and-set-store?
  (partial satisfies? proto/UnsafeCheckAndSetStore))


(def key-value-store?
  (partial satisfies? proto/KeyValueStore))

(def unsafe-key-value-store?
  (partial satisfies? proto/UnsafeKeyValueStore))


;; Namespaces.
(def namespace-regex #"^[a-z]+(-[a-z]+)*(/[a-z]+(-[a-z]+)*)*$")

(def namespace?
  (s/spec (s/and string? (partial re-matches namespace-regex))
          :gen #(string-from-regex
                 #"[a-z]+(-[a-z]+){0,2}(/[a-z]+(-[a-z]+){0,2}){0,2}")))

(s/def ::namespace namespace?)


;; Keys.
(def key-regex #"^\p{Graph}+$")

(def key?
  (s/spec (s/and string? (partial re-matches key-regex))
          :gen #(string-from-regex #"[a-zA-Z0-9\*\+-\.:<=>\?@_]+")))

(s/def ::key key?)


;; Check-and-set (CAS).
(def cas? (s/and int? (partial < 0)))

(s/def ::cas cas?)


;; Values.
(def keyword-gen
  (->> (gen/string-alphanumeric)
       (gen/such-that not-empty)
       (gen/fmap keyword)))

(def leaf-gen
  (gen/one-of [(gen/boolean) (gen/int) (gen/string-alphanumeric)]))

(def branch-gen
  (fn [g]
    (gen/frequency [[4 leaf-gen]
                    [1 (gen/map keyword-gen g)]])))
(def value-gen
  (fn []
    (gen/map keyword-gen
             (recursive-gen branch-gen leaf-gen))))

(def value? (s/spec some? :gen value-gen))

(s/def ::value value?)


;; Documents.
(def doc? (s/keys :req [::value ::cas]))


;; String Sets.
(s/def ::set-name namespace?)

(s/def ::set-value key?)


;; Exception Data.
(s/def ::reason #{::cas-mismatch ::doc-missing ::doc-exists
                  ::server-busy ::max-retries ::fatal})


;;------------------------------------------------------------------------------
;; Function Specs 
;;------------------------------------------------------------------------------

;; StringSetStore
(s/fdef set-exists?
        :args (s/cat :couch string-set-store?
                     :name key?)
        :ret boolean?)

(s/fdef set-create
        :args (s/cat :couch string-set-store?
                     :name key?)
        :ret boolean?)

(s/fdef set-destroy
        :args (s/cat :couch string-set-store?
                     :name key?)
        :ret boolean?)

(s/fdef set-add
        :args (s/cat :couch string-set-store?
                     :name namespace?
                     :value key?)
        :ret boolean?)

(s/fdef set-remove
        :args (s/cat :couch string-set-store?
                     :name namespace?
                     :value key?)
        :ret boolean?)

(s/fdef set-contents
        :args (s/cat :couch string-set-store?
                     :name namespace?) 
        :ret (s/or :s (s/coll-of string?) :f nil?))


;; CheckAndSetStore 
(s/fdef exists?
        :args (s/cat :couch check-and-set-store?
                     :namespace namespace?
                     :key key?)
        :ret boolean?)

(s/fdef create
        :args (s/cat :couch check-and-set-store?
                     :namespace namespace?
                     :key key?
                     :value value?)
        :ret (s/or :s doc? :f nil?))

(s/fdef delete
        :args (s/cat :couch check-and-set-store?
                     :namespace namespace?
                     :key key?
                     :cas cas?)
        :ret boolean?)

(s/fdef lookup
        :args (s/cat :couch check-and-set-store?
                     :namespace namespace?
                     :key key?)
        :ret (s/or :s doc? :f nil?))

(s/fdef store
        :args (s/cat :couch check-and-set-store?
                     :namespace namespace?
                     :key key?
                     :value value?
                     :cas cas?)
        :ret (s/or :s doc? :f nil?))


;; KeyValueStore
(s/fdef fetch
        :args (s/cat :couch key-value-store?
                     :namespace namespace?
                     :key key?)
        :ret (s/or :s value? :f nil?))

(s/fdef destroy
        :args (s/cat :couch key-value-store?
                     :namespace namespace?
                     :key key?)
        :ret boolean?)

(s/fdef swap-in
        :args (s/cat :couch key-value-store?
                     :namespace namespace?
                     :key key?
                     :f (s/fspec :args (s/cat :prev value?)
                                 :ret value?))
        :ret value?)
