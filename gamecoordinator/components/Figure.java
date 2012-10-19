package components;

/**
 * Abstrakte Klasse für eine Schachfigur. Es darf keine Instanz dieser Klasse gebildet werden,
 * da eine Figur immer von einem Typ (König, Dame...) sein muss.
 * 
 * @author Florian Franke
 *
 */
public abstract class Figure
{
	/**
	 * Farbe der Figur
	 */
	private byte color = util.ChessfigureConstants.BLACK;

	/**
	 * Erstellt eine Figur
	 * @param color Farbe der Figur
	 */
	public Figure(byte color) {
		this.setColor(color);
	}
	
	/**
	 * Gibt die Farbe der Figur zurueck
	 * @return Farbe der Figur
	 */
	public byte getColor() {
		return color;
	}

	/**
	 * Setzt die Farbe der Figur
	 * @param color
	 */
	public void setColor(byte color) {
		try {
			if (color == 1 || color == 0)
				this.color = color;
			else {
				throw new Exception("Ungueltige Farbe der Figur!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "Figure [color=" + color + ", toString()=" + super.toString()
				+ "]";
	}
}
