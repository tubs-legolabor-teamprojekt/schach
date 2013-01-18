package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowClosingAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        // GameCoordinator.setGameClosed()
        System.out.println("TEST");
        Gui.getInstance().setVisible(false);
        Gui.getInstance().dispose();
    }
}
