package useful;

import components.Field;

import game.Move;
import gameTree.NextMove;

public class TestingNextMove {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        NextMove moveTo = new NextMove();
        
        components.Field field = null;
        field = Field.getInstance();
        
        Move move = moveTo.getNext(field, (byte)0);
        System.out.println(move.getFieldFrom()+" "+move.getFieldTo()+" "+move.getColorOfPlayer());
        
        

    }

}
