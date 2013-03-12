package gameTree;

import game.Move;
import java.util.HashMap;
import java.util.LinkedList;
import useful.MoveGenerator;
import alphaBeta.AlphaBetaSearch;

/**
 * Klasse, die als Hauptklasse der AI dienen soll. Hier sind die Aufrufe für den Game-Coordinator zu finden!
 * Diese Klasse muss instanziiert werden.
 * Auf diese Weise können auch mehrere Klassen erstellt werden, um an schließend die KI trainiren zu können.
 * @author tobi
 *
 */
public class NextMove {
   
//################################################################################# Klassenvariable
    
    // Instanz der Alpha-Beta-Klasse
    AlphaBetaSearch search;
    
    // Instanz der MoveGenerator-Klasse
    MoveGenerator moveGen;
    
    // Instanz einer LinkesList, in der jeweils die erste Kindgeneration gespeichert ist
    LinkedList<HashMap<Integer, Byte>> liste;
    
//################################################################################# Konstruktor
    
    /**
     * Konstruktor der Klasse NextMove
     * Hier werden die nötigen Instanzen initialisiert
     */
    public NextMove(){
        
        this.search = new AlphaBetaSearch();
        this.moveGen = new MoveGenerator();
        
    }
    
//################################################################################# Methoden

    /**
     * Hauptmethode der Klasse, die vom Game-Coordinator aufgerufen werden sollte.
     * @param field
     * @param player
     * @return
     */
    public game.Move getNext(components.Field field, boolean player){
        
        // Move-Objekt, das zurückgegeben werden soll.
        Move move;
        
        /*
         * -Field-Objekt entpacken
         * -Liste mit Kind-Situationen erstellen
         * -Jede Situation einzeln bewerten
         * -Beste Situation in Zug umwandeln und in "move" speichern
         */
        
        liste = moveGen.generateMoves(field., (byte) (player==true?1:0) );
        
        // Rückgabe des Move-Objektes
        move = new Move(0,1);
        return move;
    }
    
    /**
     * Diese Methode soll ausschließlich die erste Generation der Kind-Generationen erstellen,
     * die anschließend bewertet werden kann.
     * @param feld
     * @param player
     * @return
     */
    public LinkedList<HashMap<Integer, Byte>> getChilds(HashMap<Integer, Byte> feld, boolean player){
        
        // Rückgabe der nächsten Generation;       Umwandlung von Boolean in byte
        return moveGen.generateMoves(feld,  (byte) (player==true?1:0) );
        
    }
    
//################################################################################# Getter/Setter
    
}
