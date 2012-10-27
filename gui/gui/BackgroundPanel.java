package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel
{
		
	private Image img;
	private BackgroundPanel panel;
	
	public BackgroundPanel(){
	
		img = getToolkit().createImage("gui/gui/background.jpg");

		MediaTracker mt = new MediaTracker(this);
		mt.addImage(img, 1);
		try {
			mt.waitForAll();
		} catch(InterruptedException e) {}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}

}
