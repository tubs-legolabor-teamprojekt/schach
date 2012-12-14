package util;

import components.Field;
import components.Figure;
import components.FigureException;

/**
 * Diese Klasse bietet die Moeglichkeit aus einem gegebenen Short-Wert die
 * beinhaltenden Informationen zu extrahieren.
 * 
 * @author Florian Franke
 * 
 */
public class ExtractInformationFromBinary
{

    /**
     * Wandelt den uebergebenen Short-Wert in Binaer um.
     * 
     * @param sh
     *            Short-Wert
     * @return String der Binaer-Zahl des Short-Werts
     */
    public static String shortToBinary(short sh) {
        return Integer.toBinaryString(sh);
    }

    /**
     * Wandelt den gegebenen Binaer-Wert per BitShifting in Binaerschreibweise
     * um. Dient hier nur als Beispielmethode um Zeit zu sparen, falls es mal im
     * eigenen Code gebraucht wird. Mit (1 << i) wird ein 1er-Bit um i-Schritte
     * nach links verschoben. Also bei i=3 ergibt es 8 (1000) Anschliessend wird
     * der gegebene Short-Wert (sh) mit dem (1 << i) logisch verUNDed: 1000 = 8
     * (^=(1 << 3)) & 1101 = 13 (als Beispielwert fuer den Short) ------ 1000 =
     * 8 => Also eine 1 an der 3. Binaerstelle
     * 
     * @param sh
     *            Short-Wert
     */
    public static void getBinaryByBitShifting(short sh) {
        for (int i = 15; i >= 0; i--)
            System.out.print(((sh & (1 << i)) != 0) ? "1" : "0");
    }

    /**
     * Ermittelt die Schach-Figur aus dem Short-Wert
     * 
     * @param sh
     *            Short-Wert mit der Position, Farbe und Figur
     * @return Byte-Wert der Figur
     */
    public static byte getFigure(short sh) {
        if (sh > 0) {
            // Hole die ersten Drei und addiere sie
            byte value = 0;
            try {
                int currentBit = 0;
                for (int i = 6; i < 9; i++) {
                    if ((sh & (1 << i)) != 0) {
                        // bit ist gesetzt, Wert ermitteln
                        currentBit = (sh & (1 << i));
                        // ermittelte Wert befindet sich bei 2^6 bis 2^8
                        // shift auf 2^0 bis 2^2
                        value += (currentBit >> 6);
                    }
                }
                if (!ChessfigureConstants.isValidFigureType(value))
                    throw new FigureException(
                            "Ungueltige Figur aus Short-Wert gelesen!");
            } catch (FigureException e) {
                System.out.println(e.getMessage());
            }
            return value;
        } else {
            return -1;
        }
    }

    /**
     * Ermittelt die X-Position der Figur (1-8)
     * 
     * @param sh
     *            Short-Wert mit der Position, Farbe und Figur
     * @return Byte-Wert der X-Position
     */
    public static byte getXPosition(short sh) {
        if (sh > 0) {
            // Hole die ersten Drei und addiere sie
            byte value = 0;
            try {
                int currentBit = 0;
                for (int i = 3; i < 6; i++) {
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
                    throw new FigureException(
                            "Ungueltige X-Position aus Short-Wert gelesen!");
            } catch (FigureException e) {
                System.out.println(e.getMessage());
            }
            return value;
        } else {
            return -1;
        }
    }

    /**
     * Ermittelt die Y-Position der Figur (1-8)
     * 
     * @param sh
     *            Short-Wert mit der Position, Farbe und Figur
     * @return Byte-Wert der Y-Position
     */
    public static byte getYPosition(short sh) {
        if (sh > 0) {
            // Hole die ersten Drei und addiere sie
            byte value = 0;
            try {
                int currentBit = 0;
                for (int i = 0; i < 3; i++) {
                    if ((sh & (1 << i)) != 0) {
                        // bit ist also gesetzt, Wert ermitteln
                        currentBit = (sh & (1 << i));
                        // addieren den Wert des Bits hinzu (z.B. 2^2=4)
                        value += currentBit;
                    }
                }
                value++;
                if (!Field.isValidYPosition(value))
                    throw new FigureException(
                            "Ungueltige Y-Position aus Short-Wert gelesen!");
            } catch (FigureException e) {
                System.out.println(e.getMessage());
            }
            return value;
        } else {
            return -1;
        }
    }

    /**
     * Ermittelt die Farbe der Figur
     * 
     * @param sh
     *            Short-Wert mit der Position, Farbe und Figur
     * @return Farbe der Figur (byte-Wert)
     */
    public static byte getColor(short sh) {
        if (sh > 0) {
            // Hole die ersten Drei und addiere sie
            int currentBit = sh & (1 << 9);
            byte value = 0;
            try {
                value = (byte) (currentBit >> 9);
                if (!ChessfigureConstants.isValidColor(value))
                    throw new FigureException(
                            "Ungueltige Farbe aus Short-Wert gelesen!");
            } catch (FigureException e) {
                System.out.println(e.getMessage());
            }

            return value;
        } else {
            return -1;
        }
    }

    /**
     * Gibt alle Informationen eines short-Werts als String zurueck.
     * 
     * @param sh
     *            Der Short-Wert der Figur
     * @return String der Form "[Position(z.B. e2)]: [Figur], [Farbe]"
     */
    public static String getAllInfosFromShort(short sh) {
        String s = "";

        // Feld, auf dem die Figur steht
        s += Field.getFieldName(Field.getFieldNumber(getXPosition(sh),
                getYPosition(sh)));
        s += ": ";

        // Welche Figur steht dort
        s += ChessfigureConstants.getFigureName(getFigure(sh));
        s += ", ";

        // Farbe
        s += ChessfigureConstants.getFigureColor(getColor(sh));

        return s;
    }

}
