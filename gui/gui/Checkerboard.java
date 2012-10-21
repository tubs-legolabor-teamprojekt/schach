package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class Checkerboard extends JPanel
{

	private JTable grid = null;
	
	public Checkerboard() 
	{
		this.makeTable();
	}
	
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
	
	public void fieldNumberConverter(int fieldNumber) 
	{
		// Nummerierung des Feldes: unten links - 0
		//							oben rechts - 63
		// bei mir: oben rechts - 0/0
		
		// Spalte = fieldNumber mod 8
		// Zeile = 
		
		int column = fieldNumber%8;
		int row = 0;
	}
	
}
