package components;

import javax.swing.ImageIcon;

import util.ChessfigureConstants;

/**
 * Stellt einen Turm dar.
 * 
 * @author Florian Franke
 * 
 */
public class FigureRook extends Figure {

    /**
     * Erstellt einen Turm.
     * 
     * @param color
     *            Farbe des Turms
     */
    public FigureRook(byte color) {
        super(color);
        this.setIcon();
        this.setFigureType(ChessfigureConstants.ROOK);
        this.figureLetter = ChessfigureConstants.ROOK_LETTER;
    }

    @Override
    protected void setIcon() {
        if (this.color == util.ChessfigureConstants.BLACK) {
            this.icon = new ImageIcon("images/icons/rook_black_70x70.png");
        } else {
            this.icon = new ImageIcon("images/icons/rook_white_70x70.png");
        }
    }

    @Override
    public String toString() {
        return "FigureRook [color=" + util.ChessfigureConstants.getFigureColor(super.getColor()) + "," + " icon=" + this.icon.toString() + "]";
    }
}
