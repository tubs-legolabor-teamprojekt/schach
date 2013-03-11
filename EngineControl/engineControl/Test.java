package engineControl;

import lejos.nxt.Motor;
import lejos.robotics.navigation.TachoPilot;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        int ROTATE_UP_AND_DOWN = 260;
        Motor Motor_up_and_down = Motor.B;
        Motor_up_and_down.setSpeed(150); 
        
        Motor_up_and_down.rotate(-ROTATE_UP_AND_DOWN);
        
        
        try{
            Thread.sleep(2000);
        } catch(Exception e) {
            
        }
        
        Motor_up_and_down.rotate(ROTATE_UP_AND_DOWN);
        
        
      
      
        
    }
    

}
