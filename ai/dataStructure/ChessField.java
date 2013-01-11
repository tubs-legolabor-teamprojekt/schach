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

    public ArrayList<Integer> getPositionsOfFigureType(byte figureType)
    {
        ArrayList<Integer> positionsOfFigureType = new ArrayList<Integer>();
        
        Iterator<Entry<Integer, Short>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Short> pair = (Map.Entry<Integer, Short>)it.next();
            
            if (ExtractInformationFromBinary.getFigure(pair.getValue()) == figureType) {
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
