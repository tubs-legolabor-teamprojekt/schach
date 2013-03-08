package game;

/**
 * Einstellungen zum Spielablauf.
 * 
 * @author florian 29.11.2012
 */
public class GameSettings
{

    /**
     * M��gliche Spielvarianten
     */
    public static enum GameType {
        PlayerVsComputer, Simulated, ComputerVsComputer,PlayerVsSimulatedComputer
    }
    
    /**
     * Sollen simulierte Zuege durchgefuehrt werden? Kann zu Testzwecken
     * tempor��r eingeschaltet werden, damit keine Fotos per Webcam geschossen
     * werden.
     */
    public static final GameType currentGameType = GameType.PlayerVsSimulatedComputer;
    
    /**
     * Dateiname zur Textdatei, die die Z��ge eines simulierten Spiels enth��lt
     */
    public static final String simulatedGameMoves = "FlorianF.txt";
    
    /**
     * Zeit zwischen den simulierten Z��gen
     */
    public static final long timeBetweenMoves = 1000;
    
    /**
     * Sollen die einzelnen Z��ge auf G��ltigkeit gepr��ft werden?
     */
    public static final boolean checkRules = true;

}
