package gui;

import game.Move;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import components.Figure;

/**
 * Klasse, die das Schachbrett darstellt. 
 * @author Tabea
 *
 */
public class Checkerboard extends JPanel
{

	private Gui g;
	
	private JTable grid = null;
	
	private Move move;
	private int fieldFrom, 
				fieldTo;
	private Figure figure;
	
	private int fieldFromColumn = 0,
				fieldFromRow = 0,
				fieldToColumn = 0,
				fieldToRow = 0;
	
	/**
	 * Konstruktor, der ein neues Objekt der Klasse erstellt.
	 */
	public Checkerboard(Gui g) 
	{
		this.g = g;
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
		
		// Zeilenh�he und Spaltenbreite anpassen, so dass die Felder quadratisch sind
		this.grid.setRowHeight(75);
		
		int columnIndex = 8;
		TableColumn col = null;
		for (int i = 0; i < columnIndex; i++) {
			col = this.grid.getColumnModel().getColumn(i);
			col.setPreferredWidth(75);		
			// jede Spalte enth�lt einen CellRenderer
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
		
		// in jeder Tabellenzelle wird ein JPanel eingef�gt
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
		
		// Felder d�rfen weder editiert noch ausgew�hlt werden
		this.grid.setEnabled(false);
		
		// Tabellenaussehen
		this.grid.setBorder(BorderFactory.createLineBorder(Color.black));
		this.grid.setGridColor(Color.black);
		
		// Tabelle hinzuf�gen und W�nde fett machen
		this.add(grid);
//		this.grid.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
	}
	
	/**
	 * Methode, die die verwendete Nummerieung in Tabellenform 
	 * umrechnet. Hier nur den Spaltenwert. 
	 * Sonst 0-63 von links unten nach rechts oben, meine Tabelle
	 * hat aber standardm��ig, 0/0 links oben.
	 * @param fieldNumber
	 * @return
	 */
	public int fieldNumberConverterColumn(int fieldNumber)
	{
		// Spalte = fieldNumber mod 8
		int column = (fieldNumber-1)%8;
		
		return column;
	}
	
	/**
	 * Methode, die die verwendete Nummerieung in Tabellenform 
	 * umrechnet. Hier nur den Zeilenwert.
	 * Sonst 0-63 von links unten nach rechts oben, meine Tabelle
	 * hat aber standardm��ig, 0/0 links oben.
	 * @param fieldNumber
	 * @return
	 */
	public int fieldNumberConverterRow(int fieldNumber)
	{
		// Zeile = 63-fieldNumber / 8 --> abrunden
		int row = (int)Math.floor((64-fieldNumber)/8);
		
		return row;
	}
	
	/**
	 * Methode, die die Informationen �ber das aktuelle Schachfeld und 
	 * eventuelle Sonderf�lle einholt.
	 */
	public void getCheckerboardInformation(Move move) 
	{
		// wer zieht wohin, geschmissen?, Sonderfall: PawnPromotion, Schach?, Schachmatt? 
		
		this.move = move;
		
		if (this.move.isPawnPromotion()) {
			this.g.pawnPromotionGUI();
		}
		
		this.figure = this.move.getFigure();
			
		this.fieldFrom = this.move.getFieldFrom();
		this.fieldFromColumn = this.fieldNumberConverterColumn(this.fieldFrom);
		this.fieldFromRow = this.fieldNumberConverterRow(this.fieldFrom);
		
		for (int i = 0; i < 8; i++) {
			if (i == this.fieldFromColumn) {
				for (int j = 0; j < 8; j++) {
					if (j == this.fieldFromRow) {
						CheckerboardPanel cbp = (CheckerboardPanel)this.grid.getValueAt(i, j);
						cbp.showIcon(this.figure, false);
					}
				}
			}
		}
		
		this.fieldTo = this.move.getFieldTo();
		this.fieldToColumn = this.fieldNumberConverterColumn(this.fieldTo);
		this.fieldToRow = this.fieldNumberConverterRow(this.fieldTo);
		
		System.out.println(this.fieldFrom + ": " + this.fieldFromColumn + "/" + this.fieldFromRow + " --> "
								+ this.fieldTo + ": " + this.fieldToColumn + "/" + this.fieldToRow);
		
		for (int i = 0; i < 8; i++) {
			if (i == this.fieldToColumn) {
				for (int j = 0; j < 8; j++) {
					if (j == this.fieldToRow) {
						CheckerboardPanel cbp = (CheckerboardPanel)this.grid.getValueAt(i, j);
						cbp.showIcon(this.figure, true);
					}
				}
			}
		}		
		
		this.repaint();
		this.revalidate();
		
		if (this.move.isCaptured()) {	
			// gucken, ob er die Icons �bereinander macht, oder nicht, ansonsten, geschlagenen extra wegmachen
		} 
		
		if (this.move.isCheck()) {
			javax.swing.JOptionPane.showMessageDialog(this,"Schach!");
		}
		
		if (this.move.isCheckMate()) {
			javax.swing.JOptionPane.showMessageDialog(this,"Schachmatt!");
		}
		
	}
	
}
