; Assignment 3 - COMP 348
; @author: Xavier Guertin
; Student ID: 40213525

(ns db
  (:require app)
  (:gen-class))

;;Counts the number of entries in the customer DB
(def nbOfEntriesCust
  (count app/custDB))

;;Prints the customers entries using recur on each entry
(defn printCustomerTable
  [entries]

  (println "\n                      Customer table")
  (println "\n=============================================================")
  (println (format " ID |      %-10s |       %-14s |  %-5s " "Name" "Address" "Phone Number"))
  (println "=============================================================")
  
  (loop [count 0]
    (if (= count nbOfEntriesCust)
      (println "\nAll the entries have been printed")
      (do
        (println (format " %s  | %-15s | %-20s | %-10s    "
                         (nth (nth entries count) 0)  ;custID
                         (nth (nth entries count) 1)  ;name
                         (nth (nth entries count) 2)  ;address
                         (nth (nth entries count) 3)))  ;phoneNumber
                         
        (recur (inc count))))))

;;Counts the number of entries in the products DB
(def nbOfEntriesProd 
  (count app/prodDB))

;;Prints the product entries  using recur on each entry
(defn printProductTable
  [entries]

  (println "\n     Product Table")
  (println "\n===========================")
  (println (format " ID |    %-6s  |  %5s" "Name" "Price"))
  (println "===========================")
  
  (loop [count 0]
    (if (= count nbOfEntriesProd)
      (println "\nAll the entries have been printed")
      (do
        (println (format " %s  | %-10s | %s"
                         (nth (nth entries count) 0)  ;prodID
                         (nth (nth entries count) 1)  ;itemDescription
                         (nth (nth entries count) 2)))  ;unitCost

        (recur (inc count))))))


;; Receives the needed entry(index)
;; Returns the number in the custId column of sales - 1 (to have the right index)
(defn findSalesEntryForCustomerSearch
  [count]
  (- (read-string (nth (nth app/salesDB count) 1)) 1))

;; Receives the needed entry(index)
;; Returns the number in the prodID column of sales - 1 (to have the right index)
(defn findSalesEntryForProductSearch
  [count]
  (- (read-string (nth (nth app/salesDB count) 2)) 1))

;;Counts the number of entries in the sales DB
(def nbOfEntriesSales
  (count app/salesDB))

;;Prints the sales entries using recur on each entry
(defn printSalesTable
  [entries]

  (println "\n                   Sales table")
  (println "\n==================================================")
  (println (format " ID |      %-10s |   %-9s | %-10s" "Name" "Product" "Item Count"))
  (println "==================================================")
  
  (loop [count 0]
    (if (= count nbOfEntriesSales)
      (println "\nAll the entries have been printed")
      (do
        (println (format " %s  | %-15s | %-11s | %s"
                   (nth (nth entries count) 0)  ;salesID
                   (nth (nth app/custDB (findSalesEntryForCustomerSearch count)) 1)  ;custID
                   (nth (nth app/prodDB (findSalesEntryForProductSearch count)) 1)  ;prodID
                   (nth (nth entries count) 3)))  ;itemCount 
                 (recur (inc count))))))

;;Checks if the customer received as a parameter exists
(defn checkIfCustomerExists
  [name]
  (some #(= name (second %)) app/custDB))

;;Finds the total sale for a customer if exists
(defn findTotalSale
  [custName]
  (println "Name provided:" custName)
  
  (if (checkIfCustomerExists custName)
    (println (str custName "'s total bill is: "
         (format "%.2f"
                 (apply +
                        (for [sale app/salesDB
                              :when (= (sale 1) (app/customersReduced custName))]
                          (*
                           (read-string ((nth app/prodDB (- (parse-long (sale 2)) 1)) 2))
                           (read-string (sale 3))))))                      
         "$"))
    (println "The customer does not exist. Try again.")))

;;Checks if product provided as parameter exists in the prodDB
(defn checkIfProductExists
  [name]
  (some #(= name (second %)) app/prodDB))

;;Finds the product sale count of a product
(defn findProductSaleCount [prodName]
  (if (checkIfProductExists prodName)
    (println (str prodName " sold: "
         (apply +
                (for [sale app/salesDB
                      :when (= (sale 2) (app/productsReduced prodName))]
                  (read-string (sale 3))))))
    (println "\nThe product: " prodName "was not found. Please try again.\n")))


