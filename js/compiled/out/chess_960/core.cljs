(ns chess-960.core
  (:require [reagent.core :as reagent :refer [atom]]
            [chess-960.randompos :refer [random-pos]]))


(defn board-create []
  (let [wpos  (random-pos)
        bpos  (mapv #(str % "b") wpos)
        wp    (vec (take 8 (repeat "P")))
        bp    (mapv #(str % "b") wp)
        empty (vec (take 8 (repeat nil)))]
    (vector bpos bp empty empty empty empty wp wpos)))

(def board (atom (board-create)))

(defn wich-cell? [piece [y x]]
  (let [color (cond (and (even? y) (even? x)) :td.cell-white
                    (and (even? y) (odd? x))  :td.cell-black
                    (and (odd? y) (even? x))  :td.cell-black
                    (and (odd? y) (odd? x))   :td.cell-white)]
     (cond (= piece "K")  [color {:style {:background-image "url(img/white_king.png)"}}]
           (= piece "Q")  [color {:style {:background-image "url(img/white_queen.png)"}}]
           (= piece "R")  [color {:style {:background-image "url(img/white_rock.png)"}}]
           (= piece "N")  [color {:style {:background-image "url(img/white_knight.png)"}}]
           (= piece "B")  [color {:style {:background-image "url(img/white_bishop.png)"}}]
           (= piece "P")  [color {:style {:background-image "url(img/white_pawn.png)"}}]
           (= piece "Kb") [color {:style {:background-image "url(img/black_king.png)"}}]
           (= piece "Qb") [color {:style {:background-image "url(img/black_queen.png)"}}]
           (= piece "Rb") [color {:style {:background-image "url(img/black_rock.png)"}}]
           (= piece "Nb") [color {:style {:background-image "url(img/black_knight.png)"}}]
           (= piece "Bb") [color {:style {:background-image "url(img/black_bishop.png)"}}]
           (= piece "Pb") [color {:style {:background-image "url(img/black_pawn.png)"}}]
           :else          [color])))

(defn render-board []
  (let [board @board
        cells (map #(into [:tr] (map (fn [x h] (wich-cell? x [%2 h]))
                                     % (range 8)))
                   board (range 8))]
    (into [:table.stage] cells)))

(defn button-render []
  [:div
    [:button.NiceButton {:on-click #(swap! board board-create)} "Generate"]])

(defn show []
   [:div
     [:div {:style {:width "310"
                    :margin "auto"}}
       [:h2 "Chess-960 start-positions generator APP."]
       [render-board]
       [button-render]]])


(defn run []
  (reagent/render [show]
                  (js/document.getElementById "app")))

(run)
