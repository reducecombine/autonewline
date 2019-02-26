(ns net.vemv.autonewline.impl.formatters.generic
  (:require
   [net.vemv.autonewline.impl.substitutions :refer :all]
   [net.vemv.autonewline.impl.util :refer :all]
   [rewrite-clj.zip :as zip]
   [rewrite-clj.zip.base]
   [rewrite-clj.zip.edit]
   [rewrite-clj.zip.move :as move]
   [rewrite-clj.zip.whitespace :as zip.whitespace]))

(defn format-0
  "Formats a node with a `:style/indent` of 0. See CIDER indent spec."
  [node]
  {:post [%]}
  (-> node
      zip/down*
      r
      safely-replace-whitespace-with-newline))

(defn format-0-2x-indentation
  "Formats a node with a `:style/indent` of 0 (see CIDER indent spec), and two levels of indentation."
  [node]
  {:post [%]}
  (-> node
      zip/down*
      r
      safely-replace-whitespace-with-newline-and-double-indentation))

(defn format-1
  "Formats a node with a `:style/indent` of 1. See CIDER indent spec."
  [node]
  {:post [%]}
  (-> node
      zip/down*
      r
      r
      r
      safely-replace-whitespace-with-newline))

(defn format-2-2x-indentation
  "Formats a node with a `:style/indent` of 2 (see CIDER indent spec), and two levels of indentation."
  [node]
  {:post [%]}
  (-> node
      zip/down*
      r
      r
      r
      r
      r
      safely-replace-whitespace-with-newline-and-double-indentation
      zip/up*))
