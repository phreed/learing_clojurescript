(ns modern-cljs.core
  (:require [compojure.core :refer [defroutes] :as http]
            [compojure.route :as route]))

(defroutes handler
   ;; responds to http://localhost:3000/ url
   (http/GET "/" [] "hello from the copojure server")
   ;; serving static files from the target directory
   (route/files "/" {:root "target"})
   ;; serves resources from the classpath
   (route/resources "/" {:root "target"})
   ;; when none of the others apply
   (route/not-found "page not found"))
