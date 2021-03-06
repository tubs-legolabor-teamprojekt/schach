package useful;

import java.util.HashMap;
import java.util.LinkedList;
import rating.PrimitivKI;
import rating.PrimitivRating;
import util.ChessfigureConstants;
import components.*;
import game.Move;
import gameTree.NextMove;
import useful.TextChessField;

public class TestingNextMove {

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        final String PATH = "ki.ser";
        LinkedList<SituationWithRating> li = new LinkedList<SituationWithRating>();
        LinkedList<HashMap<Integer,Byte>> moveListWhite = new LinkedList<HashMap<Integer,Byte>>();
        PrimitivKI ki = new PrimitivKI();
        ki.deserialize(PATH);
        
        MoveGenerator move = new MoveGenerator();
        Field field = new Field(true);
        field = Field.getInstance();
        
        moveListWhite = move.generateMoves(field.getCurrentFieldAsHashMapWithBytes(), ChessfigureConstants.WHITE);
        
        for(HashMap<Integer,Byte> map:moveListWhite) {
            System.out.println("------------------------------------------");
            System.out.println(TextChessField.fieldToString(map));
            ki.teachSituation(map, 3, ChessfigureConstants.BLACK);
            ki.serialize(PATH);
        }
        
        
        
        
        
/*
        // TODO Auto-generated method stub
        PrimitivKI ki = new PrimitivKI();
//        ki.deserialize("/Users/Schubi/ki.ser");
        
        NextMove moveTo = new NextMove();
        Field field = new Field(true);
        field = Field.getInstance();
        field.moveFigure(13, 29);
//        field.moveFigure(63, 46);
//        field.moveFigure(11, 27);
        System.out.println(TextChessField.fieldToString(field.getCurrentFieldAsHashMapWithBytes()));
        String fp = Fingerprint.getFingerprint(field.getCurrentFieldAsHashMapWithBytes());
//        field.moveFigure(53, 45);
        field.moveFigure(51, 35);
        
        li.add(new SituationWithRating(field.getCurrentFieldAsHashMapWithBytes(), 100, 100));
//        ki.setSituation(fp, li, 10);
//        ki.serialize("/Users/Schubi/ki.ser");
        
        
        PrimitivRating prim = new PrimitivRating();
        float rating = prim.primFigureRate(field.getCurrentFieldAsHashMapWithBytes(), ChessfigureConstants.WHITE, 3,false);
        
        
        System.out.println("Bewertung "+rating);
        System.out.println(TextChessField.fieldToString(field.getCurrentFieldAsHashMapWithBytes()));

        // int pos = ki.isRated(field.getCurrentFieldAsHashMapWithBytes());
        // System.out.println(pos);

        // System.out.println(prim.primRate(field.getCurrentFieldAsHashMapWithBytes()));
        // System.out.println(TextChessField.fieldToString(field.getCurrentFieldAsHashMapWithBytes()));
        // Move move = moveTo.getNext(field, ChessfigureConstants.BLACK);
        // System.out.println("Move-Objekt: "+move.getFieldFrom()+" "+move.getFieldTo()+" "+move.getPlayerColor());
*/
    }

    private static Field createStartField(Field field)
    {
        field = SimpleFieldCreate.setField(field, "b", 9, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "b", 10, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "b", 11, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "b", 12, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "b", 13, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "b", 14, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "b", 15, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "b", 16, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "t", 1, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "t", 8, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "s", 2, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "s", 7, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "l", 3, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "l", 6, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "d", 4, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "k", 5, (byte) 0, false);

        field = SimpleFieldCreate.setField(field, "b", 49, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 50, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 51, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 52, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 53, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 54, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 55, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 56, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "t", 57, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "t", 64, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "s", 58, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "s", 63, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "l", 59, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "l", 62, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "d", 60, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "k", 61, (byte) 1, false);
        return field;
    }

    private static Field createStartFieldOneTurn(Field field)
    {
        field = SimpleFieldCreate.setField(field, "b", 9, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "b", 10, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "b", 11, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "b", 28, (byte) 0, true);
        field = SimpleFieldCreate.setField(field, "b", 13, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "b", 14, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "b", 15, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "b", 16, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "t", 1, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "t", 8, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "s", 2, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "s", 7, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "l", 3, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "l", 6, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "d", 4, (byte) 0, false);
        field = SimpleFieldCreate.setField(field, "k", 5, (byte) 0, false);

        field = SimpleFieldCreate.setField(field, "b", 49, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 50, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 51, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 52, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 53, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 54, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 55, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "b", 56, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "t", 57, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "t", 64, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "s", 58, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "s", 63, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "l", 59, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "l", 62, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "d", 60, (byte) 1, false);
        field = SimpleFieldCreate.setField(field, "k", 61, (byte) 1, false);
        return field;
    }
    /*
     * private static Field create5(Field field) { field =
     * SimpleFieldCreate.setField(field, "k", 63, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "t", 52, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "t", 51, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "k", 1, (byte) 1); return field; }
     * 
     * 
     * // Matt in einem Zug
     * 
     * private static Field create4(Field field) { field =
     * SimpleFieldCreate.setField(field, "t", 3, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "t", 8, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "b", 9, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "l", 13, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "k", 15, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "d", 16, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "b", 18, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "s", 19, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "b", 22, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "b", 27, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "s", 28, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "b", 29, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "b", 31, (byte) 1);
     * 
     * field = SimpleFieldCreate.setField(field, "l", 39, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "b", 41, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "b", 42, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "b", 44, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "b", 45, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "b", 54, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "s", 52, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "l", 50, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "d", 58, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "t", 59, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "t", 61, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "s", 62, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "k", 63, (byte) 0); return field; }
     * 
     * private static Field create1(Field field) { field =
     * SimpleFieldCreate.setField(field, "t", 6, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "k", 7, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "b", 14, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "b", 16, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "b", 19, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "b", 23, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "l", 24, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "d", 43, (byte) 0); field =
     * SimpleFieldCreate.setField(field, "t", 33, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "b", 49, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "k", 53, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "t", 61, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "b", 54, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "b", 47, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "b", 56, (byte) 1); field =
     * SimpleFieldCreate.setField(field, "d", 12, (byte) 1); return field; }
     * 
     * private static Field create3(Field field) { FigureKing fk2 = new
     * FigureKing(ChessfigureConstants.BLACK); field.putFigureAt(59, fk2);
     * 
     * FigureRook fr1 = new FigureRook(ChessfigureConstants.BLACK);
     * field.putFigureAt(64, fr1);
     * 
     * FigureRook fr2 = new FigureRook(ChessfigureConstants.WHITE);
     * field.putFigureAt(49, fr2);
     * 
     * FigureBishop fb1 = new FigureBishop(ChessfigureConstants.BLACK);
     * field.putFigureAt(50, fb1);
     * 
     * FigureRook fr3 = new FigureRook(ChessfigureConstants.BLACK);
     * field.putFigureAt(52, fr3);
     * 
     * FigurePawn fp1 = new FigurePawn(ChessfigureConstants.BLACK);
     * field.putFigureAt(56, fp1);
     * 
     * FigurePawn fp2 = new FigurePawn(ChessfigureConstants.BLACK);
     * field.putFigureAt(46, fp2);
     * 
     * FigurePawn fp3 = new FigurePawn(ChessfigureConstants.BLACK);
     * field.putFigureAt(39, fp3);
     * 
     * FigurePawn fp4 = new FigurePawn(ChessfigureConstants.WHITE);
     * field.putFigureAt(36, fp4);
     * 
     * FigureKnight fk1 = new FigureKnight(ChessfigureConstants.BLACK);
     * field.putFigureAt(27, fk1);
     * 
     * FigureBishop fb2 = new FigureBishop(ChessfigureConstants.WHITE);
     * field.putFigureAt(23, fb2);
     * 
     * FigurePawn fp7 = new FigurePawn(ChessfigureConstants.WHITE);
     * field.putFigureAt(14, fp7);
     * 
     * FigurePawn fp5 = new FigurePawn(ChessfigureConstants.WHITE);
     * field.putFigureAt(15, fp5);
     * 
     * FigurePawn fp6 = new FigurePawn(ChessfigureConstants.WHITE);
     * field.putFigureAt(16, fp6);
     * 
     * FigureKing fk3 = new FigureKing(ChessfigureConstants.WHITE);
     * field.putFigureAt(7, fk3);
     * 
     * FigureRook fr4 = new FigureRook(ChessfigureConstants.WHITE);
     * field.putFigureAt(2, fr4); return field; }
     * 
     * private static Field create2(Field field) { FigurePawn fp3 = new
     * FigurePawn(ChessfigureConstants.BLACK); field.putFigureAt(36, fp3);
     * 
     * FigurePawn fp12 = new FigurePawn(ChessfigureConstants.BLACK);
     * field.putFigureAt(38, fp12);
     * 
     * FigureKnight fk1 = new FigureKnight(ChessfigureConstants.WHITE);
     * field.putFigureAt(37, fk1);
     * 
     * FigurePawn fp11 = new FigurePawn(ChessfigureConstants.WHITE);
     * field.putFigureAt(39, fp11);
     * 
     * FigurePawn fp13 = new FigurePawn(ChessfigureConstants.WHITE);
     * field.putFigureAt(40, fp13);
     * 
     * FigureBishop fb1 = new FigureBishop(ChessfigureConstants.BLACK);
     * field.putFigureAt(44, fb1);
     * 
     * FigureBishop fb3 = new FigureBishop(ChessfigureConstants.BLACK);
     * field.putFigureAt(50, fb3);
     * 
     * FigurePawn fp14 = new FigurePawn(ChessfigureConstants.BLACK);
     * field.putFigureAt(49, fp14); FigurePawn fp15 = new
     * FigurePawn(ChessfigureConstants.BLACK); field.putFigureAt(54, fp15);
     * FigurePawn fp16 = new FigurePawn(ChessfigureConstants.BLACK);
     * field.putFigureAt(55, fp16); FigurePawn fp17 = new
     * FigurePawn(ChessfigureConstants.BLACK); field.putFigureAt(56, fp17);
     * 
     * FigureKing fk2 = new FigureKing(ChessfigureConstants.BLACK);
     * field.putFigureAt(63, fk2);
     * 
     * FigureQueen fq1 = new FigureQueen(ChessfigureConstants.WHITE);
     * field.putFigureAt(21, fq1);
     * 
     * FigurePawn fp7 = new FigurePawn(ChessfigureConstants.WHITE);
     * field.putFigureAt(28, fp7);
     * 
     * FigurePawn fp6 = new FigurePawn(ChessfigureConstants.WHITE);
     * field.putFigureAt(30, fp6);
     * 
     * FigurePawn fp1 = new FigurePawn(ChessfigureConstants.BLACK);
     * field.putFigureAt(26, fp1);
     * 
     * FigurePawn fp5 = new FigurePawn(ChessfigureConstants.WHITE);
     * field.putFigureAt(18, fp5);
     * 
     * FigurePawn fp2 = new FigurePawn(ChessfigureConstants.WHITE);
     * field.putFigureAt(20, fp2);
     * 
     * FigureKing fk3 = new FigureKing(ChessfigureConstants.WHITE);
     * field.putFigureAt(16, fk3);
     * 
     * FigureQueen fq3 = new FigureQueen(ChessfigureConstants.BLACK);
     * field.putFigureAt(9, fq3);
     * 
     * FigureBishop fb2 = new FigureBishop(ChessfigureConstants.WHITE);
     * field.putFigureAt(15, fb2); return field; }
     */
}
