package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import components.Field;

public class TestingChess {

    public static void main(String[] args) {
        // Simulierte Zuege erstellen
        Chess game = new Chess(getMoves());
        game.startGame();
    }

    public static List<Move> getMoves() {
        List<Move> moves = new ArrayList<Move>();
        GameParser g = new GameParser("FlorianF.txt");
        g.read();
        LinkedList<String> readMoves = g.getMoves();
        

        while(!readMoves.isEmpty()) {
            System.out.println("zug:"+readMoves.get(0)+" "+readMoves.get(1));
             moves.add(new Move(Field.getFieldNumber(readMoves.get(0)), Field.getFieldNumber(readMoves.get(1))));
             readMoves.removeFirst();
             readMoves.removeFirst();
        }
        return moves;
    }

}
