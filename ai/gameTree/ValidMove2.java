package gameTree;

/**
 * Klasse zum zurckgeben gltiger Schachzge aus einer Situation heraus.
 * bergeben wird das Schachfeld und die Farbe, die ziehen darf.
 * 
 * @author tobi
 * 
 */
public class ValidMove2
{

    protected static short[] ValMove(short[] situation, boolean white) {

        short[] possMove = new short[25];

        for (int i = 0; i < possMove.length; i++) {

            possMove[i] = (short) (100 * i);

        }

        return possMove;

    }

}
