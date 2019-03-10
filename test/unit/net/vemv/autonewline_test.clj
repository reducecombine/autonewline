(ns unit.net.vemv.autonewline-test
  (:require
   [clojure.test :refer :all]
   [net.vemv.autonewline.api :as sut]
   [rewrite-clj.zip :as zip]
   [rewrite-clj.zip.base]
   [rewrite-clj.zip.edit]
   [rewrite-clj.zip.move :as move]
   [rewrite-clj.zip.walk]
   [rewrite-clj.zip.whitespace]))

(deftest format-node
  (are [x y] (= x
                (-> y zip/of-string sut/format-node zip/root-string))
    "(cond\n  1\n  [1 2 3]\n\n  2\n  (+ 3 1))"                                                 "(cond 1 [1 2 3] 2 (+ 3 1))"
    "(cond\n  1\n  1\n\n  2\n  2)"                                                             "(cond 1 1 2 2)"
    "(case [2]\n  2 [2]\n  3 [3]\n  4)"                                                        "(case [2] 2 [2] 3 [3] 4)"
    "(cond-> {}\n  true\n  identity\n\n  false\n  (assoc :a 2))"                               "(cond-> {} true identity false (assoc :a 2))"
    "(fn a []\n  1 [2] 1 3)"                                                                   "(fn a [] 1 [2] 1 3)"
    "(case [1]\n  1)"                                                                          "(case [1] 1)"
    "(case 2\n  2 (+ 3 2)\n  3 3\n  4)"                                                        "(case 2 2 (+ 3 2) 3 3 4)"
    "(cond-> {}\n  false\n  (assoc :a 2)\n\n  true\n  identity)"                               "(cond-> {} false (assoc :a 2) true identity)"
    "(fn []\n  1 [] 1 3)"                                                                      "(fn [] 1 [] 1 3)"
    "(case 1\n  1)"                                                                            "(case 1 1)"
    "(case [2]\n  2 2\n  3 3\n  4)"                                                            "(case [2] 2 2 3 3 4)"
    "(if 1\n  [2]\n  3)"                                                                       "(if 1 [2] 3)"
    "(if 1\n  2\n  3)"                                                                         "(if 1 2 3)"
    "(try)"                                                                                    "(try)"
    "(try\n  2)"                                                                               "(try 2)"
    "(try\n  (catch))"                                                                         "(try (catch))"
    "(try\n  (catch Exception))"                                                               "(try (catch Exception))"
    "(try\n  (catch Exception e))"                                                             "(try (catch Exception e))"
    "(try\n  (catch Exception e\n    1))"                                                      "(try (catch Exception e 1))"
    "(try\n  1\n  (catch Exception e\n    1)\n  (finally))"                                    "(try 1 (catch Exception e 1) (finally))"
    "(try\n  1\n  (catch Exception e)\n  (finally))"                                           "(try 1 (catch Exception e) (finally))"
    "(try\n  1\n  2\n  (catch Exception e)\n  (finally\n    1))"                               "(try 1 2 (catch Exception e) (finally 1))"
    "(try\n  1\n  2\n  (catch Exception e\n    1)\n  (finally\n    1))"                        "(try 1 2 (catch Exception e 1) (finally 1))"
    "(try\n  1\n  (catch Exception e\n    12 2)\n  (finally\n    1 2))"                        "(try 1 (catch Exception e 12 2) (finally 1 2))"
    "(try\n  (catch Exception e\n    12 2)\n  (finally\n    1 2))"                             "(try (catch Exception e 12 2) (finally 1 2))"
    "(try\n  1\n  2\n  (catch Exception e\n    12 2)\n  (finally\n    1 2))"                   "(try 1 2 (catch Exception e 12 2) (finally 1 2))"
    "(case 1\n  [1])"                                                                          "(case 1 [1])"
    "(when 1\n  2 3)"                                                                          "(when 1 2 3)"
    "(case 2\n  2 2\n  3 3\n  4)"                                                              "(case 2 2 2 3 3 4)"
    "(when [1]\n  2 [3] 4)"                                                                    "(when [1] 2 [3] 4)"
    "(do\n  [1] 2 [3] 4)"                                                                      "(do [1] 2 [3] 4)"
    "(when [1]\n  2 3)"                                                                        "(when [1] 2 3)"
    "(condp = 1\n  2 3\n  4 5\n  6)"                                                           "(condp = 1 2 3 4 5 6)"
    "(cond-> {}\n  false\n  (assoc :a 2)\n\n  (= 1 2)\n  identity\n\n  false\n  (assoc :a 2))" "(cond-> {} false (assoc :a 2) (= 1 2) identity false (assoc :a 2))"
    "(do\n  1 2 3)"                                                                            "(do 1 2 3)"
    "(cond-> {}\n  true\n  identity\n\n  false\n  identity)"                                   "(cond-> {} true identity false identity)"))

(deftest idempotency
  (are [x] (= x
                (-> x zip/of-string sut/format-node zip/root-string))
    "(cond\n  1\n  [1 2 3]\n\n  2\n  (+ 3 1))"
    "(cond\n  1\n  1\n\n  2\n  2)"
    "(case [2]\n  2 [2]\n  3 [3]\n  4)"
    "(cond-> {}\n  true\n  identity\n\n  false\n  (assoc :a 2))"
    "(fn a []\n  1 [2] 1 3)"
    "(case [1]\n  1)"
    "(case 2\n  2 (+ 3 2)\n  3 3\n  4)"
    "(cond-> {}\n  false\n  (assoc :a 2)\n\n  true\n  identity)"
    "(fn []\n  1 [] 1 3)"
    "(case 1\n  1)"
    "(case [2]\n  2 2\n  3 3\n  4)"
    "(if 1\n  [2]\n  3)"
    "(if 1\n  2\n  3)"
    "(try)"
    "(try\n  2)"
    "(try\n  (catch))"
    "(try\n  (catch Exception))"
    "(try\n  (catch Exception e))"
    "(try\n  (catch Exception e\n    1))"
    "(try\n  1\n  (catch Exception e\n    1)\n  (finally))"
    "(try\n  1\n  (catch Exception e)\n  (finally))"
    "(try\n  1\n  2\n  (catch Exception e)\n  (finally\n    1))"
    "(try\n  1\n  2\n  (catch Exception e\n    1)\n  (finally\n    1))"
    "(try\n  1\n  (catch Exception e\n    12 2)\n  (finally\n    1 2))"
    "(try\n  (catch Exception e\n    12 2)\n  (finally\n    1 2))"
    "(try\n  1\n  2\n  (catch Exception e\n    12 2)\n  (finally\n    1 2))"
    "(case 1\n  [1])"
    "(when 1\n  2 3)"
    "(case 2\n  2 2\n  3 3\n  4)"
    "(when [1]\n  2 [3] 4)"
    "(do\n  [1] 2 [3] 4)"
    "(when [1]\n  2 3)"
    "(condp = 1\n  2 3\n  4 5\n  6)"
    "(cond-> {}\n  false\n  (assoc :a 2)\n\n  (= 1 2)\n  identity\n\n  false\n  (assoc :a 2))"
    "(do\n  1 2 3)"
    "(cond-> {}\n  true\n  identity\n\n  false\n  identity)"))
