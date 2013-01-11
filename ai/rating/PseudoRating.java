package rating;

import java.util.HashMap;
import java.util.Random;
import java.util.LinkedList;

/**
 * Diese Klasse stellt eine Pseudobewertende Funktion bereit, 
 * um die Funktionalitaet der Datenstruktur im Vorhinein testen zu koennen.
 * Diese Bewertung kann zum besseren Testeinsatz einen Random-Wert zurückgeben. 
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
	 * @param situation
	 * @return
	 */
	public byte ratePositiv(HashMap<Integer, String> situation, byte player){
		
		return (byte)rand.nextInt(255);
		
	}
	
	/**
	 * Diese Methode bewertet die Situation und ersetzt in dieser Version das Kuenstliche Neuronale Netz.
	 * @param situation
	 * @return
	 */
	public byte rate(HashMap<Integer, String> situation){
		values.add(4);
		values.add(12);
		values.add(7);
		values.add(10);
		values.add(5);
		values.add(6);
		values.add(1);
		values.add(2);
		values.add(3);
		
		Vorzeichen = rand.nextBoolean();
		
		//return Vorzeichen ? (byte)(rand.nextInt(10)) : (byte)-(rand.nextInt(10));
		return (byte)(int)values.pollFirst();
		
	}
	
}
