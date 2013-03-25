package useful;

import java.util.*;
import java.util.Map.Entry;

public class TextChessField {
	
	private static final int[] fieldConversion = 	{57,58,59,60,61,62,63,64,
													49,50,51,52,53,54,55,56,
													41,42,43,44,45,46,47,48,
													33,34,35,36,37,38,39,40,
													25,26,27,28,29,30,31,32,
													17,18,19,20,21,22,23,24,
													9,10,11,12,13,14,15,16,
													1, 2, 3, 4, 5, 6, 7, 8};
	
	
	private TextChessField() {

		
	}
	
	
/*
 * Gibt eine (Schachfeld)HashMap als Text "grafisch" aus
 * @param field Hashmap mit Schachfiguren
 * @return "grafischer" String, welcher ausgegeben werden kann	
 */
	
	public static String fieldToString(HashMap<Integer, Byte> field) {
		
		String fieldArray[] = new String [65];
		for(int i=1;i<=64;i++) {
			fieldArray[i] = "  ";
		}
		
		
		Iterator<Entry<Integer,Byte>> it = field.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry<Integer, Byte> pair = (Map.Entry<Integer, Byte>) it.next();
			String figure = pair.getValue().toString();
			
			if(figure.length()==1) {
				figure = "0"+figure;
			}
			fieldArray[pair.getKey()] = figure;
		}
		
		//mal wieder konvertierung zwischen feldern...oben links ist hierdurch Feld1
		String fieldArrayConvert[] = new String [65];
			for(int i=1; i<=64; i++) {
				fieldArrayConvert[i]=fieldArray[fieldConversion[i-1]];
			}
		
		
		
		String textChessField = "";
		String borderH = " ---- ---- ---- ---- ---- ---- ---- ---- "+"\n";
		String borderV[] = {"","","","","","","",""};
		
		textChessField += borderH;
		for(int i=0;i<8;i++) {
			for(int j = ((i+1)*8-8)+1;j<=(i+1)*8;j++ ) {
				borderV[i] += "| "+fieldArrayConvert[j]+" "; 
			}
				borderV[i] += "|"+"\n";
				textChessField += borderV[i];
				textChessField += borderH;
		}
		
		return textChessField;
	}

}
