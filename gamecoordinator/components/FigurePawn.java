package components;

import javax.swing.ImageIcon;

/**
 * Stellt einen Bauern dar.
 * 
 * @author Florian Franke
 *
 */
public class FigurePawn extends Figure
{

	/**
	 * Erstellt einen Bauern.
	 * @param color Farbe des Bauern
	 */
	public FigurePawn(byte color) {
		super(color);
		this.setIcon();
	}
	
	@Override
	protected void setIcon() {
		if (this.color == util.ChessfigureConstants.BLACK) {
			this.icon = new ImageIcon("images/icons/pawn_black.png");
		} else {
			this.icon = new ImageIcon("images/icons/pawn_white.png");
		}
	}

	@Override
	public String toString() {
		return "FigurePawn [color=" + util.ChessfigureConstants.getFigureColor(super.getColor()) + "," +
				" icon=" + this.icon.toString() + "]";
	}
}