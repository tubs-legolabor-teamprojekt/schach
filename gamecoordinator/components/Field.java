package components;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

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
	 * Ermittelt die Position der Figur in der Form A7/C3 etc.
	 * @param field Nummer des Feldes (von 1 bis 64)
	 * @return Der Name des Feldes (z.B. A8)
	 */
	public static String getFieldName(int field)
	{
		int		y = ((int) Math.floor(field/8.0)) + 1;
		char	x = util.ChessfigureConstants.getXPosition((byte)(field - (8*(y-1)) + 1));
		String str = "" + x + "" + y;
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
