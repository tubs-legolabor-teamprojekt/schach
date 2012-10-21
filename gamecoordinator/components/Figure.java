package components;

import javax.swing.ImageIcon;

import util.ChessfigureConstants;

/**
 * Abstrakte Klasse f�r eine Schachfigur. Es darf keine Instanz dieser Klasse gebildet werden,
 * da eine Figur immer von einem Typ (K�nig, Dame...) sein muss.
 * 
 * @author Florian Franke
 *
 */
public abstract class Figure
{
	/**
	 * Farbe der Figur
	 */
	protected byte color = util.ChessfigureConstants.BLACK;
	
	/**
	 * Icon der Figur
	 */
	protected ImageIcon icon = null;
	
	/**
	 * Figurtyp (Koenig, Dame...)
	 */
	protected byte figureType;

	/**
	 * Erstellt eine Figur
	 * @param color Farbe der Figur
	 */
	public Figure(byte color)
	{
		this.setColor(color);
	}
	
	/**
	 * Gibt die Farbe der Figur zurueck
	 * @return Farbe der Figur
	 */
	public byte getColor()
	{
		return color;
	}

	/**
	 * Setzt die Farbe der Figur
	 * @param color
	 */
	public void setColor(byte color)
	{
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
	
	/**
	 * Je nach Farbe und Figur wird mit dieser Methode das Icon gesetzt.
	 */
	protected abstract void setIcon();
	
	/**
	 * Gibt das Icon der entsprechenden Figur zur�ck
	 * @return Icon
	 */
	public ImageIcon getIcon()
	{
		return this.icon;
	}
	
	/**
	 * Legt den Figurtyp fest.
	 * @param figureType Der neue Figurtyp
	 */
	public void setFigureType(byte figureType)
	{
		try {
			if (ChessfigureConstants.isValidFigureType(figureType))
				this.figureType = figureType;
			else
				throw new FigureException("Ungueltiger Figurtyp!");
		} catch (FigureException e)
		{
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Gibt den Byte-Wert der Figur zurueck
	 * @return
	 */
	public byte getFigureType()
	{
		return this.figureType;
	}
	
	@Override
	public String toString()
	{
		return "Figure [color=" + color + ", toString()=" + super.toString()
				+ "]";
	}
}
