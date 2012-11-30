package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Klasse, die die benötige Methode des WindowListeners implementiert.
 * @author Tabea
 *
 */
public class WindowClosingAdapter extends WindowAdapter
{
	/**
	 * Methode, die das Fenster schlißt, wenn auf das x gedrückt wird. 
	 */
	public void windowClosing(WindowEvent e)
	{
		// GameCoordinator.setGameClosed()
		Gui.getInstance().setVisible(false);
		Gui.getInstance().dispose();
	}
}
