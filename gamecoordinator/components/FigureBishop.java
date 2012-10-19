package components;

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
	}

	@Override
	public String toString() {
		return "FigureBishop [color=" + util.ChessfigureConstants.getFigureColor(super.getColor()) + "]";
	}
}
