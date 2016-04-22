(ns modern-cljs.login
   (:require [domina.core :as dom]
             [domina.events :as events]
             [hiccups.runtime])
   (:require-macros [hiccups.core :refer [html]]))

;; define the function to be attached to form submission event
(defn validate-email [email]
  (dom/destroy! (dom/by-class "email"))
  (if (not (re-matches (re-pattern (dom/attr email :pattern))
                       (dom/value email)))
    (do
      (dom/prepend! (dom/by-id "loginForm")
                    (html [:div.help.email (dom/attr email :title)]))
      false)
    true))

(defn validate-password [password]
  (dom/destroy! (dom/by-class "password"))
  (if (not (re-matches (re-pattern (dom/attr password :pattern))
                       (dom/value password)))
    (do
      (dom/append! (dom/by-id "loginForm")
               (html [:div.help.password (dom/attr password :title)]))
      false)
    true))

(defn validate-form
  "This function checks all the fields and if they are good..."
  [evt]
  (let [email (dom/by-id "email")
        password (dom/by-id "password")
        email-val (dom/value email)
        password-val (dom/value password)]
    (if (or (empty? email-val) (empty? password-val))
      (do
        (dom/destroy! (dom/by-class "help"))
        (events/prevent-default evt)
        (dom/append! (dom/by-id "loginForm")
                 (html [:div.help "Please complete the form"])))
      (if (and (validate-email email)
               (validate-password password))
        true
        (events/prevent-default evt)))))

;; define the function to attach validate-form to
;; onsubmit event of the form
(defn ^:export init []
  ;; verify that js/document exists and that
  ;; it has a getElementById property
  (when (and js/document
           (aget js/document "getElementById"))
    ;; get loginForm by element id and set its onsubmit property to
    ;; our validate-form function
    (let [validate (dom/by-id "validate")
          email (dom/by-id "email")
          password (dom/by-id "password")]
      (events/listen! validate :click (fn [evt] (validate-form evt)))
      (events/listen! email :blur (fn [evt] (validate-email email)))
      (events/listen! password :blur (fn [evt] (validate-password password)))
      (events/listen! validate :mouseover
          (fn [] (dom/append!
                   (dom/by-id "loginForm")
                   (html [:div {:class "help"} "Click to submit"]))))
      (events/listen! validate :mouseout
          (fn [] (dom/destroy!
                   (dom/by-class "help")))))))
