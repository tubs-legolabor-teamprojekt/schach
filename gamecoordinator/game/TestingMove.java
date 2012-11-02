package game;

import components.*;

public class TestingMove
{
	public static void main(String[] args) {
		/**
		 * ZUG #1
		 */
		// Bauer soll von Feld b3 (17) nach Feld c4 (27) gehen,
		// die dortige Figur schmeissen,
		// und den Gegner Schach setzen
		
		// Zug erstellen
		Move move = new Move(Field.getFieldNumber("b3"), Field.getFieldNumber("c4"), true, true, false);
		
		System.out.println(move.getAlgebraicNotation());
		System.out.println(move.getMoveAsText());
		
		/**
		 * ZUG #2
		 */
		// Bauer soll nun von Feld c7 (51) nach Feld d8 (60) gehen,
		// die dortige Figur schmeissen,
		// den Gegner Schach setzen,
		// und sich in eine Dame wandeln
		
		// Zug erstellen
		Move move2 = new Move(Field.getFieldNumber("c7"), Field.getFieldNumber("d8"), true, true, false);
		move2.setPawnPromotedTo('Q');
		System.out.println(move2.getAlgebraicNotation());
		System.out.println(move2.getMoveAsText());
		
		
		/**
		 * ZUG #3
		 */
		// Schwarzer Koenig geht von Feld h8 (62) nach g7 (55)
		Move move3 = new Move(Field.getFieldNumber("h8"), Field.getFieldNumber("g7"));
		System.out.println(move3.getAlgebraicNotation());
		System.out.println(move3.getMoveAsText());
	}

}
