package camera;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.Collections;

public class ImageLoader
{
	private final int FIELDS = 64;
	private int width, height; //groesse Schachbrett (nicht Bild)
	private ArrayList<Integer> r1, r2, g1, g2, b1, b2;
	private ArrayList<Integer> diffR, diffG, diffB;
	private int offsetX1, offsetX2, offsetY1, offsetY2;
	private List<Field> rgbFieldDiff = new ArrayList<Field>();

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
	
	/*
	 * 
	 */
	public List<Integer> getChangedPositions() {
		setFieldDiff();
		List<Field> sortedList = sortList(this.rgbFieldDiff);
		int average = sampleAverage();
		int stDev = standardDeviation(average);
		
		List<Integer> l = new ArrayList<Integer>();
		boolean rochadePossible = true;
		//Rochade nur auf der obersten o. untersten Reihe moeglich
		for(int i=0; i<4 && rochadePossible; i++) {
			if(sortedList.get(i).getPosition() > 7 && sortedList.get(i).getPosition() < 60) {
				rochadePossible = false;	
			}
		}
		
			l.add(sortedList.get(0).getValue());
			l.add(sortedList.get(1).getValue());
		
		//wenn rochade moeglich ist, dann wird noch geprueft, ob
		//die werte der felder ausserhalb der 2fachen standardabweichung vom mittelwert
	    //liegen. Wenn ja, dann ist eine Rochade sehr wahrscheinlich.
		if(rochadePossible) {
			if( (sortedList.get(2).getValue() > (average+2*stDev)) && (sortedList.get(3).getValue() > (average+2*stDev)) ) {
				l.add(sortedList.get(2).getValue());
				l.add(sortedList.get(3).getValue());
			}
		}
		
		
		return l;
	}
	
/*
 * Sortiert die Liste mittles Collections Framework	
 * @return absteigend sortierte Liste
 */
	private List<Field> sortList(List<Field> l) {
		Collections.sort(l);
		System.out.println();
		System.out.println(l.size());
		for(int i=0; i<l.size(); i++) {
			System.out.println("Nummer "+l.get(i).getPosition() +" Wert: "+ l.get(i).getValue());
		}
		return l;
	}
/*
 * Berechnet die Differenz der 64 Felder aus Bild 1 und Bild 2
 * uns speichert sie in einer Liste als Typ "Field".
 */
	private void setFieldDiff() {
		if(r1!=null && r2!=null && g1!=null && g2!=null && b1!=null && b2!=null) {
			for (int i = 0; i < FIELDS; i++) {
				Field a = new Field(i,(int) Math.abs(getPositionAverage(i, 1) - getPositionAverage(i, 2)));
				rgbFieldDiff.add(a);
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
	public void compareFields()
	{
		int average = sampleAverage();
		int stDev = standardDeviation(average);

		for (int i = 0; i < FIELDS; i++) {
			if (i % 8 == 0) {
				System.out.println();
			}
			System.out.print("\t" + getPositionAverage(i, 1));
		}

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
			System.out.print("\t" + rgbFieldDiff.get(i).getValue());
		}

		System.out.println();
		for (int i = 0; i < FIELDS; i++) {
			if (i % 8 == 0) {
				System.out.println();
			}
			if(rgbFieldDiff.get(i).getValue()>average+stDev) {
				System.out.print("\t" + "1");
			}
			else {
				System.out.print("\t" + "0");
			}
		}

		System.out.println("\n Toleranz:"+average+"  stdabweichung"+stDev);
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
				if (image == 1) {
					value = value + r1.get(y * width + x) + g1.get(y * width + x) + b1.get(y * width + x);
				}
				else if (image == 2) {
					value = value + r2.get(y * width + x) + g2.get(y * width + x) + b2.get(y * width + x);
				}
				else {
					value = value + diffR.get(y * width + x) + diffG.get(y * width + x) + diffB.get(y * width + x);
				}

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
	private int standardDeviation(int average)
	{
		// nach Formel Var(x) = E(X^2)-E(X)^2 und sqrt(Var(X)) =
		// Standardabweichung
		int t = 0;
		int av2 = (int) Math.round(Math.pow(average, 2));
		for (int i = 0; i < FIELDS; i++) {
			 //t += (int) Math.pow((diffR.get(i) + diffG.get(i) + diffB.get(i)) / 3, 2);
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
		//im.compareFields();
		List<Integer> l = im.getChangedPositions();
		System.out.println("AUSGABE");
		for(int i=0; i<l.size(); i++) {
			System.out.println(l.get(i));
		}



	}

}
