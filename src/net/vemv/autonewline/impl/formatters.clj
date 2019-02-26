(ns net.vemv.autonewline.impl.formatters
  (:require
   [net.vemv.autonewline.impl.formatters.generic :refer :all]
   [net.vemv.autonewline.impl.formatters.try :as formatters.try]
   [net.vemv.autonewline.impl.substitutions :refer :all]
   [net.vemv.autonewline.impl.util :refer :all]
   [rewrite-clj.zip :as zip]
   [rewrite-clj.zip.base]
   [rewrite-clj.zip.edit]
   [rewrite-clj.zip.move :as move]
   [rewrite-clj.zip.whitespace :as zip.whitespace]))

(defn format-threaded-cond
  "Formats a `cond->`-like node."
  [node]
  {:post [%]}
  (let [n (-> node
              zip/down*
              r
              r
              r
              safely-replace-whitespace-with-newline)]
    (loop [n n]
      (if (zip/rightmost? n)
        n
        (recur (-> n
                   r r safely-replace-whitespace-with-newline
                   r r safely-replace-whitespace-with-double-newline))))))

(defn format-cond
  "Formats a `cond`-like node."
  [node]
  {:post [%]}
  (let [n (-> node zip/down* r safely-replace-whitespace-with-newline)]
    (loop [n n]
      (if (zip/rightmost? n)
        n
        (recur (-> n
                   r r safely-replace-whitespace-with-newline
                   r r safely-replace-whitespace-with-double-newline))))))

(defn format-condp
  "Formats a `condp`-like node."
  [node]
  {:post [%]}
  (let [n (-> node zip/down* r r safely-replace-whitespace-with-single-space
              r r r safely-replace-whitespace-with-newline)]
    (loop [n n]
      (if (zip/rightmost? n)
        n
        (recur (-> n r r r r safely-replace-whitespace-with-newline))))))

(defn format-case
  "Formats a `case`-like node."
  [node]
  {:post [%]}
  (let [n (-> node zip/down* safely-replace-whitespace-with-single-space
              r r r safely-replace-whitespace-with-newline)]
    (loop [n n]
      (if (zip/rightmost? n)
        n
        (recur (-> n r r r r safely-replace-whitespace-with-newline))))))

(defn format-fn
  "Formats a `fn`-like node."
  [node]
  {:post [%]}
  (let [x (-> node zip/down* r r)
        x (case (zip/tag x)
            :vector (-> x r)
            :token (-> x r r r)
            x)]
    (safely-replace-whitespace-with-newline x)))

(defn format-if
  "Formats an `if`-like node."
  [node]
  {:post [%]}
  (-> node
      zip/down*
      r
      safely-replace-whitespace-with-single-space
      r
      r
      safely-replace-whitespace-with-newline
      r
      r
      safely-replace-whitespace-with-newline))

(defn format-try
  "Formats a `try` node."
  [node]
  {:post [%]}
  (-> node
      format-0
      (formatters.try/format-catch)
      (formatters.try/format-finally)
      (trim-trailing-whitespace)))

(defn register-methods!
  [multifn default & {:keys [mappings]
                      :or   {mappings {default                  identity
                                       :threaded-cond           format-threaded-cond
                                       :cond                    format-cond
                                       :condp                   format-condp
                                       :case                    format-case
                                       :fn                      format-fn
                                       :if                      format-if
                                       :try                     format-try
                                       :zero-double-indentation format-0-2x-indentation
                                       0                        format-0
                                       1                        format-1}}}]
  (doseq [[k v] mappings]
    (add-method multifn k v)))
