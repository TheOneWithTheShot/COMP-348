(ns db
  (:require app)
  (:gen-class))

;;Prints the customers entries
(defn printCustomerTable
  [entries]

  (println "\n                      Customer table")
  (println "\n=============================================================")
  (println (format " ID |      %-10s |       %-14s |  %-5s " "Name" "Address" "Phone Number"))
  (println "=============================================================")
  
  (def num-entries (count entries))
  (loop [count 0]
    (if (= count num-entries)
      (println "\nAll the entries have been printed")
      (do
        (println (format " %s  | %-15s | %-20s | %-10s    "
                         (nth (nth (nth entries count) 0) 0)  ;custID
                         (nth (nth (nth entries count) 0) 1)  ;name
                         (nth (nth (nth entries count) 0) 2)  ;address
                         (nth (nth (nth entries count) 0) 3)  ;phoneNumber
                         ))
        (recur (inc count))))))

;;Prints the product entries
(defn printProductTable
  [entries]

  (println "\n     Product Table")
  (println "\n===========================")
  (println (format " ID |    %-6s  |  %5s" "Name" "Price"))
  (println "===========================")
  
  (def num-entries (count entries))
  (loop [count 0]
    (if (= count num-entries)
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

(defn printSalesTable
  [entries]

  (println "\n                       Sales table")
  (println "\n===========================================================")
  (println (format " ID |      %-10s |       %-14s | %-10s" "Name" "Product" "Item Count"))
  (println "===========================================================")

  (def nbOfEntries (count entries)) 
  
  (loop [count 0]
    (if (= count nbOfEntries)
      (println "\nAll the entries have been printed")
      (do
        (println (format " %s  | %-15s | %-20s | %s"
                         (nth (nth (nth entries count) 0) 0)  ;salesID
                         (nth (nth (nth app/custDB (findSalesEntryForCustomerSearch count)) 0) 1)  ;custID
                         (nth (nth (nth app/prodDB (findSalesEntryForProductSearch count)) 0) 1)  ;prodID
                         (nth (nth (nth entries count) 0) 3)  ;itemCount
                         ))
        (recur (inc count))))))

