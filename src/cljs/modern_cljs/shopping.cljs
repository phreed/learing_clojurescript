(ns modern-cljs.shopping
   (:require [domina.core :as dc]))

(defn calculate
  "calculate the total, return false to prevent submission
   of the data to the server."[]
  (let [quantity (-> "quantity" dc/by-id dc/value)
        price (-> "price" dc/by-id dc/value)
        tax (-> "tax" dc/by-id dc/value)
        discount (-> "discount" dc/by-id dc/value)]
     (dc/set-value! (dc/by-id "total")
          (-> (* quantity price)
              (* (+ 1 (/ tax 100)))
              (- discount)
              (.toFixed 2)))
     false))

(defn ^:export init []
   (if (and js/document
            (.-getElementById js/document))
      (let [the-form (.getElementById js/document "shoppingForm")]
        (set! (.-onsubmit the-form) calculate))))
