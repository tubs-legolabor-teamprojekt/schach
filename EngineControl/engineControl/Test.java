package engineControl;

import lejos.nxt.Motor;
import lejos.robotics.navigation.TachoPilot;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
     
      int ROTATE_LEFT_RIGHT = -660;
      Motor Motor_left_right = Motor.B;
      Motor_left_right.setSpeed(180);
      
      Motor_left_right.rotate(ROTATE_LEFT_RIGHT);
      Motor_left_right.rotate(-ROTATE_LEFT_RIGHT);
      
      Motor_left_right.rotate(ROTATE_LEFT_RIGHT/7);
      Motor_left_right.rotate(-ROTATE_LEFT_RIGHT/7);
      
      
      
      
        
    }
    

}
