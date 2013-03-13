package engineControl;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.TachoPilot;


public class Nxt25_Engine {


  
  
  final private int ROTATE_FOR_GRABBING = 90;
  final private float ROTATE_LEFT_RIGHT = 29.87f;
 
  
  
 
  private final Motor Motor_grabbing = Motor.C;
  private final TachoPilot Motor_left_right = new TachoPilot(3.2f, 11.4f, Motor.A, Motor.B);


  //Zielfeld
  private int target_column;
  
  //Verbindung auf NXT-Block
  private ConnAg con;

  
  //Feld über dem der Roboter sich im moment befindet
  private int current_field;
  
  
  Nxt25_Engine() {
      
      
      this.current_field = 0;
      this.Motor_left_right.setSpeed(210);
  
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
          
          t1.target_column = which_move/10 -1;
          which_move %= 10 ;
          
          
          switch(which_move) {
              case 1: 
                  System.out.println("Bewege mich zur Spalte: "+t1.target_column);
                  if(t1.moveToColumn()) t1.con.sendInt(1);
                  break;
              /*case 2:
                  System.out.println("Bewege mich zur 'Zu-Spalte': "+coordinate);
                  t1.columnTo = coordinate;            
                  if(t1.moveToColumnTo()) t1.con.sendInt(1);
                  break;*/
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
  
  
 
    /*
     * Methode zur Links-Rechts-Bewegung
     */
  
    boolean moveToColumn() {
        //Distanz welche er zurücklegen soll, Zielfeld-momentanes Feld
        int distance = this.target_column - this.current_field;
        
                
        //Führe Bewegung aus
        this.Motor_left_right.travel(distance*(this.ROTATE_LEFT_RIGHT/7));
        
        //derzeitiges Feld wird gespeichert
        this.current_field = this.target_column;
        
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
        this.Motor_left_right.travel(-this.current_field*(this.ROTATE_LEFT_RIGHT/7));
                
       this.current_field = 0;
        return true;
      }
  
}
