package gameTree;

public class TreeTest {

	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();
		long zeit=0, zeit2 = 0;
		zeit  = System.currentTimeMillis();
		//SYSTEM============================================================================================
		
		
		
		
		GameTree gt = new GameTree(StandardSituation.get_STANDARD_SITUATION(), (byte)0, true);
		
		gt.build_Tree(true);
		//gt.build_Tree_pool(true);
		
		
		
		//SYSTEM=ENDE=======================================================================================
		System.out.println(rt.totalMemory()/1000000+" available <==> "+ (rt.totalMemory() - rt.freeMemory())/1000000 +" used <==> "+ rt.freeMemory()/1000000 +" free");
		
		zeit2  = System.currentTimeMillis();
		
		System.out.println("gebrauchte Zeit: "+(zeit2 - zeit));

	}

}
