package alphaBeta;

import java.util.LinkedList;
import java.util.*;

import rating.PseudoRating;
import useful.PseudoValidMove;

/**
 * Dieses ist der erste Test zum Aplha-Beta-CutOff zur Generierung des Spielbaumes.
 * Der Pseudocode stammt aus Wikipedia und wird nach und nach auf unsere Situation angepasst.
 * @author tobi
 *
 */
public class AlphaBetaSearch{ // Nacht erster Ueberlegung nicht Multi-Thread-faehig!!

	// ############################################# Instanzvariablen
	
	PseudoRating rate = new PseudoRating();
	PseudoValidMove move = new PseudoValidMove();
		
	// ############################################# Alpha-Beta-Cut-Off

	public int alphaBeta( HashMap<Integer, String> situation, int tiefe, int alpha, int beta ) {
		System.out.println(tiefe);
		if (tiefe == 0 /* || keineZuegeMehr(spieler)*/ ){
			return rate.rate(situation);
		}
		
		int bestValue = alpha;
		
		LinkedList<HashMap<Integer, String>> liste = move.move(situation);	// TODO Liste mit Werten füllen
		
		// TODO zur Optimierung: Felder sortieren
		
		while( !liste.isEmpty() /* TODO Noch Kindsituationen vorhanden*/){
			int Value = -alphaBeta(liste.pollFirst(), tiefe-1, -beta, -alpha);
			if(Value > bestValue){
				bestValue = Value;
				if(bestValue >= beta){ //Beta-CutOff
					System.out.println("Cut");
					break;
				}
			}
		}
		
		return 0;// TODO Größter Wert
	}
	// ############################################# Weitere Methoden
}
