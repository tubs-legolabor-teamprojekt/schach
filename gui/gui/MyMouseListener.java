package gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import components.Field;

/**
 * Klasse, die die benötigte Methode des MouseListeners implementiert.
 * 
 * @author Tabea
 * 
 */
public class MyMouseListener extends MouseAdapter
{

    private JTable grid;

    private ArrayList<Integer> a = new ArrayList<Integer>();

    private int row = 0, 
                column = 0, 
                counter = 0,
                field_row_1 = 0,
                field_column_1 = 0;
    private Color backgroundColor;
    
    
    /**
     * Konstruktor, der ein neues Objekt der Klasse erstellt und die Tabelle/das
     * Schachfeld übergeben bekommt.
     * @param grid
     */
    public MyMouseListener(JTable grid) {
        this.grid = grid;
    }

    /**
     * Methode, die reagiert, wenn auf eine Tabellenzelle geklickt wurde.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        
        // es wird nur etwas gemacht, wenn man auch seinen Zug manuell eingeben
        // soll und auf OK geklickt hat
        if (Checkerboard.getInstance().isManualMove() && 
                Checkerboard.getInstance().getMoveGui().isNormalButtonPressed()) {
            
            this.counter++;
            
            // nur wenn man höchstens zweimal geklickt hat pasiert etwas
            if (this.counter < 3) {
                CheckerboardPanel cbp_check = (CheckerboardPanel) this.grid.getValueAt(
                      this.grid.rowAtPoint(e.getPoint()), this.grid.columnAtPoint(e.getPoint()));
                
                // aktuelle Zeile und Spalte holen
                this.row = this.grid.rowAtPoint(e.getPoint());
                this.column = this.grid.columnAtPoint(e.getPoint());
                
                if (this.counter == 1 && cbp_check.isFigure()) { // beim ersten Klick muss das Feld besetzt sein
                    
                    this.field_row_1 = this.row;
                    this.field_column_1 = this.column;
                    
                    // ausgwähltes Feld farblich hervorheben
                    CheckerboardPanel cbp = (CheckerboardPanel) this.grid.getValueAt(
                            this.field_row_1, this.field_column_1);
                    this.backgroundColor = cbp.getBackground();
                    cbp.setBackground(new Color(154, 205, 50, 100));
                    Gui.getInstance().repaint();
                    Gui.getInstance().validate();
                    
                    // Feldnummer errechnen und zur ArrayList hinzufügen
                    int fieldnumber = Checkerboard.convertIntoFieldNumber(this.field_row_1, this.field_column_1);
                    this.a.add(fieldnumber);
                    
                } else if (this.counter == 1 && !cbp_check.isFigure()) { // wird beim ersten Klicken ein unbesetztes
                                                                         // Feld geklickt, passiert nichts
                    this.counter--;
                    
                } else if (this.counter == 2) { // beim zweiten Klicken darf jedes Feld gewählt werden
                    
                    if (this.field_row_1 == this.row && this.field_column_1 == this.column) {
                        this.counter = 0;
                        CheckerboardPanel cbp = (CheckerboardPanel) this.grid.getValueAt(
                                this.field_row_1, this.field_column_1);
                        cbp.setBackground(this.backgroundColor);
                        Gui.getInstance().repaint();
                        Gui.getInstance().validate();
                        this.a.clear();
                    } else {
                        // ausgwähltes Feld farblich hervorheben
                        CheckerboardPanel cbp = (CheckerboardPanel) this.grid.getValueAt(this.row, this.column);
                        cbp.setBackground(new Color(154, 205, 50, 100));
                        Gui.getInstance().repaint();
                        Gui.getInstance().validate();
                        
                        // Feldnummer errechnen und zur ArrayList hinzufügen
                        int fieldnumber = Checkerboard.convertIntoFieldNumber(this.row, this.column);
                        this.a.add(fieldnumber);
                        
                        // wenn alles korrekt geklickt wurde, fragen, ob gewünschte Eingabe
                        int reply = javax.swing.JOptionPane.showConfirmDialog(
                                Gui.getInstance(), "Sind folgende Angaben "
                                        + "korrekt?\nFeld von: " + Field.getFieldName(this.a.get(0))
                                        + "\nFeld nach: " + Field.getFieldName(this.a.get(1)),
                                        "Normaler Zug", JOptionPane.YES_NO_OPTION);
                        this.counter = 0;
                        if (reply == JOptionPane.YES_OPTION) {
                            ArrayList<Integer> al = new ArrayList<Integer>(this.a);
                            Checkerboard.getInstance().setArrayList(al);
                            Checkerboard.getInstance().setManualMove(false);
                            Checkerboard.getInstance().setMmIsReady(true);
                            this.a.clear();
                        } else {
                            this.a.clear();
                            javax.swing.JOptionPane.showMessageDialog(
                                    Gui.getInstance(), "Bitte erneute Zueingabe!",
                                    "Normaler Zug", JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                        // farbliche Hervorhebung wieder rückgängig machen
                        cbp = (CheckerboardPanel) this.grid.getValueAt(this.field_row_1, this.field_column_1);
                        if (this.field_row_1 % 2 == 0) {
                            if (this.field_column_1 % 2 == 0) {
                                cbp.setBackground(new Color(41, 15, 5, 100));
                            } else {
                                cbp.setBackground(new Color(251, 225, 172, 100));
                            }
                        } else {
                            if (this.field_column_1 % 2 == 0) {
                                cbp.setBackground(new Color(251, 225, 172, 100));
                            } else {
                                cbp.setBackground(new Color(41, 15, 5, 100));
                            }
                        }
                        cbp = (CheckerboardPanel) this.grid.getValueAt(this.row, this.column);
                        if (this.row % 2 == 0) {
                            if (this.column % 2 == 0) {
                                cbp.setBackground(new Color(41, 15, 5, 100));
                            } else {
                                cbp.setBackground(new Color(251, 225, 172, 100));
                            }
                        } else {
                            if (this.column % 2 == 0) {
                                cbp.setBackground(new Color(251, 225, 172, 100));
                            } else {
                                cbp.setBackground(new Color(41, 15, 5, 100));
                            }
                        }
                        
                        Gui.getInstance().repaint();
                        Gui.getInstance().validate();
                    }
                }
            }
        }
    }
}