(ns net.vemv.autonewline.impl.dispatch
  (:require
   [rewrite-clj.zip :as zip]
   [rewrite-clj.zip.base]
   [rewrite-clj.zip.edit]
   [rewrite-clj.zip.move :as move]
   [rewrite-clj.zip.whitespace]))

(defn dispatch [node]
  (if-let [sexpr (try
                   (zip/sexpr node)
                   (catch Exception e
                     ;; whitespace and `#_` don't support `zip/sexpr`
                     ))]
    (when (list? sexpr)
      (let [head (-> sexpr first str)]
        (-> {"bound-fn"             :fn
             "fn"                   :fn

             "case"                 :case

             "cond"                 :cond

             "cond->"               :threaded-cond
             "cond->>"              :threaded-cond

             "condp"                :condp

             "definline"            :def
             "definterface"         :def
             "defmacro"             :def
             "defmethod"            :def
             "defmulti"             :def
             "defn"                 :def
             "defn-"                :def
             "defonce"              :def
             "defprotocol"          :def
             "defrecord"            :def
             "defstruct"            :def
             "deftype"              :def

             "if"                   :if
             "if-let"               :if
             "if-not"               :if
             "if-some"              :if

             "comment"              0
             "do"                   0
             "dosync"               0
             "future"               0
             "io!"                  0
             "time"                 0
             "with-loading-context" 0
             "with-out-str"         0

             "dotimes"              1
             "doseq"                1
             "doto"                 1
             "for"                  1
             "binding"              1
             "let"                  1
             "letfn"                1
             "locking"              1
             "loop"                 1
             "sync"                 1
             "when"                 1
             "when-first"           1
             "when-let"             1
             "when-not"             1
             "when-some"            1
             "while"                1
             "with-bindings"        1
             "with-in-str"          1
             "with-local-vars"      1
             "with-open"            1
             "with-precision"       1
             "with-redefs"          1}
            (get head))))))
