package gameTree;

import game.Move;
import components.Field;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import rating.PrimitivKI;
import rating.PrimitivRating;
import useful.*;
import util.ChessfigureConstants;
import alphaBeta.AlphaBetaSearch;

/**
 * Klasse, die als Hauptklasse der AI dienen soll. Hier sind die Aufrufe für den
 * Game-Coordinator zu finden! Diese Klasse muss instanziiert werden. Auf diese
 * Weise können auch mehrere Klassen erstellt werden, um an schließend die KI
 * trainiren zu können.
 * 
 * @author tobi
 * 
 */
public class NextMove {

    // #################################################################################
    // Klassenvariable

    // Instanz der MoveGenerator-Klasse
    private MoveGenerator moveGen;

    // Instanz einer LinkesList, in der jeweils die erste Kindgeneration
    // gespeichert ist
    private LinkedList<SituationWithRating> list;

    // HashMaps zum Vergleich und zur Rückgabe
    private HashMap<Integer, Byte> beforeField;
    private HashMap<Integer, Byte> afterField;
    private PrimitivKI ki;
    private PrimitivRating prim;

    private static int INFINITY = 2147483647;

    // Anzahl parallel laufender Threads (2 ist zumindest auf meinem MAC optimal
    private final int PARALLEL = 2;
    // Suchtiefe, TODO: später automatisch an Situation anpassen lassen
    private final int DEPTH = 1;
    private final boolean TEACHINGMODE = true;
    private final String PATH = "ki.ser";

    // #################################################################################
    // Konstruktor

    /**
     * Konstruktor der Klasse NextMove Hier werden die nötigen Instanzen
     * initialisiert
     */
    public NextMove() {
        this.moveGen = new MoveGenerator();

    }

    private boolean isGameOver(HashMap<Integer, Byte> field, byte player)
    {
        LinkedList<HashMap<Integer, Byte>> childSit = moveGen.generateMoves(field, player);
        
        return childSit.isEmpty();
    }

    /**
     * Hauptmethode der Klasse, die vom Game-Coordinator aufgerufen werden
     * sollte.
     * 
     * @param field
     * @param player
     * @return
     */
    public Move getNext(Field field, byte player)
    {
        File pfad = new File("");
        System.out.println(pfad.getAbsolutePath() + "...");

        // HashMap<Integer, Byte> zusammenbauen
        beforeField = field.getCurrentFieldAsHashMapWithBytes();
        
        HashMap<Integer,Byte> cloneMap = (HashMap<Integer,Byte>)beforeField.clone();
        
        if(isGameOver(beforeField, ChessfigureConstants.WHITE) || isGameOver(beforeField, ChessfigureConstants.BLACK)) {
            int position;
            Iterator<Entry<Integer, Byte>> it = cloneMap.entrySet().iterator();
            Entry<Integer,Byte> pair = it.next();
            position = pair.getKey();
            return new Move(ChessfigureConstants.WHITE, position, position, false, true, true);
       }
        
        ki = new PrimitivKI();
        ki.deserialize(PATH);
        prim = new PrimitivRating();

        String fingerprintBeforeField = Fingerprint.getFingerprint(beforeField);
        
        if (ki.getSituations(fingerprintBeforeField) != null && ki.getDepth(fingerprintBeforeField) >= DEPTH) {
            list = ki.getSituations(fingerprintBeforeField);
            findBestSituationWithPositionInListMax();
            // afterField = list.get(rn.nextInt(list.size())).getMap();

        } else if (TEACHINGMODE) {
            ki.teachSituation(beforeField, DEPTH, player);
            ki.serialize(PATH);
            list = ki.getSituations(fingerprintBeforeField);
            findBestSituationWithPositionInListMax();
            // afterField = list.get(rn.nextInt(list.size())).getMap();
        }

        else {
            doChildSituations(player);

            // Spiel zu ende?
            rateChildSituations(player == ChessfigureConstants.WHITE ? ChessfigureConstants.BLACK : ChessfigureConstants.WHITE);
            findBestSituationInListMax();
            prim.primPositionRating(list, player);
            findBestSituationWithPositionInListMax();
            // for (SituationWithRating sit : list) {
            // System.out.println("fig " + sit.getFigureRating() + " pos " +
            // sit.getPositionRating());
            // }
        }
        return HashMapToMove(beforeField, afterField, player);
    }

    private void findBestSituationWithPositionInListMax()
    {
        // Stelle der am besten bewerteten Situation in der ArrayList
        int figureHelp = -INFINITY;
        Random rn = new Random();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPositionRating() > figureHelp) {
                figureHelp = list.get(i).getPositionRating();
            }
        }

        for (int i = 0; i < list.size(); i += 0) {
            if (list.get(i).getPositionRating() != figureHelp) {
                list.remove(i);
                i--;
            }
            i++;
        }
        afterField = list.get(rn.nextInt(list.size())).getMap();
    }

    /**
     * Finde Situation im Feld, die am besten bewertet wurde
     */
    private void findBestSituationInListMax()
    {
        // Stelle der am besten bewerteten Situation in der ArrayList
        int figureHelp = -INFINITY;
        Random rn = new Random();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getFigureRating() > figureHelp) {
                figureHelp = list.get(i).getFigureRating();
            }
        }

        for (int i = 0; i < list.size(); i += 0) {
            if (list.get(i).getFigureRating() != figureHelp) {
                list.remove(i);
                i--;
            }
            i++;
        }

        // afterField = list.get(rn.nextInt(list.size())).getMap();
    }

    /**
     * Bewerte nacheinander alle in der Liste befindlichen Situationen
     * 
     * @param player
     */
    private void rateChildSituations(byte player)
    {

        AlphaBetaSearch[] abThreads = new AlphaBetaSearch[list.size()];

        for (int i = 0; i < list.size(); i++) {
            // Thread erstellen mit SituationWithRating,depth,player
            abThreads[i] = new AlphaBetaSearch(list.get(i), DEPTH, player);
            abThreads[i].setName("" + i);
        }

        // Variablen zur Zeitmessung um das Optimum aus der nebenlaeufigkeit
        // herauszuholen
        long time = System.currentTimeMillis();

        // startet eine bestimmte Anzahl an Threads
        orderedThreadStart(abThreads, PARALLEL);
        System.out.println("\n Zeit: " + (System.currentTimeMillis() - time) + " Anzahl paralleler Threads " + PARALLEL + "; Suchtiefe " + (DEPTH) + "\n");

        LinkedList<SituationWithRating> helpList = new LinkedList<SituationWithRating>();

        int i = 0;
        for (AlphaBetaSearch ab : abThreads) {
            helpList.add(ab.getSituationWithRating());
            System.out.printf("%-3d %d  ", i++, ab.getSituationWithRating().getFigureRating());
            System.out.println("Zug: " + HashMapMoveToText(beforeField, ab.getSituationWithRating().getMap(), player) + " ");
        }

        this.list = helpList;
    }

    /*
     * Es sollten nicht alle Suchen gleichzeitig als seperater Thread gestartet
     * werden, da das Scheduling ansonsten die Sache extrem verlangsamt. Die
     * Methode orderedThreadStart achtet darauf, dass immer nur eine zuvor
     * bestimmte Anzahl an Threads gleichzeitig laufen. Wenn diese beendet
     * werden, dann startet diese Methode automatisch die naechsten Threads aus
     * dem Array AlphaBetaSearch.
     * 
     * @param ab Array von Threads des Typ AlphaBetaSearch
     * 
     * @param parallelValue Anzahl an gleichzeitig laufenden Threads
     */
    public boolean orderedThreadStart(AlphaBetaSearch[] ab, int parallelValue)
    {
        System.out.println("Anzahl an Wurzeln " + ab.length);

        /*
         * wenn anzahl der möglichen Threads kleiner ist als maximale anzahl
         * gleichzeitiger Threads dann gleich alle starten
         */
        if (parallelValue >= ab.length) {
            System.out.println("0");
            for (int i = 0; i < ab.length; i++) {
                ab[i].start();
            }
        } else {
            int counter = ab.length - 1;

            // solange Zuege da sind
            while (counter >= 0) {
                // duerfen noch Threads gestartet werden oder laufen genug
                // parallel?
                if (ab[0].getNumberOfThreads() < parallelValue) {
                    ab[counter].start();
                    counter--;
                }
                // ein wenig abwarten, damit schleife nicht komplett cpu
                // auslastet
                try {
                    Thread.sleep(1 * parallelValue);
                } catch (Exception e) {
                }
            }
        }
        // letzten Threads die noch laufen beenden lassen
        while (ab[0].getNumberOfThreads() != 0) {
            try {
                Thread.sleep(1 * parallelValue);
            } catch (Exception e) {
            }
        }
        return true;
    }

    /**
     * Fülle die Liste mit allen möglichen Kindsituationen
     * 
     * @param player
     */
    private void doChildSituations(byte player)
    {
        LinkedList<HashMap<Integer, Byte>> childSit = moveGen.generateMoves(beforeField, player);
        LinkedList<SituationWithRating> listConversion = new LinkedList<SituationWithRating>();
        while (!childSit.isEmpty()) {
            listConversion.add(new SituationWithRating(childSit.pollLast(), 0, 0));
        }
        list = listConversion;

    }

    /**
     * Finde aus dem Unterschied zwischen "beforeMap" und afterMap" ein
     * Move-Objekt
     * 
     * @param map1
     * @param map2
     * @param color
     * @return
     */
    private Move HashMapToMove(HashMap<Integer, Byte> before, HashMap<Integer, Byte> after, byte player)
    {
        Iterator<Entry<Integer, Byte>> it1 = before.entrySet().iterator();
        Iterator<Entry<Integer, Byte>> it2 = after.entrySet().iterator();
        Integer[] posBefore = new Integer[before.size()];
        Byte[] figBefore = new Byte[before.size()];
        Integer[] posAfter = new Integer[after.size()];
        Byte[] figAfter = new Byte[after.size()];

        /*
         * Variablen zur Erstellung des Move-Objektes
         */
        int from = 0, to = 0;
        byte colorOfPlayer = player;
        boolean captured = false;
        // TODO Rochade

        int itCount = 0;
        while (it1.hasNext()) {
            Map.Entry<Integer, Byte> entry1 = (Map.Entry<Integer, Byte>) it1.next();
            posBefore[itCount] = entry1.getKey();
            figBefore[itCount] = entry1.getValue();
            itCount++;
        }// while
        itCount = 0;
        while (it2.hasNext()) {
            Map.Entry<Integer, Byte> entry2 = (Map.Entry<Integer, Byte>) it2.next();
            posAfter[itCount] = entry2.getKey();
            figAfter[itCount] = entry2.getValue();
            itCount++;
        }// while

        /*
         * Auswertung der beiden Schachfelder, Unterscheidung nach
         * "einfacher Zug" und "Schlag"
         */
        if (posBefore.length == posAfter.length) {
            /*
             * Beide Felder gleiche Länge --> einfacher Zug, kein Schlag
             * 
             * if figBefore in posBefore und nicht figAfter in posAfter folgt
             * figBefore ist "from" if figAfter in posAfter und nicht figAfter
             * in posBefore folgt figAfter ist "to"
             */
            // TODO Bauernwumwandlung
            captured = false;
            boolean contains = false;

            for (int i = 0; i < posBefore.length; i++) {
                for (int j = 0; j < posAfter.length; j++) {
                    if (posBefore[i].equals(posAfter[j])) {
                        contains = true;
                    }
                }
                if (!contains) {
                    from = posBefore[i];
                }
                contains = false;
            }
            contains = false;
            for (int j = 0; j < posAfter.length; j++) {
                for (int i = 0; i < posBefore.length; i++) {
                    if (posBefore[i].equals(posAfter[j])) {
                        contains = true;
                    }
                }
                if (!contains) {
                    to = posAfter[j];
                }
                contains = false;
            }

        } else {
            /*
             * Beide Felder unterschiedliche Länge --> Zug und Schlag
             */
            captured = true;
            boolean contains = false;
            for (int i = 0; i < posBefore.length; i++) {
                for (int j = 0; j < posAfter.length; j++) {
                    if (posBefore[i].equals(posAfter[j])) {
                        contains = true;
                    }
                }
                if (!contains) {
                    from = posBefore[i];
                }
                contains = false;
            }
            for (int j = 0; j < posAfter.length; j++) {
                for (int i = 0; i < posBefore.length; i++) {
                    if ((posBefore[i].equals(posAfter[j])) && (figBefore[i].equals(figAfter[j]) == false)) {
                        to = posBefore[i];
                    }
                }
                contains = false;
            }
        }
        
        //Spielende (keine Unterscheidung Schachmatt/Patt)
        if (isGameOver(afterField, ChessfigureConstants.WHITE) || isGameOver(afterField, ChessfigureConstants.BLACK)) {
            return new Move(colorOfPlayer, from, to, captured, true, true);
        }
        else {
            return new Move(colorOfPlayer, from, to, captured, false, false);
        }

    }

    private String HashMapMoveToText(HashMap<Integer, Byte> map1, HashMap<Integer, Byte> map2, byte color)
    {
        Iterator<Entry<Integer, Byte>> it1 = map1.entrySet().iterator();
        Iterator<Entry<Integer, Byte>> it2 = map2.entrySet().iterator();
        Integer[] int1 = new Integer[map1.size()];
        Byte[] byte1 = new Byte[map1.size()];
        Integer[] int2 = new Integer[map2.size()];
        Byte[] byte2 = new Byte[map2.size()];

        /*
         * Variablen zur Erstellung des Move-Objektes
         */
        int from = 0, to = 0;
        byte colorOfPlayer = color;
        boolean captured = false;
        // TODO Rochade

        int itCount = 0;
        while (it1.hasNext()) {
            Map.Entry<Integer, Byte> entry1 = (Map.Entry<Integer, Byte>) it1.next();
            int1[itCount] = entry1.getKey();
            byte1[itCount] = entry1.getValue();
            itCount++;
        }// while
        itCount = 0;
        while (it2.hasNext()) {
            Map.Entry<Integer, Byte> entry2 = (Map.Entry<Integer, Byte>) it2.next();
            int2[itCount] = entry2.getKey();
            byte2[itCount] = entry2.getValue();
            itCount++;
        }// while

        /*
         * Auswertung der beiden Schachfelder, Unterscheidung nach
         * "einfacher Zug" und "Schlag"
         */
        if (int1.length == int2.length) {
            /*
             * Beide Felder gleiche Länge --> einfacher Zug, kein Schlag
             * 
             * iff key_1 in int1 und nicht key_1 in int2 folgt key_1 ist "from"
             * iff key_2 in int2 und nicht key_2 in int1 folgt key_2 ist "to"
             */
            // TODO Bauernwumwandlung
            captured = false;
            boolean contains = false;
            for (int i = 0; i < int1.length; i++) {
                for (int j = 0; j < int2.length; j++) {
                    if (int1[i].equals(int2[j])) {
                        contains = true;
                    }
                }
                if (!contains) {
                    from = int1[i];
                }
                contains = false;
            }
            contains = false;
            for (int j = 0; j < int2.length; j++) {
                for (int i = 0; i < int1.length; i++) {
                    if (int1[i].equals(int2[j])) {
                        contains = true;
                    }
                }
                if (!contains) {
                    to = int2[j];
                }
                contains = false;
            }

        } else {
            /*
             * Beide Felder unterschiedliche Länge --> Zug und Schlag
             */
            captured = true;
            boolean contains = false;
            for (int i = 0; i < int1.length; i++) {
                for (int j = 0; j < int2.length; j++) {
                    if (int1[i].equals(int2[j])) {
                        contains = true;
                    }
                }
                if (!contains) {
                    from = int1[i];
                }
                contains = false;
            }
            for (int j = 0; j < int2.length; j++) {
                for (int i = 0; i < int1.length; i++) {
                    if ((int1[i].equals(int2[j])) && (byte1[i].equals(byte2[j]) == false)) {
                        to = int1[i];
                    }
                }
                contains = false;
            }
        }
        String s = "";
        // return new Move(colorOfPlayer, from, to, captured);
        return s + from + " " + to;

    }

    // #################################################################################
    // Getter/Setter

}
