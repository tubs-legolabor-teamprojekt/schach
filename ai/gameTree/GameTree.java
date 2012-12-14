package gameTree;

import java.util.ArrayList;

/**
 * Diese Klasse stellt einen Spielbaum dar, der sich selbst rekursiv aufbauen
 * kann. Auf Wunsch auch mithilfe mehrerer Threads.
 * 
 * @author tobi
 * 
 */
public class GameTree extends Thread {

    /*
     * hierin werden die sub-Bume abgelegt
     */
    private ArrayList<GameTree> List = new ArrayList<GameTree>(1);

    /*
     * speichert die Spielsituation
     */
    private short[] situation;

    /*
     * Zhlt die Aufrufe der Rekursion, um einen etwaigen berblick zu geben
     */
    static private byte zaehler = 0;

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
    protected GameTree(short[] sit, byte num, boolean white) {
        this.situation = sit;
        this.numberOfThread = num;
        this.white = white;
    }

    /**
     * Stellt die run-Methode dar. Die startet einen nicht-nebenlufigen Thread.
     * 
     * @param white
     */

    public void run() {
        build_Tree_Rek((byte) 1, this.white);
        System.out.println("ready" + this.numberOfThread);
    }

    /**
     * Diese Methode soll einen einmaligen Aufruf der Rekursion durchfhren.
     * Keine Treads
     * 
     * @param white
     */
    protected void build_Tree(boolean white) {
        build_Tree_Rek((byte) 0, white);
        System.out.println("Zahl der Aufrufe: " + zaehler);
    }

    /**
     * Diese Methode soll einen Aufruf mit beliebiger Menge an Threads
     * ausfhren.
     * 
     * @param white
     */
    protected void build_Tree_pool(boolean white) {

        /*
         * Situationen erstellen
         */
        short[][] help;
        help = validMove.ValMove(this.situation, white);

        /*
         * Situationen als Objekte von GameTree abspeichern
         */
        for (byte i = 0; i < help.length; i++) {
            this.List.add(new GameTree(help[i], (byte) i, white));
        }

        /*
         * Iteration der Liste und Aufruf der zugehrigen Treads
         */
        for (byte i = 0; i < List.size(); i++) {
            List.get(i).start();
            System.out.println("start " + i);
        }

        System.out.println(zaehler + " " + List.size());
    }

    /**
     * Diese Methode baut rekursiv einen Spielbaum unter der aktuellen Situation
     * auf.
     * 
     * @param depth
     * @param white
     */
    private void build_Tree_Rek(byte depth, boolean white) {

        /*
         * Soll die Menge der Aufrufe zhlen
         */
        zaehler++;

        /*
         * Situationen erstellen
         */
        short[][] help;
        help = validMove.ValMove(this.situation, white);

        /*
         * Situationen als Objekte von GameTree abspeichern
         */
        for (byte i = 0; i < help.length; i++) {
            this.List.add(new GameTree(help[i], (byte) i, white));
        }

        /*
         * rekursiver Aufruf, falls verlangte Tiefe noch nicht erreicht
         */
        if (depth < DEPTH) {

            for (byte i = 0; i < List.size(); i++) {

                List.get(i).build_Tree_Rek((byte) (depth + 1), !white);

            }

        }

    }

}
