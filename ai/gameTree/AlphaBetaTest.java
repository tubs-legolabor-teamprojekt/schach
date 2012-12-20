package gameTree;

import java.util.LinkedList;

import alphaBeta.ABThread;
import alphaBeta.ABTree;

import components.Field;

import useful.Test_Situation;
import useful.ValidMove;
import util.ExtractInformationFromBinary;

public class AlphaBetaTest {

	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();
		long zeit=0, zeit2 = 0;
		zeit  = System.currentTimeMillis();
		//SYSTEM============================================================================================
		
		/*
		 * Hier ensteht der Test
		 */
		
		//SYSTEM=ENDE=======================================================================================
		System.out.println(rt.totalMemory()/1000000+" available <==> "+ (rt.totalMemory() - rt.freeMemory())/1000000 +" used <==> "+ rt.freeMemory()/1000000 +" free");
		
		zeit2  = System.currentTimeMillis();
		
		System.out.println("gebrauchte Zeit: "+(zeit2 - zeit) +" ms");

	}

}
