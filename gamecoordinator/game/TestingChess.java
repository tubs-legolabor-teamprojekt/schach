package game;

import game.GameSettings.GameType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import util.ChessfigureConstants;

import components.Field;

public class TestingChess {

    public static void main(String[] args) {
        
        /*
         * Startet das Spiel nach den aktuellen Einstellungen.
         * Die Einstellungen werden aus der Klasse GameSettings
         * übernommen.
         */
        startGame();
        
    }
    
    /**
     * Startet ein Spiel je nach Einstellungen der GameSettings-Klasse
     */
    public static void startGame()
    {
        if ((   GameSettings.currentGameType == GameType.Simulated ||
                GameSettings.currentGameType == GameType.PlayerVsSimulatedComputer ||
                GameSettings.currentGameType == GameType.SimulatedWithRobot) &&
            GameSettings.simulatedGameMoves.length() > 0) {
            // Simulierte Zuege erstellen
            Chess game = new Chess(getMoves());
            game.startGame();
        } else if (GameSettings.currentGameType == GameType.PlayerVsComputer ||
                GameSettings.currentGameType == GameType.ComputerVsComputer ||
                GameSettings.currentGameType == GameType.PlayerWithoutCameraVsComputer) {
            
            //Chess game = new Chess();
            //game.startGame();
            
            
            // Spiel ab vorgegebener Spielsituation
           
            HashMap<Integer, Byte> givenSituation = new HashMap<Integer, Byte>();
            
            // Situation, bei der der Gegner in einem Zug Schachmatt ist (Weisser Springer von g4 nach h6)
            givenSituation.put(Field.getFieldNumber("f1"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.ROOK, true));
            givenSituation.put(Field.getFieldNumber("g1"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.KING, true));
            givenSituation.put(Field.getFieldNumber("a2"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.PAWN, true));
            givenSituation.put(Field.getFieldNumber("b2"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.BISHOP, true));
            givenSituation.put(Field.getFieldNumber("c2"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.PAWN, true));
            givenSituation.put(Field.getFieldNumber("f2"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.PAWN, true));
            givenSituation.put(Field.getFieldNumber("h2"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.PAWN, true));
            givenSituation.put(Field.getFieldNumber("b3"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.PAWN, true));
            givenSituation.put(Field.getFieldNumber("g3"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.PAWN, true));
            givenSituation.put(Field.getFieldNumber("g4"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.KNIGHT, true));
            
            givenSituation.put(Field.getFieldNumber("d8"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.ROOK, true));
            givenSituation.put(Field.getFieldNumber("f8"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.ROOK, true));
            givenSituation.put(Field.getFieldNumber("g8"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.KING, true));
            givenSituation.put(Field.getFieldNumber("a7"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.PAWN, true));
            givenSituation.put(Field.getFieldNumber("b7"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.BISHOP, true));
            givenSituation.put(Field.getFieldNumber("c7"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.PAWN, true));
            givenSituation.put(Field.getFieldNumber("f7"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.PAWN, true));
            givenSituation.put(Field.getFieldNumber("h7"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.PAWN, true));
            givenSituation.put(Field.getFieldNumber("b6"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.PAWN, true));
            givenSituation.put(Field.getFieldNumber("g6"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.PAWN, true));
            
            /*
             * Spielsituation, bei der der Gegner nach einem Zug Schachmatt ist.
             * 
            givenSituation.put(Field.getFieldNumber("b7"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.QUEEN, true));
            givenSituation.put(Field.getFieldNumber("g6"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.KING, true));
            
            givenSituation.put(Field.getFieldNumber("h8"), ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.KING, true));
            */
            
            /*
            Field field = Field.getInstance();
            field.removeFigureAt(2);
            field.removeFigureAt(3);
            field.removeFigureAt(4);
            field.removeFigureAt(6);
            field.removeFigureAt(7);
            
            givenSituation = field.getCurrentFieldAsHashMapWithBytes();*/
            Chess game = new Chess(givenSituation);
            game.startGame();
            
        }
    }

    /**
     * Erstellt simulierte Züge
     * @return
     */
    public static List<Move> getMoves() {
        List<Move> moves = new ArrayList<Move>();
        GameParser g = new GameParser(GameSettings.simulatedGameMoves);
        g.read();
        LinkedList<String> readMoves = g.getMoves();
        
        byte colorOfPlayer = ChessfigureConstants.WHITE;
        int i = 0;
        while(!readMoves.isEmpty()) {
            // Farbe des Spielers bestimmen
            colorOfPlayer = (i % 2 == 0) ? ChessfigureConstants.WHITE : ChessfigureConstants.BLACK;
            
            System.out.println("zug:"+readMoves.get(0)+" "+readMoves.get(1));
             moves.add(new Move(colorOfPlayer, Field.getFieldNumber(readMoves.get(0)), Field.getFieldNumber(readMoves.get(1))));
             readMoves.removeFirst();
             readMoves.removeFirst();
        }
        return moves;
    }

}
