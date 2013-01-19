package rating;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import util.ChessfigureConstants;
import util.ExtractInformationFromBinary;
import dataStructure.ChessField;

public class PrimitivRating {

    Random ran = new Random();

    public int primRate(ChessField field) {
        int value = 0;
        int actValue = 0;
        Iterator<Entry<Integer, Byte>> it = field.entrySet().iterator();
        while (it.hasNext()) {
            // Aktuelles Key/Value-Paar
            Map.Entry<Integer, Byte> pair = (Map.Entry<Integer, Byte>) it.next();

            switch (ExtractInformationFromBinary.getFigure(pair.getValue())) {
            case ChessfigureConstants.PAWN:
                actValue = 1;
                break;
            case ChessfigureConstants.BISHOP:
            case ChessfigureConstants.KNIGHT:
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
