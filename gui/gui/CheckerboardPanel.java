package gui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.Figure;

/**
 * Klasse, die eine Tabellenzelle darstellt. 
 * @author Tabea
 *
 */
public class CheckerboardPanel extends JPanel
{
	
	public JLabel label = new JLabel();
	
	/**
	 * Konstruktor, der eine neue Tabellenzelle erstellt und die
	 * entsprechenden Icons anzeigt. 
	 */
	public CheckerboardPanel() 
	{
		this.add(this.label);
		
//		ImageIcon icon = new ImageIcon("images/icons/king_black.png");
//		this.label.setIcon(icon);
//		icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
//		this.label.setVisible(true);
		
		
	}
	
	/**
	 * Methode, die das entsprechende Icon entweder anzeigen oder ausblenden soll. 
	 * @param figure
	 * @param show
	 */
	public void showIcon(Figure figure, boolean show)
	{
		this.label.setIcon(figure.getIcon());
		this.label.setVisible(show);
		
//		newLabel.setIcon(figure.getIcon());
//		figure.getIcon().setImage(figure.getIcon().getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
//		newLabel.setVisible(show);
//		
//		System.out.println(this.label);
		
//		System.out.println(this.label.getComponentCount());
		
//		this.repaint();
//		this.revalidate();
	}
	
	public void setIcon() 
	{
		this.label.setIcon(new ImageIcon("gui/gui/checkerboard.png"));
	}
}