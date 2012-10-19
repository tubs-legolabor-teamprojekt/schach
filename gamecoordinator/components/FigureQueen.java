package components;

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
	}

	@Override
	public String toString() {
		return "FigureQueen [color=" + util.ChessfigureConstants.getFigureColor(super.getColor()) + "]";
	}
}
