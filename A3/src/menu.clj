(ns menu
	(:require [db :as db]))

(defn display-menu []
	(repeatedly 80 #(println "."))
	(println "********* Sales Menu *********")
	(println "------------------------------")
	(println "  1. Display Customer Table   ")
	(println "  2. Display Product Table    ")
	(println "  3. Display Sales Table      ")
	(println "  4. Total Sales for Customer ")
	(println "  5. Total Sales for Product  ")
	(println "  6. Exit                     ")
	(println "------------------------------")
)

(defn press-enter-to-continue []
	(println "Press Enter to continue")
	(read-line)
)

(defn process-choice [choice]
	(when (= choice "1") 
		(db/read-file-customer "cust.txt")
		(press-enter-to-continue)
	)
	(when (= choice "2") 
		(db/read-file-product "prod.txt")
		(press-enter-to-continue)
	)
	(when (= choice "3") 
		(println "Under Construction")
		(press-enter-to-continue)
	)
	(when (= choice "4") 
		(println "Under Construction")
		(press-enter-to-continue)
	)
	(when (= choice "5") 
		(println "Under Construction")
		(press-enter-to-continue)
	)
)

(defn loop-display-menu [exit-option]
	(display-menu)
	(def choice (read-line))
	
	(process-choice choice)
	
	(loop [count 0]
	  (if (= choice exit-option)
		(println "Good-Bye!")
		(do
		  (display-menu)
		  (def choice (read-line))
		
		  (process-choice choice)

		  (recur (inc count))))))

(defn -main [ ]
	(println "Entering main program")
	;(display-menu)
	(loop-display-menu "6")
	;(def choice (read-line))
	;(println choice)
	;(db/read-file-product "prod.txt")
)


(-main)
;(db/hello)