package camera;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

public class ImageLoader
{

    private int width, height; // gibt die groesse des Schachbretts und nicht
                               // die
                               // des Bildes an.
    private ArrayList<Integer> r1, r2, g1, g2, b1, b2;
    private ArrayList<Integer> diffR, diffG, diffB;
    public int offsetX1, offsetX2, offsetY1, offsetY2;
    private int[] rgbFieldDiff = new int[64];

    //Webcam w;

    public ImageLoader() {
       // w = new Webcam();
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

    /*
     * Vergleicht die 2 Schachbilder miteinander und erkennt, ob eine Figur
     * bewegt wurde anmerkung: funktioniert schon, return aber noch nicht fertig
     * 
     * @return Array mit geaenderten Feldern
     */
    public int[] compareFields()
    {
        int tolerance = sampleAverage();
        int stDev = standardDeviation(tolerance);

        int result[] = new int[2];

        for (int i = 0; i < 64; i++) {

            rgbFieldDiff[i] = (int) Math.abs(getPositionAverage(i, 1)
                    - getPositionAverage(i, 2));
            if (i % 8 == 0) {
                System.out.println();
            }
            System.out.print("\t" + rgbFieldDiff[i]);
        }

        return result;
    }

    /*
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

        int value = 0;
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                if (image == 1)
                    value = value + r1.get(y * width + x)
                            + g1.get(y * width + x) + b1.get(y * width + x);
                else if (image == 2)
                    value = value + r2.get(y * width + x)
                            + g2.get(y * width + x) + b2.get(y * width + x);
                else
                    value = value + diffR.get(y * width + x)
                            + diffG.get(y * width + x)
                            + diffB.get(y * width + x);

            }
        }
        return (int) (value / 3) / (unitWidth * unitHeight);

    }

    /*
     * berechnet den Offset, der um das Schachbrett als Rand bleibt. Dieser wird
     * dann bei spaeterer Berechnung abgeschnitten Offset: linke obere Ecke,
     * rechte untere Ecke.
     */
    public void calcOffset()
    {
        // OffsetGUI offsetGUI = new OffsetGUI(w.getImage(), this);
        OffsetGUI offsetGUI = new OffsetGUI(getImage(new File(
                "camera/img/schachbrett.jpg")), this);

        while (offsetGUI.getStatus() != 'n')
            System.out.println("");

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
     * Bild im entsprechenden Pfad wird geladen
     * 
     * @param file Pfad
     * 
     * @return Bild als BufferedImage
     */
    public BufferedImage getImage(File file)
    {
        BufferedImage bu = readImage(file);
        return bu;
    }

    /*
     * Differenz der beiden Bilder als Absolutwert in jeweils RGB wird berechnet
     */
    public void difference()
    {
        for (int i = 0; i < width * height; i++) {
            diffR.add(Math.abs(r1.get(i) - r2.get(i)));
            diffG.add(Math.abs(g1.get(i) - g2.get(i)));
            diffB.add(Math.abs(b1.get(i) - b2.get(i)));
        }
    }

    /*
     * Testausgabe der RGB Werte des 1. Fotos...
     */
    public void print()
    {
        for (int i = 0; i < width * height; i++) {
            if (i % (width) == 0) {
                System.out.println();
            }
            System.out.printf("%03d/%03d/%03d  ", diffR.get(i), diffG.get(i),
                    diffB.get(i));
        }
    }

    /*
     * Testausgabe der zu erkennenden markanten Unterschiede zwischen 2 Bildern
     * unter Beruecksichtigung verschiedener Helligkeitsstufen der Bilder
     */
    public void printDiffTable()
    {
        int tolerance = sampleAverage();
        int stDev = standardDeviation(tolerance);

        System.out.println("\n Differenzen mit Mittelwert " + tolerance
                + " und Standardabweichung " + stDev + " ermittelt");
        for (int i = 0; i < width * height; i++) {
            if (i % (width) == 0) {
                System.out.println();
            }
            if ((int) ((diffR.get(i) + diffG.get(i) + diffB.get(i)) / 3) > (tolerance + stDev))
                System.out.print("1 ");
            // System.out.print((int)((diffR.get(i) + diffG.get(i) +
            // diffB.get(i))/3));
            else
                System.out.print("0 ");
            // System.out.printf("%03d/%03d/%03d  ",diffR.get(i),diffG.get(i),diffB.get(i));
        }
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
        for (int i = 0; i < width * height; i++) {
            tolerance += diffR.get(i) + diffG.get(i) + diffB.get(i);
        }
        return (int) (tolerance / 3) / (width * height);
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
        int av2 = (int) Math.pow(average, 2);
        for (int i = 0; i < width * height; i++) {
            t += (int) Math.pow(
                    (diffR.get(i) + diffG.get(i) + diffB.get(i)) / 3, 2);
        }
        t = t / (width * height);
        return (int) Math.sqrt(t - av2);

    }

    /*
     * Erste Vergleichsfoto machen
     * 
     * @param file Pfad zum Foto
     */
    public boolean takePhoto1(File file)
    {
        loadPhoto(file, true);
        return true;
    }

    /*
     * Zweite Vergleichsfoto machen
     * 
     * @param file Pfad zum Foto
     */
    public boolean takePhoto2(File file)
    {
        loadPhoto(file, false);
        return true;
    }

    /*
     * Laed die RGB Werte eines Biles in die ArrayListen r1,g1,b1 bzw. r2,g2,b2
     * 
     * @param file Pfad des Biles
     * 
     * @param toggle true fuer das erste Foto, false fuer das zweite Foto
     */
    private void loadPhoto(File file, boolean toggle)
    {
        ArrayList<Integer> r = new ArrayList<Integer>();
        ArrayList<Integer> g = new ArrayList<Integer>();
        ArrayList<Integer> b = new ArrayList<Integer>();

        BufferedImage bu = readImage(file);

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

    /*
     * Foto aus Datei einlesen
     * 
     * @param file Pfad zum Foto
     */
    private BufferedImage readImage(File file)
    {
        try {
            BufferedImage bu = ImageIO.read(file);
            return bu;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Pfad falsch gesetzt o. Bild nicht vorhanden.");
        }
        return null;
    }

    public static void main(String[] args)
    {
        ImageLoader im = new ImageLoader();
        im.calcOffset();
        im.takePhoto1(new File("camera/img/schachbrett.jpg"));
        im.takePhoto2(new File("camera/img/schachbrett2.jpg"));
        im.difference();
        // System.out.println(im.getPositionAverage(12));
        im.compareFields();
        // im.print();
        // im.printDiffTable();

    }

}
