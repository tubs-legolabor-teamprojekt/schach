package gui;

import game.Move;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import util.ChessfigureConstants;

import components.Field;
import components.Figure;
import components.FigureKing;

/**
 * Klasse, die das Schachbrett darstellt.
 * 
 * @author Tabea
 * 
 */
public class Checkerboard extends JPanel
{

    private static Checkerboard instance = null;

    private JTable grid = null;

    private Move move;
    private int fieldFrom, 
                fieldTo;
    private Figure figure;

    private int fieldFromColumn = 0, 
                fieldFromRow = 0, 
                fieldToColumn = 0,
                fieldToRow = 0;

    private boolean blackWon = false, 
                    manualMove = false, 
                    mmIsReady = false;

    private MyMouseListener mml;

    private ArrayList<Integer> a = new ArrayList<Integer>();
    
    private ImageIcon   icon_king_black = new FigureKing(ChessfigureConstants.BLACK).getIcon(),
                        icon_king_white = new FigureKing(ChessfigureConstants.WHITE).getIcon(),
                        icon_rook_black = new FigureKing(ChessfigureConstants.BLACK).getIcon(),
                        icon_rook_white = new FigureKing(ChessfigureConstants.WHITE).getIcon();

    /**
     * Privater Konstruktor, der nur ein neues Objekt der Klasse erstellt. Ruft
     * die Methode zur Tabellenerstellung auf.
     */
    private Checkerboard() {
        this.makeTable();
    }

    /**
     * Gibt die Checkerboard-Instanz zurück.
     * @return Checkerboard-Instanz
     */
    public static Checkerboard getInstance() {
        if (instance == null) {
            instance = new Checkerboard();
        }

        return instance;
    }

    /**
     * Methode, die eine Tabelle erzeugt, die das Schachbrett darstellt.
     */
    public void makeTable() {

        // Tabelle erstellen
        this.grid = new JTable(8, 8);
        this.grid.setShowHorizontalLines(true);

        this.grid.setOpaque(false);

        // MouseListener hinzufügen
        this.mml = new MyMouseListener(this.grid);
        this.grid.addMouseListener(this.mml);

        // Zeilenhöhe und Spaltenbreite anpassen, so dass die Felder quadratisch
        // sind
        this.grid.setRowHeight(75);
        int columnIndex = 8;
        TableColumn col = null;
        for (int i = 0; i < columnIndex; i++) {
            col = this.grid.getColumnModel().getColumn(i);
            col.setPreferredWidth(75);
            // jede Spalte enthält einen CellRenderer
            this.grid.getColumnModel().getColumn(i)
                    .setCellRenderer(new DefaultTableCellRenderer()
                    {
                        public Component getTableCellRendererComponent(
                                JTable table, Object value, boolean isSelected,
                                boolean hasFocus, int row, int column) {
                            return (JPanel) value;
                        }
                    });
        }

        // neues CheckerboardPanel-Objekt
        CheckerboardPanel cb = new CheckerboardPanel();

        // in jeder Tabellenzelle wird ein JPanel eingefügt
        // Schachbrettmuster wird angelegt
        // erst Zeilen
        for (int i = 0; i < 8; i++) {
            // dann Spalten
            for (int j = 0; j < 8; j++) {
                // aktuelle Tabellenzelle
                this.grid.setValueAt(new CheckerboardPanel(), i, j);
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        cb = (CheckerboardPanel) this.grid.getValueAt(i, j);
                        cb.setBackground(new Color(41, 15, 5, 100));
                    } else {
                        cb = (CheckerboardPanel) this.grid.getValueAt(i, j);
                        cb.setBackground(new Color(251, 225, 172, 100));
                    }
                } else {
                    if (j % 2 == 0) {
                        cb = (CheckerboardPanel) this.grid.getValueAt(i, j);
                        cb.setBackground(new Color(251, 225, 172, 100));
                    } else {
                        cb = (CheckerboardPanel) this.grid.getValueAt(i, j);
                        cb.setBackground(new Color(41, 15, 5, 100));
                    }
                }
            }
        }

        // Felder dürfen weder editiert noch ausgewählt werden
        this.grid.setEnabled(false);

        // Tabellenaussehen
        this.grid.setBorder(BorderFactory.createLineBorder(Color.black));
        this.grid.setGridColor(Color.black);

        // Tabelle hinzufügen und Wände fett machen
        this.add(grid);
        this.grid.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
                new Color(43, 23, 2, 255)));
    }

    /**
     * Methode, die die verwendete Nummerieung in Tabellenform umrechnet. Hier
     * nur den Spaltenwert. Sonst 0-63 von links unten nach rechts oben, meine
     * Tabelle hat aber standardmäßig, 0/0 links oben.
     * @param fieldNumber Feldnummer
     * @return konvertierte Spaltennummer
     */
    public static int fieldNumberConverterColumn(int fieldNumber) {
        // Spalte = fieldNumber mod 8
        int column = (fieldNumber - 1) % 8;

        return column;
    }

    /**
     * Methode, die die verwendete Nummerieung in Tabellenform umrechnet. Hier
     * nur den Zeilenwert. Sonst 0-63 von links unten nach rechts oben, meine
     * Tabelle hat aber standardmäßig, 0/0 links oben.
     * @param fieldNumber Feldnummer
     * @return konvertierte Zeilennummer
     */
    public static int fieldNumberConverterRow(int fieldNumber) {
        // Zeile = 63-fieldNumber / 8 --> abrunden
        int row = (int) Math.floor((64 - fieldNumber) / 8);

        return row;
    }

    /**
     * Methode, die Tabellenzeile und -spalte in die von anderen gewünschte
     * Feldnummerierung von 1 bis 64 umrechnet.     * 
     * @param row Tabellenzeile
     * @param column Tabellenspalte
     * @return Feldnummer
     */
    public static int convertIntoFieldNumber(int row, int column) {
        int fieldNumber = (((8 - row) - 1) * 8) + (column + 1);
        return fieldNumber;
    }

    /**
     * Methode, die die Informationen Über das aktuelle Schachfeld und
     * eventuelle Sonderfälle einholt.
     * @param move aktuelles Move-Objekt
     */
    public void setCheckerboardInformation(Move move) {
        // wer zieht wohin, geschmissen?, Sonderfall: PawnPromotion, Schach?,
        // Schachmatt?

        this.move = move;
        // wenn der Bauer umgewandelt werden soll
        if (this.move.isPawnPromotion()) {
            Gui.getInstance().pawnPromotionGUI();
        }

        // Umrechnung der FieldFrom-Nummer
        this.fieldFrom = this.move.getFieldFrom();
        this.fieldFromColumn = fieldNumberConverterColumn(this.fieldFrom);
        this.fieldFromRow = fieldNumberConverterRow(this.fieldFrom);
        
        // Umrechnung der FieldTo-Nummer
        this.fieldTo = this.move.getFieldTo();
        this.fieldToColumn = fieldNumberConverterColumn(this.fieldTo);
        this.fieldToRow = fieldNumberConverterRow(this.fieldTo);

        // aktuelle Figur zwischenspeichern
        this.figure = Field.getInstance().getFigureAt(this.fieldFrom);

        if (!this.move.isKingSideCastling() && !this.move.isQueenSideCastling()) {        
            // ganz normaler Zug
            
            // erst Zeile
            for (int i = 0; i < 8; i++) {
                if (i == this.fieldFromRow) {
                    // dann Spalte
                    for (int j = 0; j < 8; j++) {
                        if (j == this.fieldFromColumn) {
                            // entfernt die entsprechende Figur auf dem zugehörigen Feld
                            CheckerboardPanel cbp = (CheckerboardPanel) this.grid
                                    .getValueAt(i, j);
                            cbp.label.setVisible(false);
                            Gui.getInstance().repaint();
                            Gui.getInstance().validate();
                        }
                    }
                }
            }
    
            // erst Zeile
            for (int i = 0; i < 8; i++) {
                if (i == this.fieldToRow) {
                    // dann Spalte
                    for (int j = 0; j < 8; j++) {
                        if (j == this.fieldToColumn) {
                            CheckerboardPanel cbp = (CheckerboardPanel) this.grid
                                    .getValueAt(i, j);
                            // falls dort eine Schachfigur geschlagen wurde
                            if (this.move.isCaptured()) {
                                cbp.label.setVisible(false);
                            }
                            // zeigt die entsprechende Figur auf dem zugehörigen Feld an
                            cbp.showIcon(this.figure);
                            Gui.getInstance().repaint();
                            Gui.getInstance().validate();
                        }
                    }
                }
            }

        } else if (this.move.isKingSideCastling() && !this.move.isQueenSideCastling()) {
            // kurze Rochade
            if (this.move.getPlayerColor() == ChessfigureConstants.BLACK) {
                // Ke8-g8 und Th8-f8 (kurze schwarze Rochade)
                // König: 0/4 - 0/6, Turm: 0/7 - 0/5
                
                // König weg
                CheckerboardPanel cbp = (CheckerboardPanel) this.grid.getValueAt(0, 4);
                cbp.label.setVisible(false);
                // König hin
                cbp = (CheckerboardPanel) this.grid.getValueAt(0, 6);
                cbp.label.setIcon(this.icon_king_black);
                // Turm weg
                cbp = (CheckerboardPanel) this.grid.getValueAt(0, 7);
                cbp.label.setVisible(false);
                // Turm hin
                cbp = (CheckerboardPanel) this.grid.getValueAt(0, 5);
                cbp.label.setIcon(this.icon_rook_black);
            } else {
                // Ke1-g1 und Th1-f1 (kurze weiße Rochade)
                // König: 7/4 - 7/6, Turm: 7/7 - 7/5
                
                // König weg
                CheckerboardPanel cbp = (CheckerboardPanel) this.grid.getValueAt(7, 4);
                cbp.label.setVisible(false);
                // König hin
                cbp = (CheckerboardPanel) this.grid.getValueAt(7, 6);
                cbp.label.setIcon(this.icon_king_white);
                // Turm weg
                cbp = (CheckerboardPanel) this.grid.getValueAt(7, 7);
                cbp.label.setVisible(false);
                // Turm hin
                cbp = (CheckerboardPanel) this.grid.getValueAt(7, 5);
                cbp.label.setIcon(this.icon_rook_white);
            }
        } else {
            // lange Rochade
            if (this.move.getPlayerColor() == ChessfigureConstants.BLACK) {
                // Ke8-c8 und Ta8-d8 (lange schwarze Rochade)
                // König: 0/4 - 0/2, Turm: 0/0 - 0/3
                
                // König weg
                CheckerboardPanel cbp = (CheckerboardPanel) this.grid.getValueAt(0, 4);
                cbp.label.setVisible(false);
                // König hin
                cbp = (CheckerboardPanel) this.grid.getValueAt(0, 2);
                cbp.label.setIcon(this.icon_king_black);
                // Turm weg
                cbp = (CheckerboardPanel) this.grid.getValueAt(0, 0);
                cbp.label.setVisible(false);
                // Turm hin
                cbp = (CheckerboardPanel) this.grid.getValueAt(0, 3);
                cbp.label.setIcon(this.icon_rook_black);
            } else {
                // Ke1-c1 und Ta1-d1 (lange weiße Rochade)
                // König: 7/4 - 7/2, Turm: 7/0 - 7/3
                
                // König weg
                CheckerboardPanel cbp = (CheckerboardPanel) this.grid.getValueAt(7, 4);
                cbp.label.setVisible(false);
                // König hin
                cbp = (CheckerboardPanel) this.grid.getValueAt(7, 2);
                cbp.label.setIcon(this.icon_king_white);
                // Turm weg
                cbp = (CheckerboardPanel) this.grid.getValueAt(7, 0);
                cbp.label.setVisible(false);
                // Turm hin
                cbp = (CheckerboardPanel) this.grid.getValueAt(7, 3);
                cbp.label.setIcon(this.icon_rook_white);
            }
        }

        Gui.getInstance().repaint();
        Gui.getInstance().validate();

        if (this.move.isCheck() && this.move.isCheckMate()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Schachmatt! Spiel vorbei!", "Schachmatt",
                    JOptionPane.INFORMATION_MESSAGE);
            // wer gewonnen hat
            if (this.figure.getColor() == ChessfigureConstants.BLACK) {
                this.blackWon = true;
            } else {
                this.blackWon = false;
            }
            FinishedGameGUI.getInstance();
        }

        if (this.move.isCheck() && !this.move.isCheckMate()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Schach!",
                    "Schach", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * Methode, die ausgeführt wird, wenn die Kamera den Zug nicht richtig
     * erkennen konnte. Der Benutzer kann entweder Über klicken am Schachfeld
     * einen normalen Zug auswählen oder einfach die entsprechende Rochade
     * auswählen.
     * 
     * @return ArrayList mit den Feldnummern
     */
    public ArrayList<Integer> manualMove() {
        this.manualMove = true;
        this.mmIsReady = false;
        
        System.out.println("öasfjölasjföasjfölasfölasjföajsföjasfökasj");
        System.out.println("Checkerboard vor reset:"+instance.getArrayList());
        MoveGUI.getInstance().resetMoveGui();

        System.out.println("Checkerboard nach reset:"+instance.getArrayList());
        MoveGUI.getInstance();
        System.out.println("Checkerboard nach movegui:"+instance.getArrayList());

        while (!this.mmIsReady) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Checkerboard nach while:"+instance.getArrayList());

        
        if (MoveGUI.getInstance().isNormalButtonPressed()) {
            // Feldnummern von den angeklickten Feldern
            System.out.println("geh ich hier rein???: a: " + this.a);
            return this.a;
        } else {
            // darf weiß überhaupt eine Rochade ausführen?
            if (this.figure.getColor() == ChessfigureConstants.WHITE &&
                    Field.getInstance().isRochadeWhitePossible()) {
                // welche Rochadenart
                if (MoveGUI.getInstance().isKingsideCastling()) {
                    // kurze Rochade
                    // a = 5 7 8 6
                    this.a.clear();
                    this.a.add(5);
                    this.a.add(7);
                    this.a.add(8);
                    this.a.add(6);
                    return this.a;
                } else {
                    // lange Rochade
                    // a = 5 3 1 4
                    this.a.clear();
                    this.a.add(5);
                    this.a.add(3);
                    this.a.add(1);
                    this.a.add(4);
                    return this.a;
                }
            // wenn keine Rochade erlaubt
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Keine Rochade möglich!",
                        "Rochade", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return new ArrayList<Integer>();
    }

    /**
     * Setter für die booleansche Variable, ob die manuelle Zugeingabe
     * erfolgreich beendet wurde.
     * @param mmIsReady
     */
    public void setMmIsReady(boolean mmIsReady) {
        this.mmIsReady = mmIsReady;
    }

    /**
     * Methode, die die ArrayList setzt.
     * @param a ArrayList
     */
    public void setArrayList(ArrayList<Integer> a) {
        this.a = a;
    }

    /**
     * Getter für die ArrayList.
     * @return entsprechende ArrayList
     */
    public ArrayList<Integer> getArrayList() {
        return this.a;
    }

    /**
     * Getter für die booleansche Variable, ob Schwarz oder Weiß gewonnen hat.
     * @return blackWon true oder false
     */
    public boolean hasBlackWon() {
        return this.blackWon;
    }

    /**
     * Getter für die booleansche Variable, ob der Zug manuell eingegeben werden
     * muss oder nicht.
     * @return manualMove true oder false
     */
    public boolean isManualMove() {
        return this.manualMove;
    }

    /**
     * @param manualMove true oder false
     */
    public void setManualMove(boolean manualMove) {
        this.manualMove = manualMove;
    }

    /**
     * Methode, die die Schachfiguren auf ihre Startpositionen setzt.
     * @param figures HashMap mit der Startfeldfigurenanordnung
     */
    public void getStartMap(HashMap<Integer, Figure> figures) {
        // Iterator erstellen, der ueber alle Figuren iteriert
        Iterator<Entry<Integer, Figure>> it = figures.entrySet().iterator();
        // iteriere ueber alle Figuren
        while (it.hasNext()) {
            // Key/Value-Paar speichern
            Map.Entry<Integer, Figure> pair = it.next();

            // Position der Figur steht im Key
            Integer i = pair.getKey();
            // value-Objekt
            Figure f = pair.getValue();

            // entsprechende Figuren werden an zugehöriger Position angezeigt
            // Umrechnung der Feldnummern notwendig
            CheckerboardPanel cbp = (CheckerboardPanel) this.grid
                    .getValueAt(fieldNumberConverterRow(i), (fieldNumberConverterColumn(i)));
            cbp.showIcon(f);
        }
    }

    /**
     * Methode, die die Tabelle neu zeichnen lässt, um ein neues Spiel zu
     * ermöglichen.
     */
    public void resetMap() {
        this.removeAll();
        this.makeTable();
    }
}
