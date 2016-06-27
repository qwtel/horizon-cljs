(ns horizon-cljs.core
  (:require-macros [horizon-cljs.logging :refer [log]])
  (:require [io.horizon]))

(enable-console-print!)

; (prn {:a 1 :b 2})
; (log #{1 2 3} {:a 3 :b 4})

(def horizon (js/Horizon #js {:host "localhost:8181"}))
(.onReady horizon (fn [] (log "Connected")))
(.connect horizon)

(def chat (horizon "messages"))

(def message {:text "Generic code for cover photo."
              :datetime (js/Date.)
              :author "@qwtel"})

; (log (js->clj (clj->js message) :keywordize-keys true))

(.store chat (clj->js message))

(defn map-js->clj [items]
  (map #(js->clj % :keywordize-keys true) items))

(-> chat
  (.fetch)
  (.map map-js->clj)
  (.subscribe
    (fn [items]
      (log "Items:" items))
      ; (.removeAll chat (clj->js (map :id items))))
    (fn [error] (log error))))

(-> chat
  (.findAll #js {:author "@qwtel"})
  (.watch)
  (.map map-js->clj)
  (.subscribe
    (fn [items]
      (log "Watch me:" items))))
