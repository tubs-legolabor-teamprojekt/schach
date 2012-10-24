package gui;

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
	
	private JLabel label = new JLabel();
	
	/**
	 * Konstruktor, der eine neue Tabellenzelle erstellt und die
	 * entsprechenden Icons anzeigt. 
	 */
	public CheckerboardPanel() 
	{
		this.add(this.label);
//		this.label.setIcon(new ImageIcon("gui/gui/checkerboard.png"));
//		this.label.setVisible(true);

	}
	
	/**
	 * Methode, die das entsprechende Icon entweder anzeigen oder ausblenden soll. 
	 * @param figure
	 * @param show
	 */
	public void showIcon(Figure figure, boolean show)
	{

//		this.label.setIcon(figure.getIcon());
//		this.label.setVisible(show);
//		
//		System.out.println(this.label.getComponentCount());
		
		this.repaint();
		this.revalidate();
	}
}