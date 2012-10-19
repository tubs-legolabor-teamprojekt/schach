package components;

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
	}

	@Override
	public String toString() {
		return "FigureRook [color=" + util.ChessfigureConstants.getFigureColor(super.getColor()) + "]";
	}
}
