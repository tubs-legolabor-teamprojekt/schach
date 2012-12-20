package engineControl;
import lejos.nxt.Motor;
import lejos.robotics.navigation.TachoPilot;


public class Nxt25_EngineControl {

  /*
   * z1 ist die Strecke zwischen den Felder f?r das Vorw?rts und R?ckw?rtsbewegen
   * z2 ist die Strecke f?r die Hoch/Runter bewegung
   * z3 ist die Strecke zwischen den Feldern f?r die Links/rechts bewegung
   * z4 ist die Rotation f?r das auf und zu greifen 
   */
  
  
  final private int ROTATE_FOR_Z1 = 10;
  final private int ROTATE_FOR_Z4 = 5;
  
  private final TachoPilot MA1_MA2 = new TachoPilot(3.2f, 11.4f, Motor.A, Motor.B);
  private final Motor MC2 = Motor.C;
  
  
  private int rowFrom;
  private int rowTo;
  
  private int movedDistance;
  
  
  
  
  Nxt25_EngineControl() {

  }
  
  Nxt25_EngineControl(int rowFrom, int rowTo) {
    this.rowFrom = rowFrom;
    this.rowTo = rowTo;
  }
  
  void setRow(int rowFrom, int rowTo) {
    this.rowFrom = rowFrom;
    this.rowTo = rowTo;
    
  }
  
  boolean moveToRowFrom() {
    int distance = this.rowFrom - this.movedDistance;
    
    this.MA1_MA2.travel(this.movedDistance*ROTATE_FOR_Z1);
    this.movedDistance += distance;

    return true;
  }
  
  boolean moveToRowTo() {
    int distance = this.rowTo - this.movedDistance;
    
    this.MA1_MA2.rotate(this.movedDistance*ROTATE_FOR_Z1);
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
