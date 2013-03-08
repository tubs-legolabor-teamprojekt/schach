package engineControl;
import lejos.nxt.Motor;
import lejos.robotics.navigation.TachoPilot;


public class Nxt25_Engine {


  
  
  final private int ROTATE_FOR_GRABBING = 70;
  
  //Wert für Hoch- und Runterbewegung 
  final private int ROTATE_UP_AND_DOWN = 200;
  
  
  private final Motor Motor_up_and_down = Motor.B;
  private final Motor Motor_grabbing = Motor.C;
  
  
  
  
  //Verbindung auf NXT-Block
  private ConnAg con;
  
  
  
  
  
  Nxt25_Engine() {

      this.Motor_up_and_down.setSpeed(150);   
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
          //Nehme gesendete Coordinaten in Empfang
          int coordinates = t1.con.getInt();
         
          System.out.println("Koordinaten sind: "+coordinates);
          //Wenn keine Koordinaten mehr kommen, d.h -1 übertragen wird beende schleife
          if (coordinates == -1) {
              
                  System.out.println(coordinates+" Beende weil keine coords mehr kommen");
                  try {
                      //Kurz warten damit ich die ausgabe lesen kann ;D
                    Thread.sleep(2000);
                  }
                  catch(Exception e) {
                      System.out.println("fehler 37");
                  }
                  gameExists = false;
                  //break;
          }
          
          //Anderenfalls führe bewegung aus und warte auf nächste koordinate
          else {
              System.out.println(coordinates+" le coord");
              
              t1.moveDown();
              t1.grabfigure();
              t1.moveUp();
              t1.dropfigure();
              
          }
              
              
      }
      
    }
  
  
 
  
  boolean grabfigure() {
    this.Motor_grabbing.rotate(ROTATE_FOR_GRABBING);
    return true;
  }
  
  boolean dropfigure() {
    this.Motor_grabbing.rotate(-ROTATE_FOR_GRABBING);
    return true;
  }
  
  boolean moveDown() {
      this.Motor_up_and_down.rotate(-ROTATE_UP_AND_DOWN);
      return true;
    }
    
    boolean moveUp() {
      this.Motor_up_and_down.rotate(ROTATE_UP_AND_DOWN);
      return true;
    }
  
}
