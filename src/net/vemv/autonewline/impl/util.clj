(ns net.vemv.autonewline.impl.util
  (:require
   [clojure.repl]))

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
