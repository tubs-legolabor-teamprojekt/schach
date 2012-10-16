import java.awt.image.BufferedImage;
import de.humatic.dsj.DSCapture;
import de.humatic.dsj.DSFilterInfo;
import de.humatic.dsj.DSFiltergraph;
import javax.swing.JFrame;
 
public class Webcam extends JFrame {
	
	private BufferedImage bu;
 
    private DSFilterInfo[][] dsFilterInfo;
    private DSCapture dscCapture[];
    private DSCapture dsc = null;
 
    public Webcam() {
        super("Spiegel");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dsFilterInfo = DSCapture.queryDevices();
        dscCapture = new DSCapture[dsFilterInfo[0].length - 1];
        dsc = new DSCapture(DSFiltergraph.D3D9, dsFilterInfo[0][0], false,
                DSFilterInfo.filterInfoForSystemProfile(2), null);
        dsc.flipImage(2);
        for(int i=0; i<4; i++) {
        bu = dsc.getImage();
    }
        setLocationRelativeTo(null);
        this.dispose();
    }
    
    public BufferedImage getImage() {
    	return bu;
    }
    public static void main(String[] args) {
        new Webcam();
    }
}