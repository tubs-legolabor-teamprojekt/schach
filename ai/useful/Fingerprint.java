package useful;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import util.ChessfigureConstants;
import components.Figure;

public class Fingerprint {

    /*
     * Gibt einen eindeutigen Fingerprint des aktuellen Spielfeldes
     * (Schachfiguren auf Brett) zurueck, um eine Situation zu identifizieren
     * 
     * @param map HashMap<Integer,Byte> mit Situation von der Fingerprint
     * erstellt werden soll
     * 
     * @return String Fingerprint als String als Format
     * "pos1,figwert1--pos2,figwert2--..."
     */
    public static String getFingerprint(HashMap<Integer, Byte> map)
    {
        HashMap<Integer, Byte> cloneMap = (HashMap<Integer, Byte>) map.clone();
        String fingerprint = "";
        Iterator<Entry<Integer, Byte>> it = cloneMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, Byte> pair = (Map.Entry<Integer, Byte>) it.next();
            byte figureValue = pair.getValue();
            int position = pair.getKey().intValue();
            fingerprint += position + "," + figureValue + "--";
            it.remove();
        }
        // Fingerprint s
        fingerprint = fingerprint.substring(0, fingerprint.length() - 2);
        return fingerprint;

    }
}
