package rating;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import alphaBeta.AlphaBetaSearch;
import useful.Fingerprint;
import useful.MoveGenerator;
import useful.SituationWithRating;
import util.ChessfigureConstants;

public class PrimitivKI implements Serializable {
    

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private LinkedList<SituationWithFingerprint> situationsWithFingerprint;
    private MoveGenerator moveGen = new MoveGenerator();
    final int PARALLEL = 2;

    public PrimitivKI() {
        this.situationsWithFingerprint = new LinkedList<SituationWithFingerprint>();
    }

    /*
     * Über die eingebettete Klasse ist es Moeglich, einem Fingerprint die
     * moeglichen Zuege zuzuordnen. Dies ist noetig, damit dieses Objekt in eine
     * Liste gehängt werden kann, welche dann nach Fingerprints durchsucht wird.
     */
    class SituationWithFingerprint implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        String fingerprint;
        LinkedList<SituationWithRating> situations;
        int depth;

        public SituationWithFingerprint(String fingerprint, LinkedList<SituationWithRating> situations, int depth) {
            this.fingerprint = fingerprint;
            this.situations = situations;
        }

        public void setNode(String fingerprint, LinkedList<SituationWithRating> situations, int depth)
        {
            this.fingerprint = fingerprint;
            this.situations = situations;
            this.depth = depth;
        }

        public String getFingerprint()
        {
            return this.fingerprint;
        }

        public LinkedList<SituationWithRating> getSituation()
        {
            return this.situations;
        }

        public int getDepth()
        {
            return this.depth;
        }
    }

    /*
     * Fuer die Deserialisierung; Auslesen aller gepeicherten Werte
     */
    public LinkedList<SituationWithFingerprint> getList()
    {
        return this.situationsWithFingerprint;
    }

    /*
     * Es wird eine bestimmte Situation via Fingerprint in den gespeicherten
     * Werten gesucht.
     * 
     * @fingerprint Fingerprint des aktuellen Spielfeldes
     * 
     * @return LinkedList mit Objekten des Typs SituationWithRating oder NULL
     * wenn keine solche Liste existiert
     */
    public LinkedList<SituationWithRating> getSituations(String fingerprint)
    {
        for (SituationWithFingerprint fp : situationsWithFingerprint) {
            if (fp.getFingerprint().equals(fingerprint)) {
                return fp.getSituation();
            }
        }
        return null;
    }

    /*
     * Es wird eine Situation berechnet und eingespeichert
     * 
     * @param map aktuelles Spielfeld als Hashmap
     * 
     * @param depth Wieviele Halbzuege im Vorraus gerechnet werden soll
     * 
     * @param Spieler der am Zug ist
     */
    public void teachSituation(HashMap<Integer, Byte> map, int depth, byte player)
    {
        HashMap<Integer, Byte> cloneMap = (HashMap<Integer, Byte>) map.clone();

        LinkedList<HashMap<Integer, Byte>> childSit = moveGen.generateMoves(cloneMap, player);
        LinkedList<SituationWithRating> list = new LinkedList<SituationWithRating>();
        while (!childSit.isEmpty()) {
            list.add(new SituationWithRating(childSit.pollLast(), 0));
        }

        byte changePlayer = (player == ChessfigureConstants.WHITE ? ChessfigureConstants.BLACK : ChessfigureConstants.WHITE);
        LinkedList<SituationWithRating> ratedList = rateChildSituations(changePlayer, list, depth);

        LinkedList<SituationWithRating> bestMaps = new LinkedList<SituationWithRating>();
        int max = findMaxRating(ratedList);

        SituationWithRating rating;
        while (ratedList.size() > 0) {
            rating = ratedList.pollFirst();
            if (rating.getRating() == max) {
                bestMaps.add(rating);
            }
        }
        situationsWithFingerprint.add(new SituationWithFingerprint(Fingerprint.getFingerprint(cloneMap), bestMaps, depth));
    }

    /*
     * Maximal bewertete Situation wird rausgesucht
     * 
     * @param map Hashmap der Situation
     * 
     * @return maximale Wert
     */
    private int findMaxRating(LinkedList<SituationWithRating> map) throws NullPointerException
    {
        LinkedList<SituationWithRating> cloneMap = (LinkedList<SituationWithRating>) map.clone();
        if (cloneMap.size() > 0) {
            int max = cloneMap.pollFirst().getRating();
            int rating;
            while (cloneMap.size() > 0) {
                rating = cloneMap.pollFirst().getRating();
                if (max < rating) {
                    max = rating;
                }
            }
            return max;
        } else {
            throw new NullPointerException("Leere Map uebergeben");
        }
    }

    /*
     * Methode in der die Threads initialisiert werden und die AlphaBeta Suche
     * gestartet wird
     */
    private LinkedList<SituationWithRating> rateChildSituations(byte player, LinkedList<SituationWithRating> list, int depth)
    {

        AlphaBetaSearch[] abThreads = new AlphaBetaSearch[list.size()];

        for (int i = 0; i < list.size(); i++) {
            // Thread erstellen mit SituationWithRating,depth,player
            abThreads[i] = new AlphaBetaSearch(list.get(i), depth, player);
            abThreads[i].setName("" + i);
        }

        // startet eine bestimmte Anzahl an Threads
        orderedThreadStart(abThreads, PARALLEL);

        LinkedList<SituationWithRating> helpList = new LinkedList<SituationWithRating>();

        for (AlphaBetaSearch ab : abThreads) {
            helpList.add(ab.getSituationWithRating());
        }

        return helpList;
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
    private boolean orderedThreadStart(AlphaBetaSearch[] ab, int parallelValue)
    {
        System.out.println("Anzahl an Wurzeln " + ab.length);

        /*
         * wenn anzahl der möglichen Threads kleiner ist als maximale Anzahl
         * gleichzeitiger Threads dann gleich alle starten
         */
        if (parallelValue >= ab.length) {
            // System.out.println("0");
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

    /*
     * Schreibt das aktuelle Objekt in eine Datei
     * 
     * @param filename Datei in welche geschrieben werden soll inkl. Pfad (z.B.
     * Pfad/datei.ser)
     */
    public void serialize(String filename)
    {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream o = new ObjectOutputStream(file);
            o.writeObject(this);
            o.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /*
     * Ueberschreibt das aktuelle Objekt mit dem serialisiertem Objekt
     * 
     * @param filename Pfad zum serialisiertem Objekt (z.B. Pfad/datei.ser)
     */
    public void deserialize(String filename)
    {
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream o = new ObjectInputStream(file);
            PrimitivKI ki = (PrimitivKI) o.readObject();
            o.close();
            this.situationsWithFingerprint = ki.getList();
        } catch (IOException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
    }

}
