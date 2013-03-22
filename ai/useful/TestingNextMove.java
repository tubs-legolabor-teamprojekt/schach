package useful;

import rating.PrimitivRating;
import util.ChessfigureConstants;
import components.*;
//import components.Field;
//import components.FigureBishop;
//import components.FigureKing;
//import components.FigureQueen;

import game.Move;
import gameTree.NextMove;
import useful.TextChessField;

public class TestingNextMove {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        // TODO Auto-generated method stub
        TextChessField toText = new TextChessField();
        NextMove moveTo = new NextMove();
        
        components.Field field = new Field(true);
//        field = Field.getInstance();
/*        
        // Turm
        FigureRook fr1 = new FigureRook(color);
        this.putFigureAt(++position, fr1);
        
        // Springer
        FigureKnight fk1 = new FigureKnight(color);
        this.putFigureAt(++position, fk1);
        
        // Laeufer
        FigureBishop fb1 = new FigureBishop(color);
        this.putFigureAt(++position, fb1);
        
        // Dame
        FigureQueen fq = new FigureQueen(color);
        this.putFigureAt(++position, fq);
        
        ChessfigureConstants.BLACK
        
        // Koenig
        FigureKing fk = new FigureKing(color);
        this.putFigureAt(++position, fk);
*/
        
        FigureKing fk2 = new FigureKing(ChessfigureConstants.BLACK);
        field.putFigureAt(59, fk2);
        
        FigureRook fr1 = new FigureRook(ChessfigureConstants.BLACK);
        field.putFigureAt(64, fr1);
        
        FigureRook fr2 = new FigureRook(ChessfigureConstants.WHITE);
        field.putFigureAt(49, fr2);
        
        FigureBishop fb1 = new FigureBishop(ChessfigureConstants.BLACK);
        field.putFigureAt(50, fb1);
        
        FigureRook fr3 = new FigureRook(ChessfigureConstants.BLACK);
        field.putFigureAt(52, fr3);
        
        FigurePawn fp1 = new FigurePawn(ChessfigureConstants.BLACK);
        field.putFigureAt(56, fp1);
        
        FigurePawn fp2 = new FigurePawn(ChessfigureConstants.BLACK);
        field.putFigureAt(46, fp2);
        
        FigurePawn fp3 = new FigurePawn(ChessfigureConstants.BLACK);
        field.putFigureAt(39, fp3);
        
        FigurePawn fp4 = new FigurePawn(ChessfigureConstants.WHITE);
        field.putFigureAt(36, fp4);
        
        FigureKnight fk1 = new FigureKnight(ChessfigureConstants.BLACK);
        field.putFigureAt(27, fk1);

        FigureBishop fb2 = new FigureBishop(ChessfigureConstants.WHITE);
        field.putFigureAt(23, fb2);
        
        FigurePawn fp7 = new FigurePawn(ChessfigureConstants.WHITE);
        field.putFigureAt(14, fp7);

        FigurePawn fp5 = new FigurePawn(ChessfigureConstants.WHITE);
        field.putFigureAt(15, fp5);

        FigurePawn fp6 = new FigurePawn(ChessfigureConstants.WHITE);
        field.putFigureAt(16, fp6);
        
        FigureKing fk3 = new FigureKing(ChessfigureConstants.WHITE);
        field.putFigureAt(7, fk3);

        FigureRook fr4 = new FigureRook(ChessfigureConstants.WHITE);
        field.putFigureAt(2, fr4);

        PrimitivRating prim = new PrimitivRating();
        System.out.println(prim.primRate(field.getCurrentFieldAsHashMapWithBytes()));

        
        System.out.println(toText.fieldToString(field.getCurrentFieldAsHashMapWithBytes()));
        
        Move move = moveTo.getNext(field, (byte)0);
        
        System.out.println("Move-Objekt: "+move.getFieldFrom()+" "+move.getFieldTo()+" "+move.getPlayerColor());
        
    }

}
