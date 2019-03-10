(ns integration.net.vemv.autonewline-test
  (:require
   [clojure.test :refer :all]
   [net.vemv.autonewline.api :as sut]))

(deftest format-file!
  (testing "Reformatting"
    (is (= (slurp "resources/formatted.edn")
           (-> "resources/unformatted.edn" sut/format-file!))))
  (testing "Idempotency"
    (is (= (slurp "resources/formatted.edn")
           (-> "resources/formatted.edn" sut/format-file!)))))
