package util;

import components.Field;

public class TestingTools {

	/**
	 * Testet das Extrahieren von Informationen aus einem Short-Wert.
	 */
	public static void main(String[] args) {
		// Schwarzer Koenig auf C5 (3/5)
		// 1110011101, zusammengesetzt aus:
		//   1   - schwarz
		//   110 - Koenig (6)
		//   011 - X-Position (4/D)
		//   101 - Y-Position (6)
		short sh = 925;
		
		System.out.println(ChessfigureConstants.makeFigureShort(1, ChessfigureConstants.KING, 4, 6));
		
		// Binaerstring vom Short
		System.out.println(ExtractInformationFromBinary.shortToBinary(sh));
		
		// Figur ermitteln und ausgeben
		byte figure = ExtractInformationFromBinary.getFigure(sh);
		System.out.println(ChessfigureConstants.getFigureName(figure));
		
		// Position der Figur
		byte xPos = ExtractInformationFromBinary.getXPosition(sh);
		System.out.println(Field.getXPosition(xPos));
		byte yPos = ExtractInformationFromBinary.getYPosition(sh);
		System.out.println(yPos);
		
		// Farbe der Figur
		byte color = ExtractInformationFromBinary.getColor(sh);
		System.out.println(ChessfigureConstants.getFigureColor(color));
	}

}
