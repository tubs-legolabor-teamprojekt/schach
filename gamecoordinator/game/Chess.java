package game;

import gui.Gui;

import java.util.ArrayList;
import java.util.List;

import util.ChessfigureConstants;

import components.Field;

import camera.ImageLoader;

/**
 * Startet das Spiel
 * @author Florian Franke
 * 31.10.2012
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
        
        // TODO wie wird ermittelt, ob Spieler ggn Computer oder Computer ggn Computer
        
        int moveCounter = 0;
        
        while(!this.gameCoordinator.isEndOfGame()) {

            if (this.simulatedMoves.size() == 0) {
                // TODO Zug ermitteln (AI, Webcam)
                
                // Zug von Webcam ermitteln
                ImageLoader im = new ImageLoader();
                
                // Winkel setzen
                im.setAngle(im.calcAngle());
                
                //erste Vergleichsfoto
                im.takePhoto1();
                
                // FIXME Warten bis Zug vom Benutzer durchgefuehrt wurde
                System.out.println("Foto1 taken");
                try{
                    Thread.sleep(10000);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                
                //2te Vergleichsfoto nehmen
                im.takePhoto2();
                
                // Veraenderte Positionen holen
                // Entweder
                // zwei Werte (0: from, 1: to)
                // oder
                // vier Werte (0: from, 1: to, 2: from (Rochade), 3: to (Rochade)
                // wobei der Rochade-Zug auch als erstes �bergeben werden kann
                List<Integer> listOfChangedPositions = im.getChangedPositions();
                // TODO Methode einbinden, ob man den Werten vertrauen kann
                
                // Zuege einlesen
                int        cnt = 0,
                        field1 = -1,
                        field2 = -1,
                        field3 = -1,
                        field4 = -1;
                boolean rochade = false;
                for (Integer position : listOfChangedPositions) {
                    if (cnt == 0) {
                        // erster Wert (Feld "from" von Zug 1)
                        field1 = position;
                    } else if (cnt == 1) {
                        // zweiter Wert (Feld "to" von Zug 1)
                        field2 = position;
                    } else if (cnt == 2) {
                        // Rochade (Feld "from" von Rochade-Zug)
                        field3 = position;
                    } else if (cnt == 3) {
                        // Rochade (Feld "to" von Rochade-Zug)
                        field4 = position;
                        rochade = true;
                    }
                    cnt++;
                }
                
                // Wurde beim Zug ohne Rochade geschmissen?
                boolean captured = false;
                
                // Alle Werte eingelesen, nun zuordnen
                int        field1From = -1,
                        field1To = -1,
                        field2From = -1,
                        field2To = -1;
                Field field = Field.getInstance();
                // Wurde der Koenig vom Spieler schon bewegt?
                if (!rochade && field.isCastlingWhitePossible()) {
                    // ** Keine Rochade, also nur zwei Werte nutzen ** 
                    
                    // field1 besetzt
                    boolean    field1Taken = (!field.isFigureOnField(field1) && field.isFigureOnField(field2));
                    // field2 besetzt
                    boolean field2Taken = (field.isFigureOnField(field1) && !field.isFigureOnField(field2));
                    // Beide besetzt
                    boolean bothTaken = (field1Taken && field2Taken);
                    
                    // Beide Felder besetzt (es wird geschmissen)
                    if (bothTaken) {
                        captured = true;
                        // Pruefen ob unterschiedliche Farben auf den Feldern
                        if (    field.getFigureAt(field1).getColor() == ChessfigureConstants.WHITE &&
                                field.getFigureAt(field2).getColor() == ChessfigureConstants.BLACK
                            ) {
                            // Eigene (weisse) Figur auf field1
                            // Gegnerische (schwarze) Figur auf field2
                            field1From = field1;
                            field1To = field2;
                        } else if (    field.getFigureAt(field2).getColor() == ChessfigureConstants.WHITE &&
                                    field.getFigureAt(field1).getColor() == ChessfigureConstants.BLACK) {
                            // Eigene (weisse) Figur auf field2
                            // Gegnerische (schwarze) Figur auf field1
                            field1From = field2;
                            field1To = field1;
                        } else {
                            System.out.println("FEHLER!\nKeine unterschiedlichen Farben auf den beiden Feldern!");
                        }
                    } else if (field1Taken || field2Taken) {
                        // Nicht geschmissen, ein Feld ist leer
                        if (field1Taken) {
                            // Eigene Figur auf field1
                            field1From = field1;
                            field1To = field2;
                        } else if (field2Taken) {
                            // Eigene Figur auf field2
                            field1From = field2;
                            field1To = field1;
                        }
                    } else {
                        System.out.println("FEHLER!\nKeins der beiden Felder war vor dem eingelesenen Zug besetzt!");
                    }
                } else {
                    // ** Rochade! **
                    
                    // Weisser Koenig auf Startposition
                    boolean kingOnStartposition = (    field.getFigureAt(Field.getFieldNumber("e1")) != null &&
                                                    field.getFigureAt(Field.getFieldNumber("e1")).getFigureLetter() == ChessfigureConstants.KING_LETTER &&
                                                    field.getFigureAt(Field.getFieldNumber("e1")).getColor() == ChessfigureConstants.WHITE);
                    // Weisser Turm1 auf Startposition TODO Koennte das Turm 2 sein?
                    boolean rook1OnStartposition = (    field.getFigureAt(Field.getFieldNumber("a1")) != null &&
                                                        field.getFigureAt(Field.getFieldNumber("a1")).getFigureLetter() == ChessfigureConstants.ROOK_LETTER &&
                                                        field.getFigureAt(Field.getFieldNumber("a1")).getColor() == ChessfigureConstants.WHITE);
                    // Weisser Turm2 auf Startposition
                    boolean rook2OnStartposition = (    field.getFigureAt(Field.getFieldNumber("h1")) != null &&
                                                        field.getFigureAt(Field.getFieldNumber("h1")).getFigureLetter() == ChessfigureConstants.ROOK_LETTER &&
                                                        field.getFigureAt(Field.getFieldNumber("h1")).getColor() == ChessfigureConstants.WHITE);
                    
                    // Rochade ueberhaupt erlaubt (Koenig auf Startposition, Turm1/2 auf Startposition)?
                    if ( !(    kingOnStartposition &&
                            (
                                rook1OnStartposition ||
                                rook2OnStartposition
                            )
                        ))
                    {
                        System.out.println("Keine Rochade erlaubt, wurde aber gesetzt!");
                    } else {
                        // Rochade erlaubt
                        
                        /*
                         * Idee
                         * 3 Arrays: 1.: Eingelesene Werte,
                         *           2./3. Werte kurze/lange Rochade
                         * Arrays sortieren und anschließend prüfen ob 1==2 oder 1==3 ist.
                         * Falls ja, ist es Rochade, sonst nicht
                         */
                        
                        boolean validKingSideCastling = true;
                        boolean validQueenSideCastling = true;
                        
                        // unsortiertes Array mit den eingelesenen Werten
                        int[] readValues = {field1, field2, field3, field4};
                        
                        // Eingelesene Werte sortieren
                        java.util.Arrays.sort(readValues);
                        
                        // sortiertes Array mit den Werten einer kurzen Rochade
                        int [] kingSideCastlingValues = {   Field.getFieldNumber("e1"),
                                Field.getFieldNumber("g1"),
                                Field.getFieldNumber("h1"),
                                Field.getFieldNumber("f1")};
                        
                        // Prüfe ob die eingelesenen Werte genau denen einer kurzen Rochade entsprechen
                        for (int i = 0; i < 4; i++)
                            if (readValues[i] != kingSideCastlingValues[4])
                                validKingSideCastling = false;
                        
                        if (!validKingSideCastling) {
                           // sortiertes Array mit den Werten einer kurzen Rochade
                            int [] queenSideCastlingValues = {   Field.getFieldNumber("e1"),
                                    Field.getFieldNumber("c1"),
                                    Field.getFieldNumber("a1"),
                                    Field.getFieldNumber("d1")};
                            
                            for (int i = 0; i < 4; i++)
                                if (readValues[i] != queenSideCastlingValues[i])
                                    validQueenSideCastling = false;
                        }
                        
                        
                        if (validKingSideCastling) {
                            // Kurze Rochade
                            
                            // Koenig
                            field1From = Field.getFieldNumber("e1");
                            field1To = Field.getFieldNumber("g1");
                            // Rechter Turm
                            field2From = Field.getFieldNumber("h1");
                            field2To = Field.getFieldNumber("f1");
                        } else if (validQueenSideCastling) {
                            // Lange Rochade
                            
                            // Koenig
                            field1From = Field.getFieldNumber("e1");
                            field1To = Field.getFieldNumber("c1");
                            // Linker Turm
                            field2From = Field.getFieldNumber("a1");
                            field2To = Field.getFieldNumber("d1");
                        } else {
                            System.out.println("Ungueltige Rochade!");
                        }
                    }
                }
                
                // Move-Objekte erstellen
                if (!rochade) {
                    // TODO muss hier noch ueberprueft werden, ob Schach(matt)?
                    Move newMove = new Move(field1From, field1To, captured, false, false);
                    
                    this.sendAndExecuteMove(newMove, true);
                } else {
                    // Bei Rochade muessen die Zuege nicht mehr geprueft werden.
                    Move moveRochadeKing = new Move(field1From, field1To);
                    Move moveRochadeRook = new Move(field2From, field2To);
                    
                    this.sendAndExecuteMove(moveRochadeKing, false);
                    this.sendAndExecuteMove(moveRochadeRook, false);
                }
                
                
            } else {
                // Simulierten Zug holen
                Move newMove = this.simulatedMoves.get(moveCounter);
                moveCounter++;
                
                // Simulierten Zug ausfuehren und pruefen
                this.sendAndExecuteMove(newMove, true);
            }
            
            if (moveCounter >= this.simulatedMoves.size()) {
                System.out.println("\n-----\nLetzten simulierten Zug beendet.\nSpiel vorbei.");
                break;
            }
        }
        
        // Exportieren
        System.out.println(
            Exporter.exportMovesToPGN(    "Tabea testet",                        // Name des Spiels
                                        "Braunschweig",                        // Ort
                                        "11-02-2012",                        // Datum
                                        "Tabea",                            // Spieler weiss
                                        "Florian",                            // Spieler schwarz
                                        "1-0",                                // Ergebnis
                                        this.gameCoordinator.getAllMoves())    // Zuege
        );
    }
    
    /**
     * Laesst einen Zug ausfueren und optional vorher auf Gueltigkeit pruefen
     * @param move Der auszufuehrende Zug
     * @param checkThisMove Soll der Zug vor Ausfuehrung geprueft werden?
     */
    public void sendAndExecuteMove(Move move, boolean checkThisMove)
    {
        // Wenn Zug gueltig, ausfuehren
        if(this.gameCoordinator.receiveMove(move, checkThisMove)) {
            // Zug ausfuehren
            this.gameCoordinator.execMove();
        } else {
            // TODO Was wird getan, wenn ein ungueltiger Zug vorliegt?
        }
    }
    
    /**
     * Sofern das Spiel beendet ist, wird das Ergebnis zurueckgegeben.
     * TODO Wie wird ermittelt, wer gewonnen hat?
     * @return
     */
    public String getResult()
    {
        String s = "";
        if (this.gameCoordinator.isEndOfGame()) {
            s = "1-0";
        }
        return s;
    }
}
