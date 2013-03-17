package alphaBeta;

import java.util.*;
import util.*;
import dataStructure.ChessField;
import dataStructure.ChessField;
import rating.PrimitivRating;
import useful.PseudoValidMove;
import useful.MoveGenerator;

/**
 * Dieses ist der erste Test zum Aplha-Beta-CutOff zur Generierung des
 * Spielbaumes. Der Pseudocode stammt aus Wikipedia und wird nach und nach auf
 * unsere Situation angepasst.
 * 
 * @author tobi
 * 
 */
public class AlphaBetaSearch {

    PrimitivRating rate = new PrimitivRating();
    //PseudoValidMove move = new PseudoValidMove(); //ausgedachte Züge
    MoveGenerator move = new MoveGenerator();
    public int count = 0;
    public static final int NUMBER_OF_FIGURES = 20;

    /*
     * Implementierung der AlphaBeta Suche als NegaMax Variante.
     * @param situation aktuelles Spielfeld; davon ausgehend wird gesucht
     * @param depth Anzahl der Zuege, welche im vorraus berechnet werden sollen
     * @param player Welcher Spieler gerade am Zug ist (nicht benoetigt?)
     * @param alpha untere Grenze für den Cut
     * @param beta obere Grenze für den Cut
     */
   /*
    
    public int negaMax(ChessField situation, int depth, int player, int alpha, int beta) {
        // System.out.println("tiefe " + depth 
        // +" alpha: "+alpha+" beta: "+beta );

        if (depth == 0 ) {
            count++;
            // int rating = rate.primRate(situation);
            int rating = rate.primRate(situation);
            // System.out.println(" bewertung " + rating);
            // System.out.println("zähler "+count);
            return rating;
        }

        int maxValue = alpha;

        LinkedList<ChessField> liste = move.getMoves(situation, NUMBER_OF_FIGURES);
        // TODO Liste mit Werten fuellen
        // TODO zur Optimierung: Felder sortieren

        while (!liste.isEmpty()) { // TODO Noch Kindsituationen vorhanden
            int value = -negaMax((ChessField) liste.pollFirst(), depth - 1, player, -beta, -maxValue);
            if (value > maxValue) {
                maxValue = value;
                if (maxValue >= beta) {
                    // System.out.println("Cut in Tiefe " + depth);
                    break;
                }
            }
        }
        // System.out.println("maxValue: "+maxValue);
        return maxValue; // TODO Größter Wert
    } */

    
    
    /*
     * Normale implementierung der AlphaBeta-Suche
     */
    public int min(HashMap<Integer,Byte> situation, int depth, int player, int alpha, int beta) {

        if (depth == 0 /* || keineZuegeMehr(spieler) */) {
            count++;
            int rating = rate.primRate(situation);
            return rating;
        }

        int minValue = beta;

        //LinkedList<ChessField> liste = move.getMoves(situation, NUMBER_OF_FIGURES);
        LinkedList<HashMap<Integer, Byte>> liste = move.generateMoves(situation, ChessfigureConstants.WHITE);
        

        while (!liste.isEmpty()) {
            int value = max(/*(ChessField)*/ liste.pollFirst(), depth - 1, player, alpha, minValue);
            if (value < minValue) {
                minValue = value;
                if (minValue <= alpha) {
                    break;
                }
            }
        }
        return minValue;
    }

    public int max(HashMap<Integer,Byte> situation, int depth, int player, int alpha, int beta) {

        if (depth == 0 /* || keineZuegeMehr(spieler) */) {
            count++;
            int rating = rate.primRate(situation);
            return rating;
        }

        int maxValue = alpha;

        LinkedList<HashMap<Integer, Byte>> liste = move.generateMoves(situation, ChessfigureConstants.WHITE);

        while (!liste.isEmpty()) {
            int value = min(/*(ChessField)*/ liste.pollFirst(), depth - 1, player, -beta, -maxValue);
            if (value > maxValue) {
                maxValue = value;
                if (maxValue >= beta) {
                    break;
                }
            }
        }
        return maxValue;
    }

}
