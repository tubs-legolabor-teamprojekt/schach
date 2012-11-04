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
		this.text.setText(Exporter.exportMovesToPGN("lalala", "djslaf", "fsa",
				"klasnf", "Legoroboter", "1-0", GameCoordinator.getInstance(false).getAllMoves()) + " heakj" +
				"\ncy\nx lksdj kc\nnm,kdj" + "jksdh k\nsdsajd ks\n+dfjaslj cjhsd a dl\nkda dask\nd mo" +
				" an \nclsa\nn cp\nas idv aed \nnoaskj  faskj mna\nsdk mn\ndaksh f,c ld jdf" + 
				"jksd \nhfkdsjf kjdf  \nkdsflas\npüeda , spdf sdj\nkfpasdf  .sdff o\niasf ");
		this.text.setCaretPosition(0);
	    JScrollPane scrollingArea = new JScrollPane(this.text);
//	    scrollingArea.setOpaque(false);
	    scrollingArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollingArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	 
		this.text.setOpaque(false);
		this.forText.setOpaque(false);
		scrollingArea.getViewport().setOpaque(false);
		this.forButton.setOpaque(false);
		
	    this.forText.setBorder(new EmptyBorder(20, 0, -20, 0));
	    this.forText.add(scrollingArea);
	   
		this.forButton.add(this.okButton);
		this.forButton.setBorder(new EmptyBorder(0, 0, 20, 0));
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
