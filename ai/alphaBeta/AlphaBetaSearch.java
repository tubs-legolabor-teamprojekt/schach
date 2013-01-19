package alphaBeta;

import java.util.*;

import dataStructure.ChessField;

import dataStructure.ChessField;

import rating.PrimitivRating;
import rating.PseudoRating;
import useful.PseudoValidMove;

/**
 * Dieses ist der erste Test zum Aplha-Beta-CutOff zur Generierung des
 * Spielbaumes. Der Pseudocode stammt aus Wikipedia und wird nach und nach auf
 * unsere Situation angepasst.
 * 
 * @author tobi
 * 
 */
public class AlphaBetaSearch { // Nacht erster Ueberlegung nicht
                               // Multi-Thread-faehig!!

    // ############################################# Instanzvariablen


    PrimitivRating rate = new PrimitivRating();
    PseudoValidMove move = new PseudoValidMove();
    public double count = 0;
    public static final int NUMBER_OF_FIGURES = 10;

    
    
    /*
     * Da NegaMax Variante: Tiefenabhängig. Bewertungen ungerader tiefe:
     * negativ, Bewertungen gerader Tiefe: positiv.
     */
    public int alphaBeta(ChessField situation, int depth, int player, int alpha, int beta) {

//        System.out.println("tiefe " + depth /* +" alpha: "+alpha+" beta: "+beta */);

        if (depth == 0 /* || keineZuegeMehr(spieler) */) {
            count++;
//            int rating = rate.primRate(situation);
            int rating = rate.primRate(situation);
//            System.out.println(" bewertung " + rating);
//              System.out.println("zähler "+count);
            return rating;
        }

        int maxValue = alpha;

        LinkedList<ChessField> liste = move.getMoves(situation, NUMBER_OF_FIGURES); 
//        TODO Liste mit Werten fuellen
//        TODO zur Optimierung: Felder sortieren

        while (!liste.isEmpty()) {          // TODO Noch Kindsituationen vorhanden 
            int value = -alphaBeta( (ChessField)liste.pollFirst(), depth - 1, player, -beta, -maxValue);
            if (value > maxValue) { 
                maxValue = value;
                if (maxValue >= beta) { 
                                            // System.out.println("Cut in Tiefe " + depth);
                    break;
                }
            }
        }
//        System.out.println("maxValue: "+maxValue);
        return maxValue;                    // TODO Größter Wert
    }
}
