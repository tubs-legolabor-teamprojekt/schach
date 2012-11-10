package camera;

import java.awt.image.BufferedImage;
import java.util.*;

public class ImageLoader
{
	private final int FIELDS = 64;
    private int width, height; // gibt die groesse des Schachbretts und nicht
                               // die
                               // des Bildes an.
    private ArrayList<Integer> r1, r2, g1, g2, b1, b2;
    private ArrayList<Integer> diffR, diffG, diffB;
    public int offsetX1, offsetX2, offsetY1, offsetY2;
    public int[] rgbFieldDiff = new int[FIELDS];
    

    ImageGrabber grabber;

    public ImageLoader() {
        grabber = new ImageGrabber();
        
        r1 = new ArrayList<Integer>();
        g1 = new ArrayList<Integer>();
        b1 = new ArrayList<Integer>();

        r2 = new ArrayList<Integer>();
        g2 = new ArrayList<Integer>();
        b2 = new ArrayList<Integer>();

        diffR = new ArrayList<Integer>();
        diffG = new ArrayList<Integer>();
        diffB = new ArrayList<Integer>();
    }
    
    private void setFieldDiff() {
    	if(r1!=null && r2!=null && g1!=null && g2!=null && b1!=null && b2!=null) {
    
    	for (int i = 0; i < FIELDS; i++) {

            rgbFieldDiff[i] = (int) Math.abs(getPositionAverage(i, 1)
                    - getPositionAverage(i, 2));
            if (i % 8 == 0) {
                System.out.println();
            }
    }
    	}
    	else {
    		System.out.println("Erst 2 Fotos machen");
    	}
    }

    /*
     * Vergleicht die 2 Schachbilder miteinander und erkennt, ob eine Figur
     * bewegt wurde anmerkung: funktioniert schon, return aber noch nicht fertig
     * 
     * @return Array mit geaenderten Feldern
     */
    public int[] compareFields()
    {
        setFieldDiff();

    	for (int i = 0; i < FIELDS; i++) {
            if (i % 8 == 0) {
                System.out.println();
            }
            System.out.print("\t" + getPositionAverage(i, 1));
        }
    	
        int result[] = new int[2];
        System.out.println();
        for (int i = 0; i < FIELDS; i++) {
            if (i % 8 == 0) {
                System.out.println();
            }
            System.out.print("\t" + getPositionAverage(i, 2));
        }
        System.out.println();
        for (int i = 0; i < FIELDS; i++) {
            if (i % 8 == 0) {
                System.out.println();
            }
            System.out.print("\t" + rgbFieldDiff[i]);
        }
        int average = sampleAverage();
        int stDev = standardDeviation(average);
        
        System.out.println("Toleranz:"+average+"  stdabweichung"+stDev);
        return result;
    }

    /*
     * EDIT: MOMENTAN NUR ROTE SPIELSTEINE!
     * Mittelwert der RGB-Werte eines der 64 Schachfelder wird berechnet
     * 
     * @param position Position des Schachfeldes (oben links 0, unten rechts 63)
     * 
     * @param image aus welchem Bild der RGB Wert genommen werden soll (1 oder
     * 2)
     * 
     * @return durchschnittswert rgb
     */
    private int getPositionAverage(int position, int image)
    {
        int unitWidth = (int) width / 8;
        int unitHeight = (int) height / 8;
        int unitX1 = position % 8;
        int unitY1 = (int) position / 8;
        int unitX2 = unitX1 + 1;
        int unitY2 = unitY1 + 1;

        int startX = unitX1 * unitWidth;
        int startY = unitY1 * unitHeight;
        int endX = unitX2 * unitWidth;
        int endY = unitY2 * unitHeight;
        //momentan nur ROTE SPIELSTEINE
        int value = 0;
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                if (image == 1)
                    value = value + r1.get(y * width + x);
                            //+ g1.get(y * width + x);// + b1.get(y * width + x);
                else if (image == 2)
                    value = value + r2.get(y * width + x);
                            //+ g2.get(y * width + x);// + b2.get(y * width + x);
                else
                    value = value + diffR.get(y * width + x);
                            //+ diffG.get(y * width + x);
                            //+ diffB.get(y * width + x);

            }
        }
        return (int) (value / 1) / (unitWidth * unitHeight);

    }

    /*
     * berechnet den Offset, der um das Schachbrett als Rand bleibt. Dieser wird
     * dann bei spaeterer Berechnung abgeschnitten Offset: linke obere Ecke,
     * rechte untere Ecke.
     */
    public void calcOffset()
    {
        // OffsetGUI offsetGUI = new OffsetGUI(w.getImage(), this);
        OffsetGUI offsetGUI = new OffsetGUI(grabber.getImage(), this);

        while (offsetGUI.getStatus() != 'n') {
         try {
        	Thread.sleep(1);
         } catch(Exception e) {
        	 e.printStackTrace();
         }
        }
        this.width = offsetX2 - offsetX1;
        this.height = offsetY2 - offsetY1;
        System.out.println("breite:" + this.width + " hoehe " + this.height);
    }

    /*
     * Offset eintragen, also Abstand vom Rand des Bildes bis zum eigentlichen
     * Schachfeld
     * 
     * @param offsetX1 xWert links oben
     * 
     * @param offsetY1 yWert links oben
     * 
     * @param offsetX2 xWert rechts unten
     * 
     * @param iffsetY2 yWert rechts unten
     */
    public void setOffset(int offsetX1, int offsetY1, int offsetX2, int offsetY2)
    {
        this.offsetX1 = offsetX1;
        this.offsetX2 = offsetX2;
        this.offsetY1 = offsetY1;
        this.offsetY2 = offsetY2;
        System.out.println(offsetX1 + " " + offsetY1 + " " + offsetX2 + " "
                + offsetY2);
    }

    /*
     * Ermittelt den durchschnittlichen Toleranzdifferenzwert der beiden Bilder
     * Z.B. unterschiedliche Lichtverhaeltnisse erfordern andere Toleranzen...
     * (Mittelwert)
     * 
     * @return mittlere Differenz der beiden Bilder Pixel pro Pixel
     */
    private int sampleAverage()
    {
        int tolerance = 0;
        for (int i = 0; i <FIELDS; i++) {
            tolerance += rgbFieldDiff[i];
        }
        return (int) Math.round(tolerance / FIELDS);
    }

    /*
     * Einfache Standardabweichung von der durchschnittlichen Toleranzdifferenz
     * 
     * @param average Mittelwert
     * 
     * @return mittlere Abweichung vom Mittelwert
     */
    private int standardDeviation(int average)
    {
        // nach Formel Var(x) = E(X^2)-E(X)^2 und sqrt(Var(X)) =
        // Standardabweichung
        int t = 0;
        int av2 = (int) Math.round(Math.pow(average, 2));
        for (int i = 0; i < FIELDS; i++) {
           /* t += (int) Math.pow(
                    (diffR.get(i) + diffG.get(i) + diffB.get(i)) / 3, 2);*/
        	t += (int) Math.pow(
                    (rgbFieldDiff[i]), 2);
        }
        t = Math.round(t / FIELDS);
        return (int) Math.sqrt(t - av2);

    }

    /*
     * Erste Vergleichsfoto machen
     * 
     * @param file Pfad zum Foto
     */
    public boolean takePhoto1()
    {
        loadRGB(true);
        return true;
    }

    /*
     * Zweite Vergleichsfoto machen
     * 
     * @param file Pfad zum Foto
     */
    public boolean takePhoto2()
    {
        loadRGB(false);
        return true;
    }

    /*
     * Laed die RGB Werte eines Biles in die ArrayListen r1,g1,b1 bzw. r2,g2,b2
     * 
     * @param file Pfad des Biles
     * 
     * @param toggle true fuer das erste Foto, false fuer das zweite Foto
     */
    private void loadRGB(boolean toggle)
    {
        ArrayList<Integer> r = new ArrayList<Integer>();
        ArrayList<Integer> g = new ArrayList<Integer>();
        ArrayList<Integer> b = new ArrayList<Integer>();

        BufferedImage bu = grabber.getImage();

        for (int y = offsetY1; y < offsetY2; y++) {
            for (int x = offsetX1; x < offsetX2; x++) {
                r.add((int) (((Math.pow(256, 3) + bu.getRGB(x, y)) / 65536))); // Umwandlung
                                                                               // der
                                                                               // ausgelesenen
                                                                               // Werte...
                g.add((int) (((Math.pow(256, 3) + bu.getRGB(x, y)) / 256) % 256)); // ...in
                                                                                   // RGB
                                                                                   // Werte
                b.add((int) ((Math.pow(256, 3) + bu.getRGB(x, y)) % 256));
            }
        }
        if (toggle == true) {
            this.r1 = r;
            this.b1 = b;
            this.g1 = g;
        } else {
            this.r2 = r;
            this.b2 = b;
            this.g2 = g;
        }

    }

    public static void main(String[] args)
    {
        ImageLoader im = new ImageLoader();
        im.calcOffset();
         im.takePhoto1();
        System.out.println("Foto1 taken");
         try{
        	 Thread.sleep(10000);
         }
         catch(Exception e) {
        	 e.printStackTrace();
         }
         im.takePhoto2();
         im.compareFields();
       
       

    }

}
