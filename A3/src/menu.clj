; Assignment 3 - COMP 348
; @author: Xavier Guertin
; Student ID: 40213525

(ns menu
  (:require db app)
  (:gen-class)) 

;Display menu to user
(defn displayMenu []
  (println "\n\n********* Sales Menu *********")
  (println "------------------------------")
  (println "  1. Display Customer Table   ")
  (println "  2. Display Product Table    ")
  (println "  3. Display Sales Table      ")
  (println "  4. Total Sales For Customer ")
  (println "  5. Total Sales For Product  ")
  (println "  6. Exit                     ")
  (println "------------------------------")
  (println "Select an option: ")
)

;function that asks the user to press enter to continue
(defn continue []
  (println "Press Enter to continue")
  (read-line)
  (displayMenu))

;function that receives the option chosen from the user and execute the command
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
    (println "What is the name of the customer you want to know the total sale:")
    (db/findTotalSale (read-line))
    (continue))
  
  (when (= option "5")
    (println "5\n")
    (println "What is the name of the product you want to know the total sale:")
    (db/findProductSaleCount (read-line))
    (continue))) 

;Menu loop that prompts the user for an option and leaves if option is 6
(defn menuLoop []
  (displayMenu)
  
  (executionUserChoice (read-line))
  
  (let [choice (read-line)]
    (if (= choice "6")
      (println "The program is exiting. Good bye.")
      (do 
        (executionUserChoice choice)

        (recur))))
  )

;main function
(defn -main []
  (println "Welcome to the Sales Order Application")
  (menuLoop))

;calls main
(-main)