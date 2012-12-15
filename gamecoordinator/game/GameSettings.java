package game;

/**
 * Einstellungen zum Spielablauf.
 * 
 * @author florian 29.11.2012
 */
public class GameSettings
{

    /**
     * Sollen simulierte Zuege durchgefuehrt werden? Kann zu Testzwecken
     * temporär eingeschaltet werden, damit keine Fotos per Webcam geschossen
     * werden.
     */
    public static final boolean simulateGame = true;
    
    /**
     * Sollen die einzelnen Züge auf Gültigkeit geprüft werden?
     */
    public static final boolean checkRules = true;
    
    public static final long timeBetweenMoves = 500;

}
