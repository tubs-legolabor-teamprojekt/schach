package engineControl;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        float ROTATE_BACKWARD_AND_FORWARD = -3.028571428f;
        ColorSensor senseColor = new ColorSensor(SensorPort.S1);

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
        Motors_back_and_forward.travel(ROTATE_BACKWARD_AND_FORWARD);
        
        
        try{
            Thread.sleep(2000);
        } catch(Exception e) {
            
        }
        
        
        
        while(senseColor.getColorNumber() != 13) {
            Motors_back_and_forward.forward();
        }
        Motors_back_and_forward.stop();
        
        
        
      
      
        
    }
    

}
