package util;

import components.Field;

public class TestingTools
{

    /**
     * Testet das Extrahieren von Informationen aus einem Short-Wert.
     */
    public static void main(String[] args) {
        // Schwarzer Koenig
        // 11110, zusammengesetzt aus:
        // 1   - wurde schon bewegt (16)
        // 1   - schwarz (8)
        // 110 - Koenig (6)
        byte by = 30;

        System.out.println(ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.KING, false));

        // Binaerstring vom Short
        System.out.println(ExtractInformationFromBinary.byteToBinary(by));

        // Figur ermitteln und ausgeben
        byte figure = ExtractInformationFromBinary.getFigure(by);
        System.out.println(ChessfigureConstants.getFigureName(figure));

        // Farbe der Figur
        byte color = ExtractInformationFromBinary.getColor(by);
        System.out.println(ChessfigureConstants.getFigureColor(color));

        // Wurde die Figur schon bewegt?
        byte moved = ExtractInformationFromBinary.isMoved(by);
        System.out.println(ChessfigureConstants.isFigureMoved(moved));
    }

}
