package camera;
import static com.googlecode.javacv.cpp.opencv_core.cvFlip;
//import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import java.awt.image.BufferedImage;
//import com.googlecode.javacv.CanvasFrame;
//import com.googlecode.javacv.FrameGrabber;
//import com.googlecode.javacv.VideoInputFrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public final class ImageGrabber
{
	private IplImage img;
	private OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);


	public ImageGrabber() {
		try {
			grabber.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage()
	{
		try {
			img = grabber.grab();
			if (img != null) {
				//cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
				return img.getBufferedImage();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}




