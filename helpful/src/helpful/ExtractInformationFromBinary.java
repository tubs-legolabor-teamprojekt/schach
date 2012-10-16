package helpful;
/**
 * Diese Klasse bietet die Moeglichkeit aus einem gegebenen Short-Wert
 * die beinhaltenden Informationen zu extrahieren.
 * 
 * @author Florian Franke
 *
 */
public class ExtractInformationFromBinary {

	/**
	 * Wandelt den uebergebenen Short-Wert in Binaer um.
	 * @param sh Short-Wert
	 * @return String der Binaer-Zahl des Short-Werts
	 */
	public static String shortToBinary(short sh)
	{
		return Integer.toBinaryString(sh);
	}
	
	/**
	 * Wandelt den gegebenen Binaer-Wert per BitShifting in Binaerschreibweise um.
	 * Dient hier nur als Beispielmethode um Zeit zu sparen,
	 * falls es mal im eigenen Code gebraucht wird.
	 * Mit (1 << i) wird ein 1er-Bit um i-Schritte nach links verschoben.
	 * Also bei i=3 ergibt es 8 (1000)
	 * Anschlie§end wird der gegebene Short-Wert (sh) mit dem (1 << i)
	 * logisch verUNDed:
	 *   1000  = 8 (^=(1 << 3))
	 * & 1101  = 13
	 * ------
	 *    100  = 8 => Also eine 1 an der 3. Binaerstelle
	 * 
	 * @param sh Short-Wert
	 */
	public static void getBinaryByBitShifting(short sh)
	{
		for (int i=15; i>=0; i--)
			System.out.print( ((sh & (1 << i)) != 0) ? "1" : "0" );
	}
	
	/**
	 * Ermittelt die Schach-Figur aus dem Short-Wert
	 * @param sh Short-Wert mit der Position, Farbe und Figur
	 * @return Byte-Wert der Figur
	 */
	public static byte getFigure(short sh)
	{
		// Hole die ersten Drei und addiere sie
		byte value = 0;
		for (int i=6; i<9; i++) {
			if ((sh & (1 << i)) != 0) {
				// bit ist gesetzt, Wert ermitteln
				int currentBit = (sh & (1 << i));
				// ermittelte Wert befindet sich bei 2^6 bis 2^8
				// shift auf 2^0 bis 2^2
				value += (currentBit >> 6);
			}
		}
		return value;
	}
	
	/**
	 * Ermittelt die X-Position der Figur
	 * @param sh Short-Wert mit der Position, Farbe und Figur
	 * @return Byte-Wert der X-Position
	 */
	public static byte getXPosition(short sh)
	{
		// Hole die ersten Drei und addiere sie
		byte value = 0;
		for (int i=3; i<6; i++) {
			if ((sh & (1 << i)) != 0) {
				// bit ist gesetzt, Wert ermitteln
				int currentBit = (sh & (1 << i));
				// ermittelte Wert befindet sich bei 2^6 bis 2^8
				// shift auf 2^0 bis 2^2
				value += (currentBit >> 3);
			}
		}
		return value;
	}
	
	/**
	 * Ermittelt die Y-Position der Figur
	 * @param sh Short-Wert mit der Position, Farbe und Figur
	 * @return Byte-Wert der Y-Position
	 */
	public static byte getYPosition(short sh)
	{
		// Hole die ersten Drei und addiere sie
		byte value = 0;
		for (int i=0; i<3; i++) {
			if ((sh & (1 << i)) != 0) {
				// bit ist gesetzt, Wert ermitteln
				int currentBit = (sh & (1 << i));
				// ermittelte Wert befindet sich bei 2^6 bis 2^8
				// shift auf 2^0 bis 2^2
				value += currentBit;
			}
		}
		return value;
	}
	
	/**
	 * Ermittelt die Farbe der Figur
	 * @param sh Short-Wert mit der Position, Farbe und Figur
	 * @return Farbe der Figur (byte-Wert)
	 */
	public static byte getColor(short sh)
	{
		// Hole die ersten Drei und addiere sie
		byte currentBit = (byte)(sh & (1 << 9));
		return currentBit;
	}

}
