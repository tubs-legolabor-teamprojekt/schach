package alphBeta;

import java.util.LinkedList;

import rating.Rating;
import rating.Rating.*;

/**
 * Dieses ist der erste Test zum Aplha-Beta-CutOff zur Generierung des Spielbaumes.
 * Der Pseudocode stammt aus Wikipedia und wird nach und nach auf unsere Situation angepasst.
 * @author tobi
 *
 */
public class AlphaBetaSearch{ // Nacht erster Ueberlegung nicht Multi-Thread-faehig!!

	// ############### Instanzvariablen
	
	/*
	 * Modellierte Situation. Soll einst durch eine Hash-Table ersetzt werden.
	 * Zum Zwecke des Testes nun jedoch noch mit der alten Modellierung.
	 */
	short[] situation;
	
	/*
	 * Maximale Tiefe. Diese Tiefe ist ausschlaggebend für die Staerke der KI verantwortlich. Je groeßer, desto besser.
	 */
	static byte depth = 3;
	
	/*
	 * Dieses Rating ist (bislang) nur für die erste Kind-Generation vorhanden. Anhand dieses wird der beste Zug ermittelt. 
	 */
	byte rating;
	
	/*
	 * Liste mit Folgesituationen, die durchlaufen werden 
	 */
	LinkedList<short[]> list;
	
	/*
	 * Zaehler für die untersuchten Objekte
	 */
	static int number = 0;
	
	// ############### Aufrufende public-Methode
	
	public static short getNextMove(short[] Situation){
		
		/*
		 * Erstelle alle Kind-Situationen und speichere sie in einer LinkedList
		 */
		
		/*
		 *  Durchlaufe die LinkedList und rufe für jedes Element die Rekursive Funtion auf.
		 *  Dies macht das alles auch Multi-Thread-faehig 
		 */
		
		/*
		 * waehle den am besten bewerteten Zug aus und gebe ihn in den output
		 */
		
		AlphaBetaSearch search = new AlphaBetaSearch(Situation);
		
		return 0; //Beliebiger short, der den Zug wiederspiegelt
		
	}
	
	// ############### Konstruktor
	
	public AlphaBetaSearch(short[] situation){
		this.situation = situation;
		
		System.out.println( this.miniMax(1, this.depth, 0, 0) );
	}
	
	// ############### Alpha-Beta-Cut-Off

	public int miniMax(int spieler, int tiefe, int alpha, int beta) {
		
		// zahler erhoehen
		this.number++;
		
		// Falls die maximale Tiefe erreicht ist, oder es keine Spielzuege mehr zu tun gibt
		if (tiefe == 0 /* || keineZuegeMehr(spieler)*/ ){
			
			//Bewerte die in diesem Objekt steckende Situation
			
			return Rating.ratePositiv(this.situation, (byte) spieler);
			
		}	
		
		int maxWert = alpha;
		
		// Fuelle die Liste der potentiellen KindSituationen
		// Besser sogar als Sortierte Liste im Sinne der Situationen (Turm schlaegt Dame VOR Bauer schlaegt Turm)
		generiereMoeglicheZuege(spieler);
		
		// Durchlaufe die Liste der moeglichen Züge von vorn nach hinten
		while (noch Zug da) {
			
			// Aufruf der Rekursion mit dem nächsten Kindzug
			
			int wert = -miniMax(-spieler, tiefe-1,-beta, -maxWert);		//	REKURSION
			
			
			// Gebe gefundenen Wert nach oben durch, falls gefundener Wert groeßer als bisher maximaler
			if (wert > maxWert) {
				
				maxWert = wert;
				
				// Bedingung für den Cut-Off
				if (maxWert >= beta){
					
					// Cut-Off
					break;
					
				}
				
				// Falls Bewertungen vorgenommen wurden
				if (tiefe == this.depth){
					
					// KEINE AHNUNG, WAS DAS TUT
					gespeicherterZug = Zug;
					
				}
				
			}
			
		}
		
		// Gebe den gefundenen maximalen Wert zurück in die uebergeordnete Rekursionsebene
		return maxWert;
		
	}
	
	// ############### Weitere Methoden
}
