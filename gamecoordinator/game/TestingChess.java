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
		 * Ein paar Test-Z�ge, ohne auf die Schachregeln zu achten
		 * 
		 * Momentan besteht die M�glichkeit, dass ein Spiel mitten drin abst�rzt
		 */
		
//		/*
//		 * Zug #1
//		 */
	      Move move1 = new Move(Field.getFieldNumber("e7"), Field.getFieldNumber("e5"));
	      moves.add(move1);
	      Move move2 = new Move(Field.getFieldNumber("h2"), Field.getFieldNumber("h3"));
	      moves.add(move2);
	      Move move3 = new Move(Field.getFieldNumber("d7"), Field.getFieldNumber("d6"));
	      moves.add(move3);
	      Move move4 = new Move(Field.getFieldNumber("b2"), Field.getFieldNumber("b4"));
          moves.add(move4);
          Move move5 = new Move(Field.getFieldNumber("c7"), Field.getFieldNumber("c5"));
          moves.add(move5);
          Move move6 = new Move(Field.getFieldNumber("f2"), Field.getFieldNumber("f4"));
          moves.add(move6);
          Move move7 = new Move(Field.getFieldNumber("b8"), Field.getFieldNumber("c6"));
          moves.add(move7);
          Move move8 = new Move(Field.getFieldNumber("b4"), Field.getFieldNumber("b5"));
          moves.add(move8);
          Move move9 = new Move(Field.getFieldNumber("c6"), Field.getFieldNumber("d4"));
          moves.add(move9);
          Move move10 = new Move(Field.getFieldNumber("e2"), Field.getFieldNumber("e3"));
          moves.add(move10);
          Move move11 = new Move(Field.getFieldNumber("d4"), Field.getFieldNumber("b5"),true);
          moves.add(move11);
          Move move12 = new Move(Field.getFieldNumber("f1"), Field.getFieldNumber("b5"),true);
          moves.add(move12);
          Move move13 = new Move(Field.getFieldNumber("c8"), Field.getFieldNumber("d7"));
          moves.add(move13);
          Move move14 = new Move(Field.getFieldNumber("b5"), Field.getFieldNumber("d7"),true);
          moves.add(move14);
          Move move15 = new Move(Field.getFieldNumber("d8"), Field.getFieldNumber("d7"), true);
          moves.add(move15);
          Move move16 = new Move(Field.getFieldNumber("c2"), Field.getFieldNumber("c3"));
          moves.add(move16);
          Move move17 = new Move(Field.getFieldNumber("a8"), Field.getFieldNumber("d8"));
          moves.add(move17);
          Move move18 = new Move(Field.getFieldNumber("c3"), Field.getFieldNumber("c4"));
          moves.add(move18);
//		Move move1a = new Move(Field.getFieldNumber("b3"), Field.getFieldNumber("b4"));
//		moves.add(move1a);
//		
//		/*
//		 * Zug #2 - Schmeiss eine gegnerische Figur
//		 */
//		Move move2 = new Move(Field.getFieldNumber("a1"), Field.getFieldNumber("a7"), true, false, false);
//		moves.add(move2);
//		
//		/*
//		 * Zug #3 - Ziehe vom Feld weiter, von dem eine gegnerische Figur geschmissen wurde
//		 */
//		Move move3 = new Move(Field.getFieldNumber("a7"), Field.getFieldNumber("a3"));
//		moves.add(move3);
//		
//		
//		/*
//		 * Zug #4 (fehlerhaft, da Figur auf angegebenem Feld nicht vorhanden ist!)
//		 * Momentan wird eine nullpointer-exception geworfen, da die Rules-Klasse noch nicht fertig ist.
//		 */
//		Move move4 = new Move(Field.getFieldNumber("d4"), Field.getFieldNumber("d5"));
//		moves.add(move4);
		
//		/*
//		 * Zug #5
//		 */
//		Move move5 = new Move(Field.getFieldNumber("f1"), Field.getFieldNumber("e8"), true, true, true);
//		moves.add(move5);
		
		return moves;
	}

}
