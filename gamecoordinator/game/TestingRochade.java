package game;

import java.util.ArrayList;
import java.util.List;

import components.Field;

import util.ChessfigureConstants;

public class TestingRochade
{

    public static void main(String[] args) {
        // Simulierte Zuege erstellen
        Chess game = new Chess(getMoves());
        game.startGame();
    }
    
    public static List<Move> getMoves() {
        List<Move> moves = new ArrayList<Move>();
        
        Move bewegeLaeufer   = new Move (ChessfigureConstants.WHITE, Field.getFieldNumber("f1"), Field.getFieldNumber("c4"));
        Move bewegeSpringer  = new Move (ChessfigureConstants.WHITE, Field.getFieldNumber("g1"), Field.getFieldNumber("g3"));
        
        Move rochadeKingSide = new Move (ChessfigureConstants.WHITE, 1, 1);
        rochadeKingSide.setKingSideCastling(true);
        
        moves.add(bewegeLaeufer);
        moves.add(bewegeSpringer);
        moves.add(rochadeKingSide);
        
        return moves;
    }

}
