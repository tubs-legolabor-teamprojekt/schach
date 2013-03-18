package game;

import gui.Checkerboard;
import gui.Gui;
import gui.StartWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.ChessfigureConstants;
import camera.ImageLoader;

import components.Field;
import components.Figure;

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
    private GameCoordinator gameCoordinator = null;

    /**
     * Liste an Zuegen, falls ein Spiel simuliert werden soll.
     */
    private List<Move> simulatedMoves = new ArrayList<Move>();
    
    /**
     * Objekt zur Kommunikation mit der Kamera
     */
    private ImageLoader im = null;
    
    /**
     * Leerer Konstruktor
     */
    public Chess()
    {}
    
    /**
     * Konstruktor, mit Zuegen fuer ein simuliertes Spiel
     * @param moves Zuege des simulierten Spiels
     */
    public Chess(List<Move> moves)
    {
        this.simulatedMoves = moves;
    }
    
    /**
     * Startet das Spiel
     */
    public void startGame()
    {
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

        int moveCounter = 0;
        byte currentPlayer = ChessfigureConstants.WHITE;
        while (!this.gameCoordinator.isEndOfGame()) {
            
            if (moveCounter %2 == 0)
                currentPlayer = ChessfigureConstants.WHITE;
            else
                currentPlayer = ChessfigureConstants.BLACK;

            Move move = null;
            
            if (GameSettings.currentGameType ==  GameSettings.GameType.PlayerVsComputer) {
             // Spieltyp: Spieler gegen Computer
                if (currentPlayer == ChessfigureConstants.WHITE) {
                    move = this.getMoveFromCamera(currentPlayer);
                    moveCounter++;
                } else if (currentPlayer == ChessfigureConstants.BLACK) {
                    //TODO Züge ermitteln, bisher noch simulierte Züge
                    // Simulierten Zug holen
                    Move newMove = this.simulatedMoves.get(moveCounter);
                    moveCounter++;
                    move = newMove;
                    move = additionalInformationForMove(currentPlayer, move);
                    if (moveCounter >= this.simulatedMoves.size()) {
                        move.setCheckMate(true);
                    }
                }
            } else {
                // Spieltyp: Fehlerhafte Angabe
                System.out.println("Fehlerhafte Spieltyp!");
                System.exit(0);
            }

            // Züge ausführen
            move.setPlayerColor(currentPlayer);
            this.execMove(currentPlayer, move);

            if (moveCounter >= this.simulatedMoves.size()) {
                System.out.println("\n-----\nLetzten simulierten Zug beendet.\nSpiel vorbei.");
                break;
            }
        }


        // Aktuelles Datum
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        
        // Ergebnis bestimmen
        String res = "";
        if (moveCounter % 2 == 0) {
            // Schwarz hat gewonnen, da gerade Anzahl an Zügen
            res = "0-1";
        } else {
            res = "1-0";
        }
        
        // Exportieren
        System.out.println(Exporter.exportMovesToPGN(
                "Schachspiel gegen den Legoroboter", // Name des Spiels
                "Braunschweig", // Ort
                sdf.format(new Date()), // Datum
                StartWindow.getInstance().getUsername(), // Spieler weiss
                "Computer", // Spieler schwarz
                res, // Ergebnis
                this.gameCoordinator.getAllMoves()) // Zuege
                );
    }
    
    /**
     * Führt einen Zug aus
     * @param player Spieler, der den Zug ausführt
     * @param move Der auszuführende Zug
     */
    public void execMove(byte player, Move move)
    {
        try {
            Thread.sleep(GameSettings.timeBetweenMoves);
        } catch (InterruptedException e) {
        }
        // Wenn Zug gueltig, ausfuehren
        if (this.gameCoordinator.receiveMove(move, GameSettings.checkRules)) {
            // Zug ausfuehren
            this.gameCoordinator.execMove();
        } else {
            this.execMove(player, convertFieldnumbersToMoves(player, Checkerboard.getInstance().manualMove()));
        }
    }
    
    /**
     * Ermittelt den vom Spieler durchgeführten Zug.
     * @param currentPlayer Der aktuelle Spieler
     * @return Der durchgeführte Zug
     */
    public Move getMoveFromCamera(byte currentPlayer)
    {
        Move move = null;
        
        // Zug von Webcam ermitteln
        if (this.im == null) {
            this.im = new ImageLoader();
            // Winkel setzen
            this.im.setAngle(im.calcAngle());
            this.im.calcOffset();
        }

        // erste Vergleichsfoto
        this.im.takePhoto1();

        // FIXME Warten bis Zug vom Benutzer durchgefuehrt wurde
        System.out.println("Foto1 taken");
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2te Vergleichsfoto nehmen
        this.im.takePhoto2();
        System.out.println("Foto2 taken");

        // Veraenderte Positionen holen
        List<Integer> listOfChangedPositions = this.im.getChangedPositions();

        // Konnte Kamera Züge ermitteln?
        System.out.println("Anzahl an veränderten Feldern: "+ listOfChangedPositions.size());
        if (listOfChangedPositions.size() == 0) {
            // Manuelles Einlesen der Züge durch die GUI
            move = convertFieldnumbersToMoves(currentPlayer, Checkerboard.getInstance().manualMove());
        } else {
            move = convertFieldnumbersToMoves(currentPlayer, listOfChangedPositions);
        }
        return move;
    }

    /**
     * Erstellt aus der uebergebenen Spielerfarbe und den eingelesenen Feldnummern die Zuege.
     * Die Feldnummern werden entweder ueber die Kamera oder die GUI eingelesen.
     * @param colorOfPlayer Farbe des Spielers
     * @param fieldnumbers Die zwei oder vier betroffenen Feldnummern
     * @return Move-Objekt
     */
    public static Move convertFieldnumbersToMoves(byte colorOfPlayer, List<Integer> fieldnumbers)
    {
        Move move = null;
        Field f = Field.getInstance();
        if (fieldnumbers.size() == 2) {
            // Normaler Zug
            
            int fieldFrom = -1,
                fieldTo = -1;
            
            // Welches ist das From- und To-Feld?
            if (    f.isFigureOnField(fieldnumbers.get(0)) && // Figur auf fieldnumbers[0] vorhanden
                    f.getFigureAt(fieldnumbers.get(0)).getColor() == colorOfPlayer // eigene Figur auf fieldnumbers[0]
                ) {
                fieldFrom = fieldnumbers.get(0);
                fieldTo = fieldnumbers.get(1);
            } else if (f.isFigureOnField(fieldnumbers.get(1)) && // Figur auf fieldnumbers[1] vorhanden
                       f.getFigureAt(fieldnumbers.get(1)).getColor() == colorOfPlayer // eigene Figur auf fieldnumbers[1]
                       ) {
                fieldFrom = fieldnumbers.get(1);
                fieldTo = fieldnumbers.get(0);
            } else {
                System.out.println("Felder konnten nicht zugeordnet werden!");
            }
            
            move = new Move(colorOfPlayer, fieldFrom, fieldTo);
            // Informationen hinzufügen (geschmissen?)
            move = additionalInformationForMove(colorOfPlayer, move);
            
            
        } else if (fieldnumbers.size() == 4) {
            // Rochade-Zug
            
            // Darf der Spieler noch eine Rochade spielen?
            if ( (colorOfPlayer == ChessfigureConstants.WHITE && f.isCastlingWhitePossible()) ||
                 (colorOfPlayer == ChessfigureConstants.BLACK && f.isCastlingBlackPossible())
                ) {
                // Spieler spielt Rochade
                if (isKingSideCastling(colorOfPlayer, fieldnumbers)) {
                    // Kurzen Rochade-Zug erstellen und der Liste hinzufuegen
                    Move kscMove = new Move(colorOfPlayer, 1, 1); // Unwichtige Werte
                    kscMove.setKingSideCastling(true);
                    kscMove.setPlayerColor(colorOfPlayer);
                    move = kscMove;
                } else if (isQueenSideCastling(colorOfPlayer, fieldnumbers)) {
                    // Langen Rochade-Zug erstellen und der Liste hinzufuegen
                    Move qscMove = new Move(colorOfPlayer, 1, 1); // Unwichtige Werte
                    qscMove.setQueenSideCastling(true);
                    qscMove.setPlayerColor(colorOfPlayer);
                    move = qscMove;
                } else {
                    System.out.println("Ungueltige Rochade angegeben");
                }
            } else {
                System.out.println("Keine Rochade moeglich!");
            }
        } else {
            System.out.println("Ungueltige Anzahl an uebergebenen Feldnummern");
        }
        
        return move;
    }
    
    /**
     * Erweitert das Move-Objekt um Informationen, wie z.B. ob bei dem Zug geschmissen wird.
     * @param colorOfPlayer
     * @param move
     * @return
     */
    public static Move additionalInformationForMove(byte colorOfPlayer, Move move)
    {
        Field f = Field.getInstance();
        
        // Wird geschmissen?
        if (f.isFigureOnField(move.getFieldTo()) // Figur auf To-Feld vorhanden?
           ) {
            if (f.getFigureAt(move.getFieldTo()).getColor() != colorOfPlayer) // Gegner auf To-Feld?
            {
                // Neuen Zug erstellen und der Liste hinzufügen
                move.setCaptured(true);
            } else {
                // Eigene Figur kann nicht geschmissen werden
                System.out.println("Die eigene Figur kann nicht geschmissen werden!");
            }
            
        }
        
        // Pawn Promotion?
        // Bauer-(Neue Figur)-Umwandlung, wenn:
        //  - Bauer bewegt wird und
        //  - Weisser Bauer die gegnerische erste Linie erreicht (57-64) oder
        //  - Schwarzer Bauer die gegnerische erste Linie erreicht (1-8)
        Figure movingFigure = f.getFigureAt(move.getFieldFrom());
        if (movingFigure.getFigureType() == ChessfigureConstants.PAWN &&
                ( colorOfPlayer == ChessfigureConstants.WHITE &&
                  move.getFieldTo() >= 57 &&
                  move.getFieldTo() <= 64) ||
                ( colorOfPlayer == ChessfigureConstants.BLACK &&
                  move.getFieldTo() >= 1 &&
                  move.getFieldTo() <= 8)
            ) {
            move.setPawnPromotion(true);
        }
        
        return move;
    }
    
    /**
     * Prueft die uebergebenen Feldnummern und die Farbe, ob es eine gueltige kurze Rochade ist.
     * @param colorOfPlayer Schwarz/Weiss
     * @param fieldnumbers Die vier Feldnummern der Rochade
     * @return
     */
    public static boolean isKingSideCastling(byte colorOfPlayer, List<Integer> fieldnumbers)
    {
        if (fieldnumbers.size() == 4) {
            // Weiss
            if (colorOfPlayer == ChessfigureConstants.WHITE) {
                // Pruefe ob benoetige Felder uebergeben sind
                if (!(    fieldnumbers.contains(Field.getFieldNumber("e1")) &&
                          fieldnumbers.contains(Field.getFieldNumber("g1")) &&
                          fieldnumbers.contains(Field.getFieldNumber("h1")) &&
                          fieldnumbers.contains(Field.getFieldNumber("f1"))
                )) {
                    return false;
                }
                
            } else
            // Schwarz
            if (colorOfPlayer == ChessfigureConstants.BLACK) {
                // Pruefe ob benoetige Felder uebergeben sind
                if (!(    fieldnumbers.contains(Field.getFieldNumber("e8")) &&
                          fieldnumbers.contains(Field.getFieldNumber("g8")) &&
                          fieldnumbers.contains(Field.getFieldNumber("h8")) &&
                          fieldnumbers.contains(Field.getFieldNumber("f8"))
                )) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            System.out.println("Fehlerhafte Anzahl an uebergebenen Feldern");
            return false;
        }
        
        return true;
    }
    
    /**
     * Prueft die uebergebenen Feldnummern und die Farbe, ob es eine gueltige lange Rochade ist.
     * @param colorOfPlayer Schwarz/Weiss
     * @param fieldnumbers Die vier Feldnummern
     * @return
     */
    public static boolean isQueenSideCastling(byte colorOfPlayer, List<Integer> fieldnumbers)
    {
        if (fieldnumbers.size() == 4) {
            // Weiss
            if (colorOfPlayer == ChessfigureConstants.WHITE) {
                // Pruefe ob benoetige Felder uebergeben sind
                if (!(    fieldnumbers.contains(Field.getFieldNumber("e1")) &&
                          fieldnumbers.contains(Field.getFieldNumber("c1")) &&
                          fieldnumbers.contains(Field.getFieldNumber("a1")) &&
                          fieldnumbers.contains(Field.getFieldNumber("d1"))
                )) {
                    return false;
                }
                
            } else
            // Schwarz
            if (colorOfPlayer == ChessfigureConstants.BLACK) {
                // Pruefe ob benoetige Felder uebergeben sind
                if (!(    fieldnumbers.contains(Field.getFieldNumber("e8")) &&
                          fieldnumbers.contains(Field.getFieldNumber("c8")) &&
                          fieldnumbers.contains(Field.getFieldNumber("a8")) &&
                          fieldnumbers.contains(Field.getFieldNumber("d8"))
                )) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            System.out.println("Fehlerhafte Anzahl an uebergebenen Feldern");
            return false;
        }
        
        return true;
    }

    /**
     * Sofern das Spiel beendet ist, wird das Ergebnis zurueckgegeben.
     * TODO Wie wird ermittelt, wer gewonnen hat?
     * 
     * @return
     */
    public String getResult() {
        String s = "";
        if (this.gameCoordinator.isEndOfGame()) {
            s = "1-0";
        }
        return s;
    }
}
