package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Klasse, die das allererste Fenster repräsentiert.
 * @author Tabea
 *
 */
public class StartWindow extends JFrame implements ActionListener
{
	
	private Gui g;
	
	private JPanel	forIcon = new JPanel(),
					forText = new JPanel(),
					forButton = new JPanel();
	
	private JLabel iconLabel = new JLabel();
	private ImageIcon icon = new ImageIcon("gui/gui/checkerboard.png");
	
	private JTextArea text = new JTextArea(2, 1);
	
	private JButton startButton = new JButton("Start");
	
	/**
	 * Konstruktor, der ein Gui-Objekt übergeben bekommt, dieses setzt,
	 * selbst ein neues StartWindow-Objekt erstellt, den Titel setzt und alle
	 * erforderlichen Einstellungen unternimmt.
	 * @param g
	 */
	public StartWindow(Gui g) 
	{
		this.setTitle("Wllkommen");
		this.g = g;
		this.initWindow();
		this.makeLayout();
	}
	
	/**
	 * Methode, die die Einstellungen des Fensters initialisiert.
	 */
	public void initWindow()
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
		// BackgroundPanel als Hintergrund
		this.setContentPane(new BackgroundPanel());
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(2, 2, 2, 2); 
		
		gbc.gridx = 0;
		gbc.gridy = 0;  
		gbc.gridheight = 1;  
		gbl.setConstraints(this.forIcon, gbc);
		this.getContentPane().add(this.forIcon);
		// wird auf nicht deckend gesetzt, damit 
		// das Hintergrundbild noch zu sehen ist
		this.forIcon.setOpaque(false);		
		
		gbc.gridx = 1; 
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbl.setConstraints(this.forText, gbc);
		this.getContentPane().add(this.forText);
		this.forText.setOpaque(false);
		
		gbc.gridx = 0; 
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		gbl.setConstraints(this.forButton, gbc);
		this.getContentPane().add(this.forButton);
		this.forButton.setOpaque(false);
		
		this.forIcon.add(this.iconLabel);
		this.iconLabel.setOpaque(false);
		this.icon.setImage(icon.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
		this.iconLabel.setIcon(this.icon);
		this.iconLabel.setVisible(true);
		
		this.forText.add(this.text);
		this.text.setOpaque(false);
		this.text.setEditable(false);
//		this.text.setBackground(new Color(41, 15, 5, 100));
		this.text.setText("Klicken Sie auf den \nStartbutton, um ein \nneues Spiel zu \nbeginnen.");
		this.text.setFont(new Font("Arial", Font.PLAIN, 13));
		
		this.forButton.add(this.startButton);
		this.startButton.addActionListener(this);
		this.startButton.setActionCommand("startButton");
		
		this.validate();
	}

	/**
	 * Methode, die auf eine Aktion des Benutzers wartet, um dann 
	 * entsprechend zu reagieren.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand() == "startButton") {
			// dieses Fenster wird geschlossen und das Schachfeld angezeigt
			this.g.startWindow();
			this.setVisible(false);
			this.dispose();	
		}
	}	
}
