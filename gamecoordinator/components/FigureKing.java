package components;

import javax.swing.ImageIcon;

/**
 * Stellt einen Koenig dar.
 * 
 * @author Florian Franke
 *
 */
public class FigureKing extends Figure
{

	/**
	 * Erstellt einen Koenig.
	 * @param color Farbe des Koenigs
	 */
	public FigureKing(byte color) {
		super(color);
		this.setIcon();
		this.figureLetter = 'K';
	}

	@Override
	protected void setIcon() {
		if (this.color == util.ChessfigureConstants.BLACK) {
			this.icon = new ImageIcon("images/icons/king_black.png");
		} else {
			this.icon = new ImageIcon("images/icons/king_white.png");
		}
	}
	
	@Override
	public String toString() {
		return "FigureKing [color=" + util.ChessfigureConstants.getFigureColor(super.getColor()) + "," +
				" icon=" + this.icon.toString() + "]";
	}
}
