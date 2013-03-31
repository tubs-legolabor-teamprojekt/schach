package alphaBeta;

import java.util.*;
import util.*;
import rating.PrimitivRating;
import useful.MoveGenerator;
import useful.SituationWithRating;

/**
 * Dieses ist der erste Test zum Aplha-Beta-CutOff zur Generierung des
 * Spielbaumes. Der Pseudocode stammt aus Wikipedia und wird nach und nach auf
 * unsere Situation angepasst.
 * 
 * @author tobi
 * 
 */
public class AlphaBetaSearch extends Thread {

    private PrimitivRating rate = new PrimitivRating();
    private MoveGenerator move = new MoveGenerator();
    public int count = 0;
    private SituationWithRating situation;
    private int depth,player,alpha,beta;
    private static int INFINITY = 2147483647;
    private static int numberOfThreads = 0;

    public void run() {
    	System.out.println("Thread Nummer \t"+this.getName()+"\t gestartet");
    	numberOfThreads++;
    	this.situation.setRating(min(this.situation.getMap(),this.depth,this.player,this.alpha,this.beta));
    	numberOfThreads--;
    	System.out.println("Thread Nummer \t"+this.getName()+"\t beendet");
    }
    
    public AlphaBetaSearch(SituationWithRating situation, int depth, int player) {
    	this.situation = situation;
    	this.depth = depth;
    	this.player = player;
    	this.alpha = -INFINITY;
    	this.beta = INFINITY;
    }
    
    public SituationWithRating getSituationWithRating() {
    	return this.situation;
    }
    
    public int getNumberOfThreads() {
    	return numberOfThreads;
    }
    
    /**
     * Implementierung der AlphaBeta Suche.
     * @param situation aktuelles Spielfeld; davon ausgehend wird gesucht
     * @param depth Anzahl der Zuege, welche im vorraus berechnet werden sollen
     * @param player Welcher Spieler gerade am Zug ist (nicht benoetigt?)
     * @param alpha untere Grenze für den Cut
     * @param beta obere Grenze für den Cut
         */
    private int min(HashMap<Integer,Byte> situation, int depth, int player, int alpha, int beta) {
        LinkedList<HashMap<Integer, Byte>> list = move.generateMoves(situation, ChessfigureConstants.WHITE);
        int rating;
        
        //wenn Liste leer ist, liegt Schachmatt oder Pattstellung vor
        if (depth == 0 || list.isEmpty()) {
        	if(list.isEmpty()) {
        		rating = rate.primRate(situation,player,depth,true);
        	}
        	else {
        		rating = rate.primRate(situation,player,depth,false);
        	}
            return rating;
        }
        int minValue = beta;
        while (!list.isEmpty()) {
            int value = max(list.pollFirst(), depth - 1, player==ChessfigureConstants.WHITE?ChessfigureConstants.BLACK:ChessfigureConstants.WHITE, alpha, minValue);
            if (value < minValue) {
                minValue = value;
                if (minValue <= alpha) {
                    break;
                }
            }
        }
        return minValue;
    }

    private int max(HashMap<Integer,Byte> situation, int depth, int player, int alpha, int beta) {
        LinkedList<HashMap<Integer, Byte>> list = move.generateMoves(situation, ChessfigureConstants.BLACK);
        int rating;
        if (depth == 0 || list.isEmpty()) {
        	
        	//wenn Liste leer ist, liegt Schachmatt oder Pattstellung vor
        	if(list.isEmpty()) {
        		rating = rate.primRate(situation,player,depth,true);
        	}
        	else {
        		rating = rate.primRate(situation,player,depth,false);
        	}
            return rating;
        }
        int maxValue = alpha;
        while (!list.isEmpty()) {
            int value = min(list.pollFirst(), depth - 1, player==ChessfigureConstants.WHITE?ChessfigureConstants.BLACK:ChessfigureConstants.WHITE, maxValue, beta );
            if (value > maxValue) {
                maxValue = value;
                if (maxValue >= beta) {
                    break;
                }
            }
        }
        return maxValue;
    }

/*
    public int negaMax(HashMap<Integer,Byte> situation, int depth, int player, int alpha, int beta) {

        LinkedList<HashMap<Integer, Byte>> liste = move.generateMoves(situation, (byte)player);
        if(liste.size()==0) {
            return player==0?-9999:9999;	
            }
        if (depth == 0 ) {
            count++;
            int rating = rate.primRate(situation,(byte)player);
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
        //System.out.println(liste.size());
        if(liste.size()==0) {
        return player==0?-9999:9999;	
        }
        
        if (depth == 0) {
            count++;
            int rating=0;
            try{
                rating = rate.primRate(situation,(byte)player);
            }catch(NullPointerException e){
                e.printStackTrace();
                System.out.println("Hier ist was falsch gelaufen!");
            }
            return rating;
        }
        boolean gefunden = false;
        int maxValue = -10000, value;    
        
        while (!liste.isEmpty()) {
            if(gefunden){
            	
                value = -alphaBeta(liste.getFirst(), depth - 1, player==0?1:0, -alpha-1, -alpha);
                if(value > alpha && value < beta){
                    value = -alphaBeta(liste.getFirst(), depth - 1, player==0?1:0, -beta, -value);
                }
                liste.pollFirst();
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
        if(liste.size()==0) {
            return player==0?-9999:9999;	
        }
        if(depth == 0){
            count++;
            int rating = 0;
            rating = rate.primRate(situation);
            return rating;
        }
        if(player==1){
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
    */
}
