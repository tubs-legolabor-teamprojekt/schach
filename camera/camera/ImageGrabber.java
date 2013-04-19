package camera;

import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import java.awt.image.BufferedImage;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

//Laed Bilder von der Webcam

public final class ImageGrabber {
	private IplImage img;
	private OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
	private static int i = 0;

	public ImageGrabber() {
		try {
			grabber.setImageWidth(1600);
			grabber.setImageHeight(1200);
		  
			
			grabber.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		try {
		    grabber.flush();
			img = grabber.grab();
			if (img != null) {
				cvSaveImage(i + "capture.jpg", img);
				i++;
				return img.getBufferedImage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
