package engineControl;


import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.TachoPilot;


public class Nxt23_Engine {
  
  //Konstanten für die Bewegungen
    
    //Wert für ein Feld
  final private float ROTATE_BACKWARD_AND_FORWARD = -3.028571428f;
  
  final private int ROTATE_LEFT_RIGHT = -660;
  
 
  //Motoren-deklaration
  private final TachoPilot Motors_back_and_forward = new TachoPilot(3.2f, 11.4f, Motor.A, Motor.C);
  private final Motor Motor_left_right = Motor.B;

  
  //Koordinaten (1-8) von und bis zu welchem Feld
  private int rowFrom;
  private int rowTo;
  
  private int columnFrom;
  private int columnTo;
  
  
  //Speichert bereits zurückgelegte Strecke um wieder zu INIT zurückzukehren
  private int movedDistance_forward_backward;
  private int movedDistance_left_right;
  
  //Verbindung auf NXT-Block
  private ConnAg con;
  
  /* 
   * Standard Konstruktor
   */
  public Nxt23_Engine() {
      
      
    this.movedDistance_forward_backward = 0;
    this.movedDistance_left_right = 0; 
    
    this.Motor_left_right.setSpeed(150);
    this.Motors_back_and_forward.setSpeed(200);
  }
  
  /*
   * Konstruktor mit Koordinatne als Parameter
   */
  
  public Nxt23_Engine(int rowFrom, int rowTo) {
    this.rowFrom = rowFrom;
    this.rowTo = rowTo;
    
    this.movedDistance_forward_backward = 0;
    this.movedDistance_left_right = 0;
  }
  
  /*
   * main methode läuft als schleife auf nxt solange bis gameExists false ist
   * 
   * 
   */
  public static void main(String args[]) {
    
    //Erstelle Objekt der Bewegungsklasse  
    Nxt23_Engine t1 = new Nxt23_Engine();
   
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
            t1.setRowFrom(coordinates);
            t1.setRowTo(coordinates);
            t1.setColumnFrom(coordinates);
            t1.setColumnTo(coordinates);
            
            t1.moveToRowFrom();
            t1.moveToColumnFrom();
            
            t1.moveToRowTo();
            t1.moveToColumnTo();
            
            t1.moveToInit();
            
            
            
        }
            
            
    }
    
  }
  
  boolean moveToRowFrom() {
    int distance = this.rowFrom - this.movedDistance_forward_backward;
    
    this.Motors_back_and_forward.travel(distance*ROTATE_BACKWARD_AND_FORWARD);
    this.movedDistance_forward_backward += distance;

    return true;
  }
  
  boolean moveToRowTo() {
    int distance = this.rowTo - this.movedDistance_forward_backward;
    
    this.Motors_back_and_forward.travel(distance*ROTATE_BACKWARD_AND_FORWARD);
    this.movedDistance_forward_backward += distance;

    return true;
  }
  
  void setColumnFrom(int coordinates) {
      this.columnFrom = coordinates/10000;
  }
  
  void setColumnTo(int coordinates) {
      this.columnTo = (coordinates/100)%10;
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
  
 
  
    
  boolean moveToInit() {
    this.Motors_back_and_forward.travel(-this.movedDistance_forward_backward*ROTATE_BACKWARD_AND_FORWARD);
    this.Motor_left_right.rotate(-this.movedDistance_left_right*(this.ROTATE_LEFT_RIGHT/7));
    
    
    this.movedDistance_left_right = 0;
    this.movedDistance_forward_backward = 0;
    return true;
  }
  
  void setRowFrom(int coordinates) {
      this.rowFrom = (coordinates/1000)%10;
  }
  
  void setRowTo(int coordinates) {
      this.rowTo = (coordinates/10)%10;
  }
  

}
