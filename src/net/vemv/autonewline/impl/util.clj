(ns net.vemv.autonewline.impl.util
  (:require
   [clojure.repl]
   [rewrite-clj.zip :as zip]
   [rewrite-clj.zip.base]
   [rewrite-clj.zip.edit]
   [rewrite-clj.zip.move :as move]
   [rewrite-clj.zip.remove]
   [rewrite-clj.zip.whitespace :as zip.whitespace]))

;; One always should use `zip/right*` for navigation.
;; An early implementation with `zip/next*` gave me some successes, but ultimately that failed.
;; right*, unlike next*, returns nil if going off-bounds, hence the `or` you can see here.
;; Later we check 'end' condition via `zip/rightmost?`.
(defn r [n]
  (or (zip/right* n)
      n))

(defn safely-move-to-rightmost-node [n]
  (if (zip/rightmost? n)
    n
    (recur (r n))))

(defn trim-trailing-whitespace [node]
  (let [n (zip/rightmost* node)]
    (if (zip/whitespace? n)
      (zip/remove n)
      n)))

(def defmethod-source
  "The source code of `defmethod`.

  Helps ensuring that `add-method` does the same exact thing as `defmethod`."

  "(defmacro defmethod\n  \"Creates and installs a new method of multimethod associated with dispatch-value. \"
  {:added \"1.0\"}\n  [multifn dispatch-val & fn-tail]
  `(. ~(with-meta multifn {:tag 'clojure.lang.MultiFn}) addMethod ~dispatch-val (fn ~@fn-tail)))")

(defmacro add-method
  "Installs a new method of multimethod associated with dispatch-value."
  {:pre [(-> 'defmethod clojure.repl/source-fn #{defmethod-source})]}
  [multifn dispatch-val f]
  `(. ~(with-meta multifn {:tag 'clojure.lang.MultiFn}) addMethod ~dispatch-val ~f))
