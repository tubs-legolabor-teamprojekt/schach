package components;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import util.ChessfigureConstants;

/**
 * Repraesentiert das Spielfeld und enthaelt eine HashMap mit allen Figuren und ihrer aktuellen Position.
 * @author Florian Franke
 *
 */
public class Field
{
	/**
	 * HashMap, die alle im Spiel befindlichen Figuren enthaelt.
	 */
	private HashMap<Integer, Figure> figures = new HashMap<Integer, Figure>();
	
	/**
	 * Holt die Figur an der gegebenen Position, sofern dort eine Figur vorhanden ist.
	 * @param position Die Position der Figur
	 * @return Figur oder null wenn keine Figur vorhanden ist.
	 */
	public Figure getFigureAt(Integer position)
	{
		if (this.isFigureOnField(position))
			return figures.get(position);
		else
			return null;
	}
	
	/**
	 * Ueberprueft ob auf der gegebenen Position eine Figur steht.
	 * @param position Zur pruefende Position
	 * @return True: Figur vorhanden; False: Leeres Feld
	 */
	public boolean isFigureOnField(Integer fieldNumber)
	{
		return this.figures.containsKey(fieldNumber);
	}
	
	/**
	 * Positioniert eine Figur auf dem Feld
	 * @param fieldNumber Nummer des Felds, auf dem die Figur positioniert werden soll.
	 * @param figure Die zu positionierende Figur
	 * @return True: Figur konnte positioniert werden. False: Feld schon besetzt oder nicht vorhanden.
	 */
	public boolean putFigureAt(Integer fieldNumber, Figure figure)
	{
		if (	!this.isFigureOnField(fieldNumber) &&
				fieldNumber > 0 &&
				fieldNumber <= 64) {
			this.figures.put(fieldNumber, figure);
			return true;
		} else {
			System.out.println("Es ist eine Figur auf dem Feld (" + fieldNumber + ") schon vorhanden.\n");
			
			return false;
		}
	}
	
	/**
	 * Entfernt die Figur auf der gegebenen Feldnummer.
	 * @param fieldNumber Nummer des Feldes, von dem die Figur entfernt werden soll
	 * @return True: Figur entfernt; False: Keine Figur vorhanden oder fehlerhafte Feldnummer
	 */
	public boolean removeFigureAt(Integer fieldNumber)
	{
		if (	this.isFigureOnField(fieldNumber) &&
				fieldNumber > 0 &&
				fieldNumber <= 64) {
			this.figures.remove(fieldNumber);
			return true;
		} else {
			System.out.println("Keine Figur auf dem Feld");
			return false;
		}
	}
	
	/**
	 * Exportiert alle Figuren, die sich aktuell auf dem Spielfeld befinden,
	 * in ein Short-Array
	 * @return Das Short-Array mit allen Short-Werten der Figuren
	 */
	public short[] exportToShortArray()
	{
		// short-array anlegen. die groesse entspricht der anzahl der figuren
		short[] shortArray = new short[this.figures.size()];
		
		// Iterator erstellen, der ueber alle Figuren iteriert
		Iterator<Entry<Integer, Figure>> it = this.figures.entrySet().iterator();
		// Zaehler, der bei jeder Figur inkrementiert,
		// um die aktuelle Position im ShortArray zu bestimmen.
		int arrayPosition = 0;
		// iteriere ueber alle Figuren
		while (it.hasNext()) {
			// Position der Figur steht im Key
			Integer i = (Integer) it.next().getKey();
			// value-Objekt in ein Figure-Objekt casten
			Figure f = (Figure) it.next().getValue();
			
			// Farbe, Figurtyp, X/Y-Position der aktuellen Figur ermitteln
			byte color		= f.getColor();
			byte figureType	= f.getFigureType();
			byte positionX	= getXPositionFromFieldnumber(i);
			byte positionY	= getYPositionFromFieldnumber(i);
			
			// Short-Wert mit ermittelten Werten berechnen
			short s = ChessfigureConstants.makeFigureShort(color, figureType, positionX, positionY);
			
			// neuen Short-Wert dem ShortArray hinzufuegen
			shortArray[arrayPosition] = s;
			
			// zaehler erhoehen
			arrayPosition++;
		}
		
		return shortArray;
	}
	
	/**
	 * Berechnet aus der gegebenen Feldnummer die X-Position
	 * @param fieldNumber Nummer des Feldes (1-64)
	 * @return Nummer der X-Position (1-8)
	 */
	public static byte getXPositionFromFieldnumber(int fieldNumber)
	{
		byte by = -1;
		try {
			if (fieldNumber < 1 || fieldNumber > 64)
				throw new FieldException("Gegebene Feldwert ausserhalb des Feldes (1-64)!");
			else {
				// y-Wert ermitteln
				byte y = getYPositionFromFieldnumber(fieldNumber);
				// x-Wert berechnen
				by = (byte)(fieldNumber - (8*(y-1)));
			}
		} catch (FieldException e)
		{
			System.out.println(e.getMessage());
		}
		return by;
	}
	
	/**
	 * Berechnet aus der gegebenen Feldnummer (1-64) die Y-Position (1-8)
	 * @param fieldNumber Nummer des Feldes (1-64)
	 * @return Nummer der Y-Position (1-8)
	 */
	public static byte getYPositionFromFieldnumber(int fieldNumber)
	{
		byte by = -1;
		try {
			if (fieldNumber < 1 || fieldNumber > 64)
				throw new FieldException("Gegebene Feldwert ausserhalb des Feldes (1-64)!");
			else {
				// Von (1-64) auf (0-63) aendern
				fieldNumber--;
				// Feldnummer von 1-8 berechnen
				by = (byte) (Math.floor(fieldNumber/8.0)+1);
			}
		} catch (FieldException e)
		{
			System.out.println(e.getMessage());
		}
		return by;
	}
	
	/**
	 * Ermittelt die Position der Figur in der Form A7/C3 etc.
	 * @param fieldNumber Nummer des Feldes (von 1 bis 64)
	 * @return Name des Feldes (z.B. A8)
	 */
	public static String getFieldName(int fieldNumber)
	{
		byte	y = getYPositionFromFieldnumber(fieldNumber);
		char	x = ChessfigureConstants.getXPosition(getXPositionFromFieldnumber(fieldNumber));
		String str = x + "" + y;
		return str;
	}

	@Override
	public String toString() {
		String str = "Field [figures=[";
		Iterator<Entry<Integer, Figure>> it = this.figures.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Figure f = (Figure) it.next().getValue();
			if (i > 0)
				str += ", ";
			str += f.toString();
			it.remove();
			i++;
		}
		str += "]]";
		return str;
	}
	
}
