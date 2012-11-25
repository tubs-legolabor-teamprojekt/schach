package useful;

import java.util.LinkedList;

/**
 * Pseudoklasse zur ValidMove
 * @author tobi
 *
 */
public class pseudoValidMove {

	/**
	 * Gibt eine Lite mit 40 identischen Elementen zurück...
	 * Nur zum Test der Alpha-Beta-Suche
	 * @param list
	 * @return
	 */
	public static LinkedList<short[]> move(short[] list){
		
		LinkedList<short[]> liste = new LinkedList<short[]>();
		
		for(int i = 0 ; i < 40 ; i++){
			liste.add(list);
		}
		
		return liste;
	}
	
}
