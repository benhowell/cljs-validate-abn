;;if valid, returns correctly formatted ABN, else nil
;;NOTE: only validates that the ABN conforms to spec!
(defn- validate-abn [abn]
  (let [weight [10 1 3 5 7 9 11 13 15 17 19]
        r-abn (clojure.string/replace (str abn) #"[^\d]" "")
        result (atom 0)]
    (if (= (count r-abn) 11)
      (do
        (run!
         (fn [i]
           (let [di (js/parseInt (nth r-abn i))]
             (reset! result (+ @result (* (nth weight i) (if (= i 0) (dec di) di))))))
         (range (count r-abn)))
        (if (= (mod @result 89) 0) (js/parseInt r-abn)
            ;;else invalid
            nil))
      ;; else invalid
      nil)))
