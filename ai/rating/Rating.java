package rating;

import java.util.HashMap;
import java.util.Random;

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
public class Rating {

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
		
		Vorzeichen = rand.nextBoolean();
		
		return Vorzeichen ? (byte)(rand.nextInt(255)) : (byte)-(rand.nextInt(255)); 
		
	}
	
}
