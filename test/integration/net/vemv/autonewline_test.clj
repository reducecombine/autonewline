(ns integration.net.vemv.autonewline-test
  (:require
   [clojure.test :refer :all]
   [net.vemv.autonewline.api :as sut]))

(deftest format-file!
  (are [x] (= x (-> "resources/sample.edn" sut/format-file!))
    "
(cond
  1
  [1 2 3]

  2
  (+ 3 1))

(cond
  1
  1

  2
  2)

(case [2]
  2 [2]
  3 [3]
  4)

(cond-> {}
  true
  identity

  false
  (assoc :a 2))

(fn a []
  1 [2] 1 3)

(case [1]
  1)

(case 2
  2 (+ 3 2)
  3 3
  4)

(cond-> {}
  false
  (assoc :a 2)

  true
  identity)

(fn []
  1 [] 1 3)

(case 1
  1)

(case [2]
  2 2
  3 3
  4)

(if 1
  2
  3)

(if 1
  [2]
  3)

(case 1
  [1])

(when 1
  2 3)

(case 2
  2 2
  3 3
  4)

(when [1]
  2 [3] 4)

(do
  [1] 2 [3] 4)

(when [1]
  2 3)

(condp = 1
  2 3
  4 5
  6)

(cond-> {}
  false
  (assoc :a 2)

  (= 1 2)
  identity

  false
  (assoc :a 2))

(do
  1 2 3)

(cond-> {}
  true
  identity

  false
  identity)
"))
