package engineControl;

import lejos.nxt.Motor;
import lejos.robotics.navigation.TachoPilot;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        float ROTATE_BACKWARD_AND_FORWARD = -3.028571428f;
        TachoPilot Motors_back_and_forward = new TachoPilot(3.2f, 11.4f, Motor.A, Motor.C);
        Motors_back_and_forward.setSpeed(200); 
        
        Motors_back_and_forward.travel(ROTATE_BACKWARD_AND_FORWARD);
        Motors_back_and_forward.travel(ROTATE_BACKWARD_AND_FORWARD);
        Motors_back_and_forward.travel(ROTATE_BACKWARD_AND_FORWARD);
        Motors_back_and_forward.travel(ROTATE_BACKWARD_AND_FORWARD);
        Motors_back_and_forward.travel(ROTATE_BACKWARD_AND_FORWARD);
        Motors_back_and_forward.travel(ROTATE_BACKWARD_AND_FORWARD);
        Motors_back_and_forward.travel(ROTATE_BACKWARD_AND_FORWARD);
        Motors_back_and_forward.travel(ROTATE_BACKWARD_AND_FORWARD);
        Motors_back_and_forward.travel(ROTATE_BACKWARD_AND_FORWARD);
        Motors_back_and_forward.travel(ROTATE_BACKWARD_AND_FORWARD);
        Motors_back_and_forward.travel(ROTATE_BACKWARD_AND_FORWARD);
        Motors_back_and_forward.travel(ROTATE_BACKWARD_AND_FORWARD);
        
        
        try{
            Thread.sleep(2000);
        } catch(Exception e) {
            
        }
        
        Motors_back_and_forward.travel(-12*ROTATE_BACKWARD_AND_FORWARD);
        
        
      
      
        
    }
    

}
