package gui;

import game.Exporter;
import game.GameCoordinator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class ShowPGNFormat extends JFrame implements ActionListener
{

	private Gui g;
	
	private JPanel 	forText = new JPanel(), 
					forButton = new JPanel();
	
	private JTextArea text = new JTextArea(20, 30);
	
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
		this.setSize(450, 450);
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
		
		this.text.setBorder(new EmptyBorder(5, 10, 0, 0));
		this.text.setLineWrap(true);
		this.text.setEditable(false);
		this.text.setEnabled(false);
		this.text.setDisabledTextColor(Color.black);
		this.text.setText(Exporter.exportMovesToPGN("Teamprojekt", "Legolabor", "heute",
				"!Weiﬂer Spieler!", "Legoroboter", "1-0", GameCoordinator.getInstance(false).getAllMoves()));
		this.text.setCaretPosition(0);
	    JScrollPane scrollPane = new JScrollPane(this.text);
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
//		this.text.setOpaque(false);
		this.forText.setOpaque(false);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		this.forButton.setOpaque(false);
		
		this.text.setBackground(new Color(251, 225, 172));
		scrollPane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(41, 15, 5)));
		
	    this.forText.setBorder(new EmptyBorder(20, 0, 0, 0));
	    this.forText.add(scrollPane);
	   
		this.forButton.add(this.okButton);
		this.forButton.setBorder(new EmptyBorder(0, 0, 15, 0));
		this.okButton.addActionListener(this);
		this.okButton.setActionCommand("okButton");
		this.getRootPane().setDefaultButton(this.okButton);
		
		this.getContentPane().add(this.forText, BorderLayout.CENTER);
		this.getContentPane().add(this.forButton, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "okButton") {
			this.setVisible(false);
			this.dispose();
		}
	}
}
