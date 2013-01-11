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
        
        /*
        // Test: kurze weiße Rochade
        
        Move bewegeLaeufer   = new Move (ChessfigureConstants.WHITE, Field.getFieldNumber("f1"), Field.getFieldNumber("c4"));
        Move bewegeSpringer  = new Move (ChessfigureConstants.WHITE, Field.getFieldNumber("g1"), Field.getFieldNumber("g3"));
        
        Move rochadeKingSide = new Move (ChessfigureConstants.WHITE, 1, 1);
        rochadeKingSide.setKingSideCastling(true);
        
        moves.add(bewegeLaeufer);
        moves.add(bewegeSpringer);
        moves.add(rochadeKingSide);*/
        
        /*
        // Test: kurze schwarze Rochade 
        Move bewegeLaeufer   = new Move (ChessfigureConstants.BLACK, Field.getFieldNumber("f8"), Field.getFieldNumber("c5"));
        Move bewegeSpringer  = new Move (ChessfigureConstants.BLACK, Field.getFieldNumber("g8"), Field.getFieldNumber("g6"));
        
        Move rochadeKingSide = new Move (ChessfigureConstants.BLACK, 1, 1);
        rochadeKingSide.setKingSideCastling(true);
        
        moves.add(bewegeLaeufer);
        moves.add(bewegeSpringer);
        moves.add(rochadeKingSide);*/
        
        /*
        // Test lange weiße Rochade
        
        Move bewegeLaeufer   = new Move (ChessfigureConstants.WHITE, Field.getFieldNumber("c1"), Field.getFieldNumber("f4"));
        Move bewegeSpringer  = new Move (ChessfigureConstants.WHITE, Field.getFieldNumber("b1"), Field.getFieldNumber("b3"));
        Move bewegeDame      = new Move (ChessfigureConstants.WHITE, Field.getFieldNumber("d1"), Field.getFieldNumber("g4"));
        
        Move rochadeQueenSide = new Move (ChessfigureConstants.WHITE, 1, 1);
        rochadeQueenSide.setQueenSideCastling(true);
        
        moves.add(bewegeLaeufer);
        moves.add(bewegeSpringer);
        moves.add(bewegeDame);
        moves.add(rochadeQueenSide);*/
        
        // Test: lange schwarze Rochade
        
        Move bewegeLaeufer   = new Move (ChessfigureConstants.BLACK, Field.getFieldNumber("c8"), Field.getFieldNumber("f5"));
        Move bewegeSpringer  = new Move (ChessfigureConstants.BLACK, Field.getFieldNumber("b8"), Field.getFieldNumber("b6"));
        Move bewegeDame      = new Move (ChessfigureConstants.BLACK, Field.getFieldNumber("d8"), Field.getFieldNumber("g5"));
        
        Move rochadeQueenSide = new Move (ChessfigureConstants.BLACK, 1, 1);
        rochadeQueenSide.setQueenSideCastling(true);
        
        moves.add(bewegeLaeufer);
        moves.add(bewegeSpringer);
        moves.add(bewegeDame);
        moves.add(rochadeQueenSide);
        
        return moves;
    }

}
