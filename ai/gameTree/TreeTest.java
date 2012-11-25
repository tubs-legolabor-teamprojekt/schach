package gameTree;

import java.util.LinkedList;

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
		
		short[] sit = Test_Situation.TEST_SITUATION;
		
		LinkedList<short[]> children = ValidMove.pawnCheck(sit);
		
		// Alle neuen Situationen durchlaufen
		for (short[] sh : children) {
			System.out.println("Neue Situation:\n+++");
			// Alle Figuren der neuen Situation durchlaufen
			for(int i = 0; i < sh.length; i++) {
				System.out.println(ExtractInformationFromBinary.getAllInfosFromShort(sh[i]));
			}
			System.out.println("ENDE+++");
		}
	
		
		
		
		//SYSTEM=ENDE=======================================================================================
		System.out.println(rt.totalMemory()/1000000+" available <==> "+ (rt.totalMemory() - rt.freeMemory())/1000000 +" used <==> "+ rt.freeMemory()/1000000 +" free");
		
		zeit2  = System.currentTimeMillis();
		
		System.out.println("gebrauchte Zeit: "+(zeit2 - zeit) +" ms");

	}

}
