package components;

import javax.swing.ImageIcon;

/**
 * Stellt eine Dame dar.
 * 
 * @author Florian Franke
 *
 */
public class FigureQueen extends Figure
{

	/**
	 * Erstellt eine Dame.
	 * @param color Farbe des Dame
	 */
	public FigureQueen(byte color) {
		super(color);
		this.setIcon();
		this.figureLetter = 'Q';
	}
	
	@Override
	protected void setIcon() {
		if (this.color == util.ChessfigureConstants.BLACK) {
			this.icon = new ImageIcon("images/icons/queen_black.png");
		} else {
			this.icon = new ImageIcon("images/icons/queen_white.png");
		}
	}

	@Override
	public String toString() {
		return "FigureQueen [color=" + util.ChessfigureConstants.getFigureColor(super.getColor()) + "," +
				" icon=" + this.icon.toString() + "]";
	}
}
