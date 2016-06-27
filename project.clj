(defproject horizon-cljs "0.0.0"
  :dependencies [[org.clojure/clojure       "1.9.0-alpha7"]
                 [org.clojure/clojurescript "1.9.92"]]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-figwheel "0.5.4-4"]
            [lein-npm "0.6.2"]]

  :hooks [leiningen.cljsbuild]

  :profiles {:dev {:dependencies [[binaryage/devtools "0.7.2"]]
                   :cljsbuild {:builds {:main {:figwheel true
                                               :compiler {:optimizations :none
                                                          :source-map true
                                                          :source-map-timestamp true
                                                          :preloads [horizon-cljs.preload]}}}}}
             :prod {:cljsbuild {:builds {:main {:compiler {:optimizations :advanced
                                                           :elide-asserts true
                                                           :pretty-print false}}}}}}

  :figwheel {:server-port 3450}

  :clean-targets ^{:protect false} ["resources/public/js" "target"]

  :cljsbuild {:builds {:main {:source-paths ["src"]
                              :compiler {:main horizon-cljs.core
                                         :asset-path "js"
                                         :output-dir "resources/public/js"
                                         :output-to "resources/public/js/main.js"
                                         :externs ["node_modules/@horizon/client/dist/horizon.js"]
                                         :foreign-libs [{:file "node_modules/@horizon/client/dist/horizon-dev.js"
                                                         :file-min "node_modules/@horizon/client/dist/horizon.js"
                                                         :provides ["io.horizon"]}]}}}}

  :npm {:dependencies [["@horizon/client" "1.1.3"]]})
