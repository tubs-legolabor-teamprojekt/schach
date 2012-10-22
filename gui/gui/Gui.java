package gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Klasse, die das Hauptfenster darstellt.
 * @author Tabea
 *
 */
public class Gui extends JFrame
{

	private Container c;
	
	private JPanel 	top = new JPanel(),
					bottom = new JPanel(),
					left = new JPanel(),
					right = new JPanel();
	
	private Checkerboard checkerboard = new Checkerboard();
	
	private String[] 	columnNames_1 = {"a", "b", "c", "d", "e", "f", "g", "h"},
						columnNames_2 = {"8"};
	private String[][] 	rowData_1 = {{"a", "b", "c", "d", "e", "f", "g", "h"}},
						rowData_2 = {{"8"}, {"7"}, {"6"}, {"5"}, {"4"}, {"3"}, {"2"}, {"1"}};
	private JTable 	table_bottom = new JTable(rowData_1, columnNames_1),
					table_left = new JTable(rowData_2, columnNames_2);
	
	private boolean pawnPromotion = false,
					queen = false, 
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
		this.startWindow();
		this.makeLayout();
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
		
		// nur zu Testzwecken
		this.pawnPromotionGUI();
	}
	
	/**
	 * Methode, die das Layout des Fensters erstellt.
	 */
	public void makeLayout() 
	{
		this.makeTable_bottomLayout();
		this.makeTable_leftLayout();
		
		this.c = getContentPane();
		this.c.setLayout(new BorderLayout());
		
		this.c.add(top, BorderLayout.NORTH);
		this.top.setBorder(new EmptyBorder(25, 25, 25, 25));
		
		this.c.add(bottom, BorderLayout.SOUTH);
		this.bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.bottom.add(table_bottom);
		
		this.c.add(left, BorderLayout.WEST);
		this.left.setBorder(new EmptyBorder(0, 10, 10, 10));
		this.left.add(table_left);
		
		this.c.add(right, BorderLayout.EAST);
		this.right.setBorder(new EmptyBorder(25, 25, 25, 25));
		
		this.c.add(checkerboard, BorderLayout.CENTER);
		this.checkerboard.setBorder(new EmptyBorder(-4, 0, 0, 0));
	}
	
	/**
	 * Methode, die das Layout für die Schachbrettbeschriftung am 
	 * unteren Rand des Feldes erzeugt.
	 */
	public void makeTable_bottomLayout() 
	{
//		this.table_bottom.getTableHeader().setFont(new Font("Courier", Font.BOLD, 50));
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		for(int i = 0; i < 8; i++){
			table_bottom.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		this.table_bottom.setRowHeight(30);
		this.table_bottom.setBackground(null);
		this.table_bottom.setShowHorizontalLines(false);
		this.table_bottom.setShowVerticalLines(false);
	}
	
	/**
	 * Methode, die das Layout für die Schachbretbeschriftung am 
	 * linken Rand des Feldes erzeugt.
	 */
	public void makeTable_leftLayout() 
	{		
//		this.table_left.getTableHeader().setFont(new Font("Courier", Font.BOLD, 50));
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		
		for(int i = 0; i < 1; i++){
			table_left.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
		}
		
		this.table_left.setRowHeight(75);

		TableColumn col = null;
		for (int i = 0; i < 1; i++) {
			col = this.table_left.getColumnModel().getColumn(i);
			col.setPreferredWidth(30);
		}
		
		this.table_left.setBackground(null);
		this.table_left.setShowHorizontalLines(false);
		this.table_left.setShowVerticalLines(false);
	}
	
	/**
	 * Methode, die die Informationen über das aktuelle Schachfeld und 
	 * eventuelle Sonderfälle einholt.
	 */
	/*public void getCheckerboardInformation(Move move) 
	{
		// wer zieht wohin, geschmissen?, Sonderfall: PawnPromotion, Schach?, Schachmatt? 
		this.pawnPromotion = pawnPromotion
		if (this.pawnPromotion) {
			this.pawnPromotionGUI();
		}
	}*/
	
	/**
	 * Methode, die ein neues Objekt der Klasse PawnPromotionGUI erzeugt.
	 */
	public void pawnPromotionGUI() {
		PawnPromotionGUI pp = new PawnPromotionGUI("Bauernumwandlung", this);
	}
	
	/**
	 * Methode, die die Informationen über die getroffene Spielfigurenwahl
	 * des Spielers enthält.
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
	
	/**
	 * Getter für die boolean-Variable pawnPromotion, die angibt, ob
	 * ein solcher Sonderfall vorliegt.
	 * @return
	 */
	public boolean isPawnPromotion() 
	{
		return this.pawnPromotion;
	}

	/**
	 * Main-Methode
	 * @param args
	 */
	public static void main(String[] args)
	{
		Gui g = new Gui("Schach");
		System.out.println("Hallo");
		/*Checkerboard cb = new Checkerboard();
		
		for (int i = 0; i < 64; i++) {
			cb.fieldNumberConverter(i);
		}*/
	}	
}
