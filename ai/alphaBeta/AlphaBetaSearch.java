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
    MoveGenerator move = new MoveGenerator();
    public int count = 0;

    /**
     * Implementierung der AlphaBeta Suche als NegaMax Variante.
     * @param situation aktuelles Spielfeld; davon ausgehend wird gesucht
     * @param depth Anzahl der Zuege, welche im vorraus berechnet werden sollen
     * @param player Welcher Spieler gerade am Zug ist (nicht benoetigt?)
     * @param alpha untere Grenze für den Cut
     * @param beta obere Grenze für den Cut
     */    
    public int negaMax(HashMap<Integer,Byte> situation, int depth, int player, int alpha, int beta) {

        LinkedList<HashMap<Integer, Byte>> liste = move.generateMoves(situation, ChessfigureConstants.WHITE);
        
        if (depth == 0 || liste.isEmpty()) {
            count++;
            int rating = rate.primRate(situation);
            return rating;
        }

        int maxValue = alpha;        

        while (!liste.isEmpty()) { // TODO Noch Kindsituationen vorhanden
            int value = -negaMax(liste.pollFirst(), depth - 1, player, -beta, -maxValue);
                        
            if (value > maxValue) {
                maxValue = value;
                if (maxValue >= beta) {
                    break;
                }
            }
            
        }
        return maxValue; // TODO Größter Wert
    } 

    
    
    /*
     * Normale implementierung der AlphaBeta-Suche
     */
    public int min(HashMap<Integer,Byte> situation, int depth, byte player, int alpha, int beta) {

        if (depth == 0 /* || keineZuegeMehr(spieler) */) {
            count++;
            int rating = rate.primRate(situation, player);
            return rating;
        }

        int minValue = beta;

        LinkedList<HashMap<Integer, Byte>> liste = move.generateMoves(situation, ChessfigureConstants.WHITE);
        

        while (!liste.isEmpty()) {
            int value = max(/*(ChessField)*/ liste.pollFirst(), depth - 1, player==0?(byte)1:(byte)0, alpha, minValue);
            if (value < minValue) {
                minValue = value;
                if (minValue <= alpha) {
                    break;
                }
            }
        }
        return minValue;
    }

    public int max(HashMap<Integer,Byte> situation, int depth, byte player, int alpha, int beta) {

        if (depth == 0 /* || keineZuegeMehr(spieler) */) {
            count++;
            int rating = rate.primRate(situation, player);
            return rating;
        }

        int maxValue = alpha;

        LinkedList<HashMap<Integer, Byte>> liste = move.generateMoves(situation, ChessfigureConstants.BLACK);

        while (!liste.isEmpty()) {
            int value = min(/*(ChessField)*/ liste.pollFirst(), depth - 1, player==0?(byte)1:(byte)0, -beta, -maxValue);
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
