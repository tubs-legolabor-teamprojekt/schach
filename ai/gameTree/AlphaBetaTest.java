package gameTree;

import java.util.*;

import util.ChessfigureConstants;
import dataStructure.ChessField;

import alphaBeta.AlphaBetaSearch;

public class AlphaBetaTest {

    final static int DEPTH = 2;
    final static int PLAYER = 1;

    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        long zeit = 0, zeit2 = 0;
        zeit = System.currentTimeMillis();
        // SYSTEM============================================================================================
        ChessField field = new ChessField();
        
        field.put(3, ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.PAWN, false));
        field.put(4, ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.QUEEN, false));
        field.put(5, ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.KING, false));
        field.put(6, ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.PAWN, false));
        field.put(7, ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.BISHOP, false));
        field.put(8, ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.KING, false));
        
        AlphaBetaSearch search = new AlphaBetaSearch();
        
        int Wert = search.alphaBeta(field, DEPTH, PLAYER, -100, 100);

        System.out.println("Tiefe: " + DEPTH);
        System.out.println("gefundener Wert: " + Wert);
        System.out.println("Knoten durchsucht "+search.count);

        // SYSTEM=ENDE=======================================================================================
        System.out.println(rt.totalMemory() / 1000000 + " available <==> " + (rt.totalMemory() - rt.freeMemory()) / 1000000 + " used <==> " + rt.freeMemory()
                / 1000000 + " free");

        zeit2 = System.currentTimeMillis();

        System.out.println("gebrauchte Zeit: " + (zeit2 - zeit) + " ms");

    }

}
