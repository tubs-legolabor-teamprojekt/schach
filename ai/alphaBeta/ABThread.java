package alphaBeta;

import rating.Rating;
import useful.pseudoValidMove;

/**
 * Diese Klasse stellt einen Thread dar, der auf einem Spielbaum agiert und
 * den Alpha-Beta-Wert für ihn ermittelt.
 * 
 * @author tobi
 *
 */
public class ABThread extends Thread{

	/*
	 * Instanz des Baumes, der von diesem Thread berechnet werden soll
	 */
	ABTree tree;
	
	/*
	 * wichtig???
	 */
	Rating rate = new Rating();
	
	/*
	 * Instanz der Klasse, die die aus aktueller Situation Folgesituationen erstellt
	 */
	pseudoValidMove valid = new pseudoValidMove();
		
	/*
	 * Gibt die maximale Tiefe der einzelnen Bäume an. 
	 * Gerade Zahlen sind Gegnerzüge am Ende.
	 */
	byte depth = 2;
		
//	/**
//	 * Konstruktor, der dem Thread einen festen ABTree anhängt.
//	 * Dieser kann dann auf genau diesen Teilbaum zugreifen.
//	 * @param abtree
//	 */
//	public ABThread(ABTree abtree){
//		this.tree = abtree;
//	}

	/**
	 * run-Methode, die den Thread startet.
	 * Hier wird die Rekursion eingeleitet.
	 */
	public void run(){
		tree.beta = (short)alphaBeta(1, this.depth, -999999, 999999);
	}
	
	public void setTree(ABTree tree){
		this.tree = tree;
	}
	
	/*
	 * Rekursive Methode zur Rückgabe einer Bewertung für die 
	 */
	public int alphaBeta(int spieler, int depth, int alpha, int beta){
		
		if(deph == 0){
			return rate.rate(this.)
		}
	}
	
}
