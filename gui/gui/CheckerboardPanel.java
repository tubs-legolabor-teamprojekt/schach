package gui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CheckerboardPanel extends JPanel
{
	
	private JLabel label = new JLabel();
	
	public CheckerboardPanel() 
	{
		
		this.add(this.label);
		this.label.setIcon(new ImageIcon("gui/gui/checkerboard.png"));
		this.label.setVisible(true);

	}
}
