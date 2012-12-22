package game;

import engineControl.MovementControl;
import gui.Gui;

import java.util.ArrayList;
import java.util.List;

import rules.Rules;
import util.ChessfigureConstants;

import components.Field;

/**
 * Die "Hauptklasse" des Schachroboters, hier wird das Spiel gestartet.
 * 
 * @author Florian Franke 23.10.2012
 * 
 */
public class GameCoordinator
{
    /**
     * Private Variable, die die Instanz des GameCoordinators enthält.
     */
    private static GameCoordinator instance = null;

    /**
     * Enthaelt das Spielfeld
     */
    public Field field;

    /**
     * Enthält alle Züge des aktuellen Spiels
     */
    private List<Move> moves = new ArrayList<Move>();

    /**
     * Enthaelt den aktuellen Zug
     */
    private Move currentMove = null;

    /**
     * Gui-Objekt
     */
    private Gui gui;

    /**
     * Rules-Objekt
     */
    private Rules rules = new Rules();
    
    /**
     * Roboter-Bewegung
     */
    private MovementControl movementControl = MovementControl.getInstance();

    /**
     * Ist der aktuelle Zug der letzte?
     */
    private boolean lastMove = false;

    /**
     * Sollen die Zuege etc. ausgegeben werden?
     */
    private boolean logging = false;

    /**
     * Privater Konstruktor, damit nur eine Instanz vom GameCoordintor erstellt
     * werden kann.
     */
    private GameCoordinator(boolean logging) {
        // Feld-Instanz holen
        this.field = Field.getInstance();

        // Logging?
        this.logging = logging;
    }

    /**
     * Gibt zurueck, ob das Spiel beendet ist.
     * 
     * @return True: Spiel beendet; False: Spiel laeuft noch
     */
    public boolean isEndOfGame() {
        return this.lastMove;
    }

    /**
     * Fuehrt einen Zug aus. Der Zug soll beim Aufruf gueltig sein!
     */
    public void execMove() {
        // Zug ausgeben?
        if (this.logging)
            System.out.println(this.currentMove.getMoveAsText());

        this.gui.getCheckerboard().setCheckerboardInformation(this.currentMove);
        
        if (this.currentMove.isKingSideCastling()) {
            // Gui soll Figur bewegen
            this.gui.getCheckerboard().setCheckerboardInformation(this.currentMove);
            
            if (this.currentMove.getColorOfPlayer() == ChessfigureConstants.WHITE) {
                // Kurze weisse Rochade
                
                // König versetzen
                this.field.moveFigure(Field.getFieldNumber("e1"), Field.getFieldNumber("g1"));
                // Turm versetzen
                this.field.moveFigure(Field.getFieldNumber("h1"), Field.getFieldNumber("f1"));
            } else if (this.currentMove.getColorOfPlayer() == ChessfigureConstants.BLACK) {
                // Kurze schwarze Rochade
                
                // König versetzen
                this.field.moveFigure(Field.getFieldNumber("e8"), Field.getFieldNumber("g8"));
                // Turm versetzen
                this.field.moveFigure(Field.getFieldNumber("h8"), Field.getFieldNumber("f8"));
            }
        } else if (this.currentMove.isQueenSideCastling()) {
            // Gui soll Figur bewegen
            this.gui.getCheckerboard().setCheckerboardInformation(this.currentMove);
            
            if (this.currentMove.getColorOfPlayer() == ChessfigureConstants.WHITE) {
                // Lange weisse Rochade
                
                // König versetzen
                this.field.moveFigure(Field.getFieldNumber("e1"), Field.getFieldNumber("c1"));
                // Turm versetzen
                this.field.moveFigure(Field.getFieldNumber("a1"), Field.getFieldNumber("d1"));
            } else if (this.currentMove.getColorOfPlayer() == ChessfigureConstants.BLACK) {
                // Lange schwarze Rochade
                
                // König versetzen
                this.field.moveFigure(Field.getFieldNumber("e8"), Field.getFieldNumber("c8"));
                // Turm versetzen
                this.field.moveFigure(Field.getFieldNumber("a8"), Field.getFieldNumber("d8"));
            }
        } else {
            // Wurde geschmissen?
            if (this.currentMove.isCaptured()) {
                // Geschmissene Figur vom Feld entfernen
                this.field.removeFigureAt(this.currentMove.getFieldTo());
            }
    
            // Roboter soll Figur bewegen
            movementControl.setMovefigure(this.currentMove);
            movementControl.MoveRobot();
    
            // Gui soll Figur bewegen
            // Gui muss zuerst den Zug grafisch ausfuehren, da sie auf die
            // Informationen des Feldes (fieldFrom) zugreift.
            // Wuerde Field zuerst aktualisiert werden, koennte die Gui nicht
            // mehr auf die zu versetzende Figur zugreifen!
            this.gui.getCheckerboard().setCheckerboardInformation(this.currentMove);
    
            // Figur soll Zug durchfuehren
            this.field.moveFigure(this.currentMove.getFieldFrom(),
                    this.currentMove.getFieldTo());
        }

        // War es der letzte Zug?
        this.lastMove = this.currentMove.isCheckMate();

        // TODO Wurde inzwischen die GUI beendet? => Spiel ist beendet
        
    }

    /**
     * Neuer Zug wird dem aktuellen Spielverlauf hinzugefuegt. Entweder ein vom
     * Spieler ausgefuerter Zug oder von der AI.
     * 
     * @param newMove Der auszuführende Zug, er beinhaltet schon die Eigenschaften
     *  ob geschmissen wurde, ob eine Rochade stattfindet etc.
     * @param checkThisMove Soll der uebergebene Zug auf Regeln geprüft werden?
     * @return True: Gueltiger Zug wurde gespeichert; False: Ungueltiger Zug,
     *         Fehlermeldung anzeigen
     */
    public boolean receiveMove(Move newMove, boolean checkThisMove)
    {
        if (checkThisMove) {
            if (!this.rules.checkMove(this.field, newMove)) {
                System.out.println("Ungültiger Zug laut Rules.checkMove().\nUngueltiger Zug: " + newMove.toString());
                // Fehlermeldung anzeigen (GUI)
                this.gui.showWarning("Ungültiger Zug!");
                return false;
            }
        }
        // currentMove aktualisieren
        this.currentMove = newMove;
        // Figurtyp bestimmen
        this.currentMove.setFigure();
        // Aktuellen Zug hinzufuegen
        this.moves.add(this.currentMove);
        return true;
    }

    /**
     * Setzt die Gui
     * 
     * @param gui
     *            GUI
     */
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    /**
     * Gibt alle Zuege des Spiels zurueck
     * 
     * @return ArrayList mit allen Zuegen
     */
    public List<Move> getAllMoves() {
        return this.moves;
    }

    /**
     * Um die Instanz des GameCoordinators zu holen.
     * 
     * @return GameCoordinator-Instanz
     */
    public static GameCoordinator getInstance(boolean logging) {
        if (instance == null)
            instance = new GameCoordinator(logging);

        return instance;
    }
}
