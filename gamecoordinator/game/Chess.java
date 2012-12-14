package game;

import gui.Gui;

import java.util.ArrayList;
import java.util.List;

/**
 * Startet das Spiel
 * 
 * @author Florian Franke 31.10.2012
 * 
 */
public class Chess
{
    /**
     * Objekt des GameCoordinators
     */
    private GameCoordinator gameCoordinator;

    /**
     * Liste an Zuegen, falls ein Spiel simuliert werden soll.
     */
    private List<Move> simulatedMoves = new ArrayList<Move>();

    /**
     * Leerer Konstruktor
     */
    public Chess() {
    }

    /**
     * Konstruktor, mit Zuegen fuer ein simuliertes Spiel
     * 
     * @param moves
     *            Zuege des simulierten Spiels
     */
    public Chess(List<Move> moves) {
        this.simulatedMoves = moves;
    }

    /**
     * Startet das Spiel
     */
    public void startGame() {
        // GUI initialisieren, Start-Button wird angezeigt
        Gui gui = Gui.getInstance();
        // Warten, bis Benutzer das Spiel gestartet hat
        while (!gui.isStartPressed()) {
            try {
                Thread.sleep(333);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        // Schach-Koordinator holen
        this.gameCoordinator = GameCoordinator.getInstance(true);
        // GUI uebergeben
        this.gameCoordinator.setGui(gui);

        // TODO wie wird ermittelt, ob Spieler ggn Computer oder Computer ggn
        // Computer

        int moveCounter = 0;

        while (!this.gameCoordinator.isEndOfGame()) {
            Move newMove = null;
            if (this.simulatedMoves.size() == 0) {
                // TODO Zug ermitteln (AI, Webcam)
            } else {
                // Simulierten Zug holen
                newMove = this.simulatedMoves.get(moveCounter);
                moveCounter++;
            }

            // Wenn Zug gueltig, ausfuehren
            if (this.gameCoordinator.receiveMove(newMove)) {
                // Zug ausfuehren
                this.gameCoordinator.execMove();
            } else {
                // TODO Was wird getan, wenn ein ungueltiger Zug vorliegt?
                System.exit(0);
            }
            if (moveCounter >= this.simulatedMoves.size()) {
                System.out
                        .println("\n-----\nLetzten simulierten Zug beendet.\nSpiel vorbei.");
                break;
            }
        }

        // TODO Verbindung zum Roboter beenden
        
        // Exportieren
         System.out.println( Exporter.exportMovesToPGN( "Tabea testet", // Name des Spiels
                 "Braunschweig", // Ort
                 "11-02-2012", // Datum
                 "Tabea", // Spieler weiss
                 "Florian", // Spieler schwarz
                 "1-0", // Ergebnis
                 this.gameCoordinator.getAllMoves()) // Zuege
          );
    }
}
