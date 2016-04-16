(ns modern-cljs.login
   (:require [domina.core :as dom]
             [domina.events :as evt]
             [hiccups.runtime])
   (:require-macros [hiccups.core :refer [html]]))

;; define the function to be attached to form submission event
(defn validate-form []
  ;; get email and password element from their ids in the HTML form
  (let [email (dom/by-id "email")
        password (dom/by-id "password")]
    (if (and (> (count (dom/value email)) 0)
             (> (count (dom/value password)) 0))
      true
      (do (js/alert "Please, complete the form!")
          false))))

;; define the function to attach validate-form to
;; onsubmit event of the form
(defn ^:export init []
  ;; verify that js/document exists and that
  ;; it has a getElementById property
  (if (and js/document
           (aget js/document "getElementById"))
    ;; get loginForm by element id and set its onsubmit property to
    ;; our validate-form function
    (let [validate (dom/by-id "validate")]
      (evt/listen! validate :click validate-form)
      (evt/listen! validate :mouseover
          (fn [] (dom/append!
                   (dom/by-id "loginForm")
                   (html [:div {:class "help"} "Click to submit"]))))
      (evt/listen! validate :mouseout
          (fn [] (dom/destroy!
                   (dom/by-class "help")))))))
