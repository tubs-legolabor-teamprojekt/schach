package rating;

import java.util.Random;

/**
 * Diese Klasse stellt eine Pseudobewertende Funktion bereit, 
 * um die Funktionalitaet der Datenstruktur im Vorhinein testen zu koennen.
 * Diese Bewertung kann zum besseren Testeinsatz einen Random-Wert zurückgeben. 
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
	public static byte ratePositiv(short[] situation, byte player){
		
		return (byte)rand.nextInt(255);
		
	}
	
	/**
	 * Diese Methode bewertet die Situation und ersetzt in dieser Version das Kuenstliche Neuronale Netz.
	 * @param situation
	 * @return
	 */
	public static byte rate(short[] situation){
		
		Vorzeichen = rand.nextBoolean();
		
		return Vorzeichen ? (byte)(rand.nextInt(255)) : (byte)-(rand.nextInt(255)); 
		
	}
	
}
