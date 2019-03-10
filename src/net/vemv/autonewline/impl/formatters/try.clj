(ns net.vemv.autonewline.impl.formatters.try
  (:require
   [net.vemv.autonewline.impl.formatters.generic :refer :all]
   [net.vemv.autonewline.impl.substitutions :refer :all]
   [net.vemv.autonewline.impl.util :refer :all]
   [rewrite-clj.zip :as zip]
   [rewrite-clj.zip.base]
   [rewrite-clj.zip.edit]
   [rewrite-clj.zip.move :as move]
   [rewrite-clj.zip.whitespace :as zip.whitespace]))

(defn format-catch [node]
  (let [success (and (-> node zip/tag #{:list})
                     (-> node zip/sexpr first #{'catch}))]
    (if success
      (-> node format-2-2x-indentation)
      (if (zip/rightmost? node)
        node
        (let [fixed-prior-node (-> node safely-replace-whitespace-with-newline r)]
          (recur fixed-prior-node))))))

(defn format-finally [node]
  (let [success (and (-> node zip/tag #{:list})
                     (-> node zip/sexpr first #{'finally}))]
    (if success
      (-> node format-0-2x-indentation)
      (if (zip/rightmost? node)
        node
        (recur (r node))))))
