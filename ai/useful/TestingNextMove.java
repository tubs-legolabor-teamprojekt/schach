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
        
/*
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
*/
/*
        FigurePawn fp3 = new FigurePawn(ChessfigureConstants.BLACK);
        field.putFigureAt(36, fp3);
        
        FigurePawn fp12 = new FigurePawn(ChessfigureConstants.BLACK);
        field.putFigureAt(38, fp12);
        
        FigureKnight fk1 = new FigureKnight(ChessfigureConstants.WHITE);
        field.putFigureAt(37, fk1);
        
        FigurePawn fp11 = new FigurePawn(ChessfigureConstants.WHITE);
        field.putFigureAt(39, fp11);
        
        FigurePawn fp13 = new FigurePawn(ChessfigureConstants.WHITE);
        field.putFigureAt(40, fp13);
        
        FigureBishop fb1 = new FigureBishop(ChessfigureConstants.BLACK);
        field.putFigureAt(44, fb1);
       
        FigureBishop fb3 = new FigureBishop(ChessfigureConstants.BLACK);
        field.putFigureAt(50, fb3);
       
        

        FigurePawn fp14 = new FigurePawn(ChessfigureConstants.BLACK);
        field.putFigureAt(49, fp14);
        FigurePawn fp15 = new FigurePawn(ChessfigureConstants.BLACK);
        field.putFigureAt(54, fp15);
        FigurePawn fp16 = new FigurePawn(ChessfigureConstants.BLACK);
        field.putFigureAt(55, fp16);
        FigurePawn fp17 = new FigurePawn(ChessfigureConstants.BLACK);
        field.putFigureAt(56, fp17);
        
        
        FigureKing fk2 = new FigureKing(ChessfigureConstants.BLACK);
        field.putFigureAt(63, fk2);
        
        
        
        FigureQueen fq1 = new FigureQueen(ChessfigureConstants.WHITE);
        field.putFigureAt(21, fq1);

        FigurePawn fp7 = new FigurePawn(ChessfigureConstants.WHITE);
        field.putFigureAt(28, fp7);

        FigurePawn fp6 = new FigurePawn(ChessfigureConstants.WHITE);
        field.putFigureAt(30, fp6);

        FigurePawn fp1 = new FigurePawn(ChessfigureConstants.BLACK);
        field.putFigureAt(26, fp1);

        FigurePawn fp5 = new FigurePawn(ChessfigureConstants.WHITE);
        field.putFigureAt(18, fp5);

        FigurePawn fp2 = new FigurePawn(ChessfigureConstants.WHITE);
        field.putFigureAt(20, fp2);

        FigureKing fk3 = new FigureKing(ChessfigureConstants.WHITE);
        field.putFigureAt(16, fk3);

        FigureQueen fq3 = new FigureQueen(ChessfigureConstants.BLACK);
        field.putFigureAt(9, fq3);

        FigureBishop fb2 = new FigureBishop(ChessfigureConstants.WHITE);
        field.putFigureAt(15, fb2);
*/
        field = SimpleFieldCreate.setField(field, "Rook", 6, (byte)0);
        field = SimpleFieldCreate.setField(field, "King", 7, (byte)0);
        field = SimpleFieldCreate.setField(field, "Pawn", 14, (byte)0);
        field = SimpleFieldCreate.setField(field, "Pawn", 16, (byte)0);
        field = SimpleFieldCreate.setField(field, "Pawn", 19, (byte)0);
        field = SimpleFieldCreate.setField(field, "Pawn", 23, (byte)0);
        field = SimpleFieldCreate.setField(field, "Bishop", 24, (byte)0);
        field = SimpleFieldCreate.setField(field, "Queen", 43, (byte)0);
        
        field = SimpleFieldCreate.setField(field, "Rook", 33, (byte)1);
        field = SimpleFieldCreate.setField(field, "Pawn", 49, (byte)1);
        field = SimpleFieldCreate.setField(field, "King", 53, (byte)1);
        field = SimpleFieldCreate.setField(field, "Rook", 61, (byte)1);
        field = SimpleFieldCreate.setField(field, "Pawn", 54, (byte)1);
        field = SimpleFieldCreate.setField(field, "Pawn", 47, (byte)1);
        field = SimpleFieldCreate.setField(field, "Pawn", 56, (byte)1);
        field = SimpleFieldCreate.setField(field, "Queen", 12, (byte)1);
        
        

        PrimitivRating prim = new PrimitivRating();
        System.out.println(prim.primRate(field.getCurrentFieldAsHashMapWithBytes()));

        
        System.out.println(toText.fieldToString(field.getCurrentFieldAsHashMapWithBytes()));
        
        Move move = moveTo.getNext(field, (byte)0);
        
        System.out.println("Move-Objekt: "+move.getFieldFrom()+" "+move.getFieldTo()+" "+move.getPlayerColor());
        
    }

}
