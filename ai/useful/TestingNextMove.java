package useful;

import util.ChessfigureConstants;
import components.*;
//import components.Field;
//import components.FigureBishop;
//import components.FigureKing;
//import components.FigureQueen;

import game.Move;
import gameTree.NextMove;

public class TestingNextMove {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        // TODO Auto-generated method stub
        
        NextMove moveTo = new NextMove();
        
        components.Field field = new Field(true);
        
        FigureKing fk = new FigureKing(ChessfigureConstants.BLACK);
        field.putFigureAt(12, fk);
        
        FigureKing fk2 = new FigureKing(ChessfigureConstants.WHITE);
        field.putFigureAt(28, fk2);
        
        FigureRook fk3 = new FigureRook(ChessfigureConstants.BLACK);
        field.putFigureAt(32, fk3);
        
        FigureQueen fk4 = new FigureQueen(ChessfigureConstants.WHITE);
        field.putFigureAt(52, fk4);
        
        FigureKnight fk5 = new FigureKnight(ChessfigureConstants.WHITE);
        field.putFigureAt(18, fk5);
        
        Move move = moveTo.getNext(field, (byte)0);
        
        System.out.println(move.getFieldFrom()+" "+move.getFieldTo()+" "+move.getColorOfPlayer());
        
    }

}
