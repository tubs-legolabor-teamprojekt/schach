package engineControl;


import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.nxt.remote.RemoteMotor;
import lejos.robotics.navigation.TachoPilot;


public class GetDistance {
  
  
  final private float ROTATE_FOR_Z1 = -3.1f;
  final private int ROTATE_FOR_Z4 = 25;
  
  private final TachoPilot MA1_MA2 = new TachoPilot(3.2f, 11.4f, Motor.A, Motor.C);
  private final Motor MC2 = Motor.B;
  
  
  private int rowFrom;
  private int rowTo;
  
  private int movedDistance;
  
  private ConnAg con;
  
  public GetDistance() {
    
  }
  
  public GetDistance(int rowFrom, int rowTo) {
    this.rowFrom = rowFrom;
    this.rowTo = rowTo;
    
    this.movedDistance = 0;
  }
  
  
  public static void main(String args[]) {
    
    GetDistance t1 = new GetDistance(1,7);
    
    for(int i = 0;i < 5;i++) {
        t1.moveToRowFrom();
        if(i == 0) t1.grabfigure();
        t1.moveToRowTo();
        if(i == 4) t1.dropfigure(); 
        t1.moveToInit();
        
    }
    
    
    
  }
  
  boolean moveToRowFrom() {
    int distance = this.rowFrom - this.movedDistance;
    
    this.MA1_MA2.travel(distance*ROTATE_FOR_Z1);
    this.movedDistance += distance;

    return true;
  }
  
  boolean moveToRowTo() {
    int distance = this.rowTo - this.movedDistance;
    
    this.MA1_MA2.travel(distance*ROTATE_FOR_Z1);
    this.movedDistance += distance;

    return true;
  }
  
  boolean grabfigure() {
    this.MC2.rotate(ROTATE_FOR_Z4);
    return true;
  }
  
  boolean dropfigure() {
    this.MC2.rotate(-ROTATE_FOR_Z4);
    return true;
  }
  
    
  boolean moveToInit() {
    this.MA1_MA2.travel(-this.movedDistance*ROTATE_FOR_Z1);
    this.movedDistance = 0;
    return true;
  }
  
  void setRowFrom(int coordinates) {
      this.rowFrom = (coordinates/1000)%10;
  }
  
  void setRowTo(int coordinates) {
      this.rowTo = (coordinates/10)%10;
  }
  

}
