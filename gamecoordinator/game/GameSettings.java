package game;

/**
 * Einstellungen zum Spielablauf.
 * 
 * @author florian 29.11.2012
 */
public class GameSettings
{

    /**
     * Mögliche Spielvarianten
     */
    public static enum GameType {
        PlayerVsComputer, Simulated, ComputerVsComputer,PlayerVsSimulatedComputer, SimulatedWithRobot
    }
    
    /**
     * Sollen simulierte Zuege durchgefuehrt werden? Kann zu Testzwecken
     * temporär eingeschaltet werden, damit keine Fotos per Webcam geschossen
     * werden.
     */
    public static final GameType currentGameType = GameType.Simulated;
    
    /**
     * Dateiname zur Textdatei, die die Züge eines simulierten Spiels enthält
     */
    public static final String simulatedGameMoves = "PawnPromotionTestWHITE.txt";
    
    /**
     * Zeit zwischen den simulierten Zügen
     */
    public static final long timeBetweenMoves = 100;
    
    /**
     * Sollen die einzelnen Züge auf Gültigkeit geprüft werden?
     */
    public static final boolean checkRules = true;

}
