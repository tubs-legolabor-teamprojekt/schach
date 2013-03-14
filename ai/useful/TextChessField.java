package useful;

import java.util.*;
import java.util.Map.Entry;

public class TextChessField {
	public TextChessField() {

		
	}
	
/*
 * Gibt eine (Schachfeld)HashMap als Text "grafisch" aus
 * @param field Hashmap mit Schachfiguren
 * @return "grafischer" String, welcher ausgegeben werden kann	
 */
	
	public String fieldToString(HashMap<Integer, Byte> field) {
		
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
		
		String textChessField = "";
		String borderH = " ---- ---- ---- ---- ---- ---- ---- ---- "+"\n";
		String borderV[] = {"","","","","","","",""};
		
		textChessField += borderH;
		for(int i=0;i<8;i++) {
			for(int j = ((i+1)*8-8)+1;j<=(i+1)*8;j++ ) {
				borderV[i] += "| "+fieldArray[j]+" "; 
			}
				borderV[i] += "|"+"\n";
				textChessField += borderV[i];
				textChessField += borderH;
		}
		
		return textChessField;
	}

}
