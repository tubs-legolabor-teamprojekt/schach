package game;

import util.ChessfigureConstants;
import components.Field;
import components.FieldException;
import components.Figure;

/**
 * Ein Objekt dieser Klasse stellt genau einen Zug im Spiel dar.
 * @author Florian Franke
 * 21.10.2012
 * TODO Wird noch das Figure-Objekt benoetigt, in das bei Bauer-Umwandeln gewandelt wird?
 *
 */
public class Move
{
	/**
	 * Feld vor dem Zug
	 */
	private int fieldFrom;

	/**
	 * Feld nach dem Zug
	 */
	private int fieldTo;
	
	/**
	 * Wurde eine Figur geschmissen?
	 */
	private boolean captured = false;;
	
	/**
	 * Hat ein Bauer die gegnerische Grundreihe erreicht
	 * und konnte durch eine Dame, Turm, Laeufer oder Springer
	 * umgewandelt werden?
	 */
	private boolean pawnPromotion = false;
	
	/**
	 * In welche Figur wurde der Bauer umgewandelt?
	 */
	private char pawnPromotedTo;
	
	/**
	 * Ist der gegnerische Koenig durch diesen Zug im Schach?
	 */
	private boolean check = false;;
	
	/**
	 * Ist der gegnerische Koenig durch diesen Zug schachmatt?
	 */
	private boolean checkMate = false;
	
	/**
	 * Figure-Objekt. Wird erst gesetzt, wenn der Zug ausgefuehrt werden soll
	 */
	private Figure figure = null;
	
	/**
	 * Standard-Konstruktor mit allen mindestens noetigen Informationen zu einem Zug.
	 * @param fieldFrom Feld von dem der Zug ausgeht
	 * @param fieldTo Zielfeld
	 * @param figure Figur, die den Zug macht
	 */
	public Move(int fieldFrom, int fieldTo)
	{
		this.setFieldFrom(fieldFrom);
		this.setFieldTo(fieldTo);
	}
	
	/**
	 * Konstruktor mit Standard- zusaetzlichen Informationen.
	 * Es sind alle Kombinationen moeglich, bis auf:
	 * - pawnPromotion ohne pawnPromotedTo (wenn ein Bauer die gegn. Grundlinie erreicht,
	 * muss er in etwas hoeherwertiges umgewandelt werden, ausser Koenig)
	 * @param fieldFrom Ausgangsfeld
	 * @param fieldTo Zielfeld
	 * @param figure Bewegte Figur
	 * @param captured Geschmissen
	 * @param check Schach
	 * @param checkMate Schachmatt
	 */
	public Move(	int fieldFrom,
					int fieldTo,
					boolean captured,
					boolean check,
					boolean checkMate)
	{
		this(fieldFrom, fieldTo);
		this.setCaptured(captured);
		this.setCheck(check);
		this.setCheckMate(checkMate);
	}
	
	/**
	 * Ausgabe der Feldnummer, von dem der Zug beginnt
	 * @return Feld von dem der Zug ausgeht (1-64)
	 */
	public int getFieldFrom()
	{
		return fieldFrom;
	}

	/**
	 * Setzt das Von-Feld des Spielzugs.
	 * @param fieldFrom Feld, von dem der Zug startet (1-64)
	 */
	public void setFieldFrom(int fieldFrom)
	{
		try {
			if (!Field.isValidFieldnumber(fieldFrom))
				throw new FieldException("Ungueltige Feldnummer (" + fieldFrom + ")!");
		} catch (FieldException e)
		{
			System.out.println(e.getMessage());
		}
		this.fieldFrom = fieldFrom;
	}

	/**
	 * Ausgabe der Feldnummer, auf dem der Zug endet.
	 * @return Feld auf dem der Zug endet (1-64)
	 */
	public int getFieldTo()
	{
		return fieldTo;
	}

	/**
	 * Ausgabe der Feldnummer, auf dem der Zug endet
	 * @param fieldTo Feld auf dem der Zug endet (1-64)
	 */
	public void setFieldTo(int fieldTo)
	{
		try {
			if (!Field.isValidFieldnumber(fieldTo))
				throw new FieldException("Ungueltige Feldnummer (" + fieldTo + ")!");
		} catch (FieldException e)
		{
			System.out.println(e.getMessage());
		}
		this.fieldTo = fieldTo;
	}
	
	/**
	 * Wird direkt vor Ausfuehrung des Zuges aufgerufen, damit die Figur auf dem
	 * Feld fieldFrom geholt wird.
	 */
	public boolean execMove()
	{
		this.figure = Field.getInstance().getFigureAt(this.fieldFrom);
		return (this.figure != null);
	}

	/**
	 * Figur, die den Zug taetigt.
	 * @return
	 */
	public Figure getFigure()
	{
		/*
		 * Es soll nicht auf das aktuelle Feld zugegriffen werden!
		 * Das kann zu Problemen fuehren, da nur VOR Ausfuehrung des Zuges
		 * die richtige Figur zurueckgegeben wird.
		 * Nach Ausfuehrung des Zuges wird null zurueckgegeben.
		 * 
		 * Idee: Figur soll zwischengespeichert werden.
		 * Problem dabei: Im Konstruktor kann nicht die Figur geholt werden,
		 * da z.B. beim Testen alle Zuege vorher angegeben werden und somit
		 * nicht die richtigen Figuren im Move-Objekt sind (teilweise sind
		 * sie zum Zeitpunkt des Erstellens des Zugs noch gar nicht auf dem
		 * angegebenen Feld).
		 * Problemloesung: Vorm Ausfuehren des Zuges das Zug-Objekt aktualisieren
		 * und dabei die Figur holen
		 */
		return this.figure;
	}

	/**
	 * Wurde mit diesem Zug eine gegnerische Figur geschmissen?
	 * @return True: Gegner geschmissen; False: Nicht
	 */
	public boolean isCaptured()
	{
		return captured;
	}

	/**
	 * Wurde mit diesem Zug eine gegnerische Figur geschmissen?
	 * @param captured  True: Gegner geschmissen; False: Nicht
	 */
	public void setCaptured(boolean captured)
	{
		this.captured = captured;
	}

	/**
	 * Wurde mit diesem Zug ein Bauer in eine Dame/Springer/Laeufer/Turm gewandelt?
	 * @return True: Bauer gewandelt; False: Nicht
	 */
	public boolean isPawnPromotion()
	{
		return pawnPromotion;
	}

	/**
	 * Wurde mit diesem Zug ein Bauer in eine Dame/Springer/Laeufer/Turm gewandelt?
	 * @param pawnPromotion True: Bauer gewandelt; False: Nicht
	 */
	private void setPawnPromotion(boolean pawnPromotion)
	{
		this.pawnPromotion = pawnPromotion;
	}

	/**
	 * Wenn der Bauer umgewandelt wurde, gibt die Methode den Buchstaben der neuen Figur zurueck.
	 * Wird zur Bestimmung der ausfuehrlichen algebraischen Notation benoetigt.
	 * @return Buchstabe der neuen Figur
	 */
	public char getPawnPromotedTo()
	{
		return pawnPromotedTo;
	}

	/**
	 * Methode um den Buchstaben der Figur auszugeben, in den der Bauer umgewandelt wurde.
	 * @param pawnPromotedTo Buchstabe der neuen Figur.
	 */
	public void setPawnPromotedTo(char pawnPromotedTo)
	{
		try {
			// Ist eine gueltige Figur zur Umwandlung angegeben?
			if (!ChessfigureConstants.isValidPawnPromotionFigure(pawnPromotedTo))
				throw new MoveException("Ungueltige Figur, in die umgewandelt werden soll, angegeben");
			else
				this.setPawnPromotion(true);
			
			this.pawnPromotedTo = pawnPromotedTo;
		} catch (MoveException e)
		{
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Befindet sich der gegnerische Koenig, nach diesem Zug, im Schach?
	 * @return True: Gegnerischer Koenig ist Schach; False: Gegner nicht Schach
	 */
	public boolean isCheck()
	{
		return check;
	}

	/**
	 * Befindet sich der gegnerische Koenig, nach diesem Zug, im Schach?
	 * @param check True: Gegnerischer Koenig ist Schach; False: Gegner nicht Schach
	 */
	public void setCheck(boolean check)
	{
		this.check = check;
	}

	/**
	 * Ist der gegnerische Gegner mit diesem Zug Schachmatt?
	 * Das Spiel endet also mit diesem Zug.
	 * @return True: Gegner ist Schachmatt, Spiel beendet.
	 * False: Gegner nicht Schachmatt.
	 */
	public boolean isCheckMate()
	{
		return checkMate;
	}

	/**
	 * Ist der gegnerische Gegner mit diesem Zug Schachmatt?
	 * Das Spiel endet also mit diesem Zug.
	 * @param checkMate True: Gegner ist Schachmatt, Spiel beendet.
	 * False: Gegner nicht Schachmatt.
	 */
	public void setCheckMate(boolean checkMate)
	{
		this.checkMate = checkMate;
	}
	
	/**
	 * Gibt den Zug in der ausfuehrlichen algebraischen Schachnotation aus.
	 * Beschreibung zur Notation, siehe /infos/Ausfuehrliche_Schachnotation.txt.
	 * @return String, der den Zug in algebraischer Schachnotation enthaelt.
	 */
	public String getAlgebraicNotation()
	{
		// Anfangsbuchstabe
		String figureLetter	= "" + this.getFigure().getFigureLetter();
		// Zielfeld
		String to			= Field.getFieldName(this.fieldTo);
		// Geschmissen
		String captured		= (this.isCaptured()) ? "x" : "";
		// Schach oder Schachmatt
		String checkOrCheckMate = "";
		if (this.isCheckMate())
			checkOrCheckMate = "#";
		else if (this.isCheck())
			checkOrCheckMate = "+";
		// Bauer umgewandelt
		String pawnPromotion = (this.isPawnPromotion()) ? "=" + this.getPawnPromotedTo() : "";
		
		// Zusammensetzen
		String move = figureLetter + captured + to + checkOrCheckMate + pawnPromotion;
		
		return move;
	}
	
	/**
	 * Ausgabe des Zuges in Textform, ohne Ruecksicht auf eine schoene Satzbildung :)
	 * @return Zug als Text
	 */
	public String getMoveAsText()
	{
		String str = "";
		// Farbe
		str += ChessfigureConstants.getFigureColor(this.getFigure().getColor()) + "er ";
		
		// Figur
		str += ChessfigureConstants.getFigureName(this.getFigure().getFigureType()) + " ";
		
		// Von
		str += "zieht von " + Field.getFieldName(this.fieldFrom) + " ";
		
		// Nach
		str += "nach " + Field.getFieldName(this.fieldTo) + " ";
		
		// Sschmeissen
		if (this.isCaptured())
			str += "und schmeisst eine gegnerische Figur ";
		
		// Schach(matt)
		if (this.isCheckMate())
			str += "und der Gegner ist Schachmatt ";
		else if (this.isCheck())
			str += "und der Gegner ist Schach ";
		
		// Bauer-Umwandlung
		if (this.isPawnPromotion())
			str += "und erreicht die Grundlinie des Gegners und wandelt sich in "
				+ ChessfigureConstants.getFigureName(										// Figurtyp => Name
						ChessfigureConstants.getFigureTypeFromLetter(this.pawnPromotedTo)	// Kuerzel => Figurtyp (byte)
				);
		
		return str;
	}

	@Override
	public String toString()
	{
		return "Move [fieldFrom=" + fieldFrom + ", fieldTo=" + fieldTo
				+ ", figure=" + this.getFigure() + ", captured=" + captured
				+ ", pawnPromotion=" + pawnPromotion + ", pawnPromotedTo="
				+ pawnPromotedTo + ", check=" + check + ", checkMate="
				+ checkMate + ", toString()=" + super.toString() + "]";
	}
}
