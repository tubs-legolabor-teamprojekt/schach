package camera;

import java.awt.image.BufferedImage;
import de.humatic.dsj.DSCapture;
import de.humatic.dsj.DSFilterInfo;
import de.humatic.dsj.DSFiltergraph;

public class Webcam
{

    private BufferedImage bu;

    private DSFilterInfo[][] dsFilterInfo;
    private DSCapture dsc = null;

    public Webcam() {
        dsFilterInfo = DSCapture.queryDevices();
        dsc = new DSCapture(DSFiltergraph.D3D9, dsFilterInfo[0][0], false,
                DSFilterInfo.filterInfoForSystemProfile(2), null);
        dsc.flipImage(2);
        for (int i = 0; i < 5; i++) {
            bu = dsc.getImage();
        }
    }

    public BufferedImage getImage()
    {
        return bu;
    }
}