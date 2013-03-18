package game;

import util.ChessfigureConstants;

import components.Field;
import components.FieldException;
import components.Figure;

/**
 * Ein Objekt dieser Klasse stellt genau einen Zug im Spiel dar.
 * 
 * @author Florian Franke 16.11.2012
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
     * Farbe des Spielers, der diesen Zug ausführt.
     */
    private byte colorOfPlayer = -1;

    /**
     * Wurde eine Figur geschmissen?
     */
    private boolean captured = false;;

    /**
     * Hat ein Bauer die gegnerische Grundreihe erreicht und konnte durch eine
     * Dame, Turm, Laeufer oder Springer umgewandelt werden?
     */
    private boolean pawnPromotion = false;

    /**
     * In welche Figur wurde der Bauer umgewandelt?
     */
    private char pawnPromotedTo;
    
    /**
     * Dieser Zug ist eine kurze Rochade.
     */
    private boolean kingSideCastling = false;
    
    /**
     * Dieser Zug ist eine lange Rochade.
     */
    private boolean queenSideCastling = false;

    /**
     * Ist der gegnerische Koenig durch diesen Zug im Schach?
     */
    private boolean check = false;;

    /**
     * Ist der gegnerische Koenig durch diesen Zug schachmatt?
     */
    private boolean checkMate = false;

    /**
     * Figure-Kuerzel
     */
    private char figureLetter;

    /**
     * Standard-Konstruktor mit allen mindestens noetigen Informationen zu einem
     * Zug.
     * 
     * @param fieldFrom
     *            Feld von dem der Zug ausgeht
     * @param fieldTo
     *            Zielfeld
     * @param figure
     *            Figur, die den Zug macht
     */
    public Move(byte colorOfPlayer, int fieldFrom, int fieldTo) {
        this.setColorOfPlayer(colorOfPlayer);
        this.setFieldFrom(fieldFrom);
        this.setFieldTo(fieldTo);
    }

    public Move(byte colorOfPlayer, int fieldFrom, int fieldTo, boolean captured) {
        this(colorOfPlayer, fieldFrom, fieldTo);
        this.setCaptured(captured);
    }

    /**
     * Konstruktor mit Standard- zusaetzlichen Informationen. Es sind alle
     * Kombinationen moeglich, bis auf: - pawnPromotion ohne pawnPromotedTo
     * (wenn ein Bauer die gegn. Grundlinie erreicht, muss er in etwas
     * hoeherwertiges umgewandelt werden, ausser Koenig)
     * 
     * @param fieldFrom
     *            Ausgangsfeld
     * @param fieldTo
     *            Zielfeld
     * @param figure
     *            Bewegte Figur
     * @param captured
     *            Geschmissen
     * @param check
     *            Schach
     * @param checkMate
     *            Schachmatt
     */
    public Move(byte colorOfPlayer, int fieldFrom, int fieldTo, boolean captured, boolean check,
            boolean checkMate) {
        this(colorOfPlayer, fieldFrom, fieldTo);
        this.setCaptured(captured);
        this.setCheck(check);
        this.setCheckMate(checkMate);
    }
    
    /**
     * Gibt die Farbe des Spielers zurück, der den Zug ausführt.
     * @return Farbe des Spielers
     */
    @Deprecated
    public byte getColorOfPlayer() {
       return this.colorOfPlayer;
    }
    
    /**
     * Setzt die Farbe des Spielers
     * @param color
     */
    public void setColorOfPlayer(byte color) {
        if (ChessfigureConstants.isValidColor(color))
            this.colorOfPlayer = color;
    }

    /**
     * Ausgabe der Feldnummer, von dem der Zug beginnt
     * 
     * @return Feld von dem der Zug ausgeht (1-64)
     */
    public int getFieldFrom() {
        return fieldFrom;
    }

    /**
     * Setzt das Von-Feld des Spielzugs.
     * 
     * @param fieldFrom
     *            Feld, von dem der Zug startet (1-64)
     */
    public void setFieldFrom(int fieldFrom) {
        try {
            if (!Field.isValidFieldnumber(fieldFrom))
                throw new FieldException("Ungueltige Feldnummer (" + fieldFrom
                        + ")!");
        } catch (FieldException e) {
            System.out.println(e.getMessage());
        }
        this.fieldFrom = fieldFrom;
    }

    /**
     * Ausgabe der Feldnummer, auf dem der Zug endet.
     * 
     * @return Feld auf dem der Zug endet (1-64)
     */
    public int getFieldTo() {
        return fieldTo;
    }

    /**
     * Ausgabe der Feldnummer, auf dem der Zug endet
     * 
     * @param fieldTo
     *            Feld auf dem der Zug endet (1-64)
     */
    public void setFieldTo(int fieldTo) {
        try {
            if (!Field.isValidFieldnumber(fieldTo))
                throw new FieldException("Ungueltige Feldnummer (" + fieldTo
                        + ")!");
        } catch (FieldException e) {
            System.out.println(e.getMessage());
        }
        this.fieldTo = fieldTo;
    }

    /**
     * Wurde mit diesem Zug eine gegnerische Figur geschmissen?
     * 
     * @return True: Gegner geschmissen; False: Nicht
     */
    public boolean isCaptured() {
        return captured;
    }

    /**
     * Wurde mit diesem Zug eine gegnerische Figur geschmissen?
     * 
     * @param captured
     *            True: Gegner geschmissen; False: Nicht
     */
    public void setCaptured(boolean captured) {
        this.captured = captured;
    }

    /**
     * Wurde mit diesem Zug ein Bauer in eine Dame/Springer/Laeufer/Turm
     * gewandelt?
     * 
     * @return True: Bauer gewandelt; False: Nicht
     */
    public boolean isPawnPromotion() {
        return pawnPromotion;
    }

    /**
     * Wurde mit diesem Zug ein Bauer in eine Dame/Springer/Laeufer/Turm
     * gewandelt?
     * 
     * @param pawnPromotion
     *            True: Bauer gewandelt; False: Nicht
     */
    public void setPawnPromotion(boolean pawnPromotion) {
        this.pawnPromotion = pawnPromotion;
    }

    /**
     * Wenn der Bauer umgewandelt wurde, gibt die Methode den Buchstaben der
     * neuen Figur zurueck. Wird zur Bestimmung der ausfuehrlichen algebraischen
     * Notation benoetigt.
     * 
     * @return Buchstabe der neuen Figur
     */
    public char getPawnPromotedTo() {
        return pawnPromotedTo;
    }

    /**
     * Methode um den Buchstaben der Figur auszugeben, in den der Bauer
     * umgewandelt wurde.
     * 
     * @param pawnPromotedTo
     *            Buchstabe der neuen Figur.
     */
    public void setPawnPromotedTo(char pawnPromotedTo) {
        try {
            // Ist eine gueltige Figur zur Umwandlung angegeben?
            if (!ChessfigureConstants
                    .isValidPawnPromotionFigure(pawnPromotedTo))
                throw new MoveException(
                        "Ungueltige Figur, in die umgewandelt werden soll, angegeben");
            else
                this.setPawnPromotion(true);

            this.pawnPromotedTo = pawnPromotedTo;
        } catch (MoveException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Befindet sich der gegnerische Koenig, nach diesem Zug, im Schach?
     * 
     * @return True: Gegnerischer Koenig ist Schach; False: Gegner nicht Schach
     */
    public boolean isCheck() {
        return check;
    }

    /**
     * Befindet sich der gegnerische Koenig, nach diesem Zug, im Schach?
     * 
     * @param check
     *            True: Gegnerischer Koenig ist Schach; False: Gegner nicht
     *            Schach
     */
    public void setCheck(boolean check) {
        this.check = check;
    }

    /**
     * Ist der gegnerische Gegner mit diesem Zug Schachmatt? Das Spiel endet
     * also mit diesem Zug.
     * 
     * @return True: Gegner ist Schachmatt, Spiel beendet. False: Gegner nicht
     *         Schachmatt.
     */
    public boolean isCheckMate() {
        return checkMate;
    }

    /**
     * Ist der gegnerische Gegner mit diesem Zug Schachmatt? Das Spiel endet
     * also mit diesem Zug.
     * 
     * @param checkMate
     *            True: Gegner ist Schachmatt, Spiel beendet. False: Gegner
     *            nicht Schachmatt.
     */
    public void setCheckMate(boolean checkMate) {
        if (checkMate)
            this.setCheck(true);
        this.checkMate = checkMate;
    }

    /**
     * Setzt das Figure-Kuerzel. Soll nur ausgefuehrt werden, wenn der Zug auf
     * Gueltigkeit gerpueft wurde
     * 
     * @param figureLetter
     */
    public void setFigure() {
        Figure f = Field.getInstance().getFigureAt(this.getFieldFrom());
        if (f != null)
            this.figureLetter = f.getFigureLetter();
        else
            System.out
                    .println("Move.setFigure(): Konnte figureLetter nicht setzen!");
    }

    /**
     * Gibt das Figurkuerzel zurueck
     * 
     * @return
     */
    public char getFigureLetter()
    {
        return this.figureLetter;
    }
    
    /**
     * Ist dieser Zug ist eine kurze Rochade?
     * @param kingSideCastling
     */
    public void setKingSideCastling(boolean kingSideCastling)
    {
        if (kingSideCastling) {
            if (this.isQueenSideCastling()) {
                this.setQueenSideCastling(false);
            }
            this.kingSideCastling = true;
            
            // from/to-Werte auf den Zug des Köenigs einstellen
            if (this.colorOfPlayer == ChessfigureConstants.WHITE) {
                this.fieldFrom = Field.getFieldNumber("e1");
                this.fieldTo = Field.getFieldNumber("g1");
            } else if (this.colorOfPlayer == ChessfigureConstants.BLACK) {
                this.fieldFrom = Field.getFieldNumber("e8");
                this.fieldTo = Field.getFieldNumber("g8");
            }
        } else {
            this.kingSideCastling = false;
        }
    }
    
    /**
     * Kurze Rochade?
     * @return
     */
    public boolean isKingSideCastling()
    {
        return this.kingSideCastling;
    }
    
    /**
     * Ist dieser Zug ist eine lange Rochade?
     * @param queenSideCastling
     */
    public void setQueenSideCastling(boolean queenSideCastling)
    {
        if (queenSideCastling) {
            if (this.isKingSideCastling()) {
                this.setKingSideCastling(false);
            }
            this.queenSideCastling = true;
            
            // from/to-Werte auf den Zug des Köenigs einstellen
            if (this.colorOfPlayer == ChessfigureConstants.WHITE) {
                this.fieldFrom = Field.getFieldNumber("e1");
                this.fieldTo = Field.getFieldNumber("c1");
            } else if (this.colorOfPlayer == ChessfigureConstants.BLACK) {
                this.fieldFrom = Field.getFieldNumber("e8");
                this.fieldTo = Field.getFieldNumber("c8");
            }
        } else {
            this.queenSideCastling = false;
        }
    }
    
    /**
     * Lange Rochade?
     * @return
     */
    public boolean isQueenSideCastling()
    {
        return this.queenSideCastling;
    }
    
    /**
     * Setzt die Spielerfarbe (nur bei Rochade benoetigt, bisher)
     * @param color
     */
    public void setPlayerColor(byte color)
    {
        if (ChessfigureConstants.isValidColor(color))
            this.colorOfPlayer = color;
    }
    
    /**
     * Gibt die Farbe des Spielers dieses Zugs zurueck
     * @return
     */
    public byte getPlayerColor()
    {
        return this.colorOfPlayer;
    }

    /**
     * Gibt den Zug in der ausfuehrlichen algebraischen Schachnotation aus.
     * Beschreibung zur Notation, siehe /infos/Ausfuehrliche_Schachnotation.txt.
     * 
     * @return String, der den Zug in algebraischer Schachnotation enthaelt.
     */
    public String getAlgebraicNotation() {
        String move = "";
        
        if (isKingSideCastling()) {
            move = "0-0";
        } else if (isQueenSideCastling()) {
            move = "0-0-0";
        } else {
            // Anfangsbuchstabe
            String figureLetter = "";
            if (getFigureLetter() != ' ')
                figureLetter = "" + getFigureLetter();
            // Zielfeld
            String to = Field.getFieldName(this.fieldTo);
            // Geschmissen
            String captured = (this.isCaptured()) ? "x" : "";
            // Schach oder Schachmatt
            String checkOrCheckMate = "";
            if (this.isCheckMate())
                checkOrCheckMate = "#";
            else if (this.isCheck())
                checkOrCheckMate = "+";
            // Bauer umgewandelt
            String pawnPromotion = (this.isPawnPromotion()) ? "="
                    + this.getPawnPromotedTo() : "";
    
            // Zusammensetzen
            move = figureLetter + captured + to + checkOrCheckMate
                    + pawnPromotion;
        }

        return move;
    }

    /**
     * Ausgabe des Zuges in Textform, ohne Ruecksicht auf eine schoene
     * Satzbildung :)
     * 
     * @return Zug als Text
     */
    public String getMoveAsText() {
        String str = "";
        
        if (this.isKingSideCastling()) {
            str = "Kurze " + ChessfigureConstants.getFigureColor(this.getPlayerColor()) + "e Rochade";
        } else if (this.isQueenSideCastling()) {
            str = "Lange " + ChessfigureConstants.getFigureColor(this.getPlayerColor()) + "e Rochade";
        } else {
            // Farbe
            str += ChessfigureConstants.getFigureColor(Field.getInstance()
                    .getFigureAt(this.getFieldFrom()).getColor())
                    + "er ";
    
            // Figur
            str += ChessfigureConstants.getFigureName(Field.getInstance()
                    .getFigureAt(this.getFieldFrom()).getFigureType())
                    + " ";
    
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
                        + ChessfigureConstants
                                .getFigureName( // Figurtyp => Name
                                ChessfigureConstants
                                        .getFigureTypeFromLetter(this.pawnPromotedTo) // Kuerzel
                                                                                      // =>
                                                                                      // Figurtyp
                                                                                      // (byte)
                                );
        }

        return str;
    }

    @Override
    public String toString() {
        String str = "";
        
        if (this.isKingSideCastling()) {
            str = "Move [Kurze " + ChessfigureConstants.getFigureColor(this.getPlayerColor()) + "e Rochade]";
        } else if (this.isQueenSideCastling()) {
            str = "Move [Lange " + ChessfigureConstants.getFigureColor(this.getPlayerColor()) + "e Rochade]";
        } else {
            str =  "Move [fieldFrom=" + Field.getFieldName(fieldFrom) + ", fieldTo=" + Field.getFieldName(fieldTo)
                    + ", figure="
                    + Field.getInstance().getFigureAt(this.getFieldFrom())
                    + ", captured=" + captured + ", pawnPromotion=" + pawnPromotion
                    + ", pawnPromotedTo=" + pawnPromotedTo + ", check=" + check
                    + ", checkMate=" + checkMate + ", toString()="
                    + super.toString() + "]";
        }
        
        return str;
    }
}
