package dataStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import util.ChessfigureConstants;
import util.ExtractInformationFromBinary;


@SuppressWarnings("serial")
public class ChessField extends HashMap<Integer, Byte>
{
    
    /*
     * TODO
     * Was passiert, das diese Spielsituation herbeiführte.
     * Grund: Heuristik ermöglichen, um Züge vorzusortieren.
     */
    
    @Override
    public Byte put(Integer key, Byte value) {
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
        Iterator<Entry<Integer, Byte>> it = this.entrySet().iterator();
        while (it.hasNext()) {
            // Aktuelles Key/Value-Paar
            Map.Entry<Integer, Byte> pair = (Map.Entry<Integer, Byte>)it.next();
            
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
    
    /**
     * Bestückt das Schachfeld als Startfeld.
     */
    public void equipStartField()
    {
     // Feld sicherheitshalber leern
        this.clear();
        
        // Reihe 1 und 8 setzen (Koenig, Dame, Springer, Laeufer, Turm)
        for (int i = 1; i <= 2; i++) {
            
            int rowCount = 0;
            byte color = ChessfigureConstants.WHITE;
            
            if (i == 2) {
                rowCount = 56;
                color = ChessfigureConstants.BLACK;
            }
            
            int position = rowCount;
            // Turm
            
            byte fr1 = ChessfigureConstants.makeFigureByte(color, ChessfigureConstants.ROOK, false);
            this.put(++position, fr1);
            
            // Springer
            byte fk1 = ChessfigureConstants.makeFigureByte(color, ChessfigureConstants.KNIGHT, false);
            this.put(++position, fk1);
            
            // Laeufer
            byte fb1 = ChessfigureConstants.makeFigureByte(color, ChessfigureConstants.BISHOP, false);
            this.put(++position, fb1);
            
            // Dame
            byte fq = ChessfigureConstants.makeFigureByte(color, ChessfigureConstants.QUEEN, false);
            this.put(++position, fq);
            
            // Koenig
            byte fk = ChessfigureConstants.makeFigureByte(color, ChessfigureConstants.KING, false);
            this.put(++position, fk);
            
            // Laeufer
            byte fb2 = ChessfigureConstants.makeFigureByte(color, ChessfigureConstants.BISHOP, false);
            this.put(++position, fb2);
            
            // Springer
            byte fk2 = ChessfigureConstants.makeFigureByte(color, ChessfigureConstants.KNIGHT, false);
            this.put(++position, fk2);
            
            // Turm
            byte fr2 = ChessfigureConstants.makeFigureByte(color, ChessfigureConstants.ROOK, false);
            this.put(++position, fr2);
        }
        
        // Bauern setzen
        for (int j = 1; j <= 16; j++) {
            if (j <= 8) {
                // Position in Reihe 2 bestimmen
                Integer position = 8 + j;
                
                // weissen Bauern aufs Spielfeld setzen
                this.put(position, ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.PAWN, false));

            } else {
                // Position in Reihe 7 bestimmen
                int position = 40 + j;
                
                // schwarzen Bauern aufs Spielfeld setzen
                this.put(position, ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.PAWN, false));
            }
        }
    }

    @Override
    public String toString() {
        return "ChessField [map=" + this + "]";
    }
    
}
