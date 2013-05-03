package useful;

import components.Field;
import components.FigurePawn;
import components.*;

public class SimpleFieldCreate {

    private static final String KING = "k";
    private static final String QUEEN = "d";
    private static final String PAWN = "b";
    private static final String KNIGHT = "s";
    private static final String BISHOP = "l";
    private static final String ROOK = "t";
    
    public static Field setField(Field field, String figure, int number, byte color, boolean moved){
        Figure k = null;
        
        if(figure.equals(KING)){
            k = new FigureKing(color);
        }
        else if(figure.equals(QUEEN)){
            k = new FigureQueen(color);          
        }
        else if(figure.equals(PAWN)){
            k = new FigurePawn(color);            
        }
        else if(figure.equals(KNIGHT)){
            k = new FigureKnight(color);            
        }
        else if(figure.equals(BISHOP)){
            k = new FigureBishop(color);            
        }
        else if(figure.equals(ROOK)){
            k = new FigureRook(color);            
        }
        k.setMoved(moved);
        field.putFigureAt(number, k);
        return field;
    }
    
    
}
