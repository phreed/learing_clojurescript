(ns modern-cljs.shopping
   (:require [domina.core :as dom]
             [domina.events :as evt]))

(defn calculate
  "calculate the total, return false to prevent submission
   of the data to the server."[]
  (let [quantity (-> "quantity" dom/by-id dom/value)
        price (-> "price" dom/by-id dom/value)
        tax (-> "tax" dom/by-id dom/value)
        discount (-> "discount" dom/by-id dom/value)]
     (dom/set-value! (dom/by-id "total")
          (-> (* quantity price)
              (* (+ 1 (/ tax 100)))
              (- discount)
              (.toFixed 2)))))

(defn ^:export init []
   (if (and js/document
            (.-getElementById js/document))
      (evt/capture! (dom/by-id "calc") :click calculate)))
