package components;

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
	}

	@Override
	public String toString() {
		return "FigureKing [color=" + util.ChessfigureConstants.getFigureColor(super.getColor()) + "]";
	}
}
