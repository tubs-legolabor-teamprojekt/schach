package useful;

import java.util.HashMap;
import java.util.LinkedList;

import dataStructure.ChessField;

/**
 * Pseudoklasse zur ValidMove
 * 
 * Diese Klasse sollte instanziierbar bleiben, um mehrere Threads versorgen zu
 * können.
 * 
 * @author tobi
 * 
 */
public class PseudoValidMove {
    
    public static final int anzahl = 30;
    /**
     * Gibt eine Lite mit 10 identischen Elementen zurück... Nur zum Test der
     * Alpha-Beta-Suche
     * 
     * @param list
     * @return
     */
    public LinkedList<ChessField> move(ChessField list) {

        /*
         * Erstellt eine neue Liste, die vom generischen Typen ABTree ist
         */
        LinkedList<ChessField> liste = new LinkedList<ChessField>();

        ChessField map = new ChessField();
        map.equipStartField();

        /*
         * die übergebene Situation wird vervielfältigt und in die neue Liste
         * gespeichert
         */

        for (int i = 1; i <= anzahl; i++) {

            liste.add(map);
        }

        /*
         * Die nun gefülte Liste mit 10 identischen Situationen wird an die
         * aufrufende Methode zurückgegeben
         */
        return liste;
    }

}
