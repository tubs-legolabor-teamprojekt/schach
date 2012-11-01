package game;

import java.util.ArrayList;
import java.util.List;

import components.Field;

public class TestingChess
{

	public static void main(String[] args) {
		// Simulierte Zuege erstellen
		Chess game = new Chess(getMoves());
		game.startGame();
	}
	
	public static List<Move> getMoves()
	{
		List<Move> moves = new ArrayList<Move>();
		
		/*
		 * Zug #1
		 */
		Move move1 = new Move(Field.getFieldNumber("b2"), Field.getFieldNumber("b3"));
		moves.add(move1);
		
		
		/*
		 * Zug #2 (fehlerhaft, da Figur auf angegebenem Feld nicht vorhanden ist!)
		 * Momentan wird eine nullpointer-exception geworfen, da die Rules-Klasse noch nicht fertig ist.
		 */
//		Move move2 = new Move(Field.getFieldNumber("d4"), Field.getFieldNumber("d5"));
//		moves.add(move2);
		
		/*
		 * Zug #3
		 */
		Move move3 = new Move(Field.getFieldNumber("f1"), Field.getFieldNumber("b5"), true, true, true);
		moves.add(move3);
		
		return moves;
	}

}
