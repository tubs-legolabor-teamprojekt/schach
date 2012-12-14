package gameTree;

import java.util.ArrayList;

/**
 * Diese Klasse stellt einen Spielbaum dar, der sich selbst rekursiv aufbauen
 * kann. Auf Wunsch auch mithilfe mehrerer Threads.
 * 
 * @author tobi
 * 
 */
public class GameTree2 extends Thread
{

    /*
     * hierin werden die sub-Bume abgelegt
     */
    private ArrayList<GameTree2> List = new ArrayList<GameTree2>(1);

    /*
     * speichert die Spielsituation
     */
    private short[] situation;

    /*
     * Soll einst die Bewertung der Situation enthalten
     */
    byte rating = 0;

    /*
     * statische Baumtiefe
     */
    static final byte DEPTH = 3;

    /*
     * Diese Nummer steht fr den Namen des Threads zur berwachung der
     * Nebenlufigkeit.
     */
    private byte numberOfThread;

    /*
     * Farbe des naechsten zuges
     */
    private boolean white;

    /**
     * Konstruktor der Klasse GameTree
     * 
     * @param sit
     */
    protected GameTree2(short sit, boolean white) {
        // this.situation = sit;
        this.white = white;
    }

    /**
     * Diese Methode soll einen einmaligen Aufruf der Rekursion durchfhren.
     * Keine Treads
     * 
     * @param white
     */
    protected void build_Tree(boolean white) {
        // build_Tree_Rek((byte) 0, white, );
    }

    /**
     * Diese Methode baut rekursiv einen Spielbaum unter der aktuellen Situation
     * auf.
     * 
     * @param depth
     * @param white
     */
    private void build_Tree_Rek(byte depth, boolean white, short[] sit) {

        /*
         * aktuelle Situation speichern
         */
        short[] situation = sit;

        /*
         * Situationen erstellen
         */
        short[] help;
        help = ValidMove2.ValMove(situation, white);

        /*
         * Situationen als Objekte von GameTree abspeichern
         */
        for (byte i = 0; i < help.length; i++) {
            this.List.add(new GameTree2(help[i], white));
        }

        /*
         * rekursiver Aufruf, falls verlangte Tiefe noch nicht erreicht
         */
        if (depth < DEPTH) {

            for (byte i = 0; i < List.size(); i++) {

                List.get(i).build_Tree_Rek((byte) (depth + 1), !white,
                        situation);

            }

        }

    }

}
