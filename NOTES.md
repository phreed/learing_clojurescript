
I also created a ~/.boot/profile.boot containing
```clojure
(set-env! :dependencies '[[boot-deps "0.1.6"]])
(require '[boot-deps :refer [ancient latest]])
```
This makes it possible to check to see if 
things are up-to-date.


