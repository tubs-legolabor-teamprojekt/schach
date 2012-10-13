import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;




public class ImageLoader {
	
	private int width, height;
	private Vector<Integer> r1,r2,g1,g2,b1,b2;
	
	public ImageLoader() {	
		r1 = new Vector<Integer>();
		g1 = new Vector<Integer>();
		b1 = new Vector<Integer>();
		
		r2 = new Vector<Integer>();
		g2 = new Vector<Integer>();
		b2 = new Vector<Integer>();
		}
	
	/*
	 * Testausgabe der RGB Werte des 1. Fotos...
	 */
	public void ausgabe() {
		for(int i=0; i< width*height; i++) {
			if(i%(width) == 0) {
				System.out.println();	
			}
			System.out.printf("%03d/%03d/%03d  ",r1.get(i),g1.get(i),b1.get(i));
		}	
	}
	
	/*
	 * Erste Vergleichsfoto machen
	 * @param file Pfad zum Foto
	 */
	public boolean takePhoto1(File file) {
		loadPhoto(file, true);
		return true;
	}
	
	/*
	 * Zweite Vergleichsfoto machen
	 * @param file Pfad zum Foto
	 */
	public boolean takePhoto2(File file) {
		loadPhoto(file, false);
		return true;
	}
	
	/*
	 * L�d die RGB Werte eines Biles in die Vectoren r1,g1,b1 bzw. r2,g2,b2
	 * @param file Pfad des Biles
	 * @param toggle true f�r das erste Foto, false f�r das zweite Foto
	 */
	private void loadPhoto(File file, boolean toggle) {
		Vector<Integer> r = new Vector<Integer>();
		Vector<Integer> g = new Vector<Integer>();
		Vector<Integer> b = new Vector<Integer>();
		
		BufferedImage bu = readImage(file);
		this.height = bu.getHeight();
		this.width = bu.getWidth();
		
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				r.add((int)(((Math.pow(256,3)+bu.getRGB(x,y))/65536)));			//Umwandlung der ausgelesenen Werte...
				g.add((int)(((Math.pow(256,3)+bu.getRGB(x,y))/256)%256));		//...in RGB Werte
		        b.add((int)((Math.pow(256,3)+bu.getRGB(x,y))%256));
			}
		}
		if(toggle == true) {
			this.r1 = r; 
			this.b1 = b; 
			this.g1 = g;
		}
		else {
			this.r2 = r;
			this.b2 = b;
			this.g2 = g;
		}
		
		
	}
	
	/*
	 * Foto aus Datei einlesen
	 * @param file Pfad zum Foto
	 */
	private BufferedImage readImage(File file) {
		try {
			BufferedImage bu = ImageIO.read(file);
			return bu;
		}
		catch(IOException e) {
			e.printStackTrace();
			System.out.println("Pfad falsch gesetzt o. Bild nicht vorhanden.");
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		ImageLoader im = new ImageLoader();
		im.takePhoto1(new File("test.jpg"));
		im.ausgabe();

	}

}

