package engineControl;
import game.Move;


public class MoveRobot {
  
  private Nxt25_EngineControl Engine1;
  private Nxt23_EngineControl Engine2;
  private Move movefigure;
  
  public MoveRobot(Move movefigure) {
    this.movefigure = movefigure;
    
    
    Engine1 = new Nxt25_EngineControl();
    Engine2 = new Nxt23_EngineControl();
    
  }

  void setMovefigure(Move movefigure) {
    this.movefigure = movefigure;
  }

  boolean startrobot() {
    
    int[] coordinates = this.getCoordinates();
    
    this.Engine1.setRow(coordinates[1], coordinates[3]);
    this.Engine2.setColumn(coordinates[0], coordinates[2]);
    
    
    if (!this.movefigure.isCaptured()) {
      
      
      this.Engine1.moveToRowFrom();
      this.Engine2.moveToColumnFrom();
      
      this.Engine2.moveDown();
      this.Engine1.grabfigure();
      
      this.Engine2.moveUp();
      
      this.Engine1.moveToRowTo();
      this.Engine2.moveToColumnTo();
      
      this.Engine2.moveDown();
      this.Engine1.dropfigure();
      
      this.Engine2.moveUp();
      
      this.Engine1.moveToInit();
      this.Engine2.moveToInit();
      
      return true;
      
          
    }
    
    else {
      
      //Beseitige geschlagene Figur
      this.Engine1.moveToRowTo();
      this.Engine2.moveToColumnTo();
      
      this.Engine2.moveDown();
      this.Engine1.grabfigure();
      this.Engine2.moveUp();
      
      this.Engine1.moveToInit();
      this.Engine2.moveToInit();
      
      this.Engine2.moveDown();
      this.Engine1.dropfigure();
      
      this.Engine2.moveUp();
      
      //BewegeFigur
      this.Engine1.moveToRowFrom();
      this.Engine2.moveToColumnFrom();
      
      this.Engine2.moveDown();
      this.Engine1.grabfigure();
      
      this.Engine2.moveUp();
      
      this.Engine1.moveToRowTo();
      this.Engine2.moveToColumnTo();
      
      this.Engine2.moveDown();
      this.Engine1.dropfigure();
      
      this.Engine2.moveUp();
      
      this.Engine1.moveToInit();
      this.Engine2.moveToInit();
      
      return true;
      
    }
    
    
    
  }
  /**
   * 
   * 
   * @return int[] x_y Koordinaten f?r die Bewegung
   */
  
  int[] getCoordinates() {
    int[] x_y = new int[4];
    
    int FieldFrom = this.movefigure.getFieldFrom();
    int FieldTo = this.movefigure.getFieldTo();
        
    
    if (FieldFrom % 8 == 0) {
      x_y[0] = 8;
      x_y[1] = FieldFrom / (8 - (FieldFrom % 8));
    }
    else  {
      x_y[0] = FieldFrom % 8;
      x_y[1] = FieldFrom / (8 - (FieldFrom % 8));
    }
    
    if (FieldTo % 8 == 0) {
      x_y[2] = 8;
      x_y[3] = FieldTo / (8 - (FieldTo % 8));
    }
    else  {
      x_y[2] = FieldTo % 8;
      x_y[3] = FieldTo / (8 - (FieldTo % 8));
    }
    
      
    return x_y;
    
  }
  
  

}
