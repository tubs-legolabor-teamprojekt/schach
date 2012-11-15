package camera;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class OffsetGUI extends JFrame implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private char state = '1'; // 1 fuer koord1, 2 fuer koord2, n fuer fertig
	private int offsetX1, offsetX2, offsetY1, offsetY2;
	private ImageLoader im;
	private boolean offset = true;

	public OffsetGUI(BufferedImage bu, ImageLoader im, boolean offset) {
		this.offset = offset;
		this.im = im;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(100, 100); // einmal bereitsstellen, spaeter die Bounds nochmal
		// anpassen
		panel = new PaintImage(bu);
		panel.addMouseListener(this);
		getContentPane().add(panel);
		setVisible(true);

		// int sizeHeight = bu.getHeight() + (int)
		// getContentPane().getBounds().getHeight();
		// int sizeWidth = bu.getWidth() + (int)
		// getContentPane().getBounds().getWidth();
		/*
		 * noch dynamisch anpassen... hat bislang nicht geklappt!
		 */

		int sizeHeight = bu.getHeight() + 37;
		int sizeWidth = bu.getWidth() + 15;

		setSize(sizeWidth, sizeHeight); // richtige Groesse setzen

	}

	public char getStatus()
	{
		return state;
	}

	public void mouseClicked(MouseEvent e)
	{
		// erster Klick, offset oben links, zweiter Klick, offset oben rechts
		switch (state) {
		case '1':
			if(offset) {
				offsetX1 = e.getX();
				offsetY1 = e.getY();
			}
			else {
				im.setVecA(e.getX(), e.getY());
			}
			state = '2';
			break;
		case '2':
			if(offset) {
				offsetX2 = e.getX();
				offsetY2 = e.getY();
				im.setOffset(offsetX1, offsetY1, offsetX2, offsetY2);
			}
			else {
				im.setVecB(e.getX(), e.getY());
			}
			state = 'n';
			setVisible(false);
			dispose();
			break;
		case 'n':
			System.out.println("neu kalibrieren");
			break;
		}

	}

	public void mouseReleased(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{

	}

	public void mouseEntered(MouseEvent e)
	{

	}

	public void mouseExited(MouseEvent e)
	{

	}

}
