package components;

import javax.swing.ImageIcon;

/**
 * Stellt einen Turm dar.
 * 
 * @author Florian Franke
 *
 */
public class FigureRook extends Figure
{

	/**
	 * Erstellt einen Turm.
	 * @param color Farbe des Turms
	 */
	public FigureRook(byte color) {
		super(color);
		this.setIcon();
		this.figureLetter = 'R';
	}
	
	@Override
	protected void setIcon() {
		if (this.color == util.ChessfigureConstants.BLACK) {
			this.icon = new ImageIcon("images/icons/rook_black.png");
		} else {
			this.icon = new ImageIcon("images/icons/rook_white.png");
		}
	}

	@Override
	public String toString() {
		return "FigureRook [color=" + util.ChessfigureConstants.getFigureColor(super.getColor()) + "," +
				" icon=" + this.icon.toString() + "]";
	}
}
