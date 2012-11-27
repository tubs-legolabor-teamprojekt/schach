package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Klasse, die die ben�tige Methode des WindowListeners implementiert.
 * @author Tabea
 *
 */
public class WindowClosingAdapter extends WindowAdapter
{
	/**
	 * Methode, die das Fenster schli�t, wenn auf das x gedr�ckt wird. 
	 */
	public void windowClosing(WindowEvent e)
	{
		// GameCoordinator.setGameClosed()
		Gui.getInstance().setVisible(false);
		Gui.getInstance().dispose();
	}
}
