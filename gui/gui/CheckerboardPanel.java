package gui;

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
	}
	
	/**
	 * Methode, die das entsprechende Icon entweder anzeigen oder ausblenden soll. 
	 * @param figure
	 * @param show
	 */
	public void showIcon(Figure figure, boolean show)
	{
		this.label.setIcon(figure.getIcon());
//		this.label.setBorder(new EmptyBorder(-3, 0, 0, 0));
		this.label.setVisible(show);
	}
}