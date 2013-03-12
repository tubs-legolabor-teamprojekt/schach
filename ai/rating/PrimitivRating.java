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

    //Statt HashMap<Integer, Byte> kann auch Chessfield genommen werden
    public int primRate(HashMap<Integer, Byte> field) {
        int value = 0;
        int actValue = 0;
        Iterator<Entry<Integer, Byte>> it = field.entrySet().iterator();
        while (it.hasNext()) {
            // Aktuelles Key/Value-Paar
            Map.Entry<Integer, Byte> pair = (Map.Entry<Integer, Byte>) it.next();

            byte figureValue = pair.getValue();
            Figure figure = ChessfigureConstants.makeFigureFromByte(figureValue);
            byte figureType = figure.getFigureType();

            switch (figureType) {
            case ChessfigureConstants.PAWN:
                actValue = 1;
                break;
            case ChessfigureConstants.ROOK:
                actValue = 5;
            case ChessfigureConstants.KNIGHT:
            case ChessfigureConstants.BISHOP:
                actValue = 3;
                break;
            case ChessfigureConstants.QUEEN:
                actValue = 9;
                break;
            default:
                actValue = 9999;
                break;
            }

            if (ExtractInformationFromBinary.getColor(pair.getValue()) == ChessfigureConstants.BLACK) {
                // Schwarz
                value -= actValue;
            } else {
                // Weiss
                value += actValue;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return value;
    }

    public int randomRate(ChessField situation) {
        boolean bool = ran.nextBoolean();
        return bool ? ran.nextInt(40) : -ran.nextInt(40);
    }

}
