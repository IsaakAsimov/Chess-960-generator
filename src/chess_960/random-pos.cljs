(ns chess-960.randompos)


(def board (vec (take 8 (repeat nil))))

(defn options []
  {:bishop  (rand-nth [0 2 4 6])
   :bishop2 (rand-nth [1 3 5 7])
   :queen   (rand-nth [0 1 2 3 4 5])
   :knight  (rand-nth [0 1 2 3 4])
   :knight2 (rand-nth [0 1 2 3])})

(defn nth-empty [n list]
  (let [clear (filterv identity
                       (map #(if (not %)
                               %2
                               nil)
                            list (range)))]
    (nth clear n)))

(defn fill-b [list]
  (let [B-in  (assoc list (:bishop (options)) "B")
        B2-in (assoc B-in (:bishop2 (options)) "B")
        Q-in  (assoc B2-in (nth-empty (:queen (options)) B2-in) "Q")
        N-in  (assoc Q-in (nth-empty (:knight (options)) Q-in) "N")
        N2-in (assoc N-in (nth-empty (:knight2 (options)) N-in) "N")
        R-in  (assoc N2-in (nth-empty 0 N2-in) "R")
        R2-in (assoc R-in (nth-empty 1 R-in) "R")
        K-in  (assoc R2-in (nth-empty 0 R2-in) "K")]
    K-in))

(defn random-pos []
  (fill-b board))