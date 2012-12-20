package alphaBeta;

import java.util.LinkedList;
import java.util.*;

import rating.Rating;
import rating.Rating.*;
import useful.pseudoValidMove;

/**
 * Dieses ist der erste Test zum Aplha-Beta-CutOff zur Generierung des Spielbaumes.
 * Der Pseudocode stammt aus Wikipedia und wird nach und nach auf unsere Situation angepasst.
 * @author tobi
 *
 */
public class AlphaBetaSearch{ // Nacht erster Ueberlegung nicht Multi-Thread-faehig!!

	// ############################################# Instanzvariablen
	
	Rating rate = new Rating();
	pseudoValidMove move = new pseudoValidMove();
	
	/*
	 * Maximale Tiefe. Diese Tiefe ist ausschlaggebend für die Staerke der KI verantwortlich. Je groeßer, desto besser.
	 */
	static final byte DEPTH = 3;
	
	// ############################################# Alpha-Beta-Cut-Off

	public int alphaBeta( HashMap<Integer, String> situation, int tiefe, int alpha, int beta ) {
		
		if (tiefe == 0 /* || keineZuegeMehr(spieler)*/ ){
			return rate.rate(situation);
		}
		
		int best = -9999999;
		
		LinkedList<HashMap<Integer, String>> liste = move.move(situation);	// TODO Liste mit Werten füllen
		
		// TODO zur Optimierung: Felder sortieren
		
		while( !liste.isEmpty() /* TODO Noch Kindsituationen vorhanden*/){
			int Wert = -alphaBeta(liste.pollFirst(), tiefe-1, -beta, -alpha);
			
		}
		
		return 0;// TODO Größter Wert
	}
	// ############################################# Weitere Methoden
	public void setSituation(HashMap<Integer, String> sit){
		this.situation = sit;
	}
}
