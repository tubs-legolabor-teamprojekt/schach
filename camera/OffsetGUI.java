import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class OffsetGUI extends JFrame implements MouseListener {
	private JPanel panel;
	private char state = '1';			//1 für koord1, 2 für koord2, n für fertig
	private int offsetX1, offsetX2, offsetY1, offsetY2;
	
	
	
	public OffsetGUI(BufferedImage bu) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(bu.getHeight(),bu.getWidth()); 			//einmal bereitsstellen, später die Bounds nochmal anpassen
		panel = new PaintImage(bu);
		panel.addMouseListener(this);
		getContentPane().add(panel);
		setVisible(true);
		
		int sizeHeight = bu.getHeight() + (bu.getHeight() - (int) getContentPane().getBounds().getHeight());
		int sizeWidth = bu.getWidth() + (bu.getWidth() - (int) getContentPane().getBounds().getWidth());
		
		setSize(sizeWidth,sizeHeight); 					//richtige Größe setzen

	}
	
	public int getOffsetX1() {
		return offsetX1;
	}
	
	public int getOffsetX2() {
		return offsetX2;
	}
	
	public int getOffsetY1() {
		return offsetY1;
	}
	
	public int getOffsetY2() {
		return offsetY2;
	}
	
	public char getStatus() {
		return state;
	}
	
	public void mouseClicked(MouseEvent e) {
		//erster Klick, offset oben links, zweiter Klick, offset oben rechts
		switch(state) {
		case '1': offsetX1 = e.getX(); offsetY1 = e.getY(); state = '2'; break;
		case '2': offsetX2 = e.getX(); offsetY2 = e.getY(); state = 'n'; setVisible(false); dispose(); break;
		case 'n': System.out.println("neu kalibrieren"); break;
		}
		
	}
	
	public void mouseReleased(MouseEvent e) {
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	public void mouseExited(MouseEvent e) {
		
	}
	
}
