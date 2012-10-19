package components;

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
	}

	@Override
	public String toString() {
		return "FigureKnight [color=" + util.ChessfigureConstants.getFigureColor(super.getColor()) + "]";
	}
}
