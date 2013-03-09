package engineControl;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.TachoPilot;


public class Nxt25_Engine {


  
  
  final private int ROTATE_FOR_GRABBING = 70;
  final private int ROTATE_LEFT_RIGHT = -660;
 
  
  
 
  private final Motor Motor_grabbing = Motor.C;
  private final Motor Motor_left_right = Motor.B;
  
  
  private int columnFrom;
  private int columnTo;
  
  //Verbindung auf NXT-Block
  private ConnAg con;
  
  private int movedDistance_left_right;
  
  
  
  Nxt25_Engine() {
      
      
      this.movedDistance_left_right = 0;
      this.Motor_left_right.setSpeed(150);
  
      Motor_grabbing.setSpeed(150);
  }
  

  
  
  public static void main(String args[]) {
      
      //Erstelle Objekt der Bewegungsklasse  
      Nxt25_Engine t1 = new Nxt25_Engine();
     
      boolean gameExists = true;
      
      //Erstelle ConnAg Objekt, Warte auf Verbindung
      t1.con = new ConnAg();
      
      
      //While schleife solange spiel existiert
      while(gameExists) {
        //Variable, welche die Koordinate und die Art der Bewegung enthält muss noch aufgeteilt werden
          
          System.out.println("Warte auf Eingabe...");
          int which_move = t1.con.getInt();
          
          System.out.println("Eingabe ist angekommen: "+which_move);
          
          if (which_move == -1) {
              
              System.out.println(which_move+" Fehlerhafte Übertragung");
              try {
                  //Kurz warten damit ich die ausgabe lesen kann ;D
                Thread.sleep(2000);
              }
              catch(Exception e) {
                  System.out.println("Thread unterbrochen");
              }
              Button.waitForPress();
              gameExists = false;

      }
          
          int coordinate = which_move/10 -1;
          which_move %= 10 ;
          
          
          switch(which_move) {
              case 1: 
                  System.out.println("Bewege mich zur 'Von-Spalte': "+coordinate);
                  t1.columnFrom = coordinate;
                  if(t1.moveToColumnFrom()) t1.con.sendInt(1);
                  break;
              case 2:
                  System.out.println("Bewege mich zur 'Zu-Spalte': "+coordinate);
                  t1.columnTo = coordinate;            
                  if(t1.moveToColumnTo()) t1.con.sendInt(1);
                  break;
              case 3:
                  System.out.println("Greife Figur");
                  if(t1.grabfigure()) t1.con.sendInt(1);
                  break;
              case 4:
                  System.out.println("Lasse Figur los");
                  if(t1.dropfigure()) t1.con.sendInt(1);
                  break;
              case 5:
                  System.out.println("Fahre zum Start");
                  if(t1.moveToInit()) t1.con.sendInt(1);
                  break;
              default: 
                  System.out.println("Irgendwas ist schief gelaufen "+which_move);
                  break;
          } 
              
      }
      
    }
  
  
 
  boolean moveToColumnFrom() {
      
      
      int distance = this.columnFrom - this.movedDistance_left_right;      
      this.Motor_left_right.rotate(distance*(this.ROTATE_LEFT_RIGHT/7));
      this.movedDistance_left_right += distance;
      
      return true;
    }
    
    boolean moveToColumnTo() {
       
        
      int distance = this.columnTo - this.movedDistance_left_right;
      
      this.Motor_left_right.rotate(distance*(this.ROTATE_LEFT_RIGHT/7));
      this.movedDistance_left_right += distance;
        
      return true;
    }
    

  
  boolean grabfigure() {
    this.Motor_grabbing.rotate(ROTATE_FOR_GRABBING);
    return true;
  }
  
  boolean dropfigure() {
    this.Motor_grabbing.rotate(-ROTATE_FOR_GRABBING);
    return true;
  }
  
 
    
    boolean moveToInit() {
        this.Motor_left_right.rotate(-this.movedDistance_left_right*(this.ROTATE_LEFT_RIGHT/7));
                
        this.movedDistance_left_right = 0;
        return true;
      }
  
}
