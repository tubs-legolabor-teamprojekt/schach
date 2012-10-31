package game;

import java.util.ArrayList;
import java.util.List;

import util.ChessfigureConstants;

import components.*;

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
		Figure bauer = new FigurePawn(ChessfigureConstants.WHITE);
		Move move1 = new Move(Field.getFieldNumber("b2"), Field.getFieldNumber("b3"), bauer);
		
		moves.add(move1);
		
		/*
		 * Zug #2
		 */
		/*
		Figure koenig = new FigureKing(ChessfigureConstants.BLACK);
		Move move2 = new Move(Field.getFieldNumber("b2"), Field.getFieldNumber("b4"), koenig, true, true, true);
		
		moves.add(move2);*/
		
		return moves;
	}

}
