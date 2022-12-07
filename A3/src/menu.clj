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
  (println "Select an option:\n"))


(defn continue []
  (println "Press Enter to continue")
  (read-line))


(defn executionUserChoice [option]
  (when (= option "1") 
     (db/printCustomerTable app/custDB)
     (continue))
  
  (when (= option "2") 
     (db/printProductTable app/prodDB)
     (continue))
  
  (when (= option "3") 
     (db/printSalesTable app/salesDB)
     (continue))
  
  (when (= option "4") 
     (println "Not working")
     (continue))
  
  (when (= option "5") 
     (println "Not working")
     (continue)))
  


(defn menuLoop [exit]
  (display-menu)
  (def choice (read-line))
  
  (executionUserChoice choice)
  
  (loop [count 0]
    (if (= choice exit)
     (println "Good-Bye!")
     (do
         (display-menu)
         (def choice (read-line))
    
         (executionUserChoice choice)

         (recur (inc count))))))

(defn -main []
  (println "Entering main program")
  (menuLoop "6"))

(-main)