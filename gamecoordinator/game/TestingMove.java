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
		
		// Weissen Bauer erstellen
		byte colorWhite = 0;
		Figure bauer = new FigurePawn(colorWhite);
		
		// Zug erstellen
		Move move = new Move(Field.getFieldNumber("b3"), Field.getFieldNumber("c4"), bauer, true, true, false, false, ' ');
		
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
		Move move2 = new Move(Field.getFieldNumber("c7"), Field.getFieldNumber("d8"), bauer, true, true, false, true, 'Q');
		System.out.println(move2.getAlgebraicNotation());
		System.out.println(move2.getMoveAsText());
		
		
		/**
		 * ZUG #3
		 */
		// Schwarzer Koenig geht von Feld h8 (62) nach g7 (55)
		byte colorBlack = 1;
		Figure koenig = new FigureKing(colorBlack);
		Move move3 = new Move(Field.getFieldNumber("h8"), Field.getFieldNumber("g7"), koenig);
		System.out.println(move3.getAlgebraicNotation());
		System.out.println(move3.getMoveAsText());
	}

}
