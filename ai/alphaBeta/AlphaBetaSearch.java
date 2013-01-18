package alphaBeta;

import java.util.LinkedList;
import java.util.*;

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

    PseudoRating rate = new PseudoRating();
    PseudoValidMove move = new PseudoValidMove();
    public double count = 0;

    // ############################################# Alpha-Beta-Cut-Off

    /*
     * Da NegaMax Variante: Tiefenabhängig. Bewertungen ungerader tiefe:
     * negativ, Bewertungen gerader Tiefe: positiv.
     */
    public int alphaBeta(HashMap<Integer, String> situation, int depth, int player, int alpha, int beta) {

        //System.out.println("tiefe " + depth /* +" alpha: "+alpha+" beta: "+beta */);

        if (depth == 0 /* || keineZuegeMehr(spieler) */) {
            count++;
            byte rating = rate.rate(situation, player);
            //System.out.println(" bewertung " + rating);
            //System.out.println("zähler "+count);
            return (int) rating;
        }

        int maxValue = alpha;

        LinkedList<HashMap<Integer, String>> liste = move.move(situation); // TODO
                                                                           // Liste
                                                                           // mit
                                                                           // Werten
                                                                           // füllen

        // TODO zur Optimierung: Felder sortieren

        while (!liste.isEmpty()) { /* TODO Noch Kindsituationen vorhanden */
            int value = -alphaBeta(liste.pollFirst(), depth - 1, -player, -beta, -maxValue);
            if (value > maxValue) { // hier nicht ">=", weil: Wenn es keine
                                    // Verbesserung ist, brauch ich nicht
                                    // schauen...
                maxValue = value;
                if (maxValue >= beta) { // Beta-CutOff //hier ">=", weil: hier
                                        // kann ich abbrechen, bei gleichheit.
                                        // bei value>=maxvalue hätte zuvor ein
                                        // cutoff stattgefunden
                    //System.out.println("Cut in Tiefe " + depth);
                    break;
                }
            }
        }
        // System.out.println("maxValue: "+maxValue);
        return maxValue;// TODO Größter Wert
    }
    // ############################################# Weitere Methoden
}
