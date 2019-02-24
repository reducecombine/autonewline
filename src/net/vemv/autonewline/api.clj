(ns net.vemv.autonewline.api
  (:refer-clojure :exclude [format])
  (:require
   [clojure.java.io :as io]
   [net.vemv.autonewline.impl.dispatch :refer [dispatch]]
   [net.vemv.autonewline.impl.formatters :refer [register-methods!]]
   [rewrite-clj.zip :as zip]))

(defmulti format-node
  "Formats a `rewrite-clj.node.protocols/Node`
  (i.e. each type of Clojure expression: see the built-in categorization in `net.vemv.autonewline.impl.dispatch`),
  using the rewrite-clj API."
  dispatch
  :default ::default)

(register-methods! format-node ::default)

(defn format-file!
  "Returns the formatting of `filename` according to a formatter, default `#'format-node``.

  `filename` will not be written to."
  [filename & {:keys [node-formatter]
               :or   {node-formatter format-node}}]
  {:pre [(string? filename)
         (some-> filename io/file .exists)]
   :post [(string? %)]}
  (loop [current-node (zip/of-file filename)]
    (let [formatted-node (-> current-node (zip/prewalk node-formatter))]
      (if-let [next-node (-> formatted-node zip/right)]
        (recur next-node)
        (zip/root-string formatted-node)))))
