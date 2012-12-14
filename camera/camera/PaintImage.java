package camera;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class PaintImage extends JPanel {
    /*
     * Frame auf dem das Kalibrierungsbild ausgegeben wird
     */
    private static final long serialVersionUID = 1L;
    private BufferedImage bu;

    public PaintImage(BufferedImage bu) {
        this.bu = bu;
    }

    // @override
    public void paintComponent(Graphics g) {
        g.drawImage(bu, 0, 0, null);
        repaint();
    }

}
