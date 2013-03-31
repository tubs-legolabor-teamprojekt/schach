package rating;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.HashMap;
import components.Figure;
import util.ChessfigureConstants;
import util.ExtractInformationFromBinary;
import dataStructure.ChessField;

public class PrimitivRating {

    Random ran = new Random();
    int value = 0;
    byte figureValue;
    byte figureType;
    private static int HIGHESTRATING = 2147483000;

    /*
     * Bewertet eine Spielsituation unabhaengig (ausnahme: Schachmatt) vom Spieler
     * @param field Zu bewertendes Feld
     * @param player Spieler der am Zug ist; wird nur fuer Schachmattstellung gebraucht
     * @param depth Aktuelle Suchtiefe um schnelle Mattzuege besser zu bewerten
     * @param checkmate Bei true liegt Mattstellung (oder Patt) vor
     */
    public int primRate(HashMap<Integer, Byte> field,int player,int depth, boolean checkmate) throws NullPointerException {
        value = 0;
        if(checkmate) {
        	if((byte)player == ChessfigureConstants.WHITE)
        		return HIGHESTRATING+depth;
        	else
        		return -(HIGHESTRATING+depth);
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
                value += ExtractInformationFromBinary.getColor(pair.getValue())== ChessfigureConstants.WHITE? 1:-1;
                break;
            case ChessfigureConstants.ROOK:
                value += ExtractInformationFromBinary.getColor(pair.getValue())== ChessfigureConstants.WHITE? 5:-5;
                break;
            case ChessfigureConstants.KNIGHT:
            case ChessfigureConstants.BISHOP:
                value += ExtractInformationFromBinary.getColor(pair.getValue())== ChessfigureConstants.WHITE? 3:-3;
                break;
            case ChessfigureConstants.QUEEN:
                value += ExtractInformationFromBinary.getColor(pair.getValue())== ChessfigureConstants.WHITE? 9:-9;
                break;
            default:
                value += ExtractInformationFromBinary.getColor(pair.getValue())== ChessfigureConstants.WHITE? 999:-999;
                break;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return -value;
    }

    public int primRate(HashMap<Integer, Byte> field, byte player) throws NullPointerException {
        int value = 0;
        Iterator<Entry<Integer, Byte>> it = field.entrySet().iterator();
        while (it.hasNext()) {
            // Aktuelles Key/Value-Paar
            Map.Entry<Integer, Byte> pair = (Map.Entry<Integer, Byte>) it.next();

            figureValue = pair.getValue();
            Figure figure = ChessfigureConstants.makeFigureFromByte(figureValue);
            figureType = figure.getFigureType();

            switch (figureType) {
            case ChessfigureConstants.PAWN:
                value += ExtractInformationFromBinary.getColor(pair.getValue())== ChessfigureConstants.WHITE? 1:-1;
                break;
            case ChessfigureConstants.ROOK:
                value += ExtractInformationFromBinary.getColor(pair.getValue())== ChessfigureConstants.WHITE? 5:-5;
                break;
            case ChessfigureConstants.KNIGHT:
            case ChessfigureConstants.BISHOP:
                value += ExtractInformationFromBinary.getColor(pair.getValue())== ChessfigureConstants.WHITE? 3:-3;
                break;
            case ChessfigureConstants.QUEEN:
                value += ExtractInformationFromBinary.getColor(pair.getValue())== ChessfigureConstants.WHITE? 9:-9;
                break;
            default:
                value += ExtractInformationFromBinary.getColor(pair.getValue())== ChessfigureConstants.WHITE? 999:-999;
                break;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        if(player== 0){
            return -value;
        }
        if(player== 1){
            return value;
        }
        System.out.println("FAAAAAAAAAAIL");
        return 0;
        
    }

    public int randomRate(ChessField situation) {
        boolean bool = ran.nextBoolean();
        return bool ? ran.nextInt(40) : -ran.nextInt(40);
    }

}
