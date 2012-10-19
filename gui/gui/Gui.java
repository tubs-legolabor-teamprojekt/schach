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

public class Gui extends JFrame
{

	private Container c;
	private JPanel 	top = new JPanel(),
					bottom = new JPanel(),
					left = new JPanel(),
					right = new JPanel();
	private Checkerboard checkerboard = new Checkerboard();
	
	private String[] columnNames = {"a", "b", "c", "d", "e", "f", "g", "h"};
	private String[][] rowData = {{"a", "b", "c", "d", "e", "f", "g", "h"}};
	private JTable tableTB = new JTable(rowData, columnNames);
	
	/**
	 * Konstruktor, der ein neues Fenster erstellt und 
	 * Setting und Layout festlegt.
	 * @param title
	 */
	public Gui(String title)
	{
		this.startWindow();
		this.makeLayout();
	}
	
	/**
	 * Settings 
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
	
	public void makeLayout() 
	{
		this.c = getContentPane();
		this.c.setLayout(new BorderLayout());
		
		this.c.add(top, BorderLayout.NORTH);
		this.top.setBorder(new EmptyBorder(25, 25, 25, 25));
//		this.top.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.makeTableTBlayout();		
//		this.top.add(tableTB);
		
		this.c.add(bottom, BorderLayout.SOUTH);
		this.bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.bottom.add(tableTB);
		
		this.c.add(left, BorderLayout.WEST);
		this.left.setBorder(new EmptyBorder(25, 25, 25, 25));
		
		this.c.add(right, BorderLayout.EAST);
		this.right.setBorder(new EmptyBorder(25, 25, 25, 25));
		
		this.c.add(checkerboard, BorderLayout.CENTER);
		this.checkerboard.setBorder(new EmptyBorder(-4, 0, 0, 0));
		
//		this.top.setBackground(Color.blue);
//		this.bottom.setBackground(Color.red);
//		this.left.setBackground(Color.yellow);
//		this.right.setBackground(Color.green);
//		this.checkerboard.setBackground(Color.orange);
		
	}
	
	public void makeTableTBlayout() 
	{
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		for(int i = 0; i < 8; i++){
			tableTB.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		this.tableTB.setRowHeight(30);
		this.tableTB.setBackground(null);
		this.tableTB.setShowHorizontalLines(false);
		this.tableTB.setShowVerticalLines(false);
	}
	
	public static void main(String[] args)
	{
		Gui g = new Gui("Schach");
	}

}
