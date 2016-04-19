(ns modern-cljs.remotes
  (:require [modern-cljs.core :refer [handler]]
            [compojure.handler :as http]
            [shoreleave.middleware.rpc :as rpc]))

;; compare with what is found in
;; modern/src/cljs/modern_cljs/shopping.cljs
(rpc/defremote calculate [quantity price tax discount]
  (Thread/sleep 2000)
  (-> (* quantity price)
      (* (+ 1 (/ tax 100)))
      (- discount)))

(def app (-> (var handler)
             (rpc/wrap-rpc)
             (http/site)))
