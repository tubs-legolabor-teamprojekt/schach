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
		 * Ein paar Test-Züge, ohne auf die Schachregeln zu achten
		 * 
		 * Momentan besteht die Möglichkeit, dass ein Spiel mitten drin abstürzt
		 */
		
//		/*
//		 * Zug #1
//		 */
		Move move1 = new Move(Field.getFieldNumber("b2"), Field.getFieldNumber("b3"));
		moves.add(move1);
		
		Move move1a = new Move(Field.getFieldNumber("b3"), Field.getFieldNumber("b4"));
		moves.add(move1a);
		
		/*
		 * Zug #2 - Schmeiss eine gegnerische Figur
		 */
		Move move2 = new Move(Field.getFieldNumber("a1"), Field.getFieldNumber("a7"), true, false, false);
		moves.add(move2);
		
		/*
		 * Zug #3 - Ziehe vom Feld weiter, von dem eine gegnerische Figur geschmissen wurde
		 */
		Move move3 = new Move(Field.getFieldNumber("a7"), Field.getFieldNumber("a3"));
		moves.add(move3);
		
		
//		/*
//		 * Zug #4 (fehlerhaft, da Figur auf angegebenem Feld nicht vorhanden ist!)
//		 * Momentan wird eine nullpointer-exception geworfen, da die Rules-Klasse noch nicht fertig ist.
//		 */
//		Move move4 = new Move(Field.getFieldNumber("d4"), Field.getFieldNumber("d5"));
//		moves.add(move4);
		
//		/*
//		 * Zug #5
//		 */
		Move move5 = new Move(Field.getFieldNumber("f1"), Field.getFieldNumber("e8"), true, true, true);
		moves.add(move5);
		
		return moves;
	}

}
