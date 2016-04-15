(set-env!
  :project "modern-cljs"
  :source-paths #{"src/cljs"}
  :resource-paths #{"html"}
  :dependencies
   '[ [org.clojure/clojure "1.8.0"]
      [org.clojure/clojurescript "1.8.40"]
      [adzerk/boot-cljs "1.7.228-1"]
      [pandeiro/boot-http "0.7.3"]
      [adzerk/boot-reload "0.4.7"]
      [adzerk/boot-cljs-repl "0.3.0"]
      [com.cemerick/piggieback "0.2.1" :scope "test"]
      [weasel "0.7.0" :scope "test"]
      [org.clojure/tools.nrepl "0.2.12" :scope "test"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[pandeiro.boot-http :refer [serve]]
         '[adzerk.boot-reload :refer [reload]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]])

(deftask dev
  "Launch Immediate Feedback Development Environment."
   ;; Tasks may control, produce or act.
  []
  (comp
    ;; serve: reads from its :dir and provides a service on its :port
    (serve :dir "target" :port 3388)
    ;; watch the source files, goes after the server so it does not restart.
    (watch)
    ;; constructs a javascript reloader on the way out and sends a reload on the way back
    (reload)
    ;; constructs a cljs-repl to be run on the browser (start-repl) to connect
    (cljs-repl)
    ;; run the google-closure compiler
    (cljs)
    ;; save the stuff in a place where the server can find it
    (target :dir #{"target"})))
