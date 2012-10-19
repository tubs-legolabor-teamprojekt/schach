package components;

import javax.swing.ImageIcon;

/**
 * Stellt einen Laeufer dar.
 * 
 * @author Florian Franke
 *
 */
public class FigureBishop extends Figure
{

	/**
	 * Erstellt einen Laeufer.
	 * @param color Farbe des Laeufers
	 */
	public FigureBishop(byte color) {
		super(color);
		this.setIcon();
	}

	@Override
	protected void setIcon() {
		if (this.color == util.ChessfigureConstants.BLACK) {
			this.icon = new ImageIcon("images/icons/bishop_black.png");
		} else {
			this.icon = new ImageIcon("images/icons/bishop_white.png");
		}
	}
	
	@Override
	public String toString() {
		return "FigureBishop [color=" + util.ChessfigureConstants.getFigureColor(super.getColor()) + "," +
				" icon=" + this.icon.toString() + "]";
	}
}
