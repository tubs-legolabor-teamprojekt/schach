package engineControl;


import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.TachoPilot;


public class MoveTest {
  
  
  final private float ROTATE_FOR_Z1 = -3.5f;
  final private int ROTATE_FOR_Z4 = 25;
  
  private final TachoPilot MA1_MA2 = new TachoPilot(3.2f, 11.4f, Motor.A, Motor.C);
  private final Motor MC2 = Motor.B;
  
  
  private int rowFrom;
  private int rowTo;
  
  private int movedDistance;
  
  private ConnAg con;
  
  public MoveTest() {
    
  }
  
  public MoveTest(int rowFrom, int rowTo) {
    this.rowFrom = rowFrom;
    this.rowTo = rowTo;
    
    this.movedDistance = 0;
  }
  
  
  public static void main(String args[]) {
    
    MoveTest t1 = new MoveTest(1,5);
    boolean gameExists = true;
    t1.con = new ConnAg();
    
    while(gameExists) {
        
            
    }
    
    
    
    /*
    t1.moveToRowFrom();
    
    t1.grabfigure();
    
    t1.moveToRowTo();
    
    t1.dropfigure();
    
    t1.moveToInit();
  
    */
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

}
