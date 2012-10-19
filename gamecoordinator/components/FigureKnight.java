package components;

import javax.swing.ImageIcon;

/**
 * Stellt einen Springer dar.
 * 
 * @author Florian Franke
 *
 */
public class FigureKnight extends Figure
{

	/**
	 * Erstellt einen Springer.
	 * @param color Farbe des Springers
	 */
	public FigureKnight(byte color) {
		super(color);
		this.setIcon();
	}
	
	@Override
	protected void setIcon() {
		if (this.color == util.ChessfigureConstants.BLACK) {
			this.icon = new ImageIcon("images/icons/knight_black.png");
		} else {
			this.icon = new ImageIcon("images/icons/knight_white.png");
		}
	}

	@Override
	public String toString() {
		return "FigureKnight [color=" + util.ChessfigureConstants.getFigureColor(super.getColor()) + "," +
				" icon=" + this.icon.toString() + "]";
	}
}
