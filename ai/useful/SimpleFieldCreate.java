package useful;

import components.Field;
import components.FigurePawn;
import components.*;

public class SimpleFieldCreate {

    private static final String KING = "King";
    private static final String QUEEN = "Queen";
    private static final String PAWN = "Pawn";
    private static final String KNIGHT = "Knight";
    private static final String BISHOP = "Bishop";
    private static final String ROOK = "Rook";
    
    public static Field setField(Field field, String figure, int number, byte color){
        
        if(figure.equals(KING)){
            FigureKing k = new FigureKing(color);
            field.putFigureAt(number, k);
        }
        if(figure.equals(QUEEN)){
            FigureQueen k = new FigureQueen(color);
            field.putFigureAt(number, k);            
        }
        if(figure.equals(PAWN)){
            FigurePawn k = new FigurePawn(color);
            field.putFigureAt(number, k);            
        }
        if(figure.equals(KNIGHT)){
            FigureKnight k = new FigureKnight(color);
            field.putFigureAt(number, k);            
        }
        if(figure.equals(BISHOP)){
            FigureBishop k = new FigureBishop(color);
            field.putFigureAt(number, k);            
        }
        if(figure.equals(ROOK)){
            FigureRook k = new FigureRook(color);
            field.putFigureAt(number, k);            
        }
        
        
        
        
        return field;
    }
    
    
}
