package alphaBeta;

import java.util.LinkedList;

public class ABTree {

	/*
	 * Enthält die weiteren ABTree's, die die Baumstruktur darstellen sollen
	 */
	LinkedList<ABTree> liste;
	
	/*
	 * Modelliert die Situation auf beliebige Weise. 
	 * Wird noch in eine HashMap geändert.
	 */
	short[] situation;
	
	/*
	 * Bewertungsvariablen für die Realisierung der Alpha-Beta-Suche.
	 * Sie werden im Baum gespeichert, um die Möglichkeit der einfachen Beschreibung zu gewährleisten
	 */
	public short alpha=0, beta=0;
	
	/**
	 * Konstruktor der Klasse ABTree
	 */
	public ABTree(){
		
	}
	
	/**
	 * neuer Konstruktor der Klasse
	 */
	public ABTree(short[] situation){
		this.situation = situation;
	}
	
}
