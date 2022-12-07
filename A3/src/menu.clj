(ns menu
  (:require [db])
  (:require [app])
  (:gen-class)) 

(defn display-menu []
  (println "\n\n********* Sales Menu *********")
  (println "------------------------------")
  (println "  1. Display Customer Table   ")
  (println "  2. Display Product Table    ")
  (println "  3. Display Sales Table      ")
  (println "  4. Total Sales For Customer ")
  (println "  5. Total Sales For Product  ")
  (println "  6. Exit                     ")
  (println "------------------------------")
  (print "Select an option: "))


(defn continue []
  (println "Press Enter to continue")
  (read-line))


(defn executionUserChoice [option]
  (when (= option "1")
    (println "1\n")
    (db/printCustomerTable app/custDB)
    (continue))
  
  (when (= option "2")
    (println "2\n")
    (db/printProductTable app/prodDB)
    (continue))
  
  (when (= option "3")
    (println "3\n")
    (db/printSalesTable app/salesDB)
    (continue))
  
  (when (= option "4")
    (println "4\n")
    (println "What is the name of the customer you want to know the total sale.")
    (db/findTotalSale (read-line))
    (continue))
  
  (when (= option "5")
    (println "5\n")
    (println "Not working")
    (continue)))
  


(defn menuLoop []
  (display-menu)
  (def choice (read-line))
  
  (executionUserChoice choice)
  
  (loop [count 0]
    (if (= choice 6)
     (println "The program is exiting. Good bye.")
     (do
         (display-menu)
         (def choice (read-line))
    
         (executionUserChoice choice)

         (recur (inc count))))))

(defn -main []
  (println "Entering main program")
  (menuLoop))

(-main)