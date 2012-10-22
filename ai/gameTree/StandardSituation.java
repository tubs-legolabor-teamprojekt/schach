package gameTree;

/**
 * Diese Klasse beinhaltet die StandardSituation (zu Beginn eines Spieles) als Array-Of-Short. Quasi das frisch aufgebaute Spielfeld.
 * @author tobi
 *
 */
public class StandardSituation {

	/**
	 * Array-Of-Short, dass das Spielfeld beschreibt
	 */
	private static final short[] STANDARD_SITUATION = 
	{
		65,    //Bauer a2
		73,    //Bauer b2
		81,    //Bauer c2
		89,    //Bauer d2
		97,    //Bauer e2
		105,   //Bauer f2
		113,   //Bauer g2
		121,   //Bauer h2
		128,   //Turm a1
		184,   //Turm h1
		200,   //Springer b1
		240,   //Springer g1
		272,   //Läufer c1
		296,   //Läufer f1
		344,   //Dame d1
		416,   //König e1
		582,   //Bauer a7
		590,   //Bauer b7 
		598,   //Bauer c7
		606,   //Bauer d7
		614,   //Bauer e7
		622,   //Bauer f7
		630,   //Bauer g7
		638,   //Bauer h7
		647,   //Turm a8
		703,   //Turm h8
		719,   //Springer b8
		759,   //Springer g8
		791,   //Läufer c8
		815,   //Läufer f8
		863,   //Dame d8
		935    //König e8
	};
	
	/**
	 * Getter-Methode für das private array
	 * @return
	 */
	public static short[] get_STANDARD_SITUATION(){
		return STANDARD_SITUATION;
	}
	
}
