(set-env!
  :project "modern-cljs"
  :source-paths #{"src/cljs"}
  :resource-paths #{"html"}
  :dependencies '[[org.clojure/clojure "1.8.0"]
                  [adzerk/boot-cljs "1.7.228-1"]] )

(require '[adzerk.boot-cljs :refer [cljs]])
