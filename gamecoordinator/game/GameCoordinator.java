package game;

import gui.Gui;

import java.util.ArrayList;
import java.util.List;

import rules.Rules;
import sun.tools.tree.ThisExpression;

import components.Field;

/**
 * Die "Hauptklasse" des Schachroboters, hier wird das Spiel gestartet.
 * @author Florian Franke
 * 23.10.2012
 *
 */
public class GameCoordinator
{
	/**
	 * Private Variable, die die Instanz des GameCoordinators enthaelt.
	 */
	private static GameCoordinator instance = null;
	
	/**
	 * Enthaelt das Spielfeld
	 */
	private Field field;
	
	/**
	 * Enthaelt alle Zuege des aktuellen Spiels
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
	 * Ist der aktuelle Zug der letzte?
	 */
	private boolean lastMove = false;
	
	/**
	 * Sollen die Zuege etc. ausgegeben werden?
	 */
	private boolean logging = false;
	
	
	/**
	 * Privater Konstruktor, damit nur eine Instanz vom GameCoordintor erstellt werden kann.
	 */
	private GameCoordinator(boolean logging)
	{
		// Feld-Instanz holen
		this.field = Field.getInstance();
		// Logging?
		this.logging = logging;
	}
	
	/**
	 * Gibt zurueck, ob das Spiel beendet ist.
	 * @return True: Spiel beendet; False: Spiel laeuft noch
	 */
	public boolean isEndOfGame()
	{
		return this.lastMove;
	}
	
	/**
	 * Fuehrt einen Zug aus.
	 * Der Zug soll beim Aufruf gueltig sein!
	 */
	public void execMove()
	{
		// Zug soll ausgefuehrt werden
		if (this.currentMove.execMove()) {
			
			// Zug ausgeben?
			if (this.logging)
				System.out.println(this.currentMove.getMoveAsText());
			
			// Wurde geschmissen?
			if (this.currentMove.isCaptured()) {
				// Geschmissene Figur vom Feld entfernen
				this.field.removeFigureAt(this.currentMove.getFieldTo());
				
				// TODO Roboter soll Figur entfernen
			}
			
			// Figur soll Zug durchfuehren
			this.field.moveFigure(this.currentMove.getFieldFrom(), this.currentMove.getFieldTo());
			
			// TODO Roboter soll Figur bewegen
			
			// Gui soll Figur bewegen
			this.gui.getCheckerboard().setCheckerboardInformation(this.currentMove);
			
			// War es der letzte Zug?
			this.lastMove = this.currentMove.isCheckMate();
			
			// Wurde inzwischen die GUI beendet? => Spiel ist beendet
			if (!this.gui.isDisplayable())
				this.lastMove = true;
		} else {
			System.out.println("Der Zug konnte nicht ausgefuehrt werden!\nthis.currentMove.execMove() schlug fehl!");
		}
	}
	
	/**
	 * Neuer Zug wird dem aktuellen Spielverlauf hinzugefuegt.
	 * Entweder ein vom Spieler ausgefuerter Zug oder von der AI.
	 * @return True: Gueltiger Zug wurde gespeichert;
	 * False: Ungueltiger Zug, Fehlermeldung anzeigen
	 */
	public boolean receiveMove(Move newMove)
	{
		// TODO Wenn Rules fertig, Zug ueberpruefen
//		if (!this.rules.checkMove(this.field, newMove)) {
//			// TODO Fehlermeldung anzeigen (GUI)
//			this.gui.showWarning("Ungueltiger Zug!");
//			System.out.println("Ungueltiger Zug laut Rules.checkMove()");
//			return false;
//		} else {
		
			// Aktuellen Zug hinzufuegen
			this.moves.add(newMove);
			
			// currentMove aktualisieren
			this.currentMove = newMove;
			return true;
//		}
	}
	
	/**
	 * Setzt die Gui
	 * @param gui GUI
	 */
	public void setGui(Gui gui)
	{
		this.gui = gui;
	}
	
	/**
	 * Um die Instanz des GameCoordinators zu holen.
	 * @return GameCoordinator-Instanz
	 */
	public static GameCoordinator getInstance(boolean logging)
	{
		if (instance == null)
			instance = new GameCoordinator(logging);
		
		return instance;
	}
}
