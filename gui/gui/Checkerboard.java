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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import util.ChessfigureConstants;

import components.Field;
import components.Figure;

/**
 * Klasse, die das Schachbrett darstellt.
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
					manualMove = false;
	
	private MyMouseListener mml;
	
	private ArrayList<Integer> a = new ArrayList<Integer>();

	/**
	 * Privater Konstruktor, der nur ein neues Objekt der Klasse erstellt.
	 * Ruft die Methode zur Tabellenerstellung auf.
	 */
	private Checkerboard() 
	{
		this.makeTable();
	}
	
	/**
	 * Gibt die Checkerboard-Instanz zurück.
	 * @return Checkerboard-Instanz
	 */
	public static Checkerboard getInstance()
	{
		if (instance == null) {
			instance = new Checkerboard();
		}
		
		return instance;
	}
	
	/**
	 * Methode, die eine Tabelle erzeugt, die das Schachbrett darstellt.
	 */
	public void makeTable()
	{
		
		// Tabelle erstellen
		this.grid = new JTable(8, 8);
		this.grid.setShowHorizontalLines(true);
		
		this.grid.setOpaque(false);
		
		this.mml = new MyMouseListener(this.grid);
		this.grid.addMouseListener(this.mml);
		
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
				if (i%2 == 0) {
					if (j%2 == 0) {
						cb = (CheckerboardPanel)this.grid.getValueAt(i, j);
						cb.setBackground(new Color(41, 15, 5, 100));
					} else {
						cb = (CheckerboardPanel)this.grid.getValueAt(i, j);
						cb.setBackground(new Color(251, 225, 172, 100));
					}
				} else {
					if (j%2 == 0) {
						cb = (CheckerboardPanel)this.grid.getValueAt(i, j);
						cb.setBackground(new Color(251, 225, 172, 100));
					} else {
						cb = (CheckerboardPanel)this.grid.getValueAt(i, j);
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
		this.grid.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(43, 23, 2, 255)));
	}
	
	/**
	 * Methode, die die verwendete Nummerieung in Tabellenform 
	 * umrechnet. Hier nur den Spaltenwert. 
	 * Sonst 0-63 von links unten nach rechts oben, meine Tabelle
	 * hat aber standardmäßig, 0/0 links oben.
	 * @param fieldNumber Feldnummer
	 * @return konvertierte Spaltennummer
	 */
	public static int fieldNumberConverterColumn(int fieldNumber)
	{
		// Spalte = fieldNumber mod 8
		int column = (fieldNumber-1)%8;
		
		return column;
	}
	
	/**
	 * Methode, die die verwendete Nummerieung in Tabellenform 
	 * umrechnet. Hier nur den Zeilenwert.
	 * Sonst 0-63 von links unten nach rechts oben, meine Tabelle
	 * hat aber standardmäßig, 0/0 links oben.
	 * @param fieldNumber Feldnummer
	 * @return konvertierte Zeilennummer
	 */
	public static int fieldNumberConverterRow(int fieldNumber)
	{
		// Zeile = 63-fieldNumber / 8 --> abrunden
		int row = (int)Math.floor((64-fieldNumber)/8);
		
		return row;
	}
	
	public static int convertIntoFieldNumber(int row, int column)
	{
		int fieldNumber = (((8 - row) - 1) * 8) + (column + 1); 
		return fieldNumber;
	}
	
	/**
	 * Methode, die die Informationen über das aktuelle Schachfeld und 
	 * eventuelle Sonderfälle einholt.
	 * @param move aktuelles Move-Objekt
	 */
	public void setCheckerboardInformation(Move move) 
	{
		// wer zieht wohin, geschmissen?, Sonderfall: PawnPromotion, Schach?, Schachmatt? 
		
		this.move = move;
		System.out.println("halsdk");
		// wenn der Bauer umgewandelt werden soll
		if (this.move.isPawnPromotion()) {
			Gui.getInstance().pawnPromotionGUI();
		}
		
		// Umrechnung der FieldFrom-Nummer
		this.fieldFrom = this.move.getFieldFrom();
		this.fieldFromColumn = fieldNumberConverterColumn(this.fieldFrom);
		this.fieldFromRow = fieldNumberConverterRow(this.fieldFrom);
		
		this.figure = Field.getInstance().getFigureAt(this.fieldFrom);
		
		// erst Zeile 
		for (int i = 0; i < 8; i++) {
			if (i == this.fieldFromRow) {
				// dann Spalte
				for (int j = 0; j < 8; j++) {
					if (j == this.fieldFromColumn) {
						// entfernt die entsprechende Figur auf dem zugehörigen Feld
						CheckerboardPanel cbp = (CheckerboardPanel)this.grid.getValueAt(i, j);
						cbp.label.setVisible(false);
						Gui.getInstance().repaint();
						Gui.getInstance().validate();
					}
				}
			}
		}
		
		// Umrechnung der FieldTo-Nummer
		this.fieldTo = this.move.getFieldTo();
		this.fieldToColumn = fieldNumberConverterColumn(this.fieldTo);
		this.fieldToRow = fieldNumberConverterRow(this.fieldTo);	
		
		// Zeile
		for (int i = 0; i < 8; i++) {
			if (i == this.fieldToRow) {
				// dann Spalte
				for (int j = 0; j < 8; j++) {
					if (j == this.fieldToColumn) {
						CheckerboardPanel cbp = (CheckerboardPanel)this.grid.getValueAt(i, j);
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
		
		Gui.getInstance().repaint();
		Gui.getInstance().validate();
		
		if (this.move.isCheck() && this.move.isCheckMate()) {
			javax.swing.JOptionPane.showMessageDialog(this,"Schachmatt! Spiel vorbei!", "Schachmatt", JOptionPane.INFORMATION_MESSAGE);
			// Result
			if (this.figure.getColor() == ChessfigureConstants.BLACK) {
				this.blackWon = true;
			} else {
				this.blackWon = false;
			}
			FinishedGameGUI.getInstance();
		}
		
		if (this.move.isCheck() && !this.move.isCheckMate()) {
			javax.swing.JOptionPane.showMessageDialog(this,"Schach!", "Schach", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	public ArrayList<Integer> manualMove()
	{
		this.manualMove = true;
		System.out.println("lksdf");
		MoveGUI.getInstance();
		System.out.println("lksdasdgfadsfasff");
		if (MoveGUI.getInstance().isKingsideCastling()) {
			// kurze Rochade 
			// a =  5 7 8 6
			this.a.add(5);
			this.a.add(7);
			this.a.add(8);
			this.a.add(6);
		} else if (!MoveGUI.getInstance().isKingsideCastling()) {
			// lange Rochade
			// a = 5 3 1 4
			this.a.add(5);
			this.a.add(3);
			this.a.add(1);
			this.a.add(4);
		} else {
			
			
			// Feldnummern von den angeklickten Feldern
//			a.add();
//			a.add();
		}
		System.out.println("Tabea" + this.a);
		return this.a;
	}

	public void setArrayList(ArrayList<Integer> a)
	{
		this.a = a;
	}
	
	public ArrayList<Integer> getArrayList()
	{
		return this.a;
	}
	
	public boolean hasBlackWon() 
	{
		return this.blackWon;
	}
	
	public boolean isManualMove() 
	{
		return this.manualMove;
	}
	
	public void setManualMove(boolean manualMove)
	{
		this.manualMove = manualMove;
	}
	
	/**
	 * Methode, die die Schachfiguren auf ihre Startpositionen setzt.
	 * @param figures HashMap mit der Startfeldfigurenanordnung
	 */
	public void getStartMap(HashMap<Integer, Figure> figures)
	{
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
			CheckerboardPanel cbp = (CheckerboardPanel)this.grid.
					getValueAt(fieldNumberConverterRow(i),
					(fieldNumberConverterColumn(i)));
			cbp.showIcon(f);
		}
	}
	
	/**
	 * Methode, die die Tabelle neu zeichnen lässt, um ein 
	 * neues Spiel zu ermöglichen.
	 */
	public void resetMap() {
		this.removeAll();
		this.makeTable();
	}
}
