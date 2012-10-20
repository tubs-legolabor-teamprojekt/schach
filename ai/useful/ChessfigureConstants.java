package useful;

/**
 * Definiert alle Konstanten zur Kodierung der Figuren.
 * Jede Figur hat einen Integer-Wert zugewiesen bekommen (siehe docs)
 * und dieser wird mit einer Konstanten in Java verwendet.
 * 
 * @author Florian Franke
 *
 */
public final class ChessfigureConstants {

	// Bauer
	public static final int PAWN = 1;
	
	// Turm
	public static final int ROOK = 2;
	
	// Springer
	public static final int KNIGHT = 3;
	
	// Laeufer
	public static final int BISHOP = 4;
	
	// Dame
	public static final int QUEEN = 5;
	
	// Koenig
	public static final int KING = 6;
	
	
	/**
	 * Gibt den Namen der Figur des uebergebenen byte-Werts zurück.
	 * @param figureValue byte-Wert der Figur
	 * @return Name der Figur
	 */
	public static String getFigureName(byte figureValue) {
		String figure = "";
		
		switch(figureValue) {
		case PAWN:
			figure = "Bauer";
			
		case ROOK:
			figure = "Turm";
			break;
		
		case KNIGHT:
			figure = "Springer";
			break;
			
		case BISHOP:
			figure = "Laeufer";
			break;
			
		case QUEEN:
			figure = "Dame";
			break;
			
		case KING:
			figure = "Koenig";
			break;
			
		default:
			figure = "Ungueltig!";
			break;
		}
		
		return figure;
	}
	
	/**
	 * Gibt die Farbe der Figur zurück
	 * @param figureColor Wert der Farbe als byte
	 * @return Farbe der Figur
	 */
	public static String getFigureColor(byte figureColor)
	{
		return ( (figureColor==0) ? "Weiss" : "Schwarz");
	}
	
	/**
	 * Gibt die X-Position als Buchstabe aus
	 * @param xPos 
	 * @return
	 */
	public static char getXPosition(byte xPos)
	{
		char xPosChar = 'x';
		switch(xPos) {
		case 1:
			xPosChar = 'a';
			break;
			
		case 2:
			xPosChar = 'b';
			break;
			
		case 3:
			xPosChar = 'c';
			break;
			
		case 4:
			xPosChar = 'd';
			break;
			
		case 5:
			xPosChar = 'e';
			break;
			
		case 6:
			xPosChar = 'f';
			break;
			
		case 7:
			xPosChar = 'g';
			break;
			
		case 8:
			xPosChar = 'h';
			break;
		default:
			break;
		}
		
		return xPosChar;
	}

}
