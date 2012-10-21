package util;

import components.FigureException;

/**
 * Definiert alle Konstanten zur Kodierung der Figuren.
 * Jede Figur hat einen Integer-Wert zugewiesen bekommen (siehe docs)
 * und dieser wird mit einer Konstanten in Java verwendet.
 * 
 * @author Florian Franke
 *
 */
public final class ChessfigureConstants {

	/**
	 * Byte-Wert des Bauern
	 */
	public static final int PAWN = 1;
	
	/**
	 * Byte-Wert des Turms
	 */
	public static final int ROOK = 2;
	
	/**
	 * Byte-Wert des Springers
	 */
	public static final int KNIGHT = 3;
	
	/**
	 * Byte-Wert des Laeufers
	 */
	public static final int BISHOP = 4;
	
	/**
	 * Byte-Wert der Dame
	 */
	public static final int QUEEN = 5;
	
	/**
	 * Byte-Wert des Koenigs
	 */
	public static final int KING = 6;
	
	/**
	 * Farbe: Schwarz
	 */
	public static final byte BLACK = 1;
	
	/**
	 * Farbe: Weiss
	 */
	public static final byte WHITE = 0;
	
	
	/**
	 * Gibt den Namen der Figur des uebergebenen byte-Werts zurŸck.
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
	 * Gibt die Farbe der Figur zurŸck
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
	
	/**
	 * Erstellt den Short-Wert fŸr eine Figur.
	 * @param color Farbe der Figur (0 oder 1)
	 * @param figureType Figurtyp
	 * @param positionX X-Position auf dem Feld
	 * @param positionY Y-Position auf dem Feld
	 * @return short-Wert der erzeugten Figur.
	 * TODO Fehlerhafte Eingaben abfangen
	 */
	public static short makeFigureShort(int color, int figureType, int positionX, int positionY)
	{
		short s = 0;
		try {
			if (!(color == BLACK || color == WHITE))
				throw new FigureException("Farbe der Figur ist ungueltig!");
			
			// Schwarz => 1 an Bit 10 (2^9)
			if (color == BLACK)
				s += Math.pow(2, 9);
			
			// Figurtyp
			if (	figureType == PAWN ||
					figureType == ROOK ||
					figureType == KNIGHT ||
					figureType == BISHOP ||
					figureType == QUEEN ||
					figureType == KING
					)
				s += (figureType << 6);
			else
				throw new FigureException("Ungueltiger Figurtyp angegeben!");
			
			// X-Position
			if (positionX > 0 && positionX <= 8)
				s += ((positionX-1) << 3);
			else
				throw new FigureException("Ungueltige X-Position!\nMuss zwischen 1 und 8 liegen.");
			
			// Y-Position
			if (positionY > 0 && positionY <= 8)
				s += positionY-1;
			else
				throw new FigureException("Ungueltige Y-Position!\nMuss zwischen 1 und 8 liegen.");
			
		} catch (FigureException e)
		{
			System.out.println(e.getMessage());
		}
		
		return s;
	}

}
