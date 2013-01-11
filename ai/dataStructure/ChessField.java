package dataStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import util.ExtractInformationFromBinary;


@SuppressWarnings("serial")
public class ChessField extends HashMap<Integer, Short>
{
    private HashMap<Integer, Short> map = new HashMap<Integer, Short>();
    
    
    
    @Override
    public Short put(Integer key, Short value) {
        if (key < 1 || key > 64)
            throw new IllegalArgumentException("Wert nicht zwischen 1 und 64.");
        
        super.put(key, value);
        
        return value;
    }

    /**
     * Ermittelt die Feldnummer(n), auf denen die übergebene Figur steht.
     * @param figureType Figurtyp
     * @param figureColor Farbe der Figur
     * @return Liste von Feldnummern
     */
    public ArrayList<Integer> getPositionsOfFigureType(byte figureType, byte figureColor)
    {
        // Liste für die Feldnummern
        ArrayList<Integer> positionsOfFigureType = new ArrayList<Integer>();
        
        // Iteriere über alle Figuren der Map
        Iterator<Entry<Integer, Short>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            // Aktuelles Key/Value-Paar
            Map.Entry<Integer, Short> pair = (Map.Entry<Integer, Short>)it.next();
            
            // Entpspricht die aktuelle Figur der gesuchten?
            if (    ExtractInformationFromBinary.getFigure(pair.getValue()) == figureType &&
                    ExtractInformationFromBinary.getColor(pair.getValue()) == figureColor) {
                // Figur gefunden, Feldnummer hinzufügen
                positionsOfFigureType.add(pair.getKey());
            }
            
            it.remove(); // avoids a ConcurrentModificationException
        }
        
        return positionsOfFigureType;
    }

    @Override
    public String toString() {
        return "ChessField [map=" + map + "]";
    }
    
}
