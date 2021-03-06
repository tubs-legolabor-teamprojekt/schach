package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
 * Klasse, die ein kleines Fenster für die Spielfigurauswahl bei der
 * pawn-promotion darstellt.
 * 
 * @author Tabea
 * 
 */
public class PawnPromotionGUI extends JFrame implements ActionListener
{

    private static PawnPromotionGUI instance = null;

    private JPanel  forText = new JPanel(), 
                    forRadioButtons = new JPanel(),
                    forRadioButtonsLeft = new JPanel(),
                    forRadioButtonsRight = new JPanel(), 
                    forOkButton = new JPanel();

    private JTextArea text = new JTextArea("     In welche Figur soll der \nBauer umgewandelt werden?", 1, 1);

    private ImageIcon img = new ImageIcon("gui/gui/checkerboard.png");

    private JRadioButton    queen = new JRadioButton("Dame"),
                            bishop = new JRadioButton("Läufer"), 
                            knight = new JRadioButton("Springer"), 
                            rook = new JRadioButton("Turm");

    private JButton okButton = new JButton("OK");

    /**
     * Privater Konstruktor, der nur ein neues Objekt der Klasse
     * PawnPromotionGUI erstellt. Der Titel wird gesetzt und alle
     * Fenstereinstellung vorgenommen.
     */
    private PawnPromotionGUI() {
        this.setTitle("Bauernumwandlung");
        this.startWindow();
        this.makeLayout();
    }

    /**
     * Gibt die PawnPromotionGUI-Instanz zurück.
     * @return PawnPromotionGUI-Instanz
     */
    public static PawnPromotionGUI getInstance() {
        if (instance == null) {
            instance = new PawnPromotionGUI();
        }

        return instance;
    }

    /**
     * Methode, die die Einstellungen des Fensters setzt.
     */
    public void startWindow() {
        this.setIconImage(new ImageIcon("gui/gui/checkerboard.png").getImage());
        this.setSize(250, 200);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    /**
     * Methode, die das Layout des Fensters erstellt.
     */
    public void makeLayout() {
        this.setAlwaysOnTop(true);
        this.toFront();
        
        this.setContentPane(new BackgroundPanel());
        this.setLayout(new BorderLayout());

        this.makeRadioButtons();

        this.getContentPane().add(this.forText, BorderLayout.NORTH);
        this.add(this.forRadioButtons, BorderLayout.CENTER);
        this.add(this.forOkButton, BorderLayout.SOUTH);

        this.forText.setOpaque(false);
        this.forText.add(this.text);

        this.text.setOpaque(false);
        this.text.setFont(new Font("Arial", Font.BOLD, 13));
        this.text.setEditable(false);
        this.text.setEnabled(false);
        this.text.setDisabledTextColor(Color.black);

        this.forRadioButtons.setOpaque(false);

        this.forOkButton.setOpaque(false);
        this.forOkButton.add(this.okButton);

//        this.okButton.setEnabled(false);
        this.okButton.addActionListener(this);
        this.okButton.setActionCommand("button_ok");

        this.validate();
    }

    /**
     * Methode, die das Layout für die Anordnung der Radiobuttons erstellt.
     */
    public void makeRadioButtons() {

        // um die Radibuttons zu gruppieren
        ButtonGroup group = new ButtonGroup();

        this.queen.setBackground(new Color(251, 190, 172));
        this.bishop.setBackground(new Color(251, 190, 172));
        this.knight.setBackground(new Color(140, 95, 70));
        this.rook.setBackground(new Color(140, 95, 70));

        this.queen.setOpaque(false);
        this.bishop.setOpaque(false);
        this.knight.setOpaque(false);
        this.rook.setOpaque(false);

        // Dame-Radiobutton
        group.add(this.queen);
        this.queen.setSelected(true);
        this.queen.addActionListener(this);
        this.queen.setActionCommand("radiobutton_queen");

        // Läufer-Radiobutton
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

        this.forRadioButtons.setLayout(new GridLayout(1, 2));

        this.forRadioButtonsLeft.setLayout(new GridLayout(0, 1));
        this.forRadioButtonsLeft.setBorder(new EmptyBorder(0, 35, 0, 0));
        this.forRadioButtonsLeft.setOpaque(false);
        this.forRadioButtonsLeft.add(this.queen);
        this.forRadioButtonsLeft.add(this.bishop);

        this.forRadioButtonsRight.setLayout(new GridLayout(0, 1));
        this.forRadioButtonsRight.setBorder(new EmptyBorder(0, 5, 0, 0));
        this.forRadioButtonsRight.setOpaque(false);
        this.forRadioButtonsRight.add(this.knight);
        this.forRadioButtonsRight.add(this.rook);

        this.forRadioButtons.add(this.forRadioButtonsLeft);
        this.forRadioButtons.add(this.forRadioButtonsRight);
    }

    /**
     * Methode, die die vom Benutzer getroffene Auswahl speichert und nach
     * Bestätigung die Informationen an die Klasse Gui weitergibt.
     * @param e auslösendes Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // erst wenn ein Radiobutton ausgewählt wurde, darf man bestätigen
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

        // wenn OK geklickt wird, werden die Informationen weitergegeben
        if (e.getActionCommand() == "button_ok") {
            Checkerboard.getInstance().pawnPromotionInformation(this.queen.isSelected(),
                    this.bishop.isSelected(), this.knight.isSelected(),this.rook.isSelected());
            Checkerboard.getInstance().setPPIsReady(true);
            // Fenster schließen
            this.setVisible(false);
            this.dispose();
        }
    }
}
