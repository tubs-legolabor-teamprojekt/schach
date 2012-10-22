package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Klasse, die das Schachbrett darstellt. 
 * @author Tabea
 *
 */
public class Checkerboard extends JPanel
{

	private JTable grid = null;
	
	/**
	 * Konstruktor, der ein neues Objekt der Klasse erstellt.
	 */
	public Checkerboard() 
	{
		this.makeTable();
	}
	
	/**
	 * Methode, die eine Tabelle erzeugt, die das Schachbrett darstellt.
	 */
	public void makeTable()
	{
		
		// Tabelle erstellen
		this.grid = new JTable(8, 8);
		this.grid.setShowHorizontalLines(true);
		
		// Zeilenhöhe und Spaltenbreite anpassen, so dass die Felder quadratisch sind
		this.grid.setRowHeight(75);
		
		int columnIndex = 8;
		TableColumn col = null;
		for (int i = 0; i < columnIndex; i++) {
			col = this.grid.getColumnModel().getColumn(i);
			col.setPreferredWidth(75);		
			// jede Spalte enthält einen CellRenderer
			this.grid.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer()
				{
					public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,  
	                        boolean hasFocus, int row, int column) 
					{
						return (JPanel)value;
					}
				}
			);
		}
		
		CheckerboardPanel cb = new CheckerboardPanel();
		
		// in jeder Tabellenzelle wird ein JPanel eingefügt
		// Schachbrettmuster wird angelegt
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				this.grid.setValueAt(new CheckerboardPanel(), i, j);
				if (i%2 == 0) {
					if (j%2 == 0) {
						cb = (CheckerboardPanel)this.grid.getValueAt(i, j);
						cb.setBackground(Color.black);
					} else {
						cb = (CheckerboardPanel)this.grid.getValueAt(i, j);
						cb.setBackground(Color.white);
					}
				} else {
					if (j%2 == 0) {
						cb = (CheckerboardPanel)this.grid.getValueAt(i, j);
						cb.setBackground(Color.white);
					} else {
						cb = (CheckerboardPanel)this.grid.getValueAt(i, j);
						cb.setBackground(Color.black);
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
//		this.grid.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
	}
	
	/**
	 * Methode, die die übergebene Feldnummer an die Zählweise der JTable anpasst. 
	 * @param fieldNumber
	 */
	public void fieldNumberConverter(int fieldNumber) 
	{
		/* 
		 * Nummerierung des Feldes: unten links - 0
		 * 							oben rechts - 63
		 * bei mir: oben rechts - 0/0
		 * 
		 * Spalte = fieldNumber mod 8
		 * Zeile = 63-fieldNumber / 8 --> abrunden
		 */							
		
		int column = fieldNumber%8;
		int row = (int)Math.floor((63-fieldNumber)/8);
		
		//System.out.println("FieldNumber: " + fieldNumber + " ,column: " + column + " ,row: " + row);	
	}
}
