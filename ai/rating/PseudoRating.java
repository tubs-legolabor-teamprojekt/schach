package rating;

import java.util.HashMap;
import java.util.Random;
import java.util.LinkedList;

/**
 * Diese Klasse stellt eine Pseudobewertende Funktion bereit, um die
 * Funktionalitaet der Datenstruktur im Vorhinein testen zu koennen. Diese
 * Bewertung kann zum besseren Testeinsatz einen Random-Wert zurückgeben.
 * 
 * Klasse sollte instanziierbar bleiben, um mehrere threads versorgen zu können.
 * 
 * @author tobi
 * 
 */
public class PseudoRating {

    LinkedList<Integer> values = new LinkedList<Integer>();
    boolean Vorzeichen;
    Random rand = new Random();

    /**
     * Diese Methode bewertet die Situation mit ausschließlich positiven Werten
     * und ersetzt in dieser Version das Kuenstliche Neuronale Netz.
     * 
     * @param situation
     * @return
     */
    public byte ratePositiv(HashMap<Integer, String> situation, byte player) {

        return (byte) rand.nextInt(255);

    }

    /**
     * Diese Methode bewertet die Situation und ersetzt in dieser Version das
     * Kuenstliche Neuronale Netz.
     * 
     * @param situation
     * @return
     */
    public byte rate(HashMap<Integer, String> situation, int player) {
        /*
         * values.add(-17); values.add(0); values.add(-7);
         * 
         * values.add(-13); values.add(-15); values.add(-18);
         * 
         * values.add(-7); values.add(-6); values.add(-10);
         * 
         * values.add(-15); values.add(-18); values.add(-7);
         * 
         * values.add(-3); values.add(-7); values.add(-10);
         * 
         * //values.add(-10); //cuttoff: 1,3
         * 
         * values.add(-17); values.add(-10); values.add(-8);
         * 
         * values.add(-13); values.add(-11); values.add(-12);
         * 
         * values.add(-17); //cutoff: 0,-3
         */
        /*
         * values.add(13); values.add(15); values.add(18);
         * 
         * values.add(10);
         * 
         * values.add(15); values.add(18); values.add(7);
         */

        // Vorzeichen = rand.nextBoolean();

        return (byte) (player * (rand.nextInt(100)));
        // return (byte)(int)values.pollFirst();

    }

}
