package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * Klasse, die ein kleines Fenster für die Spielfigurauswahl bei der pawn-promotion darstellt.
 * @author Tabea
 *
 */
public class PawnPromotionGUI extends JFrame implements ActionListener
{

	private Gui g;	
	private Container c;
	private JPanel 	forText = new JPanel(),
					forRadioButtons = new JPanel(),
					forRadioButtonsLeft = new JPanel(),
					forRadioButtonsRight = new JPanel(),
					forOkButton = new JPanel();	
	private JTextArea text = new JTextArea("     In welche Figur soll der \nBauer umgewandelt werden?", 1, 1);
	private JRadioButton 	queen = new JRadioButton("Dame"),
							bishop = new JRadioButton("Läufer"),
							knight = new  JRadioButton("Springer"),
							rook = new JRadioButton("Turm");
	private JButton okButton = new JButton("OK");
	
	/**
	 * Konstruktor, der ein neues Objekt der Klasse PawnPromotionGUI erstellt und
	 * einen Titel und ein Gui-Objekt übergeben bekommt.
	 * @param title
	 * @param g
	 */
	public PawnPromotionGUI(String title, Gui g)
	{
		super(title);
		this.g = g;
		
		this.startWindow();
		this.makeLayout();
	}
	
	/**
	 * Methode, die die Einstellungen des Fensters setzt. 
	 */
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
		
		this.makeRadioButtons();
		
		this.c.add(this.forText, BorderLayout.NORTH);
		this.c.add(this.forRadioButtons, BorderLayout.CENTER);
		this.c.add(this.forOkButton, BorderLayout.SOUTH);
		
		this.forText.add(this.text);
		this.text.setBackground(null);
		this.text.setEditable(false);
		
		this.forOkButton.add(this.okButton);
		
		this.okButton.setEnabled(false);
		this.okButton.addActionListener(this);
		this.okButton.setActionCommand("button_ok");
	}
	
	/**
	 * Methode, die das Layout für die Anordnung der Radiobuttons erstellt. 
	 */
	public void makeRadioButtons() {
		
		ButtonGroup group = new ButtonGroup();
		
		// Dame-Radiobutton
		group.add(this.queen);
		this.queen.addActionListener(this);
		this.queen.setActionCommand("radiobutton_queen");
		
		// Laeufer-Radiobutton
		group.add(this.bishop);
		this.bishop.addActionListener(this);
		this.bishop.setActionCommand("radiobutton_bishop");
		
		// Springer-Radiobutton
		group.add(this.knight);
		this.knight.addActionListener(this);
		this.knight.setActionCommand("radiobutton_knight");
		
		// Turm-Radiobutton
		group.add(this.rook);
		this.rook.addActionListener(this);
		this.rook.setActionCommand("radiobutton_rook");
		
		this.forRadioButtons.setLayout(new GridLayout(1,2));
		
		this.forRadioButtonsLeft.setLayout(new GridLayout(0, 1));
		this.forRadioButtonsLeft.setBorder(new EmptyBorder(0, 35, 0, 0));
		this.forRadioButtonsLeft.add(this.queen);
		this.forRadioButtonsLeft.add(this.bishop);
		
		this.forRadioButtonsRight.setLayout(new GridLayout(0, 1));
		this.forRadioButtonsRight.setBorder(new EmptyBorder(0, 5, 0, 0));
		this.forRadioButtonsRight.add(this.knight);
		this.forRadioButtonsRight.add(this.rook);
		
		this.forRadioButtons.add(this.forRadioButtonsLeft);
		this.forRadioButtons.add(this.forRadioButtonsRight);	
	}
	
	/**
	 * Methode, die die vom Benutzer getroffene Auswahl speichert und 
	 * nach Bestätigung die Informationen an die Klasse Gui weitergibt.
	 * @param e
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand() == "radiobutton_queen") {
			this.okButton.setEnabled(true);
		}
		if (e.getActionCommand() == "radiobutton_bishop") {
			this.okButton.setEnabled(true);
		}
		if (e.getActionCommand() == "radiobutton_knight") {
			this.okButton.setEnabled(true);
		}
		if (e.getActionCommand() == "radiobutton_rook") {
			this.okButton.setEnabled(true);
		}
		if (e.getActionCommand() == "button_ok") {
			this.g.pawnPromotionInformation(this.queen.isSelected(), 
					this.bishop.isSelected(), this.knight.isSelected(), 
					this.rook.isSelected());
			this.setVisible(false);
			this.dispose();	
		}
	}
}
