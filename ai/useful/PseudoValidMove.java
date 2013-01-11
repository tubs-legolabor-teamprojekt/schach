package useful;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Pseudoklasse zur ValidMove
 * 
 * Diese Klasse sollte instanziierbar bleiben, um mehrere Threads versorgen zu können.
 * 
 * @author tobi
 *
 */
public class PseudoValidMove {

	/**
	 * Gibt eine Lite mit 10 identischen Elementen zurück...
	 * Nur zum Test der Alpha-Beta-Suche
	 * @param list
	 * @return
	 */
	public LinkedList<HashMap<Integer, String>> move (HashMap<Integer, String> list){
		
		/*
		 * Erstellt eine neue Liste, die vom generischen Typen ABTree ist
		 */
		LinkedList<HashMap<Integer, String>> liste = new LinkedList<HashMap<Integer, String>>();
		
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		
		/*
		 * die übergebene Situation wird vervielfältigt und in die neue Liste gespeichert 
		 */
		for(int i = 0 ; i < 10 ; i++){
			liste.add(map);
		}
		
		/*
		 * Die nun gefülte Liste mit 10 identischen Situationen wird 
		 * an die aufrufende Methode zurückgegeben
		 */
		return liste;
	}
	
}
