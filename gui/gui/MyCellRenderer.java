package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Klasse, die einen eigenen TableCellRenderer repräsentiert.
 * 
 * @author Tabea
 * 
 */
public class MyCellRenderer extends DefaultTableCellRenderer
{

    private boolean bottom;

    /**
     * Konstruktor, der ein neues Objekt der Klase erzeugt und setzt die
     * übergebene Variable bottom.
     * @param bottom untere oder linke Tabelle
     */
    public MyCellRenderer(boolean bottom) {
        this.bottom = bottom;
    }

    /**
     * Methode, die die Eigenschaften der Tabellenzellen setzt.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel c = (JLabel) super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
        // Tabelleneigenschaften
        c.setFont(new Font("Arial", Font.BOLD, 15));
        c.setForeground(new Color(43, 23, 2));
        c.setVerticalAlignment(JLabel.CENTER);

        // wenn es sich um die untere Tabelle handelt, wird die Beschriftung
        // anders ausgerichtet, als wenn es sich um die linke Tabelle handelt
        if (this.bottom) {
            c.setHorizontalAlignment(JLabel.CENTER);
        } else {
            c.setHorizontalAlignment(JLabel.RIGHT);
        }

        return c;
    }
}
