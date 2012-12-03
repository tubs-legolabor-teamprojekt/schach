package useful;

import java.util.LinkedList;

import alphaBeta.ABTree;

/**
 * Pseudoklasse zur ValidMove
 * 
 * Diese Klasse sollte instanziierbar bleiben, um mehrere Threads versorgen zu können.
 * 
 * @author tobi
 *
 */
public class pseudoValidMove {

	/**
	 * Gibt eine Lite mit 40 identischen Elementen zurück...
	 * Nur zum Test der Alpha-Beta-Suche
	 * @param list
	 * @return
	 */
	public LinkedList<ABTree> move(short[] list){
		
		/*
		 * Erstellt eine neue Liste, die vom generischen Typen ABTree ist
		 */
		LinkedList<ABTree> liste = new LinkedList<ABTree>();
		
		/*
		 * die übergebene Situation wird vervielfältigt und in die neue Liste gespeichert 
		 */
		for(int i = 0 ; i < 40 ; i++){
			ABTree tree = new ABTree(list);
			liste.add(tree);
		}
		
		/*
		 * Die nun gefülte Liste mit 40 identischen Situationen wird 
		 * an die aufrufende Methode zurückgegeben
		 */
		return liste;
	}
	
}
