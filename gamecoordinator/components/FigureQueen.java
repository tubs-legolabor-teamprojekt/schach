package components;

import javax.swing.ImageIcon;

import util.ChessfigureConstants;

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
		this.setFigureType(ChessfigureConstants.QUEEN);
		this.figureLetter = ChessfigureConstants.QUEEN_LETTER;
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
