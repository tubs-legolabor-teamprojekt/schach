package components;

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
	}

	@Override
	public String toString() {
		return "FigurePawn [color=" + util.ChessfigureConstants.getFigureColor(super.getColor()) + "]";
	}
}
