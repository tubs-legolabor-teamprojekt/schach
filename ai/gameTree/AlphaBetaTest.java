package gameTree;

import java.util.*;

import useful.PseudoValidMove;
import util.ChessfigureConstants;
import dataStructure.ChessField;

import alphaBeta.AlphaBetaSearch;

public class AlphaBetaTest {

    final static int DEPTH = 3;
    final static int PLAYER = 1;

    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        long zeit = 0, zeit2 = 0;
        zeit = System.currentTimeMillis();

        // Initialisierung der Suche
        ChessField field = new ChessField();
        field.equipStartField();
        AlphaBetaSearch search = new AlphaBetaSearch();
        int Wert = search.max(field, DEPTH, PLAYER, -100, 100);

        System.out.println("gefundener Wert: " + Wert);
        System.out.println("Knoten durchsucht " + search.count);
        System.out.println("Verhältnis (real/max): " + Math.floor((search.count / (Math.pow(PseudoValidMove.NUMBER_OF_CHILDS, DEPTH))) * 10000) / 100.);
        System.out.println("Tiefe: " + DEPTH);

        // Ausgabe Zeitmessung, Anzahl besuchter Blaetter etc.
        System.out.println(rt.totalMemory() / 1000000 + " verfuegbar <==> " + (rt.totalMemory() - rt.freeMemory()) / 1000000 + " benutzt <==> "
                + rt.freeMemory() / 1000000 + " free");
        zeit2 = System.currentTimeMillis();
        System.out.println("gebrauchte Zeit: " + (zeit2 - zeit) + " ms");

    }

}
