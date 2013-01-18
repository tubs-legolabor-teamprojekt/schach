package gameTree;

import java.util.*;

import alphaBeta.AlphaBetaSearch;

public class AlphaBetaTest {

    final static int DEPTH = 2;
    final static int PLAYER = 1;

    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        long zeit = 0, zeit2 = 0;
        zeit = System.currentTimeMillis();
        // SYSTEM============================================================================================

        AlphaBetaSearch search = new AlphaBetaSearch();

        /*
         * Hier ensteht der Test
         */
        HashMap<Integer, String> map = new HashMap<Integer, String>();

        int Wert = search.alphaBeta(map, DEPTH, PLAYER, -100, 100);

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
