(ns db
	(:require [clojure.string :as str]))

(defn print-entries-customer 
	[entries]
	;(println entries)
	;(println (count entries) )
	(def num-entries (count entries))
	(loop [count 0]
		(if (= count num-entries)	
		(println "Listing complete!")
		(do
			(println (format "%s  | %-20s | %-30s | %-6s" 
				(nth (nth (nth entries count) 0) 0 )  ;id
				(nth (nth (nth entries count) 0) 1 )  ;name
				(nth (nth (nth entries count) 0) 2 )  ;address
				(nth (nth (nth entries count) 0) 3 )  ;phone number
				))
			(recur (inc count)))))

	)

(defn read-file-customer
	"reads given file"
	[filename]
	(let [file-contents (slurp filename)]
		(def file-contents-split (str/split-lines file-contents))
	)
	
	(def entries 
		(for [line file-contents-split]
			[(str/split line #"\|")]
		)
	)

	(println (format "Id | %-20s | %-30s | %-6s" "Name" "Address" "Phone#"))
	(println "---+----------------------+--------------------------------+------------")

	(print-entries-customer entries)
)

(defn print-entries-product 
	[entries]
	;(println entries)
	;(println (count entries) )
	(def num-entries (count entries))
	(loop [count 0]
		(if (= count num-entries)	
		(println "Listing complete!")
		(do
			(println (format "%s  | %-20s | %4s" 
				(nth (nth (nth entries count) 0) 0 )  ;id
				(nth (nth (nth entries count) 0) 1 )  ;name
				(nth (nth (nth entries count) 0) 2 )  ;phone number
				))
			(recur (inc count)))))

	)

(defn read-file-product
	"reads given file"
	[filename]
	(let [file-contents (slurp filename)]
		(def file-contents-split (str/split-lines file-contents))
	)
	
	(def entries 
		(for [line file-contents-split]
			[(str/split line #"\|")]
		)
	)

	(println (format "Id | %-20s | %4s" "Name" "Price"))
	(println "---+-------------------------------")

	(print-entries-product entries)
)

