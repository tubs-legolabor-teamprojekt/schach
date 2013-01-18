package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import components.Field;

/**
 * Klasse, die das Fenster repr�sentiert, wenn ein Spiel beendet wurde.
 * 
 * @author Tabea
 * 
 */
public class FinishedGameGUI extends JFrame implements ActionListener {

    private static FinishedGameGUI instance = null;

    private GridBagLayout gbl = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    private JPanel forText = new JPanel();

    private JTextArea text = new JTextArea(1, 1), startExplanation = new JTextArea(1, 1), showExplanation = new JTextArea(1, 1),
            exportExplanation = new JTextArea(1, 1), userExplanation = new JTextArea(1, 1), endExplanation = new JTextArea(1, 1);

    private JButton startButton = new JButton("neues Spiel"), showButton = new JButton("Spielverlauf"), exportButton = new JButton("Exportieren"),
            userButton = new JButton("Benutzer wechseln"), endButton = new JButton("Beenden");

    private int counter = 0;

    /**
     * privater Konstruktor, der nur ein neues Objekt der Klasse erstellt, den
     * Titel setzt und alle Fenstereinstellungen aufruft.
     */
    private FinishedGameGUI() {
        this.setTitle("Spiel vorbei");
        this.initWindow();
        this.makeLayout();
    }

    /**
     * Gibt die FinishedGameGUI-Instanz zur�ck.
     * 
     * @return FinishedGameGUI-Instanz
     */
    public static FinishedGameGUI getInstance() {
        if (instance == null) {
            instance = new FinishedGameGUI();
        }

        return instance;
    }

    /**
     * Methode, die die Einstellungen des Fensters initialisiert.
     */
    public void initWindow() {
        this.setIconImage(new ImageIcon("gui/gui/checkerboard.png").getImage());
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Methode, die das gesamt FensterLayout erstellt.
     */
    public void makeLayout() {
        this.setContentPane(new BackgroundPanel());
        this.setLayout(gbl);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        this.makeTextLayout();
        this.makeStartLayout();
        this.makeShowLayout();
        this.makeExportLayout();
        this.makeUserLayout();
        this.makeEndLayout();
    }

    /**
     * Methode, die das Layout f�r das TextPanel erstellt.
     */
    public void makeTextLayout() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbl.setConstraints(this.forText, gbc);
        this.getContentPane().add(this.forText);

        this.forText.setOpaque(false);
        this.forText.add(this.text);
        this.text.setText("Was m�chten Sie machen?");
        this.text.setOpaque(false);
        this.text.setEditable(false);
        this.text.setEnabled(false);
        this.text.setDisabledTextColor(Color.black);
        this.text.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * Methode, die das Layout f�r das StartPanel erstellt.
     */
    public void makeStartLayout() {
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbl.setConstraints(this.startButton, gbc);
        this.getContentPane().add(this.startButton);
        this.startButton.addActionListener(this);
        this.startButton.setActionCommand("startButton");

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        this.startExplanation.setText("Beginnt ein neues Spiel.");
        gbl.setConstraints(this.startExplanation, gbc);
        this.getContentPane().add(this.startExplanation);
        this.startExplanation.setOpaque(false);
        this.startExplanation.setEditable(false);
        this.startExplanation.setEnabled(false);
        this.startExplanation.setDisabledTextColor(Color.black);
    }

    /**
     * Methode, die das Layout f�r das ShowPanel erstellt.
     */
    public void makeShowLayout() {
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbl.setConstraints(this.showButton, gbc);
        this.getContentPane().add(this.showButton);
        this.showButton.addActionListener(this);
        this.showButton.setActionCommand("showButton");

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        this.showExplanation.setText("Gibt das Spiel im pgn-Format aus.");
        gbl.setConstraints(this.showExplanation, gbc);
        this.getContentPane().add(this.showExplanation);
        this.showExplanation.setOpaque(false);
        this.showExplanation.setEditable(false);
        this.showExplanation.setEnabled(false);
        this.showExplanation.setDisabledTextColor(Color.black);
    }

    /**
     * Methode, die das Layout f�r das ExportPanel erstellt.
     */
    public void makeExportLayout() {
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbl.setConstraints(this.exportButton, gbc);
        this.getContentPane().add(this.exportButton);
        this.exportButton.addActionListener(this);
        this.exportButton.setActionCommand("exportButton");

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        this.exportExplanation.setText("Speichert das Spiel im png-Format ab.");
        gbl.setConstraints(this.exportExplanation, gbc);
        this.getContentPane().add(this.exportExplanation);
        this.exportExplanation.setOpaque(false);
        this.exportExplanation.setEditable(false);
        this.exportExplanation.setEnabled(false);
        this.exportExplanation.setDisabledTextColor(Color.black);
    }

    /**
     * Methode, die das Layout f�r das UserPanel erstellt.
     */
    public void makeUserLayout() {
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbl.setConstraints(this.userButton, gbc);
        this.getContentPane().add(this.userButton);
        this.userButton.addActionListener(this);
        this.userButton.setActionCommand("userButton");

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        this.userExplanation.setText("Wechselt den Benutzer.");
        gbl.setConstraints(this.userExplanation, gbc);
        this.getContentPane().add(this.userExplanation);
        this.userExplanation.setOpaque(false);
        this.userExplanation.setEditable(false);
        this.userExplanation.setEnabled(false);
        this.userExplanation.setDisabledTextColor(Color.black);
    }

    /**
     * Methode, die das Layout f�r das EndPanel erstellt.
     */
    public void makeEndLayout() {
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbl.setConstraints(this.endButton, gbc);
        this.getContentPane().add(this.endButton);
        this.endButton.addActionListener(this);
        this.endButton.setActionCommand("endButton");

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        this.endExplanation.setText("Beendet das Programm.");
        gbl.setConstraints(this.endExplanation, gbc);
        this.getContentPane().add(this.endExplanation);
        this.endExplanation.setOpaque(false);
        this.endExplanation.setEditable(false);
        this.endExplanation.setEnabled(false);
        this.endExplanation.setDisabledTextColor(Color.black);
    }

    /**
     * Methode, die entsprechend auf die ausgef�hrten Aktionen des Benutzers
     * reagiert.
     * 
     * @param e
     *            ausl�sendes ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "startButton") {
            // aktuelle Schachbrett wird auf das Ausgangsfeld zur�ckgesetzt
            Field f = Field.getInstance();
            f.resetField();
            Gui.getInstance().getCheckerboard().resetMap();
            Gui.getInstance().getCheckerboard().getStartMap(f.getCurrentFieldAsHashMap());
            // dieses Fenster wird geschlossen
            this.setVisible(false);
            this.dispose();
        }
        if (e.getActionCommand() == "showButton") {
            // zeigt das aktuelle Spiel im PGN-Format ab
            ShowPGNFormat.getInstance();
        }
        if (e.getActionCommand() == "exportButton") {
            this.counter++;
            if (this.counter == 1) {
                StAXWriter s = new StAXWriter();
                if (s.makeFile()) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Das Spiel wurde erfolgreich " + "in den Order 'saveGame' gespeichert.", "Gespeichert!",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Das Spiel konnte nicht " + "gespeichert werden!", "Fehler",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Das Spiel wurde bereits " + "gespeichert!", "Fehler", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (e.getActionCommand() == "userButton") {
            Field f = Field.getInstance();
            f.resetField();
            Gui.getInstance().getCheckerboard().resetMap();
            Gui.getInstance().getCheckerboard().getStartMap(f.getCurrentFieldAsHashMap());
            // dieses Fenster wird geschlossen
            Gui.getInstance().setVisible(false);
            Gui.getInstance().dispose();
            this.setVisible(false);
            this.dispose();
            StartWindow.getInstance().reset();
            /*
             * hier soll der Benutzername ge�ndert werden
             */
        }
        if (e.getActionCommand() == "endButton") {
            // alle noch ge�ffneten Fenster werden geschlossen
            Gui.getInstance().setVisible(false);
            Gui.getInstance().dispose();
            this.setVisible(false);
            this.dispose();
        }
    }
}
