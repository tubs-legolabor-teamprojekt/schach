package gameTree;

import game.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import useful.MoveGenerator;
import util.ChessfigureConstants;
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
    private AlphaBetaSearch search;
    
    // Instanz der MoveGenerator-Klasse
    private MoveGenerator moveGen;
    
    // Instanz einer LinkesList, in der jeweils die erste Kindgeneration gespeichert ist
    private LinkedList<HashMap<Integer, Byte>> liste;
    
    //Array zur Bewertung der einzelnen Einträge aus der Liste    
    ArrayList<Integer> rate = new ArrayList<Integer>();
    
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
        
        //HashMap<Integer, Byte> zusammenbauen
        //Alle Kindsituationen der ersten Generatino erstellen
        HashMap<Integer, Byte> map = new HashMap<Integer, Byte>();
        byte value;
        for (int i = 1; i <= 64; i++) {
            value = 0;
            if(field.getCurrentFieldAsHashMap().containsKey(i)){
                value = field.getCurrentFieldAsHashMap().get(i).getFigureType();
                value += (field.getCurrentFieldAsHashMap().get(i).getColor())*8; //Color=1 für Schwarz??
                value += (field.getCurrentFieldAsHashMap().get(i).isMoved()) ? 16 : 0; 
                map.put(i, value);
            }
        }
        
        //Bewertung der Kindgenerationen aus der Liste
        liste = moveGen.generateMoves(map, (byte) (player==true?1:0) );        
        for (int i = 1; i < liste.size(); i++) {            
            rate.add(search.max(liste.get(i), 5, player?0:1, -100, 100));            
        }
        
        //Stelle der am besten bewerteten Situation in der ArrayList
        int help = rate.isEmpty() ? 0 : rate.get(0);
        for (int i = 0; i < rate.size(); i++) {
            if(rate.get(i)> help){
                help=i;
            }
        }
        
        //Zug aus Feldern extrahieren
        
        
        // Erstellung eines Move-Objektes zur rückgabe an die aufrufende Methode
        move = new Move();
        
        
        // Rückgabe des Move-Objektes
        return move;
    }
    
//################################################################################# Getter/Setter
    
}
