package camera;
import static com.googlecode.javacv.cpp.opencv_core.cvFlip;
//import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import java.awt.image.BufferedImage;
//import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.VideoInputFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class ImageGrabber
{
  IplImage image;
  FrameGrabber grabber = new VideoInputFrameGrabber(0);

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

          IplImage img;
          img = grabber.grab();
          if (img != null) {
              cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
              // cvSaveImage((i++)+"-aa.jpg", img);
              // show image on window
              img.getBufferedImage();
              return img.getBufferedImage();
          }

      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }

}




