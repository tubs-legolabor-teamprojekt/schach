package gameTree;

import java.util.LinkedList;

import alphaBeta.ABThread;
import alphaBeta.ABTree;

import components.Field;

import useful.Test_Situation;
import useful.ValidMove;
import util.ExtractInformationFromBinary;

public class TreeTest {

	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();
		long zeit=0, zeit2 = 0;
		zeit  = System.currentTimeMillis();
		//SYSTEM============================================================================================
		
		System.out.println("Situation erstellen");		
		short[] sit = Test_Situation.TEST_SITUATION;
		System.out.println("Situation erstellen fertig");
		
		System.out.println("Spielbaum erstellen");
		ABTree tree = new ABTree(sit);
		System.out.println("Spielbaum erstellen fertig");
		
		System.out.println("Thread erstellen");
		ABThread t1 = new ABThread();
		System.out.println("Thread erstellen fertig");
		
		System.out.println("Spielbaum zuweisen");
		t1.setTree(tree);
		System.out.println("Spielbaum zuweisen fertig");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Thread starten");
		t1.run();
		System.out.println("Thread fertig");
		
		System.out.println("Wert "+tree.beta);
		
	
		
		
		
		//SYSTEM=ENDE=======================================================================================
		System.out.println(rt.totalMemory()/1000000+" available <==> "+ (rt.totalMemory() - rt.freeMemory())/1000000 +" used <==> "+ rt.freeMemory()/1000000 +" free");
		
		zeit2  = System.currentTimeMillis();
		
		System.out.println("gebrauchte Zeit: "+(zeit2 - zeit) +" ms");

	}

}
