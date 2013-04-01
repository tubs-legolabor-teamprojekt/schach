package useful;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import util.ChessfigureConstants;
import components.Figure;

public class Fingerprint {
	
	public static String getFingerprint(HashMap<Integer,Byte> map) { 
		String fingerprint = "";
		Iterator<Entry<Integer, Byte>> it = map.entrySet().iterator();
		
		try {
			MessageDigest digest = MessageDigest.getInstance( "SHA", "SUN" );
		while(it.hasNext()) {
			
			
			Map.Entry<Integer, Byte> pair = (Map.Entry<Integer, Byte>) it.next();
			byte figureValue = pair.getValue();
			int position = pair.getKey().intValue();
			Figure figure = ChessfigureConstants.makeFigureFromByte(figureValue);
			
			//nach und nach einzelne Figuren+Positionen hinzufuegen zum Hash
			digest.update(String.valueOf(figure.getFigureLetter()).getBytes());
			digest.update(String.valueOf(position).getBytes());
			
			it.remove();
		}
		for(byte b:digest.digest()) {
			fingerprint += String.valueOf((int)b);
		}
	}catch(Exception e){
	}
		
		return fingerprint;
	
}
}
