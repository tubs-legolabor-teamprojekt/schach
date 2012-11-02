package components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

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
	 * Private Variable, in der die Instanz dieser Klasse gespeichert ist.
	 */
	private static Field instance = null;
	
	/**
	 * Privater Konstruktor, damit nur eine Field-Instanz moeglich ist.
	 * Erstellt alle noetigen Figuren und positioniert sie auf dem Spielfeld.
	 */
	private Field()
	{
		// Figuren aufs (virtuelle) Feld stellen
		this.equipField();
	}
	
	/**
	 * Gibt die Field-Instanz zurueck
	 * @return Field-Instanz
	 */
	public static Field getInstance()
	{
		if (instance == null)
			instance = new Field();
		
		return instance;
	}
	
	/**
	 * Stattet das Schachfeld mit den Figuren aus.
	 */
	private void equipField()
	{
		// Feld sicherheitshalber leern
		this.figures.clear();
		
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
			FigureRook fr1 = new FigureRook(color);
			this.putFigureAt(++position, fr1);
			
			// Springer
			FigureKnight fk1 = new FigureKnight(color);
			this.putFigureAt(++position, fk1);
			
			// Laeufer
			FigureBishop fb1 = new FigureBishop(color);
			this.putFigureAt(++position, fb1);
			
			// Dame
			FigureQueen fq = new FigureQueen(color);
			this.putFigureAt(++position, fq);
			
			// Koenig
			FigureKing fk = new FigureKing(color);
			this.putFigureAt(++position, fk);
			
			// Laeufer
			FigureBishop fb2 = new FigureBishop(color);
			this.putFigureAt(++position, fb2);
			
			// Springer
			FigureKnight fk2 = new FigureKnight(color);
			this.putFigureAt(++position, fk2);
			
			// Turm
			FigureRook fr2 = new FigureRook(color);
			this.putFigureAt(++position, fr2);
		}
		
		// Bauern setzen
		for (int j = 1; j <= 16; j++) {
			if (j <= 8) {
				// Position in Reihe 2 bestimmen
				Integer position = 8 + j;
				
				// weissen Bauern aufs Spielfeld setzen
				this.putFigureAt(position, new FigurePawn(ChessfigureConstants.WHITE));

			} else {
				// Erstelle schwarze Bauern
				FigurePawn pawn = new FigurePawn(ChessfigureConstants.BLACK);
				
				// Position in Reihe 7 bestimmen
				int position = 40 + j;
				
				// schwarzen Bauern aufs Spielfeld setzen
				this.figures.put(position, pawn);
			}
		}
	}
	
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
	 * Bewegt eine Figur vom Start- aufs Zielfeld.
	 * Es wird davon ausgegangen, dass das Zielfeld leer ist. Sollte also eine Figur mit diesem
	 * Zug geschmissen werden, muss sie vorher entfernt worden sein.
	 * Sicherheitshalber wird es jedoch ueberprueft und notfalls gewarnt.
	 * @param fromFieldNumber Startfeld
	 * @param toFieldNumber Zielfeld
	 * @return True: Zug wurde ausgefuehrt; False: Zug konnte nicht ausgefuehrt werden
	 */
	public boolean moveFigure(Integer fromFieldNumber, Integer toFieldNumber)
	{
		// Rueckgabewert
		boolean ret = false;
		
		try {
			// Befindet sich eine Figur auf dem Feld?
			if (!this.isFigureOnField(fromFieldNumber))
				throw new FieldException("Es befindet sich keine Figur auf dem Feld angegebenen Feld (" + getFieldName(fromFieldNumber) + ")!");
			
			// Ist das Zielfeld frei?
			if (this.isFigureOnField(toFieldNumber))
				throw new FieldException("+++++\nEs befindet sich eine Figur auf Feld " + getFieldName(toFieldNumber) + ".\nWenn die Figur geschlagen werden soll, muss sie zuerst entfernt werden!\n+++++");
			
			
			// Figur temporaer speichern
			Figure figure = this.figures.get(fromFieldNumber);
			// Figur vom alten Feld entfernen
			this.removeFigureAt(fromFieldNumber);
			// Figur auf neues Feld setzen
			this.putFigureAt(toFieldNumber, figure);
			
			// Zug erfolgreich
			ret = true;
			
		} catch (FieldException e)
		{
			System.out.println(e.getMessage());
		}
		
		return ret;
	}
	
	/**
	 * Entfernt die Figur auf der gegebenen Feldnummer.
	 * @param fieldNumber Nummer des Feldes, von dem die Figur entfernt werden soll
	 * @return True: Figur entfernt; False: Keine Figur vorhanden oder fehlerhafte Feldnummer
	 */
	public boolean removeFigureAt(Integer fieldNumber)
	{
		if (	this.isFigureOnField(fieldNumber) &&
				isValidFieldnumber(fieldNumber)) {
			this.figures.remove(fieldNumber);
			return true;
		} else {
			System.out.println("Keine Figur auf dem Feld (" + getFieldName(fieldNumber) + ").");
			return false;
		}
	}
	
	/**
	 * Gibt das aktuelle Feld als Hashmap aus.
	 * Integer: Feldposition
	 * Figure: Figur auf dem Feld
	 * @return HashMap<Integer, Figure>
	 */
	public HashMap<Integer, Figure> getCurrentFieldAsHashMap()
	{
		return this.figures;
	}
	
	/**
	 * Gibt das aktuelle Feld aus.
	 * @return String mit dem aktuellen Feld
	 */
	public String getCurrentField()
	{
		// Figuren auf dem Feld werden im Strin gespeichert
		String str = "";
		
		// Nach Feldnummer (Key) sortieren
		List<Integer> sortedList = new ArrayList<Integer>();
		sortedList.addAll(this.figures.keySet());
		Collections.sort(sortedList);
		
		for (int i = 0; i < sortedList.size(); i++) {
			// Feldnummer aus der sortierten Liste holen
			Integer fieldNumber = sortedList.get(i);
			// Figur holen
			Figure f = this.getFigureAt(fieldNumber);
			
			// In Ausgabe-String speichern
			str += "Feld " + getFieldName(fieldNumber) + "(" + fieldNumber + "): " + f.toString() + "\n";
		}
		
		return str;
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
			// Key/Value-Paar speichern
			Map.Entry<Integer, Figure> pair = it.next();
			
			// Position der Figur steht im Key
			Integer i = pair.getKey();
			// value-Objekt
			Figure f = pair.getValue();
			
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
	 * Setzt das Feld in den Startzustand zurueck
	 */
	public void resetField()
	{
		this.equipField();
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
	 * Ermittelt anhand der Zeile (a-h) die entsprechende Nummer (1-8)
	 * @param c Char-Wert der Zeile
	 * @return Zeilennummer
	 */
	public static int getColumnnumberByChar(char c)
	{
		int i = -1;
		
		if (isValidXPosition(c)) {
			if (c == 'a')
				i = 1;
			else if (c == 'b')
				i = 2;
			else if (c == 'c')
				i = 3;
			else if (c == 'd')
				i = 4;
			else if (c == 'e')
				i = 5;
			else if (c == 'f')
				i = 6;
			else if (c == 'g')
				i = 7;
			else if (c == 'h')
				i = 8;
		}
		
		return i;
	}
	
	/**
	 * Ermittelt die Position anhand der Feldnummer in der Form A7/C3 etc.
	 * @param fieldNumber Nummer des Feldes (von 1 bis 64)
	 * @return Name des Feldes (z.B. A8)
	 */
	public static String getFieldName(int fieldNumber)
	{
		byte	y = getYPositionFromFieldnumber(fieldNumber);
		char	x = getXPosition(getXPositionFromFieldnumber(fieldNumber));
		String str = x + "" + y;
		return str;
	}
	
	/**
	 * Ermittelt aus dem uebergebenen Feldnamen (a-h1-8) die Feldnummer (1-64)
	 * @param fieldName
	 * @return
	 */
	public static int getFieldNumber(String fieldName)
	{
		int fieldNumber = -1;
		
		String pattern = "[a-h]{1}[1-8]{1}";
		
		try {
			if (!Pattern.matches(pattern, fieldName))
				throw new FieldException("Die Eingabe (" + fieldName + ") entspricht nicht einer gueltigen Feldbezeichnung!\n Buchstaben klein geschrieben?");
			else {
				// Gueltige Feldbezeichnung, Zeile und Spalte "ausschneiden"
				int column = getColumnnumberByChar(fieldName.substring(0, 1).charAt(0));
				int row = Integer.parseInt(fieldName.substring(1, 2));
				
				// Feldnummer berechnen
				fieldNumber = ((row-1)*8) + column;
			}
		} catch (FieldException e)
		{
			System.out.println(e.getMessage());
		}
		
		return fieldNumber;
	}
	
	/**
	 * Berechnet aus der Zeile und Spalte die Feldnummer.
	 * @param column Spalte
	 * @param row Zeile
	 * @return Feldnummer
	 */
	public static int getFieldNumber(int column, int row)
	{
		int fieldNumber = -1;
		try {
			if (!(row > 0 && row <= 8 && column > 0 && column <= 8)) {
				throw new FieldException("Ungueltige Zeilen/Spaltengroessen");
			} else {
				fieldNumber = ((row-1)*8) + column;
			}
		} catch (FieldException e)
		{
			System.out.println(e.getMessage());
		}
		return fieldNumber;
	}
	
	
	/**
	 * Ueberprueft ob der gegebenen byte-Wert eine gueltige X-Position ist
	 * @param xPosition X-Position
	 * @return True: Gueltige Position; False: Ungueltige Position
	 */
	public static boolean isValidXPosition(byte xPosition)
	{
		return (xPosition > 0 && xPosition <= 8);
	}
	
	/**
	 * Ueberprueft ob der gegebenen char-Wert eine gueltige X-Position ist
	 * @param xPosition X-Position als Char (a-h)
	 * @return True: Gueltige Position; False: Ungueltige Position
	 */
	public static boolean isValidXPosition(char xPosition)
	{
		return (	xPosition == 'a' ||
					xPosition == 'b' ||
					xPosition == 'c' ||
					xPosition == 'd' ||
					xPosition == 'e' ||
					xPosition == 'f' ||
					xPosition == 'g' ||
					xPosition == 'h');
	}
	
	/**
	 * Ueberprueft ob der gegebenen byte-Wert eine gueltige Y-Position ist
	 * @param xPosition X-Position
	 * @return True: Gueltige Position; False: Ungueltige Position
	 */
	public static boolean isValidYPosition(byte yPosition)
	{
		return (yPosition > 0 && yPosition <= 8);
	}
	
	/**
	 * Prueft ob die uebergebene Feldnummer korrekt ist.
	 * @param fieldNumber Feldnummer
	 * @return True: Gehoert zum Schachfeld; False: Gehoert nicht zum Schachfeld
	 */
	public static boolean isValidFieldnumber(int fieldNumber)
	{
		return (fieldNumber > 0 && fieldNumber <= 64);
	}
	
	/**
	 * Gibt die X-Position als Buchstabe aus
	 * @param xPos 
	 * @return
	 */
	public static char getXPosition(byte xPos)
	{
		char xPosChar = 'x';
		switch(xPos) {
		case 1:
			xPosChar = 'a';
			break;
			
		case 2:
			xPosChar = 'b';
			break;
			
		case 3:
			xPosChar = 'c';
			break;
			
		case 4:
			xPosChar = 'd';
			break;
			
		case 5:
			xPosChar = 'e';
			break;
			
		case 6:
			xPosChar = 'f';
			break;
			
		case 7:
			xPosChar = 'g';
			break;
			
		case 8:
			xPosChar = 'h';
			break;
		default:
			break;
		}
		
		return xPosChar;
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
