package util;

import components.*;

/**
 * Definiert alle Konstanten zur Kodierung der Figuren. Jede Figur hat einen
 * Integer-Wert zugewiesen bekommen (siehe docs) und dieser wird mit einer
 * Konstanten in Java verwendet.
 * 
 * @author Florian Franke
 * 
 */
public final class ChessfigureConstants
{

    /**
     * Byte-Wert des Bauern
     */
    public static final byte PAWN = 1;
    /**
     * Byte-Wert des Turms
     */
    public static final byte ROOK = 2;

    /**
     * Turm-Kuerzel
     */
    public static final char ROOK_LETTER = 'R';

    /**
     * Byte-Wert des Springers
     */
    public static final byte KNIGHT = 3;

    /**
     * Springer-Kuerzel
     */
    public static final char KNIGHT_LETTER = 'N';

    /**
     * Byte-Wert des Laeufers
     */
    public static final byte BISHOP = 4;

    /**
     * Laeufer-Kuerzel
     */
    public static final char BISHOP_LETTER = 'B';

    /**
     * Byte-Wert der Dame
     */
    public static final byte QUEEN = 5;

    /**
     * Dame-Kuerzel
     */
    public static final char QUEEN_LETTER = 'Q';

    /**
     * Byte-Wert des Koenigs
     */
    public static final byte KING = 6;

    /**
     * Koenig-Kuerzel
     */
    public static final char KING_LETTER = 'K';

    /**
     * Farbe: Schwarz
     */
    public static final byte BLACK = 1;

    /**
     * Farbe: Weiss
     */
    public static final byte WHITE = 0;
    
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
            break;
            
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
     * Ermittelt als dem Kuerzel der Figur den Byte-Wert
     * @param c Kuerzel der Figur
     * @return Byte-Wert der Figur
     */
    public static byte getFigureTypeFromLetter(char c)
    {
        byte by = -1;
        System.out.println("MIMIMI " + c);
        if (c == ' ')
            by = PAWN;
        else if (c == ROOK_LETTER)
            by = ROOK;
        else if (c == KNIGHT_LETTER)
            by = KNIGHT;
        else if (c == BISHOP_LETTER)
            by = BISHOP;
        else if (c == QUEEN_LETTER)
            by = QUEEN;
        else if (c == KING_LETTER)
            by = KING;
        
        return by;
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
     * Erstellt den Byte-Wert für eine Figur.
     * @param color Farbe der Figur (0 oder 1)
     * @param figureType Figurtyp
     * @param positionX X-Position auf dem Feld
     * @param positionY Y-Position auf dem Feld
     * @return byte-Wert der erzeugten Figur
     */
    public static byte makeFigureByte(byte color, byte figureType, boolean moved)
    {
        byte by = 0;
        try {
            if (!(color == BLACK || color == WHITE))
                throw new FigureException("Farbe der Figur ist ungueltig!");
            
            // Figur schon bewegt?
            if (moved)
                by += Math.pow(2, 4);
            
            // Schwarz => 1 an Bit 4 (2^3)
            if (color == BLACK)
                by += Math.pow(2, 3);
            
            // Figurtyp
            if (isValidFigureType(figureType))
                by += (figureType << 0);
            else
                throw new FigureException("Ungueltiger Figurtyp angegeben!");
            
        } catch (FigureException e)
        {
            System.out.println(e.getMessage());
        }
        
        return by;
    }
    
    /**
     * Ueberprueft ob der uebergebene byte-Wert ein gueltiger Figurtyp ist.
     * @param figureType Zu pruefender byte-Wert
     * @return True: Gueltiger Figurtyp; False: Ungueltiger Figurtyp
     */
    public static boolean isValidFigureType(byte figureType)
    {
        return (    figureType == PAWN ||
                figureType == ROOK ||
                figureType == KNIGHT ||
                figureType == BISHOP ||
                figureType == QUEEN ||
                figureType == KING);
    }
    
    /**
     * Erstellt aus einem Byte-Wert die dazugehoerige Figur.
     * Da in der Figur nicht die Position gespeichert wird,
     * werden die Positionsinformationen des Byte-Werts ignoriert.
     * @param sh Byte-Wert, der die zu erstellende Figur enthaelt
     * @return Ein Objekt der entsprechenden Subklasse von Figure
     * (FigureKing, FigureQueen...)
     */
    public static Figure makeFigureFromByte(byte by)
    {
        // Figurtyp ermitteln
        byte figureType = ExtractInformationFromBinary.getFigure(by);
        
        // Farbe der Figur ermitteln
        byte figureColor = ExtractInformationFromBinary.getColor(by);
        
        // Figurtyp steht fest.
        // Nun wird ein Objekt der entsprechenden Klasse erstellt
        switch (figureType) {
        case PAWN:
            return new FigurePawn(figureColor);
        case ROOK:
            return new FigureRook(figureColor);
        case KNIGHT:
            return new FigureKnight(figureColor);
        case BISHOP:
            return new FigureBishop(figureColor);
        case QUEEN:
            return new FigureQueen(figureColor);
        case KING:
            return new FigureKing(figureColor);
        default :
            // duerfte nie der Fall sein, da in ExtractInformationFromBinary.getFigure
            // geprueft wird, ob es eine gueltige Figur ist.
            return null;
        }
    }
    
    /**
     * Ueberprueft ob die uebergebene Farbe gueltig ist.
     * @param color Zu pruefende Farbe
     * @return True: gueltig; False: Farbe ungueltig
     */
    public static boolean isValidColor(byte color)
    {
        return (color == WHITE || color == BLACK);
    }
    
    /**
     * Ueberprueft, ob ein char-Wert einem Figuren-Kuerzel entspricht
     * @param letter Zu pruefendes Kuerzel
     * @return True: Kuerzel gueltig; False: Nicht
     */
    public static boolean isValidFigureLetter(char letter)
    {
        return (letter == KING_LETTER ||
                isValidPawnPromotionFigure(letter));
    }
    
    /**
     * Ueberprueft, ob ein char-Wert einer regelkonformen Bauer-Umwandlung entspricht
     * @param letter Zu pruefendes Kuerzel
     * @return True: Kuerzel gueltig; False: Nicht
     */
    public static boolean isValidPawnPromotionFigure(char letter)
    {
        return (letter == QUEEN_LETTER ||
                letter == KNIGHT_LETTER ||
                letter == BISHOP_LETTER ||
                letter == ROOK_LETTER);
    }
    
    /**
     * Wurde die Figur schon bewegt?
     * @param by
     * @return
     */
    public static boolean isFigureMoved(byte by)
    {
        return (by == 1);
    }
}
