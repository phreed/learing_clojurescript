(ns modern-cljs.login.validators
  (:require [valip.core :as valid]
            [valip.predicates :as pred]))

(def ^:dynamic *re-password* #"^(?=.*\d).{4,16}$")

(defn user-credential-errors [email password]
  (valid/validate {:email email :password password}
            [:email present? "Email can't be empty."]
            [:email email-address? "The provided email is invalid."]
            [:password present? "Password can't be empty."]
            [:password (matches *re-password*) "The provided password is invalid"]))
