(ns net.vemv.autonewline.impl.substitutions
  (:require
   [rewrite-clj.node.whitespace :as node.whitespace]
   [rewrite-clj.zip :as zip]
   [rewrite-clj.zip.base]
   [rewrite-clj.zip.edit]
   [rewrite-clj.zip.move :as move]
   [rewrite-clj.zip.whitespace]))

(defn safely-replace-whitespace-with-newline [node]
  (if (zip/whitespace? node)
    (zip/replace node (node.whitespace/->WhitespaceNode "\n  "))
    node))

(defn safely-replace-whitespace-with-newline-and-two-spaces [node]
  (if (zip/whitespace? node)
    (zip/replace node (node.whitespace/->WhitespaceNode "\n  "))
    node))

(defn safely-replace-whitespace-with-newline-and-double-indentation [node]
  (if (zip/whitespace? node)
    (zip/replace node (node.whitespace/->WhitespaceNode "\n    "))
    node))

(defn safely-replace-whitespace-with-double-newline [node]
  (if (zip/whitespace? node)
    (zip/replace node (node.whitespace/->WhitespaceNode "\n\n  "))
    node))

(defn safely-replace-whitespace-with-single-space [node]
  (if (zip/whitespace? node)
    (zip/replace node (node.whitespace/->WhitespaceNode " "))
    node))
