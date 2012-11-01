package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartWindow extends JFrame implements ActionListener
{
	
	private Gui g;
	private Container c;
	private JButton start = new JButton("Start");
	
	private JPanel 	top = new JPanel(),
			bottom = new JPanel(),
			left = new JPanel(),
			right = new JPanel(),
			center = new JPanel();
	
	public StartWindow(String title, Gui g) {
		super(title);
		this.g = g;
		this.startWindow();
		this.makeLayout();
	}
	
	public void startWindow()
	{
		this.setIconImage(new ImageIcon("gui/gui/checkerboard.png").getImage());
		this.setSize(250, 200);
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
		this.c = getContentPane();
		this.c.setLayout(new BorderLayout());
		
//		this.c.add(this.top, BorderLayout.NORTH);
//		this.c.add(this.bottom, BorderLayout.SOUTH);
//		this.c.add(this.left, BorderLayout.WEST);
//		this.c.add(this.right, BorderLayout.EAST);
		this.c.add(this.center, BorderLayout.CENTER);
		
//		this.top.setBackground(Color.green);
//		this.bottom.setBackground(Color.yellow);
//		this.left.setBackground(Color.red);
//		this.right.setBackground(Color.blue);
//		this.center.setBackground(Color.pink);
		
		this.center.add(this.start);
		this.start.addActionListener(this);
		this.start.setActionCommand("start_button");
		this.validate();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "start_button") {
			this.g.startWindow();
			this.setVisible(false);
			this.dispose();	
		}
	}
	
}
