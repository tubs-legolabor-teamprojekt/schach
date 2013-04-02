package camera;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.Collections;

/*
 * @author Marcel Schubert
 * 
 * Klasse fuer Kalibrierung des Schachfelds unter der Kamera
 * sowie der Berechnung der geaenderten Schachfiguren
 */
public class ImageLoader {
    private final int FIELDS = 64;
    private int width, height; // groesse Schachbrett (nicht Bild)
    private ArrayList<Integer> r1, r2, g1, g2, b1, b2;
    private ArrayList<Integer> diffR, diffG, diffB;
    private int offsetX1, offsetX2, offsetY1, offsetY2;
    private List<Field> rgbFieldDiff = new ArrayList<Field>();
    private Vec2f a, b;
    private float angle = 0;
    
    //mein internes system auf die andere Nummerierung anpassen
    private int[] fieldConversion = {57,58,59,60,61,62,63,64,
    								 49,50,51,52,53,54,55,56,
    								 41,42,43,44,45,46,47,48,
    								 33,34,35,36,37,38,39,40,
    								 25,26,27,28,29,30,31,32,
    								 17,18,19,20,21,22,23,24,
    								  9,10,11,12,13,14,15,16,
    								  1, 2, 3, 4, 5, 6, 7, 8};
    ImageRotator ir;

    ImageGrabber grabber;

    public ImageLoader() {
        a = new Vec2f();
        b = new Vec2f();
        grabber = new ImageGrabber();
        ir = new ImageRotator();
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
    public void setOffset(int offsetX1, int offsetY1, int offsetX2, int offsetY2) {
        this.offsetX1 = offsetX1;
        this.offsetX2 = offsetX2;
        this.offsetY1 = offsetY1;
        this.offsetY2 = offsetY2;
     //   System.out.println(offsetX1 + " " + offsetY1 + " " + offsetX2 + " " + offsetY2);
    }

    /*
     * setzt den Vektor A, welcher auf linke obere Ecke des Schachfeldes zeigt
     * 
     * @param x links oben x Wert
     * 
     * @param y links oben y Wert
     */
    public void setVecA(float x, float y) {
        this.a.setX(x);
        this.a.setY(y);
    }

    /*
     * setzt den Vektor B, welcher auf rechte untere Ecke des Schachfeldes zeigt
     * 
     * @param x rechts unten x Wert
     * 
     * @param y rechts unten y Wert
     */
    public void setVecB(float x, float y) {
        this.b.setX(x);
        this.b.setY(y);
    }

    /*
     * Winkel um den das Schachfeld gedreht werden muss um horizontal zu sein
     * 
     * @angle Winkel in Grad
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

    /*
     * Winkel um den das Schachfeld gedreht werden muss um horizontal zu sein
     * 
     * @return Winkel in Grad
     */
    public float getAngle() {
        return this.angle;
    }

    /*
     * Gibt eine Liste der veraenderten Positionen zurueckTop 2 (4 bei Rochade)
     * mit meister Aenderung werden zurueckgegeben
     * 
     * @return Liste mit "Field" Elementen. Entweder 2 oder 4(Rochade)
     */
    public List<Integer> getChangedPositions() {
        setFieldDiff();
        List<Field> sortedList = sortList(this.rgbFieldDiff);
        int average = sampleAverage();
        int stDev = standardDeviation(average);

        List<Integer> l = new ArrayList<Integer>();
        boolean rochadePossible = true;
        // Rochade nur auf der obersten o. untersten Reihe moeglich
        for (int i = 0; i < 4 && rochadePossible; i++) {
            if (sortedList.get(i).getPosition() > 7 && sortedList.get(i).getPosition() < 56) {
                rochadePossible = false;
            }
        }
        //+1 weil schachsystem von 1-64 felder
        System.out.println("FELD1"+fieldConversion[sortedList.get(0).getPosition()]);
        System.out.println("FELD2"+fieldConversion[sortedList.get(1).getPosition()]);
        
        l.add(fieldConversion[sortedList.get(0).getPosition()]);
        l.add(fieldConversion[sortedList.get(1).getPosition()]);

        // wenn rochade moeglich ist, dann wird noch geprueft, ob
        // die werte der felder ausserhalb der 2fachen standardabweichung vom
        // mittelwert
        // liegen. Wenn ja, dann ist eine Rochade sehr wahrscheinlich.
        if (rochadePossible) {
            if ((sortedList.get(2).getValue() > (average + 1 * stDev)) && (sortedList.get(3).getValue() > (average + 1 * stDev))) {
                l.add(fieldConversion[sortedList.get(2).getPosition()]);
                l.add(fieldConversion[sortedList.get(3).getPosition()]);
            }
        }

        return l;
    }

    /*
     * Sortiert die Liste mittles Collections Framework
     * 
     * @return absteigend sortierte Liste
     */
    private List<Field> sortList(List<Field> l) {
        Collections.sort(l);
        //System.out.println();
        //System.out.println(l.size());
//        for (int i = 0; i < l.size(); i++) {
          //  System.out.println("Nummer " + l.get(i).getPosition() + " Wert: " + l.get(i).getValue());
//        }
        return l;
    }

    /*
     * Berechnet die Differenz der 64 Felder aus Bild 1 und Bild 2 uns speichert
     * sie in einer Liste als Typ "Field".
     */
    private void setFieldDiff() {
        if (r1 != null && r2 != null && g1 != null && g2 != null && b1 != null && b2 != null) {
            for (int i = 0; i < FIELDS; i++) {
                Field a = new Field(i, (int) Math.abs(getPositionAverage(i, 1) - getPositionAverage(i, 2)));
                rgbFieldDiff.add(a);
                if (i % 8 == 0) {
         //           System.out.println();
                }
            }
        } else {
       //     System.out.println("Erst 2 Fotos machen");
        }
    }

    /*
     * Vergleicht die 2 Schachbilder miteinander und erkennt, ob eine Figur
     * bewegt wurde anmerkung: funktioniert schon, return aber noch nicht fertig
     * 
     * @return Array mit geaenderten Feldern
     */
    public void compareFields() {
        int average = sampleAverage();
        int stDev = standardDeviation(average);

        for (int i = 0; i < FIELDS; i++) {
            if (i % 8 == 0) {
       //         System.out.println();
            }
       //     System.out.print("\t" + getPositionAverage(i, 1));
        }

        System.out.println();
        for (int i = 0; i < FIELDS; i++) {
            if (i % 8 == 0) {
         //       System.out.println();
            }
         //   System.out.print("\t" + getPositionAverage(i, 2));
        }
       // System.out.println();
        for (int i = 0; i < FIELDS; i++) {
            if (i % 8 == 0) {
           //     System.out.println();
            }
       //     System.out.print("\t" + rgbFieldDiff.get(i).getValue());
        }

       // System.out.println();
        for (int i = 0; i < FIELDS; i++) {
            if (i % 8 == 0) {
         //       System.out.println();
            }
            if (rgbFieldDiff.get(i).getValue() > average + stDev) {
           //     System.out.print("\t" + "1");
            } else {
           //     System.out.print("\t" + "0");
            }
        }

   //     System.out.println("\n Toleranz:" + average + "  stdabweichung" + stDev);
    }

    /*
     * EDIT: MOMENTAN NUR ROTE SPIELSTEINE! Mittelwert der RGB-Werte eines der
     * 64 Schachfelder wird berechnet
     * 
     * @param position Position des Schachfeldes (oben links 0, unten rechts 63)
     * 
     * @param image aus welchem Bild der RGB Wert genommen werden soll (1 oder
     * 2)
     * 
     * @return durchschnittswert rgb
     */
    private int getPositionAverage(int position, int image) {
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
        // momentan nur ROTE SPIELSTEINE
        int value = 0;
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                if (image == 1) {
                    value = value + r1.get(y * width + x) + g1.get(y * width + x) + b1.get(y * width + x);
                    // value = value + g1.get(y * width + x);
                } else if (image == 2) {
                    value = value + r2.get(y * width + x) + g2.get(y * width + x) + b2.get(y * width + x);
                    // value = value + g2.get(y * width + x);
                } else {
                    value = value + diffR.get(y * width + x) + diffG.get(y * width + x) + diffB.get(y * width + x);
                    // value = value + diffG.get(y * width + x);
                }

            }
        }
        return (int) (value / 3) / (unitWidth * unitHeight);

    }

    public String numberToRaster(int position) {
        String pos = "";
        String[] character = { "a", "b", "c", "d", "e", "f", "g", "h" };

        pos = character[position % 8];
        pos += (8 - (int) (position / 8));

        return pos;
    }

    /*
     * berechnet den Offset, der um das Schachbrett als Rand bleibt. Dieser wird
     * dann bei spaeterer Berechnung abgeschnitten Offset: linke obere Ecke,
     * rechte untere Ecke.
     */
    public void calcOffset() {
        // OffsetGUI offsetGUI = new OffsetGUI(w.getImage(), this);
        BufferedImage img = grabber.getImage();
        img = ir.getRotatedImage(img, this.angle);
        OffsetGUI offsetGUI = new OffsetGUI(img, this, true);

        while (offsetGUI.getStatus() != 'n') {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.width = offsetX2 - offsetX1;
        this.height = offsetY2 - offsetY1;
   //     System.out.println("breite:" + this.width + " hoehe " + this.height);
    }

    /*
     * Berechnet den Winkel, um den das Spielfeld gedreht werden muss, damit der
     * obere Rand des Schachfelds horizontal ist
     * 
     * @return Winkel in Grad
     */
    public float calcAngle() {
        OffsetGUI offsetGUI = new OffsetGUI(grabber.getImage(), this, false);
        while (offsetGUI.getStatus() != 'n') {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Vec2f diagon = new Vec2f();
        diagon.calcVec2f(a, b);
        return -(diagon.getAngle(new Vec2f(100, 0)) - 45);
    }

    /*
     * Ermittelt den durchschnittlichen Toleranzdifferenzwert der beiden Bilder
     * Z.B. unterschiedliche Lichtverhaeltnisse erfordern andere Toleranzen...
     * (Mittelwert)
     * 
     * @return mittlere Differenz der beiden Bilder Pixel pro Pixel
     */
    private int sampleAverage() {
        int tolerance = 0;
        for (int i = 0; i < FIELDS; i++) {
            tolerance += rgbFieldDiff.get(i).getValue();
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

    private int standardDeviation(int average) {
        // nach Formel Var(x) = E(X^2)-E(X)^2 und sqrt(Var(X)) =
        // Standardabweichung
        int t = 0;
        int av2 = (int) Math.round(Math.pow(average, 2));
        for (int i = 0; i < FIELDS; i++) {
            // t += (int) Math.pow((diffR.get(i) + diffG.get(i) + diffB.get(i))
            // / 3, 2);
            t += (int) Math.pow((rgbFieldDiff.get(i).getValue()), 2);
        }
        t = Math.round(t / FIELDS);
        return (int) Math.sqrt(t - av2);

    }

    /*
     * Erste Vergleichsfoto machen
     * 
     * @param file Pfad zum Foto
     */
    public boolean takePhoto1() {
    	rgbFieldDiff.clear();
        loadRGB(true);
        return true;
    }

    /*
     * Zweite Vergleichsfoto machen
     * 
     * @param file Pfad zum Foto
     */
    public boolean takePhoto2() {
        loadRGB(false);
        return true;
    }

    /*
     * Laed die RGB Werte eines Bildes in die ArrayListen r1,g1,b1 bzw. r2,g2,b2
     * 
     * @param file Pfad des Biles
     * 
     * @param toggle true fuer das erste Foto, false fuer das zweite Foto
     */
    private void loadRGB(boolean toggle) {
        ArrayList<Integer> r = new ArrayList<Integer>();
        ArrayList<Integer> g = new ArrayList<Integer>();
        ArrayList<Integer> b = new ArrayList<Integer>();

        BufferedImage bu = grabber.getImage();
        bu = ir.getRotatedImage(bu, angle);

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

    public static void main(String[] args) {

        // ImageLoader erstellen
        ImageLoader im = new ImageLoader();
        // Winkel um den Schachbrett gedreht werden soll eingeben
        // Bild wird in Fenster angezeigt
        im.setAngle(im.calcAngle());

        // Offset bestimmen, also die groesse des Schachbretts
        // wird ueber Fenster manuell bestimmt
        im.calcOffset();

        // erste Vergleichsfoto
        im.takePhoto1();

        // nachfolgend nur fuer Tests (damit ich zeit habe
        // Figuren umzusetzen
      //  System.out.println("Foto1 taken");
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2te Vergleichsfoto nehmen
        im.takePhoto2();

        // Hier werden die geaenderten Positionen geholt
        List<Integer> l = im.getChangedPositions();

        // irrelevant
     //   System.out.println("AUSGABE");
        for (int i = 0; i < l.size(); i++) {
     //       System.out.println(l.get(i));
     //       System.out.println(im.numberToRaster(l.get(i)));
        }
        im.compareFields();

        /*
         * Aufruf erfolgt so: 1.) initialisieren; Imageloader, winkel setzen,
         * offset berechnen 2.) ueber takePhoto 1 und 2 Fotos aufnehmen
         * 3.)Positionen entnehmen
         * 
         * Schritt 2.) und 3.) beliebig oft wiederholbar
         */

    }

}
