package game;

import engineControl.MovementControl;
import gui.Checkerboard;
import gui.Gui;

import java.util.ArrayList;
import java.util.List;

import rules.Rules;
import util.ChessfigureConstants;

import components.Field;
import components.Figure;
import components.FigureBishop;
import components.FigureKnight;
import components.FigureQueen;
import components.FigureRook;

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
    private MovementControl movementControl = null;

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

        if (this.currentMove.isKingSideCastling()) {
            // Gui soll Figur bewegen
            this.gui.getCheckerboard().setCheckerboardInformation(this.currentMove);
            
            if (this.currentMove.getPlayerColor() == ChessfigureConstants.WHITE) {
                // Kurze weisse Rochade
                
                // König versetzen
                this.field.moveFigure(Field.getFieldNumber("e1"), Field.getFieldNumber("g1"));
                // Turm versetzen
                this.field.moveFigure(Field.getFieldNumber("h1"), Field.getFieldNumber("f1"));
            } else if (this.currentMove.getPlayerColor() == ChessfigureConstants.BLACK) {
                // Kurze schwarze Rochade
                
                // König versetzen
                this.field.moveFigure(Field.getFieldNumber("e8"), Field.getFieldNumber("g8"));
                // Turm versetzen
                this.field.moveFigure(Field.getFieldNumber("h8"), Field.getFieldNumber("f8"));
            }
        } else if (this.currentMove.isQueenSideCastling()) {
            // Gui soll Figur bewegen
            this.gui.getCheckerboard().setCheckerboardInformation(this.currentMove);
            
            if (this.currentMove.getPlayerColor() == ChessfigureConstants.WHITE) {
                // Lange weisse Rochade
                
                // König versetzen
                this.field.moveFigure(Field.getFieldNumber("e1"), Field.getFieldNumber("c1"));
                // Turm versetzen
                this.field.moveFigure(Field.getFieldNumber("a1"), Field.getFieldNumber("d1"));
            } else if (this.currentMove.getPlayerColor() == ChessfigureConstants.BLACK) {
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
    
            if (GameSettings.currentGameType == GameSettings.GameType.PlayerVsComputer) {
                // Roboter soll Figur bewegen
                if (this.movementControl == null) {
                    this.movementControl = MovementControl.getInstance();
                }
                
                this.movementControl.setMovefigure(this.currentMove);
                this.movementControl.moveRobot();
            }
    
            // Gui soll Figur bewegen
            // Gui muss zuerst den Zug grafisch ausfuehren, da sie auf die
            // Informationen des Feldes (fieldFrom) zugreift.
            // Wuerde Field zuerst aktualisiert werden, koennte die Gui nicht
            // mehr auf die zu versetzende Figur zugreifen!
            this.gui.getCheckerboard().setCheckerboardInformation(this.currentMove);
            
            // Bauer umgewandelt in...
            Figure newFigure = null;
            if (this.currentMove.isPawnPromotion()) {
                if (this.currentMove.getPlayerColor() == ChessfigureConstants.WHITE) {
                    while (Checkerboard.getInstance().getPawnPromotionInformation() == 'A') {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                
                    this.currentMove.setPawnPromotedTo(Checkerboard.getInstance().getPawnPromotionInformation());
                }
                
                if (this.currentMove.getPawnPromotedTo() == ChessfigureConstants.BISHOP_LETTER) {
                    newFigure = new FigureBishop(this.currentMove.getPlayerColor());
                } else if (this.currentMove.getPawnPromotedTo() == ChessfigureConstants.KNIGHT_LETTER) {
                    newFigure = new FigureKnight(this.currentMove.getPlayerColor());
                } else if (this.currentMove.getPawnPromotedTo() == ChessfigureConstants.QUEEN_LETTER) {
                    newFigure = new FigureQueen(this.currentMove.getPlayerColor());
                } else if (this.currentMove.getPawnPromotedTo() == ChessfigureConstants.ROOK_LETTER) {
                    newFigure = new FigureRook(this.currentMove.getPlayerColor());
                }
                
            }
    
            // Figur soll Zug durchfuehren
            this.field.moveFigure(this.currentMove.getFieldFrom(),
                    this.currentMove.getFieldTo());
            
            if (this.currentMove.isPawnPromotion()) {
                this.field.removeFigureAt(this.currentMove.getFieldTo());
                this.field.putFigureAt(this.currentMove.getFieldTo(), newFigure);
            }
        }

        // War es der letzte Zug?
        this.lastMove = this.currentMove.isCheckMate();
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
