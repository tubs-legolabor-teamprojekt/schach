package gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

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
                counter = 0;

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
        this.counter++;

        // es wird nur etwas gemacht, wenn man auch seinen Zug manuell eingeben
        // soll und nicht mehr als zweimal geklickt hat
        if (Checkerboard.getInstance().isManualMove() && this.counter < 3) {
            
            // aktuelle Zeile und Spalte holen
            this.row = this.grid.rowAtPoint(e.getPoint());
            this.column = this.grid.columnAtPoint(e.getPoint());
            
            System.out.println("zeile: " + this.row + ", spalte: " + this.column);
            CheckerboardPanel cbp = (CheckerboardPanel) this.grid.getValueAt(this.row, this.column);
            System.out.println(cbp.getBackground());
//            cbp.setBackground(new Color(41, 15, 5, 170));
            cbp.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(255, 0, 0, 255)));
            Gui.getInstance().repaint();
            Gui.getInstance().validate();
            
            // Feldnummer errechnen und zur ArrayList hinzufügen
            int fieldnumber = Checkerboard.convertIntoFieldNumber(this.row, this.column);
            this.a.add(fieldnumber);
            System.out.println(this.counter);
      
            // wenn man beide Felder ausgewählt hat Abfrage, ob korrekt
            // wenn ja, weiter im Programm, ansonsten erneute Abfrage
            if (this.counter == 2) {
                int reply = javax.swing.JOptionPane.showConfirmDialog(
                        Gui.getInstance(), "Sind folgende Angaben "
                                + "korrekt?\nFeld von: " + Field.getFieldName(this.a.get(0))
                                + "\nFeld nach: " + Field.getFieldName(this.a.get(1)),
                                "Normaler Zug", JOptionPane.YES_NO_OPTION);
                this.counter = 0;
                if (reply == JOptionPane.YES_OPTION) {
                    Checkerboard.getInstance().setArrayList(this.a);
                    Checkerboard.getInstance().setManualMove(false);
                } else {
                    this.a.clear();
                    javax.swing.JOptionPane.showMessageDialog(
                            Gui.getInstance(), "Bitte erneute Zueingabe!",
                            "Normaler Zug", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        } else {
            System.out.println("hahah geht nicht");
        }
    }
}