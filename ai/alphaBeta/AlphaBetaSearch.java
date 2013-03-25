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

        LinkedList<HashMap<Integer, Byte>> liste = move.generateMoves(situation, (byte)player);
        
        if (depth == 0 || liste.isEmpty()) {
            count++;
            int rating = rate.primRate(situation);
            return rating;
        }

        int maxValue = alpha;        

        while (!liste.isEmpty()) {
            int value = -negaMax(liste.pollFirst(), depth - 1, player==0?1:0, -beta, -maxValue);
                        
            if (value > maxValue) {
                maxValue = value;
                if (maxValue >= beta) {
                    break;
                }
            }   
        }
        return maxValue;
    }
    
    public int alphaBeta(HashMap<Integer,Byte> situation, int depth, int player, int alpha, int beta) {

        LinkedList<HashMap<Integer, Byte>> liste = move.generateMoves(situation, (byte)player);
        
        if (depth == 0 || liste==null || liste.isEmpty()) {
            count++;
            int rating=0;
            try{
                rating = rate.primRate(situation);
            }catch(NullPointerException e){
                e.printStackTrace();
                System.out.println("Hier ist was falsch gelaufen!");
            }
            return rating+depth;
        }
        boolean gefunden = false;
        int maxValue = -10000, value;    
        
        while (!liste.isEmpty()) {
            if(gefunden){
                value = -alphaBeta(liste.pollFirst(), depth - 1, player==0?1:0, -alpha-1, -alpha);
                if(value > alpha && value < beta){
                    value = -alphaBeta(liste.pollFirst(), depth - 1, player==0?1:0, -beta, -value);
                }
            }else{
                value = -alphaBeta(liste.pollFirst(), depth - 1, player==0?1:0, -beta, -alpha);
            }
            if (value > maxValue){
                if (value >= beta)
                    return value;
                maxValue = value;
                if (value > alpha){
                    alpha = value;
                    gefunden = true;
                }
            }
        }        
        return maxValue;
    }
    
    public int alphaBeta_2(HashMap<Integer, Byte> situation, int depth, int alpha, int beta, int player){
        LinkedList<HashMap<Integer, Byte>> liste = move.generateMoves(situation, (byte)player);
        if(depth == 0/* || liste==null || liste.isEmpty()*/){
            count++;
            int rating = 0;
            rating = rate.primRate(situation);
            return rating;
        }
        if(player==0){
            int help = 0;
            while(!liste.isEmpty()){
                help = alphaBeta_2(liste.pollFirst(), depth - 1, alpha, beta, player==0?1:0);
                if(help>alpha){
                    alpha=help;
                }
                if(beta <= alpha){
                    break;
                }
            }
            return alpha;
        } else {
            int help = 0;
            while(!liste.isEmpty()){
                help = alphaBeta_2(liste.pollFirst(), depth - 1, alpha, beta, player==0?1:0);
                if(help<beta){
                    beta=help;
                }
                if(beta <= alpha){
                    break;
                }
            }    
            return beta;
        }
    }
    
    
    /*
     * Normale implementierung der AlphaBeta-Suche
     */
    public int min(HashMap<Integer,Byte> situation, int depth, int player, int alpha, int beta) {
        LinkedList<HashMap<Integer, Byte>> liste = move.generateMoves(situation, ChessfigureConstants.WHITE);
        if (depth == 0 || liste.isEmpty()) {
            count++;
            int rating = rate.primRate(situation, (byte)player);
            return rating;
        }
        int minValue = beta;
        while (!liste.isEmpty()) {
            int value = max(liste.pollFirst(), depth - 1, player==0?1:0, alpha, minValue);
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
        LinkedList<HashMap<Integer, Byte>> liste = move.generateMoves(situation, ChessfigureConstants.BLACK);
        if (depth == 0 || liste.isEmpty()) {
            count++;
            int rating = rate.primRate(situation, (byte)player);
            return rating;
        }
        int maxValue = alpha;
        while (!liste.isEmpty()) {
            int value = min(liste.pollFirst(), depth - 1, player==0?1:0, -beta, -maxValue);
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
