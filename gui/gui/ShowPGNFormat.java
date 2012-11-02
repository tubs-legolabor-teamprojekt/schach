package gui;

import game.Exporter;
import game.GameCoordinator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class ShowPGNFormat extends JFrame implements ActionListener
{

	private Gui g;
	
	private JPanel 	forText = new JPanel(), 
					forButton = new JPanel();
	
	private JTextArea text = new JTextArea();
	
	
	
	private JButton okButton = new JButton("Ok");
	
	
	public ShowPGNFormat(Gui g) 
	{
		this.g = g;
		this.setTitle("PGN-Format");
		this.initWindow();
		this.makeLayout();
	}
	
	/**
	 * Methode, die die Einstellungen des Fensters initialisiert.
	 */
	public void initWindow()
	{
		this.setIconImage(new ImageIcon("gui/gui/checkerboard.png").getImage());
		this.setSize(200, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Methode, die das gesamt FensterLayout erstellt.
	 */
	public void makeLayout() 
	{
		this.setContentPane(new BackgroundPanel());
		this.setLayout(new BorderLayout());		
		
//		this.forText.add(this.text);
//		this.text.setOpaque(false);
		this.text.setEditable(false);
		this.text.setEnabled(false);
		this.text.setDisabledTextColor(Color.black);
		this.text.setText(Exporter.exportMovesToPGN("lalala", "djslaf", "fsa",
				"klasnf", "Legoroboter", "1-0", GameCoordinator.getInstance(false).getAllMoves()));
		
		JScrollPane scrollPane = new JScrollPane(this.text, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
	            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.forText.add(scrollPane);
		scrollPane.repaint();
		scrollPane.validate();
		
		this.forButton.add(this.okButton);
		this.okButton.addActionListener(this);
		this.okButton.setActionCommand("okButton");
		this.getRootPane().setDefaultButton(this.okButton);
		
		this.getContentPane().add(this.forText, BorderLayout.CENTER);
		this.getContentPane().add(this.forButton, BorderLayout.SOUTH);
		
		this.forText.setOpaque(false);
		this.forButton.setOpaque(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "okButton") {
			this.setVisible(false);
			this.dispose();
		}
	}
}
