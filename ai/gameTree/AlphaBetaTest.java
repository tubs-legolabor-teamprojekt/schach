package gameTree;

import java.util.*;

import alphaBeta.AlphaBetaSearch;

public class AlphaBetaTest {

	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();
		long zeit=0, zeit2 = 0;
		zeit  = System.currentTimeMillis();
		//SYSTEM============================================================================================
		
		AlphaBetaSearch Search = new AlphaBetaSearch();
		
		/*
		 * Hier ensteht der Test
		 */
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		int depth = 2;
		
		int Wert = Search.alphaBeta(map, depth, -9999, 9999);
		System.out.println("Tiefe: "+depth);
		System.out.println("gefundener Wert: "+Wert);
		
		//SYSTEM=ENDE=======================================================================================
		System.out.println(rt.totalMemory()/1000000+" available <==> "+ (rt.totalMemory() - rt.freeMemory())/1000000 +" used <==> "+ rt.freeMemory()/1000000 +" free");
		
		zeit2  = System.currentTimeMillis();
		
		System.out.println("gebrauchte Zeit: "+(zeit2 - zeit) +" ms");

	}

}
