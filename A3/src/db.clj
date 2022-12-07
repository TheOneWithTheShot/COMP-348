(ns db
  (:require app)
  (:gen-class))

(def nbOfEntriesCust
  (count app/custDB))

;;Prints the customers entries
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
                         (nth (nth (nth entries count) 0) 0)  ;custID
                         (nth (nth (nth entries count) 0) 1)  ;name
                         (nth (nth (nth entries count) 0) 2)  ;address
                         (nth (nth (nth entries count) 0) 3)  ;phoneNumber
                         ))
        (recur (inc count))))))

(def nbOfEntriesProd 
  (count app/prodDB))

;;Prints the product entries
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
                         (nth (nth (nth entries count) 0) 0)  ;prodID
                         (nth (nth (nth entries count) 0) 1)  ;itemDescription
                         (nth (nth (nth entries count) 0) 2)  ;unitCost
                         ))
        (recur (inc count))))))


(defn findSalesEntryForCustomerSearch
  [count]
  (- (read-string (nth (nth (nth app/salesDB count) 0) 1)) 1)
)

(defn findSalesEntryForProductSearch
  [count]
  (- (read-string (nth (nth (nth app/salesDB count) 0) 2)) 1)
)

(def nbOfEntriesSales
  (count app/salesDB))

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
                         (nth (nth (nth entries count) 0) 0)  ;salesID
                         (nth (nth (nth app/custDB (findSalesEntryForCustomerSearch count)) 0) 1)  ;custID
                         (nth (nth (nth app/prodDB (findSalesEntryForProductSearch count)) 0) 1)  ;prodID
                         (nth (nth (nth entries count) 0) 3)  ;itemCount
                         ))
        (recur (inc count)))))
)

(defn checkIfExists
  [name]
    (some #(= name %) app/custDB))

(defn findTotalSale
  [name]
  (println "Name provided:" name)
  
  (if (checkIfExists name)
    ()
    (println "The customer does not exist. Try again."))
)
