(ns interop.react-native-community.geolocation.v1
  (:require ["@react-native-community/geolocation" :as module]
            [goog.object :as gobject]))

(assert module)

;https://github.com/react-native-community/react-native-geolocation/blob/master/js/implementation.native.js

(defn massage-error [e]
  (ex-info (gobject/get e "message") (js->clj e :keywordize-keys true)))

(defn massage-position [p]
  (js->clj p :keywordize-keys true))

(defn set-rn-configuration [config]
  (module/setRNConfiguration config))

(defn request-authorization []
  (module/requestAuthorization))

(defn get-current-position
  [succ-cb err-cb options]
  (module/getCurrentPosition
    (comp succ-cb massage-position)
    (comp err-cb massage-error)
    (clj->js options)))

(defn watch-position
  [succ-cb err-cb options]
  (module/watchPosition
    (comp succ-cb massage-position)
    (comp err-cb massage-error)
    (clj->js options)))

(defn clear-watch [id]
  (module/clearWatch id))

(defn stop-observing []
  (module/stopObserving))

(comment
  (def log (partial println ::log))
  (def err (partial println ::err))
  (set-rn-configuration {})
  (request-authorization)
  (get-current-position log err {})
  (def id (watch-position log err {}))
  (clear-watch id)
  (stop-observing))

