package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

import javax.swing.JPanel;

/**
 * Klasse, die das Hintergrundbild für die Frames darstellt.
 * @author Tabea
 *
 */
public class BackgroundPanel extends JPanel
{
		
	private Image img;
	
	/**
	 * Konstruktor, der das Bild einfügt.
	 */
	public BackgroundPanel()
	{
		// Hintergrundbild
		img = getToolkit().createImage("gui/gui/background.jpg");

		MediaTracker mt = new MediaTracker(this);
		mt.addImage(img, 1);
		try {
			mt.waitForAll();
		} catch(InterruptedException e) {}
	}
	
	/**
	 * Methode, die das Bild zeichnet.
	 */
	@Override
	protected void paintComponent(Graphics g) 
	{
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
}
