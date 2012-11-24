package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.InsetsUIResource;

public class MoveGUI extends JFrame implements ActionListener
{

	private static MoveGUI instance = null;	
	
	private JPanel 	forNormalMove = new JPanel(),
					forCastling = new JPanel(),
					forText_NM = new JPanel(),
					forText_C = new JPanel(),
					forExplanationtext = new JPanel(),
					forCoordinates = new JPanel(),
					forRadioButtons = new JPanel(),
					forButton_NM = new JPanel(),
					forButton_C = new JPanel();
	
	private JTextArea 	text_NM = new JTextArea(),
						text_C = new JTextArea(), 
						explanationtext = new JTextArea();
	
	private JLabel	from = new JLabel("Von Feld "),
					to = new JLabel("nach");
	
	private JTextField 	forFrom = new JTextField(3),
						forTo = new JTextField(3);
	
	private JRadioButton 	kingsideCastling = new JRadioButton("Kurze Rochade"), // kurze Rochade
							queensideCastling = new JRadioButton("Lange Rochade"); // lange Rochade
	
	private JButton okButton_NM = new JButton("Ok"),
					okButton_C = new JButton("Fertig");
	
	private boolean isKingsideCastling = false;
	
	private MoveGUI()
	{
		this.setTitle("Manuelle Zugeingabe");
		this.startWindow();
		this.makeLayout();	
	}
	
	public static MoveGUI getInstance()
	{
		if (instance == null) {
			instance = new MoveGUI();
		}
		return instance;
	}
	
	public void startWindow()
	{
		this.setIconImage(new ImageIcon("gui/gui/checkerboard.png").getImage());
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void makeLayout() 
	{
		this.toFront();
//		this.setAlwaysOnTop(true);
		
		UIManager.put("TabbedPane.contentOpaque", false);
		UIManager.put("TabbedPane.background", new Color(251, 225, 172, 100));
		UIManager.put("TabbedPane.selected", new Color(251, 225, 172));
//		UIManager.put("TabbedPane.contentBorderInsets", new InsetsUIResource(0, 0, 0, 0));
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setOpaque(false);

		this.setContentPane(new BackgroundPanel());
		this.getContentPane().add(tabbedPane);
		
		tabbedPane.setPreferredSize(new Dimension(294, 168));
		
		tabbedPane.addTab("Normaler Zug", this.forNormalMove);
		tabbedPane.addTab("Rochade", this.forCastling);		
		
		this.forCastling.setOpaque(false);
		this.forNormalMove.setOpaque(false);
		this.forText_NM.setOpaque(false);
		this.forText_C.setOpaque(false);
//		this.forCoordinates.setOpaque(false);
		this.forExplanationtext.setOpaque(false);
		this.forRadioButtons.setOpaque(false);
		this.forButton_NM.setOpaque(false);
		this.forButton_C.setOpaque(false);
		this.text_NM.setOpaque(false);
		this.explanationtext.setOpaque(false);
		this.text_C.setOpaque(false);
		this.from.setOpaque(false);
		this.to.setOpaque(false);
		
		this.makeNormalMove();
		this.makeCastling();
	}
	
	public void makeNormalMove()
	{
		this.forNormalMove.setLayout(new GridLayout(3, 1));
//		this.forNormalMove.setBackground(new Color(251, 225, 172));
		this.forNormalMove.add(this.forText_NM);
//		this.forNormalMove.add(this.forCoordinates);
		this.forNormalMove.add(this.forExplanationtext);
		this.forNormalMove.add(this.forButton_NM);
		
		this.forText_NM.add(this.text_NM);
		this.text_NM.setText("Bitte Zug eingeben!");
		this.text_NM.setEnabled(false);
		this.text_NM.setEditable(false);
		this.text_NM.setDisabledTextColor(Color.black);
		this.text_NM.setFont(new Font("Arial", Font.BOLD, 14));
		this.text_NM.setBorder(new EmptyBorder(10, 0, 0, 0));
		
		this.forExplanationtext.add(this.explanationtext);
		this.forExplanationtext.setBorder(new EmptyBorder(-5, 0, 0, 0));
		this.explanationtext.setText("1. Feld 'von' anklicken \n" +
				"2. Feld 'nach' anklicken");
		this.explanationtext.setEnabled(false);
		this.explanationtext.setEditable(false);
		this.explanationtext.setDisabledTextColor(Color.black);
		this.explanationtext.setFont(new Font("Arial", Font.PLAIN, 14));		
		
//		this.forCoordinates.add(this.from);
//		this.forCoordinates.add(this.forFrom);
//		this.forCoordinates.add(this.to);
//		this.forCoordinates.add(this.forTo);	
		
		this.forButton_NM.add(this.okButton_NM);
		this.okButton_NM.addActionListener(this);
		this.okButton_NM.setActionCommand("button_ok_NM");
		
	}
	
	public void makeCastling()
	{
		this.forCastling.setLayout(new GridLayout(3, 1));
//		this.forCastling.setBackground(new Color(251, 225, 172));
		this.forCastling.add(this.forText_C);
		this.forCastling.add(this.forRadioButtons);
		this.forCastling.add(this.forButton_C);
		
		this.forText_C.add(this.text_C);
		this.text_C.setText("Bitte Rochade ausw�hlen!");
		this.text_C.setEnabled(false);
		this.text_C.setEditable(false);
		this.text_C.setDisabledTextColor(Color.black);
		this.text_C.setFont(new Font("Arial", Font.BOLD, 14));
		this.text_C.setBorder(new EmptyBorder(10, 0, 0, 0));
		
		this.makeRadioButtons();
		
		this.forButton_C.add(this.okButton_C);
		this.okButton_C.setEnabled(false);
		this.okButton_C.addActionListener(this);
		this.okButton_C.setActionCommand("button_ok_C");

	}
	
	public void makeRadioButtons() {
		
		// um die Radibuttons zu gruppieren
		ButtonGroup group = new ButtonGroup();
		
		this.kingsideCastling.setBackground(new Color(140, 95, 70));
		this.queensideCastling.setBackground(new Color(140, 95, 70));
		
		group.add(this.kingsideCastling);
		this.kingsideCastling.addActionListener(this);
		this.kingsideCastling.setActionCommand("radiobutton_short");
		this.kingsideCastling.setOpaque(false);
		
		group.add(this.queensideCastling);
		this.queensideCastling.addActionListener(this);
		this.queensideCastling.setActionCommand("radiobutton_long");
		this.queensideCastling.setOpaque(false);
		
		this.forRadioButtons.add(this.kingsideCastling);
		this.forRadioButtons.add(this.queensideCastling);
	}
	
	public boolean isKingsideCastling()
	{
		return this.isKingsideCastling;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand() == "button_ok_NM") {
			this.setVisible(false);
			
		}

		if (this.kingsideCastling.isSelected() || this.queensideCastling.isSelected()) {
			this.okButton_C.setEnabled(true);
		}
		
		if (e.getActionCommand() == "button_ok_C") {
			this.isKingsideCastling = this.kingsideCastling.isSelected();
			this.setVisible(false);
			this.dispose();	
		}
		
	}
	
	
	public static void main(String[] args)
	{
		MoveGUI.getInstance();
	}

	
	
	
}
