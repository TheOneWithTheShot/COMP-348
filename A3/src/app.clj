(ns app
  (:require menu)
  (:require [clojure.string :as string])
  (:gen-class))

;Splits the lines of the given file
(defn splitContentOfFiles
  "Splits the inputs from a given file"
  [fileName]
  (string/split-lines (slurp fileName))
)

;Converts text file inputs as a vector
(defn loadData
  "Receives fileName
   \nCreates a vector by:
   1. Splits the file lines using fn \"splitContentOfFiles\"
   2. Splits the data \"|\"
   \nReturns a vector of all the retrieve data."
  [fileName]

  (for [line (splitContentOfFiles fileName)]
    [(string/split line #"\|")])
)

  
(def custDB (loadData "cust.txt"))
(def prodDB (loadData "prod.txt"))
(def salesDB (loadData "sales.txt"))