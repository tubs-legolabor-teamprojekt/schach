package rating;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import util.ChessfigureConstants;
import util.ExtractInformationFromBinary;
import dataStructure.ChessField;

public class PrimitivRating {

    public int primRate(ChessField field){
        int value = 0;
        int actValue = 0;
        Iterator<Entry<Integer, Byte>> it = field.entrySet().iterator();
        while (it.hasNext()) {
            // Aktuelles Key/Value-Paar
            Map.Entry<Integer, Byte> pair = (Map.Entry<Integer, Byte>)it.next();
            
            switch ( ExtractInformationFromBinary.getFigure(pair.getValue()) ) {
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
    
    public static void main(String[] args) {
        ChessField field = new ChessField();
        
        field.put(3, ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.PAWN, false));
        field.put(4, ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.QUEEN, false));
        field.put(5, ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.KING, false));
        field.put(6, ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.PAWN, false));
        field.put(7, ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.BISHOP, false));
        field.put(8, ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.KING, false));
        PrimitivRating rate = new PrimitivRating();
        System.out.println(rate.primRate(field));
    }
    
}
