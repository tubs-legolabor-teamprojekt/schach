package rating;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.HashMap;
import components.Figure;
import useful.SituationWithRating;
import util.ChessfigureConstants;
import util.ExtractInformationFromBinary;

public class PrimitivRating implements Serializable{

    Random ran = new Random();
    byte figureValue;
    byte figureType;
    private static int HIGHESTRATING = 2147483000;

    /*
     * Bewertet eine Spielsituation unabhaengig (ausnahme: Schachmatt) vom
     * Spieler
     * 
     * @param field Zu bewertendes Feld
     * 
     * @param player Spieler der am Zug ist; wird nur fuer Schachmattstellung
     * gebraucht
     * 
     * @param depth Aktuelle Suchtiefe um schnelle Mattzuege besser zu bewerten
     * 
     * @param checkmate Bei true liegt Mattstellung (oder Patt) vor
     */
    public int primFigureRate(HashMap<Integer, Byte> field, int player, int depth, boolean checkmate) throws NullPointerException
    {
        int actValue = 0;

        if (checkmate) {
            if ((byte) player == ChessfigureConstants.WHITE)
                return HIGHESTRATING + depth;
            else
                return -(HIGHESTRATING + depth);
        }
        Iterator<Entry<Integer, Byte>> it = field.entrySet().iterator();
        while (it.hasNext()) {
            // Aktuelles Key/Value-Paar
            Map.Entry<Integer, Byte> pair = (Map.Entry<Integer, Byte>) it.next();

            figureValue = pair.getValue();
            Figure figure = ChessfigureConstants.makeFigureFromByte(figureValue);
            figureType = figure.getFigureType();

            switch (figureType) {
                case ChessfigureConstants.PAWN:
                    actValue += ExtractInformationFromBinary.getColor(pair.getValue()) == ChessfigureConstants.WHITE ? 1 : -1;
                    break;
                case ChessfigureConstants.ROOK:
                    actValue += ExtractInformationFromBinary.getColor(pair.getValue()) == ChessfigureConstants.WHITE ? 5 : -5;
                    break;
                case ChessfigureConstants.KNIGHT:
                case ChessfigureConstants.BISHOP:
                    actValue += ExtractInformationFromBinary.getColor(pair.getValue()) == ChessfigureConstants.WHITE ? 3 : -3;
                    break;
                case ChessfigureConstants.QUEEN:
                    actValue += ExtractInformationFromBinary.getColor(pair.getValue()) == ChessfigureConstants.WHITE ? 9 : -9;
                    break;
                default:
                    actValue += ExtractInformationFromBinary.getColor(pair.getValue()) == ChessfigureConstants.WHITE ? 999 : -999;
                    break;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return -actValue;
    }

    public void primPositionRating(LinkedList<SituationWithRating> list, int player)
    {
        for (SituationWithRating sit : list) {
            HashMap<Integer, Byte> cloneMap = (HashMap<Integer, Byte>) sit.getMap().clone();
            int rating = positionRating(cloneMap, player);
            sit.setPositionRating(rating);
        }
    }

    /*
     * Bewertet die Stellung der Figuren auf dem Feld. Ist Gedacht als 2tes
     * Rating neben dem Figurenrating. Wenn sich eine Spielfigur im Mittelfeld
     * befindet, wird dies gut bewertet, ansonsten nicht gut.
     */

    private int positionRating(HashMap<Integer, Byte> field, int player)
    {
        Iterator<Entry<Integer, Byte>> it = field.entrySet().iterator();
        int actValue = 0;

        while (it.hasNext()) {

            Map.Entry<Integer, Byte> pair = (Map.Entry<Integer, Byte>) it.next();
            figureValue = pair.getValue();
            Figure figure = ChessfigureConstants.makeFigureFromByte(figureValue);
            figureType = figure.getFigureType();

            if (figure.getColor() == player) {
                if (pair.getKey() > 16 && pair.getKey() < 49) {
                    switch (figureType) {
                        case ChessfigureConstants.ROOK:
                            actValue += 5;
                            break;
                        case ChessfigureConstants.KNIGHT:
                        case ChessfigureConstants.BISHOP:
                            actValue += 3;
                            break;
                        case ChessfigureConstants.QUEEN:
                            actValue += 9;
                            break;
                    }
                    it.remove();
                }
            }
        }
        return actValue;
    }

    /*
     * public int primRate(HashMap<Integer, Byte> field, byte player) throws
     * NullPointerException { int value = 0; Iterator<Entry<Integer, Byte>> it =
     * field.entrySet().iterator(); while (it.hasNext()) { // Aktuelles
     * Key/Value-Paar Map.Entry<Integer, Byte> pair = (Map.Entry<Integer, Byte>)
     * it.next();
     * 
     * figureValue = pair.getValue(); Figure figure =
     * ChessfigureConstants.makeFigureFromByte(figureValue); figureType =
     * figure.getFigureType();
     * 
     * switch (figureType) { case ChessfigureConstants.PAWN: value +=
     * ExtractInformationFromBinary.getColor(pair.getValue()) ==
     * ChessfigureConstants.WHITE ? 1 : -1; break; case
     * ChessfigureConstants.ROOK: value +=
     * ExtractInformationFromBinary.getColor(pair.getValue()) ==
     * ChessfigureConstants.WHITE ? 5 : -5; break; case
     * ChessfigureConstants.KNIGHT: case ChessfigureConstants.BISHOP: value +=
     * ExtractInformationFromBinary.getColor(pair.getValue()) ==
     * ChessfigureConstants.WHITE ? 3 : -3; break; case
     * ChessfigureConstants.QUEEN: value +=
     * ExtractInformationFromBinary.getColor(pair.getValue()) ==
     * ChessfigureConstants.WHITE ? 9 : -9; break; default: value +=
     * ExtractInformationFromBinary.getColor(pair.getValue()) ==
     * ChessfigureConstants.WHITE ? 999 : -999; break; } it.remove(); // avoids
     * a ConcurrentModificationException } if (player == 0) { return -value; }
     * if (player == 1) { return value; } System.out.println("FAAAAAAAAAAIL");
     * return 0;
     * 
     * }
     * 
     * public int randomRate(ChessField situation) { boolean bool =
     * ran.nextBoolean(); return bool ? ran.nextInt(40) : -ran.nextInt(40); }
     */

}
