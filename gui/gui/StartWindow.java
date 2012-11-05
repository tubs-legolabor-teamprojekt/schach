package gui;

import java.awt.Color;
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
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Klasse, die das allererste Fenster repräsentiert.
 * 
 * @author Tabea
 * 
 */
public class StartWindow extends JFrame implements ActionListener
{

	private static StartWindow instance = null;

	private JPanel forIcon = new JPanel(), forText = new JPanel(),
			forName = new JPanel(), forButton = new JPanel();

	private JLabel iconLabel = new JLabel();
	private ImageIcon icon = new ImageIcon("gui/gui/checkerboard.png");

	private JTextArea text = new JTextArea(2, 1);

	private JTextField name = new JTextField("Benutzername", 20);
	private int counter = 0;
	private String username;

	private JButton startButton = new JButton("Start");

	/**
	 * Privater Konstruktor, der nur ein neues StartWindow-Objekt erstellt, 
	 * den Titel setzt und alle erforderlichen Einstellungen unternimmt.
	 */
	private StartWindow() 
	{
		this.setTitle("Willkommen");
		this.initWindow();
		this.makeLayout();
	}
	
	/**
	 * Gibt die StartWindow-Instanz zurück.
	 * @return SatrtWindow-Instanz
	 */
	public static StartWindow getInstance() 
	{
		if (instance == null) {
			instance = new StartWindow();
		}
		
		return instance;
	}

	/**
	 * Methode, die die Einstellungen des Fensters initialisiert.
	 */
	public void initWindow() 
	{
		this.setIconImage(new ImageIcon("gui/gui/checkerboard.png").getImage());
		this.setSize(300, 250);
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
		gbl.setConstraints(this.forName, gbc);
		this.getContentPane().add(this.forName);
		this.forName.setOpaque(false);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		gbl.setConstraints(this.forButton, gbc);
		this.getContentPane().add(this.forButton);
		this.forButton.setOpaque(false);

		this.forIcon.add(this.iconLabel);
		this.iconLabel.setOpaque(false);
		this.icon.setImage(icon.getImage().getScaledInstance(70, 70,
				Image.SCALE_DEFAULT));
		this.iconLabel.setIcon(this.icon);
		this.iconLabel.setVisible(true);

		this.forText.add(this.text);
		this.text.setOpaque(false);
		this.text.setEditable(false);
		this.text.setEnabled(false);
		this.text.setDisabledTextColor(Color.black);
		// this.text.setBackground(new Color(41, 15, 5, 100));
		this.text
				.setText("Benutzernamen eingeben und \nein neues Spiel starten.");
		this.text.setFont(new Font("Arial", Font.PLAIN, 13));

		this.forName.add(this.name);
		this.name.setFont(new Font("Arial", Font.ITALIC, 12));
		this.name.setForeground(Color.LIGHT_GRAY);
		this.name.getDocument().addDocumentListener(new MyDocListener());
		this.name.addFocusListener(new java.awt.event.FocusAdapter()
		{
			public void focusGained(java.awt.event.FocusEvent evt) 
			{
				if (counter == 0) {
					name.setText("");
				}
				name.setFont(new Font("Arial", Font.PLAIN, 12));
				name.setForeground(Color.BLACK);
				counter++;
				if (counter > 0) {
					checkFieldsFull();
				}
				// ((javax.swing.JTextField) evt.getComponent()).setText("");
			}
		});

		this.forButton.add(this.startButton);
		this.startButton.setEnabled(false);
		this.startButton.addActionListener(this);
		this.startButton.setActionCommand("startButton");
		// damit man mit Enter bestätigen kann
		this.getRootPane().setDefaultButton(this.startButton);

		this.validate();
	}

	/**
	 * Methode, die prüft, ob der Benutzer etwas in das Textfeld 
	 * geschrieben hat. Wenn ja, wird diese Eingabe als Benutzername
	 * abgespeichert. 
	 */
	private void checkFieldsFull() 
	{
		if (this.name.getText().length() == 0) {
			this.startButton.setEnabled(false);
			return;
		}
		this.startButton.setEnabled(true);
		this.username = this.name.getText();
	}
	
	/**
	 * Gibt den Benutzernamen zurück.
	 * @return Benutzername
	 */
	public String getUsername() 
	{
		return this.username;
	}
	
	/**
	 * Methode, die eine neue Instanz der Klasse erstellt. 
	 */
	public void reset()
	{
		instance = null;
		instance = new StartWindow();
	}
	
	/**
	 * Methode, die auf eine Aktion des Benutzers wartet, um dann entsprechend
	 * zu reagieren.
	 * @param e auslösendes ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand() == "startButton") {
			// dieses Fenster wird geschlossen und das Schachfeld angezeigt
			Gui.getInstance().startWindow();
			this.setVisible(false);
			this.dispose();
		}
	}

	/**
	 * Private Klasse, die entsprechend auf Vorgänge im Textfeld reagiert.
	 * @author Tabea
	 *
	 */
	private class MyDocListener implements DocumentListener
	{
		public void changedUpdate(DocumentEvent e) {
			checkFieldsFull();
		}

		public void insertUpdate(DocumentEvent e) {
			checkFieldsFull();
		}

		public void removeUpdate(DocumentEvent e) {
			checkFieldsFull();
		}
	}
}
