package useful;

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
        
        FigureKing fk = new FigureKing(ChessfigureConstants.BLACK);
        field.putFigureAt(18, fk);
        
        FigureKing fk2 = new FigureKing(ChessfigureConstants.WHITE);
        field.putFigureAt(49, fk2);
        
//        FigureRook fk3 = new FigureRook(ChessfigureConstants.BLACK);
//        field.putFigureAt(32, fk3);
        
        FigureQueen fk4 = new FigureQueen(ChessfigureConstants.WHITE);
        field.putFigureAt(36, fk4);
        
//        FigureKnight fk5 = new FigureKnight(ChessfigureConstants.WHITE);
//        field.putFigureAt(18, fk5);
        
//        System.out.println(toText.fieldToString(field.getCurrentFieldAsHashMapWithBytes()));
        
        Move move = moveTo.getNext(field, (byte)0);
        
        System.out.println("Move-Objekt: "+move.getFieldFrom()+" "+move.getFieldTo()+" "+move.getColorOfPlayer());
        
    }

}
