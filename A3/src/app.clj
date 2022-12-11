; Assignment 3 - COMP 348
; @author: Xavier Guertin
; Student ID: 40213525

(ns app
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
    (string/split line #"\|"))
)

;Reduce the sequence of entry to get only the id and name
(defn reduceEntry [db]
  (reduce (fn [result entry] (assoc result (entry 1) (name (entry 0)))) {} (seq db)))

;Define all data structures
(def customersReduced (reduceEntry app/custDB))
(def productsReduced (reduceEntry app/prodDB)) 
(def custDB (loadData "cust.txt"))
(def prodDB (loadData "prod.txt"))
(def salesDB (loadData "sales.txt"))