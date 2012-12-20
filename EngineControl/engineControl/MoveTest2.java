package engineControl;
import lejos.nxt.Motor;
import lejos.robotics.navigation.TachoPilot;


public class MoveTest2 {
  //Hoch Runter
  final private int ROTATE_FOR_Z2 = -3;
  //Links Rechts
  final private int ROTATE_FOR_Z3 = 25;
  
  private final TachoPilot MB1_MB2 = new TachoPilot(3.2f, 11.4f, Motor.A, Motor.B);
  private final Motor MC1 = Motor.C;
  
  private int columnFrom;
  private int columnTo;
  
  private int movedDistance;
  
  
  MoveTest2() {
    this.movedDistance = 0;
    
  }
  
  MoveTest2(int columnFrom, int columnTo) {
    this.columnFrom = columnFrom;
    this.columnTo = columnTo;
    
    this.movedDistance = 0;
  }
  
  public static void main(String[] args) {
    MoveTest2 t2 = new MoveTest2(1,5);
    t2.MB1_MB2.setSpeed(70);
    t2.MC1.setSpeed(70);
    
    t2.moveToColumnFrom();
    
    t2.moveDown();
    
    t2.moveToColumnTo();
    
    t2.moveUp();
    
    t2.moveToInit();
  }
  
  boolean moveToColumnFrom() {
    int distance = this.columnFrom - this.movedDistance;
    
    this.MC1.rotate(distance*ROTATE_FOR_Z3);
    this.movedDistance += distance;
    
    return true;
  }
  
  boolean moveToColumnTo() {
    int distance = this.columnTo - this.movedDistance;
    
    this.MC1.rotate(distance*ROTATE_FOR_Z3);
    this.movedDistance += distance;
      
    return true;
  }
  
  boolean moveUp() {
    this.MB1_MB2.travel(-ROTATE_FOR_Z2);
    return true;
  }
  
  boolean moveDown() {
    this.MB1_MB2.travel(ROTATE_FOR_Z2);
    return true;
  }
  
  boolean moveToInit() {
    this.MC1.rotate(-this.movedDistance*ROTATE_FOR_Z3);
    this.movedDistance = 0;
    return true;
  }

}


