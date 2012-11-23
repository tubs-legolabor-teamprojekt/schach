package useful;

import util.ChessfigureConstants;

/**
 * Diese Klasse beinhaltet die Test_Situation als Array-Of-Short.
 * @author tobi
 *
 */
public class Test_Situation {

	/**
	 * Array-Of-Short, dass das Spielfeld beschreibt
	 */
	public static final short[] TEST_SITUATION = 
	{
		97,    //weißer Bauer e2
		416,   //weißer König e1
		ChessfigureConstants.makeFigureShort(ChessfigureConstants.BLACK, ChessfigureConstants.QUEEN, 4, 3)
	};
	
}
