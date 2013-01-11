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
	    // System.out.println("tiefe "+tiefe);
		if (tiefe == 0 /* || keineZuegeMehr(spieler)*/ ){
			byte rating = rate.rate(situation);
			// System.out.println(" bewertung "+rating);
		    return (int)rating;
		}
		
		int maxValue = alpha;
		
		LinkedList<HashMap<Integer, String>> liste = move.move(situation);	// TODO Liste mit Werten füllen
		
		// TODO zur Optimierung: Felder sortieren
		
		while( !liste.isEmpty() ){                                            /* TODO Noch Kindsituationen vorhanden*/
			int value = -alphaBeta(liste.pollFirst(), tiefe-1, -beta, -maxValue);
			if(value > maxValue){
				maxValue = value;
				if(maxValue >= beta){ //Beta-CutOff
					System.out.println("Cut in Tiefe "+tiefe);
					break;
				}
			}
		}
		//System.out.println("maxValue: "+maxValue);
		return maxValue;// TODO Größter Wert
	}
	// ############################################# Weitere Methoden
}
