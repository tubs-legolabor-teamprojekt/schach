package alphBeta;

import java.util.LinkedList;

/**
 * Dieses ist der erste Test zum Aplha-Beta-CutOff zur Generierung des Spielbaumes.
 * Der Pseudocode stammt aus Wikipedia und wird nach und nach auf unsere Situation angepasst.
 * @author tobi
 *
 */
public class AlphaBetaSearch extends Thread{

	// ############### Instanzvariablen
	
	/*
	 *  Modellierte Situation. Soll einst durch eine Hash-Table ersetzt werden.
	 *  Zum Zwecke des Testes nun jedoch noch mit der alten Modellierung.
	 */
	short[] situation;
	
	/*
	 * Maximale Tiefe. Diese Tiefe ist ausschlaggebend für die Staerke der KI verantwortlich. Je groeßer, desto besser.
	 */
	byte depth = 3;
	
	/*
	 * Dieses Rating ist (bislang) nur für die erste Kind-Generation vorhanden. Anhand dieses wird der beste Zug ermittelt. 
	 */
	byte rating;
	
	/*
	 * Liste mit Folgesituationen, die durchlaufen werden 
	 */
	LinkedList<short[]> list;
	
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
	}
	
	// ############### Alpha-Beta-Cut-Off

	int miniMax(int spieler, int tiefe, int alpha, int beta) {
		
		// Falls die maximale Tiefe erreicht ist, oder es keine Spielzuege mehr zu tun gibt
		if (tiefe == 0 or keineZuegeMehr(spieler)){
			
			//Bewerte die in diesem Objekt steckende Situation
			return bewerten(spieler);
			
		}	
		
		int maxWert = alpha;
		
		// Fuelle die Liste der potentiellen KindSituationen
		// Besser sogar als Sortierte Liste im Sinne der Situationen (Turm schlaegt Dame VOR Bauer schlaegt Turm)
		generiereMoeglicheZuege(spieler);
		
		// Durchlaufe die Liste der moeglichen Züge von vorn nach hinten
		while (noch Zug da) {
			
			// Aufruf der Rekursion mit dem nächsten Kindzug
			fuehreNaechstenZugAus();									//
			int wert = -miniMax(-spieler, tiefe-1,-beta, -maxWert);		//	REKURSION
			macheZugRueckgaengig();										//
			
			// Gebe gefundenen Wert nach oben durch, falls gefundener Wert groeßer als bisher maximaler
			if (wert > maxWert) {
				
				maxWert = wert;
				
				// Bedingung für den Cut-Off
				if (maxWert >= beta){
					
					// Cut-Off
					break;
					
				}
				
				// Falls Bewertungen vorgenommen wurden
				if (tiefe == anfangstiefe){
					
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
