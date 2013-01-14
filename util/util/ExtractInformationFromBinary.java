package util;

import components.Field;
import components.Figure;
import components.FigureException;

/**
 * Diese Klasse bietet die Moeglichkeit aus einem gegebenen Byte-Wert
 * die beinhaltenden Informationen zu extrahieren.
 * 
 * @author Florian Franke
 *
 */
public class ExtractInformationFromBinary
{

    /**
     * Wandelt den uebergebenen Byte-Wert in Binaer um.
     * @param sh Byte-Wert
     * @return String der Binaer-Zahl des Byte-Werts
     */
    public static String byteToBinary(byte by)
    {
        return Integer.toBinaryString(by);
    }
    
    /**
     * Wandelt den gegebenen Binaer-Wert per BitShifting in Binaerschreibweise um.
     * Dient hier nur als Beispielmethode um Zeit zu sparen,
     * falls es mal im eigenen Code gebraucht wird.
     * Mit (1 << i) wird ein 1er-Bit um i-Schritte nach links verschoben.
     * Also bei i=3 ergibt es 8 (1000)
     * Anschliessend wird der gegebene Byte-Wert (sh) mit dem (1 << i)
     * logisch verUNDed:
     *   1000  = 8 (^=(1 << 3))
     * & 1101  = 13 (als Beispielwert fuer den Byte)
     * ------
     *   1000  = 8 => Also eine 1 an der 3. Binaerstelle
     * 
     * @param sh Byte-Wert
     */
    public static void getBinaryByBitShifting(byte by)
    {
        for (int i=7; i>=0; i--)
            System.out.print( ((by & (1 << i)) != 0) ? "1" : "0" );
    }
    
    /**
     * Ermittelt die Schach-Figur aus dem Byte-Wert
     * @param sh Byte-Wert mit der Position, Farbe und Figur
     * @return Byte-Wert der Figur
     */
    public static byte getFigure(byte by)
    {
        if (by > 0) {
            // Hole die ersten Drei und addiere sie
            byte value = 0;
            try {
                int currentBit = 0;
                for (int i=0; i<3; i++) {
                    if ((by & (1 << i)) != 0) {
                        // bit ist gesetzt, Wert ermitteln
                        currentBit = (by & (1 << i));
                        // ermittelte Wert befindet sich bei 2^6 bis 2^8
                        // shift auf 2^0 bis 2^2
                        value += (currentBit >> 0);
                    }
                }
                if (!ChessfigureConstants.isValidFigureType(value))
                    throw new FigureException("Ungueltige Figur aus Byte-Wert gelesen!");
            } catch (FigureException e)
            {
                System.out.println(e.getMessage());
            }
            return value;
        } else {
            return -1;
        }
    }
    
    /**
     * Ermittelt die X-Position der Figur (1-8)
     * @param sh Short-Wert mit der Position, Farbe und Figur
     * @return Byte-Wert der X-Position
     */
    @Deprecated
    public static byte getXPosition(short sh)
    {
        if (sh > 0) {
            // Hole die ersten Drei und addiere sie
            byte value = 0;
            try {
                int currentBit = 0;
                for (int i=3; i<6; i++) {
                    if ((sh & (1 << i)) != 0) {
                        // bit ist gesetzt, Wert ermitteln
                        currentBit = (sh & (1 << i));
                        // ermittelte Wert befindet sich bei 2^3 bis 2^5
                        // shift auf 2^0 bis 2^2
                        value += (currentBit >> 3);
                    }
                }
                value++;
                if (!Field.isValidXPosition(value))
                    throw new FigureException("Ungueltige X-Position aus Short-Wert gelesen!");
            } catch (FigureException e)
            {
                System.out.println(e.getMessage());
            }
            return value;
        } else {
            return -1;
        }
    }
    
    /**
     * Ermittelt die Y-Position der Figur (1-8)
     * @param sh Short-Wert mit der Position, Farbe und Figur
     * @return Byte-Wert der Y-Position
     */
    @Deprecated
    public static byte getYPosition(short sh)
    {
        if (sh > 0) {
            // Hole die ersten Drei und addiere sie
            byte value = 0;
            try {
                int currentBit = 0;
                for (int i=0; i<3; i++) {
                    if ((sh & (1 << i)) != 0) {
                        // bit ist also gesetzt, Wert ermitteln
                        currentBit = (sh & (1 << i));
                        // addieren den Wert des Bits hinzu (z.B. 2^2=4)
                        value += currentBit;
                    }
                }
                value++;
                if (!Field.isValidYPosition(value))
                    throw new FigureException("Ungueltige Y-Position aus Short-Wert gelesen!");
            } catch (FigureException e)
            {
                System.out.println(e.getMessage());
            }
            return value;
        } else {
            return -1;
        }
    }
    
    /**
     * Ermittelt die Farbe der Figur
     * @param sh Byte-Wert mit der Position, Farbe und Figur
     * @return Farbe der Figur (byte-Wert)
     */
    public static byte getColor(byte by)
    {
        if (by > 0) {
            // Hole das 3. Bit
            int currentBit = by & (1 << 3);
            byte value = 0;
            try {
                value = (byte)(currentBit >> 3);
                if (!ChessfigureConstants.isValidColor(value))
                    throw new FigureException("Ungueltige Farbe aus Byte-Wert gelesen!");
            } catch (FigureException e)
            {
                System.out.println(e.getMessage());
            }
            
            return value;
        } else {
            return -1;
        }
    }
    
    /**
     * Ermittelt das dem übergebenem byte-Wert, ob die Figur schon bewegt wurde
     * @param by
     * @return
     */
    public static byte isMoved(byte by)
    {
        if (by > 0) {
            // Hole das 4. Bit
            int currentBit = by & (1 << 4);
            byte value = 0;
            value = (byte)(currentBit >> 4);
            
            return value;
        } else {
            return -1;
        }
    }
    
    /**
     * Gibt alle Informationen eines Byte-Werts als String zurueck.
     * @param sh Der Byte-Wert der Figur
     * @return String der Form "[Position(z.B. e2)]: [Figur], [Farbe]"
     */
    public static String getAllInfosFromByte(byte by)
    {
        String s = "";
        
        // Feld, auf dem die Figur steht
        //s += Field.getFieldName(Field.getFieldNumber(getXPosition(by), getYPosition(by)));
        //s += ": ";
        
        // Welche Figur steht dort
        s += ChessfigureConstants.getFigureName(getFigure(by));
        s += ", ";
        
        // Farbe
        s += ChessfigureConstants.getFigureColor(getColor(by));
        
        return s;
    }

}
