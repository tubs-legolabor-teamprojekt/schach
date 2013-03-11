package engineControl;


import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.TachoPilot;


public class Nxt23_Engine {
  
  //Konstanten für die Bewegungen
    
    //Wert für ein Feld
  final private float ROTATE_BACKWARD_AND_FORWARD = -3.028571428f;
  //Wert für Hoch- und Runterbewegung 
  final private int ROTATE_UP_AND_DOWN = 260; 
 
  
 
  //Motoren-deklaration
  private final TachoPilot Motors_back_and_forward = new TachoPilot(3.2f, 11.4f, Motor.A, Motor.C);
  private final Motor Motor_up_and_down = Motor.B;

  
  //Koordinaten (1-8) von und bis zu welchem Feld
  private int rowFrom;
  private int rowTo;
  
  //Konstante für Anzahl der Felder vom Startpunkt des Roboters
  private final int FIELDS_FROM_START = 4;
  

  
  
  //Speichert bereits zurückgelegte Strecke um wieder zu INIT zurückzukehren
  private int movedDistance_forward_backward;

  
  //Verbindung auf NXT-Block
  private ConnAg con;
  
  /* 
   * Standard Konstruktor
   */
  public Nxt23_Engine() {
      
      
    this.movedDistance_forward_backward = 0;
    
    this.Motor_up_and_down.setSpeed(150); 
    this.Motors_back_and_forward.setSpeed(200);
  }
  
  /*
   * Konstruktor mit Koordinatne als Parameter
   */
  
  public Nxt23_Engine(int rowFrom, int rowTo) {
    this.rowFrom = rowFrom;
    this.rowTo = rowTo;
    
    this.movedDistance_forward_backward = 0;

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
        
        int coordinate = which_move/10;
        which_move %= 10 ;
        
        
        switch(which_move) {
            case 1: 
                System.out.println("Bewege mich zur 'Von-Reihe': "+coordinate);
                t1.rowFrom = coordinate+t1.FIELDS_FROM_START;            
                if(t1.moveToRowFrom()) t1.con.sendInt(1);
                break;
            case 2:
                System.out.println("Bewege mich zur 'Zu-Reihe': "+coordinate);
                t1.rowTo = coordinate+t1.FIELDS_FROM_START;            
                if(t1.moveToRowTo()) t1.con.sendInt(1);
                break;
            case 3:
                System.out.println("Senke Greifarm");
                if(t1.moveDown()) t1.con.sendInt(1);
                break;
            case 4:
                System.out.println("Fahre Greifarm wieder hoch");
                if(t1.moveUp()) t1.con.sendInt(1);
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
  
  boolean moveDown() {
      this.Motor_up_and_down.rotate(-ROTATE_UP_AND_DOWN);
      return true;
    }
    
    boolean moveUp() {
      this.Motor_up_and_down.rotate(ROTATE_UP_AND_DOWN);
      return true;
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
      
  boolean moveToInit() {
    this.Motors_back_and_forward.travel(-this.movedDistance_forward_backward*ROTATE_BACKWARD_AND_FORWARD);

    this.movedDistance_forward_backward = 0;
    return true;
  }
  


}
