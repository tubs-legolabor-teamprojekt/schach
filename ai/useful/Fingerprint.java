package useful;

import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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
    @SuppressWarnings("unchecked")
    public static String getFingerprint(HashMap<Integer, Byte> map)
    {
        HashMap<Integer, Byte> cloneMap = (HashMap<Integer, Byte>) map.clone();
        String fingerprint = "";
        Iterator<Entry<Integer, Byte>> it = cloneMap.entrySet().iterator();

        /*
         * Da die HashMap die Eintraege unterschiedlich ausliest, ist hier die Moeglichkeit,
         * FigureValue mit position zu verknuepfen um sie dann sortieren zu koennen.
         */
        class Identifier implements Comparable {
            byte figureValue;
            int position;

            public Identifier(byte figureValue, int position) {
                this.figureValue = figureValue;
                this.position = position;
            }

            public byte getFigureValue()
            {
                return this.figureValue;
            }

            public int getPosition()
            {
                return this.position;
            }

            public int compareTo(Object id)
            {
                int position;
                if (id instanceof Identifier) {
                    position = ((Identifier) id).getPosition();
                    return (position-this.position);
                } else {
                    return 0;
                }
            }
        }
        List<Identifier> id = new LinkedList<Identifier>();

        while (it.hasNext()) {
            Map.Entry<Integer, Byte> pair = (Map.Entry<Integer, Byte>) it.next();
            byte figureValue = pair.getValue();
            int position = pair.getKey().intValue();
            id.add(new Identifier(figureValue, position));
            it.remove();
        }
        //fuer einen eindeutigen Fingerprint, muss Liste sortiert werden.
        Collections.sort(id);

        while (id.size() > 0) {
            fingerprint += id.get(0).getPosition() + "," + id.get(0).getFigureValue() + "--";
            id.remove(0);
        }
        fingerprint = fingerprint.substring(0, fingerprint.length() - 2);
        return fingerprint;

    }
}
