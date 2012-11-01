package gui;

import game.Move;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import components.Field;
import components.FigureKing;

/**
 * Klasse, die das Hauptfenster darstellt.
 * @author Tabea
 *
 */
public class Gui extends JFrame
{

	private Container c;
	
	final Color alphaZero = new Color(0, true);
	
	private JPanel 	top = new JPanel(),
					bottom = new JPanel(),
					left = new JPanel(),
					right = new JPanel();
	
	private Checkerboard checkerboard;
	
	private String[] 	columnNames_1 = {"a", "b", "c", "d", "e", "f", "g", "h"},
						columnNames_2 = {"8"};
	private String[][] 	rowData_1 = {{"a", "b", "c", "d", "e", "f", "g", "h"}},
						rowData_2 = {{"8"}, {"7"}, {"6"}, {"5"}, {"4"}, {"3"}, {"2"}, {"1"}};
	private JTable 	table_bottom = new JTable(rowData_1, columnNames_1),
					table_left = new JTable(rowData_2, columnNames_2);
	
	private boolean queen = false, 
					bishop = false,
					knight = false,
					rook = false;
	
	/**
	 * Konstruktor, der ein neues Fenster erstellt und 
	 * Einstellungen und Layout festlegt.
	 * @param title
	 */
	public Gui(String title)
	{
		super(title);
		
		this.checkerboard = new Checkerboard(this);
		
		this.startWindow();
		this.makeLayout();
		
		//this.pawnPromotionGUI();
		
	}
	
	/**
	 * Methde, die die Fenstereinstellungen anlegt.
	 */
	public void startWindow()
	{
		this.setIconImage(new ImageIcon("gui/gui/checkerboard.png").getImage());
		this.setSize(750, 750);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Methode, die das Layout des Fensters erstellt.
	 */
	public void makeLayout() 
	{
		this.makeTable_bottomLayout();
		this.makeTable_leftLayout();
		
		this.setContentPane(new BackgroundPanel());
		this.setLayout(new BorderLayout());
		
//		this.c = getContentPane();
//		this.c.setLayout(new BorderLayout());
		
//		this.c.add(this.top, BorderLayout.NORTH);
		this.getContentPane().add(this.top, BorderLayout.NORTH);
		this.top.setBorder(new EmptyBorder(25, 25, 25, 25));
		this.top.setOpaque(false);
		
//		this.c.add(this.bottom, BorderLayout.SOUTH);
		this.getContentPane().add(this.bottom, BorderLayout.SOUTH);
		this.bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.bottom.add(this.table_bottom);
		this.bottom.setOpaque(false);
		
//		this.c.add(this.left, BorderLayout.WEST);
		this.getContentPane().add(this.left, BorderLayout.WEST);
		this.left.setBorder(new EmptyBorder(0, 10, 10, 10));
		this.left.add(this.table_left);
		this.left.setOpaque(false);
		
//		this.c.add(this.right, BorderLayout.EAST);
		this.getContentPane().add(this.right, BorderLayout.EAST);
		this.right.setBorder(new EmptyBorder(25, 25, 25, 25));
		this.right.setOpaque(false);
		
//		this.c.add(this.checkerboard, BorderLayout.CENTER);
		this.getContentPane().add(this.checkerboard, BorderLayout.CENTER);
		this.checkerboard.setBorder(new EmptyBorder(-4, 0, 0, 0));
		this.checkerboard.setOpaque(false);
	}
	
	/**
	 * Methode, die das Layout für die Schachbrettbeschriftung am 
	 * unteren Rand des Feldes erzeugt.
	 */
	public void makeTable_bottomLayout() 
	{
		for (int i = 0; i < 8; i++) {
			this.table_bottom.getColumnModel().getColumn(i).setCellRenderer(new MyCellRenderer());
		}
	
		this.table_bottom.setEnabled(false);
		this.table_bottom.setRowHeight(30);
//		this.table_bottom.setBackground(null);
		this.table_bottom.setOpaque(false);
		this.table_bottom.setBackground(alphaZero);
		this.table_bottom.setShowHorizontalLines(false);
		this.table_bottom.setShowVerticalLines(false);
	}
	
	/**
	 * Methode, die das Layout für die Schachbretbeschriftung am 
	 * linken Rand des Feldes erzeugt.
	 */
	public void makeTable_leftLayout() 
	{	
		
		this.table_left.getColumnModel().getColumn(0).setCellRenderer(new MyCellRenderer());
		
		/*	
//		this.table_left.getTableHeader().setFont(new Font("Courier", Font.BOLD, 50));
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		
		for(int i = 0; i < 1; i++){
			table_left.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
		}*/
		
		this.table_left.setRowHeight(75);

		TableColumn col = null;
		for (int i = 0; i < 1; i++) {
			col = this.table_left.getColumnModel().getColumn(i);
			col.setPreferredWidth(30);
		}
		
		this.table_left.setEnabled(false);
//		this.table_left.setBackground(null);
		this.table_left.setOpaque(false);
		this.table_left.setBackground(alphaZero);
		this.table_left.setShowHorizontalLines(false);
		this.table_left.setShowVerticalLines(false);
	}
	
	
	/**
	 * Methode, die ein neues Objekt der Klasse PawnPromotionGUI erzeugt.
	 */
	public void pawnPromotionGUI() 
	{
		PawnPromotionGUI pp = new PawnPromotionGUI("Bauernumwandlung", this);
	}
	
	/**
	 * Methode, die die Informationen über die getroffene Spielfigurenwahl
	 * des Spielers enthält, wenn der Bauer ausgewechselt werden durfte.
	 * @param queen
	 * @param bishop
	 * @param knight
	 * @param rook
	 */
	public void pawnPromotionInformation(boolean queen, boolean bishop, 
			boolean knight, boolean rook) 
	{
		this.queen = queen;
		this.bishop = bishop;
		this.knight = knight;
		this.rook = rook;
		
//		System.out.println("queen: " + this.queen + " bishop: " + this.bishop
//				+ " knight: " + this.knight + " rook: " + this.rook);
	}
	
	public void showWarning(String message)
	{
		javax.swing.JOptionPane.showMessageDialog(this, message, "Fehler!", JOptionPane.WARNING_MESSAGE);
	}

	public Checkerboard getCheckerboard() {
		return checkerboard;
	}

	/**
	 * Main-Methode
	 * @param args
	 */
	public static void main(String[] args)
	{
		Gui g = new Gui("Schach");
		
		Move move = new Move(Field.getFieldNumber("e8"), Field.getFieldNumber("e7"), new FigureKing((byte)1), true, true, false );
		
		
		Field f = Field.getInstance();
		g.getCheckerboard().getStartMap(f.getCurrentFieldAsHashMap());
		g.getCheckerboard().getCheckerboardInformation(move);
		
//		Move move2 = new Move(Field.getFieldNumber("c7"), Field.getFieldNumber("d8"), new FigurePawn((byte)0), true, true, false, true);
//		cb.getCheckerboardInformation(move);
		
		/*
		for (int i = 0; i < 64; i++) {
			cb.fieldNumberConverter(i);
		}*/
	}	
}
