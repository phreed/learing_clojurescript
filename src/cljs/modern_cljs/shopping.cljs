(ns modern-cljs.shopping
   (:require [domina.core :as dom]
             [domina.events :as evt]
             [hiccups.runtime])
   (:require-macros [hiccups.core :refer [html]]))

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
   (when (and js/document
            (aget js/document "getElementById"))
      (let [calc (dom/by-id "calc")]
        (evt/listen! calc :click calculate)
        (evt/listen! calc :mouseover
           (fn [] (dom/append!
                    (dom/by-id "shoppingForm")
                    (html [:div {:class "help"} "Click to calculate"]))))
        (evt/listen! calc :mouseout
           (fn [] (dom/destroy! (dom/by-class "help")) calculate)))))
