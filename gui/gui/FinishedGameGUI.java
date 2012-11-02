package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import components.Field;

/**
 * Klasse, die das Fenster repräsentiert, wenn ein Spiel beendet wurde.
 * @author Tabea
 *
 */
public class FinishedGameGUI extends JFrame implements ActionListener
{

	private Gui g;
	
	private JPanel 	forText = new JPanel(),
					forStart = new JPanel(),
					forShow = new JPanel(),
					forExport = new JPanel(),
					forEnd = new JPanel();
	
	private JTextArea 	text = new JTextArea(2, 1),
						startExplanation = new JTextArea(1, 1),
						showExplanation = new JTextArea(1, 1),
						exportExplanation = new JTextArea(1, 1),
						endExplanation = new JTextArea(1, 1);
	
	private JButton startButton = new JButton("Start"),
					showButton = new JButton("Spielverlauf"),
					exportButton = new JButton("Exportieren"),
					endButton = new JButton("Beenden");
	
	/**
	 * Konstruktor, der ein neues Objekt der Klasse erstellt,
	 * das übregebene Gui-Objekt setzt und die Fenstereinstellungen
	 * alle aufruft.
	 * @param g
	 */
	public FinishedGameGUI(Gui g) 
	{
		this.g = g;
		this.setTitle("Spiel vorbei");
		this.initWindow();
		this.makeLayout();
	}
	
	/**
	 * Methode, die die Einstellungen des Fensters initialisiert.
	 */
	public void initWindow()
	{
		this.setIconImage(new ImageIcon("gui/gui/checkerboard.png").getImage());
		this.setSize(550, 300);
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
		this.setLayout(new GridLayout(5, 1));
		
		this.getContentPane().add(this.forText);
		this.getContentPane().add(this.forStart);
		this.getContentPane().add(this.forShow);
		this.getContentPane().add(this.forExport);
		this.getContentPane().add(this.forEnd);
		
		this.forText.setOpaque(false);
		this.forStart.setOpaque(false);
		this.forShow.setOpaque(false);
		this.forExport.setOpaque(false);
		this.forEnd.setOpaque(false);
		
		this.forText.add(this.text);
		this.text.setText("\nWas möchten Sie machen?");
		this.text.setOpaque(false);
		this.text.setEditable(false);
		this.text.setEnabled(false);
		this.text.setDisabledTextColor(Color.black);
		this.text.setFont(new Font("Arial", Font.BOLD, 14));	
		
		this.makeForStartLayout();
		this.makeForShowLayout();
		this.makeForExportLayout();
		this.makeForEndLayout();
	}
	
	/**
	 * Methode, die das Layout für das StartPanel erstellt.
	 */
	public void makeForStartLayout() 
	{
		GridBagLayout gbl = new GridBagLayout();
		this.forStart.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(2, 2, 2, 2); 

		gbc.gridx = 0;
		gbc.gridy = 0;  
		gbc.gridheight = 1; 
		gbl.setConstraints(this.startButton, gbc);
		this.forStart.add(this.startButton);
		this.startButton.addActionListener(this);
		this.startButton.setActionCommand("startButton");
		
		gbc.gridx = 1;
		gbc.gridy = 0;  
		gbc.gridheight = 1; 
		gbc.gridwidth = 2;
		this.startExplanation.setText("Beginnt ein neues Spiel.");
		gbl.setConstraints(this.startExplanation, gbc);
		this.forStart.add(this.startExplanation);
		this.startExplanation.setOpaque(false);
		this.startExplanation.setEditable(false);
		this.startExplanation.setEnabled(false);
		this.startExplanation.setDisabledTextColor(Color.black);
	}
	
	/**
	 * Methode, die das Layout für das ShowPanel erstellt.
	 */
	public void makeForShowLayout() 
	{
		GridBagLayout gbl = new GridBagLayout();
		this.forShow.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(2, 2, 2, 2); 

		gbc.gridx = 0;
		gbc.gridy = 0;  
		gbc.gridheight = 1;  
		gbl.setConstraints(this.showButton, gbc);
		this.forShow.add(this.showButton);
		this.showButton.addActionListener(this);
		this.showButton.setActionCommand("showButton");
		
		gbc.gridx = 1;
		gbc.gridy = 0;  
		gbc.gridheight = 1; 
		gbc.gridwidth = 2;
		this.showExplanation.setText("Gibt das Spiel im pgn-Format aus.");
		gbl.setConstraints(this.showExplanation, gbc);
		this.forShow.add(this.showExplanation);
		this.showExplanation.setOpaque(false);
		this.showExplanation.setEditable(false);
		this.showExplanation.setEnabled(false);
		this.showExplanation.setDisabledTextColor(Color.black);
	}
	
	/**
	 * Methode, die das Layout für das ExportPanel erstellt.
	 */
	public void makeForExportLayout() 
	{
		GridBagLayout gbl = new GridBagLayout();
		this.forExport.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(2, 2, 2, 2); 

		gbc.gridx = 0;
		gbc.gridy = 0;  
		gbc.gridheight = 1;  
		gbl.setConstraints(this.exportButton, gbc);
		this.forExport.add(this.exportButton);
		this.exportButton.addActionListener(this);
		this.exportButton.setActionCommand("exportButton");
		
		gbc.gridx = 1;
		gbc.gridy = 0;  
		gbc.gridheight = 1; 
		gbc.gridwidth = 2;
		this.exportExplanation.setText("Speichert das Spiel im png-Format ab.");
		gbl.setConstraints(this.exportExplanation, gbc);
		this.forExport.add(this.exportExplanation);
		this.exportExplanation.setOpaque(false);
		this.exportExplanation.setEditable(false);
		this.exportExplanation.setEnabled(false);
		this.exportExplanation.setDisabledTextColor(Color.black);
	}
	
	/**
	 * Methode, die das Layout für das EndPanel erstellt.
	 */
	public void makeForEndLayout() 
	{
		GridBagLayout gbl = new GridBagLayout();
		this.forEnd.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(2, 5, 2, 5); 

		gbc.gridx = 0;
		gbc.gridy = 0;  
		gbc.gridheight = 1;  
		gbl.setConstraints(this.endButton, gbc);
		this.forEnd.add(this.endButton);
		this.endButton.addActionListener(this);
		this.endButton.setActionCommand("endButton");
		
		gbc.gridx = 1;
		gbc.gridy = 0;  
		gbc.gridheight = 1; 
		gbc.gridwidth = 2;
		this.endExplanation.setText("Beendet das Programm.");
		gbl.setConstraints(this.endExplanation, gbc);
		this.forEnd.add(this.endExplanation);
		this.endExplanation.setOpaque(false);
		this.endExplanation.setEditable(false);
		this.endExplanation.setEnabled(false);
		this.endExplanation.setDisabledTextColor(Color.black);
	}

	/**
	 * Methode, die entsprechend auf die ausgeführten Aktionen
	 * des Benutzers reagiert. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "startButton") {
			// aktuelle Schachbrett wird auf das Ausgangsfeld zurückgesetzt
			Field f = Field.getInstance();
			f.resetField();
			this.g.getCheckerboard().resetMap();
			this.g.getCheckerboard().getStartMap(f.getCurrentFieldAsHashMap());
			// dieses Fenster wird geschlossen
			this.setVisible(false);
			this.dispose();
		}
		if (e.getActionCommand() == "showButton") {
			ShowPGNFormat pgn = new ShowPGNFormat(this.g);
			System.out.println("hallo");
			/*
			 * hier soll ein neues Fenster geöffnet werden, dass den genazen Text anzeigt
			 */
		}
		if (e.getActionCommand() == "exportButton") {
			System.out.println("hallöchen");
			/*
			 * hier soll eine Methode aufgerufen werden, die den Spielverlauf abspeichert
			 * Benachrichtigung, ob erfolgreich gespeichert --> Dialog-Fenster
			 * neues Fenster, was danach geschiet
			 */
		}
		if (e.getActionCommand() == "endButton") {
			// alle noch geöffneten Fenster werden geschlossen
			this.g.setVisible(false);
			this.g.dispose();
			this.setVisible(false);
			this.dispose();
		}
	}
}
