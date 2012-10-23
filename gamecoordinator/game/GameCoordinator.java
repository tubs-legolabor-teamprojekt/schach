package game;

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
	private Move[] moves;
	
	/**
	 * Privater Konstruktor, damit nur eine Instanz vom GameCoordintor erstellt werden kann.
	 */
	private GameCoordinator()
	{
		// Feld-Instanz holen
		this.field = Field.getInstance();
	}
	
	
	public void startGame()
	{
		
	}
	
	
	public void endOfGame()
	{
		
	}
	
	
	public void execMove()
	{
		
	}
	
	/**
	 * Um die Instanz des GameCoordinators zu holen.
	 * @return GameCoordinator-Instanz
	 */
	public static GameCoordinator getInstance()
	{
		if (instance == null)
			instance = new GameCoordinator();
		
		return instance;
	}
}
