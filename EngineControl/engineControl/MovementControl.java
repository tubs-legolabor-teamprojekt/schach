package engineControl;

import game.Move;

import java.io.IOException;

import lejos.nxt.Button;
import lejos.pc.comm.NXTCommBluecove;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTInfo;

public class MovementControl {

    /**
     * @param args
     */

    private boolean gameExists = true;
    private ConnServ con_Nxt25;
    private ConnServ con_Nxt23;
    private Move movefigure;
    private static MovementControl instance = null;
    
    //Singleton-Constructor creates connection to nxt25 and nxt23 when used
    private MovementControl() {
        this.con_Nxt25 = new ConnServ("NXT_25");
        this.con_Nxt23 = new ConnServ("NXT_23");
         
    }
    
    
    public static MovementControl getInstance() {
        if (instance == null) {
            instance = new MovementControl();
            
        }
        return instance;
    }

    public static void main(String[] args) {

        /*
         * Erstelle Instanz der Klasse zum testen
         */
        
        MovementControl m = MovementControl.getInstance();
        
        //Künstliche Move Objekte, welche nacheinander abgearbeitet werden
        //Simulation von richtigem spiel
        Move testmove1 = new Move((byte)1,1,64,false);
        m.setMovefigure(testmove1);
        
        m.MoveRobot();
        
        /* Move testmove2 = new Move((byte) 1,2,13,false);
        
        m.setMovefigure(testmove2);
        m.MoveRobot();
        */
        
        

       
        
    }
    
    /*
     * Methode zur Roboterbewegung des aktuellen move objektes
     * Sendet den Koordianten Integer an die beiden NXT-Bloecke
     * 
     */
    
    public void MoveRobot() {
        
        
        int concatenatedCoords = this.createIntForSending();
        
        
        System.out.println("Konkatenierte Koordinaten: "+concatenatedCoords);
        
        /* 
         * Zu Erst werden die rowFrom und ColumnFrom Koordinaten an beide NXT Blöcke versand, 
         * damit gleichzeitig links rechts und vorwärts Bewegung ausgeführt wird 
         */
        int rowFrom = (concatenatedCoords/1000)%10;
        int columnFrom = concatenatedCoords/10000;
                
        int rowTo = (concatenatedCoords/10)%10;
        int columnTo = (concatenatedCoords/100)%10;
        
        
        rowFrom = rowFrom*10+1;
        columnFrom = columnFrom*10+1;
        
        rowTo = rowTo*10+2;
        columnTo = columnTo*10+1;
        
        System.out.println("rowFrom = "+rowFrom);
        System.out.println("columnFrom = "+columnFrom);
        
        
        try {
            System.out.println("Warte 8 Sekunden vor dem Senden...");
            Thread.sleep(8000);            
        } catch (InterruptedException e) {
           System.out.println("Thread_Sleep wurde unterbrochen");
        }
        
        
        
        System.out.println("Sende Koordinaten...");
        this.con_Nxt23.sendInt(rowFrom);
        this.con_Nxt25.sendInt(columnFrom);
        
        
        System.out.println("Warte auf Antwort...");        
        int doneRow = this.con_Nxt23.getInt();
        int doneColumn = this.con_Nxt25.getInt();
        
        
        if (doneRow != 1 && doneColumn != 1) {
            System.out.println("Es wurde nicht der richtige Wert zurückgegeben, Bewegung unerfolgreich");
        }
        System.out.println("Wenn hier 1 1 steht ist alles richtig gelaufen: "+doneRow+" "+doneColumn);
        doneRow = 0;
        doneColumn = 0;
        
        
        //Bewege Runter
        this.con_Nxt23.sendInt(13);
        if(this.con_Nxt23.getInt() == -1) System.out.println("Fehler Bewege Runter");
        
        //Greife Figur
        this.con_Nxt25.sendInt(13);
        if (this.con_Nxt25.getInt()==-1) System.out.println("Fehler Greife Figur") ;
        
        
        //Bewege Hoch
        this.con_Nxt23.sendInt(14);
        if (this.con_Nxt23.getInt()==-1) System.out.println("Fehler Bewege Hoch");
        
        //Fahre zur 2.Koordinate
        System.out.println("Sende Koordinaten2...");
        this.con_Nxt23.sendInt(rowTo);
        this.con_Nxt25.sendInt(columnTo);
        
        System.out.println("Warte auf Antwort...");        
        doneRow = this.con_Nxt23.getInt();
        doneColumn = this.con_Nxt25.getInt();
        
        if (doneRow != 1 && doneColumn != 1) {
            System.out.println("Es wurde nicht der richtige Wert zurückgegeben, Bewegung unerfolgreich");
        }
        System.out.println("Wenn hier 1 1 steht ist alles richtig gelaufen: "+doneRow+" "+doneColumn);
        doneRow = 0;
        doneColumn = 0;
        
        //Fahre Runter
        this.con_Nxt23.sendInt(13);
        if(this.con_Nxt23.getInt()==-1) System.out.println("Fehler Bewege Runter");
        
        //Lasse Figur los
        this.con_Nxt25.sendInt(14);
        if(this.con_Nxt25.getInt()==-1) System.out.println("Fehler Figur loslassen");
        
        
        //Fahre hoch
        this.con_Nxt23.sendInt(14);
        if(this.con_Nxt23.getInt()==-1) System.out.println("Fehler Bewege Hoch");
        
        //Bewege zum start
        this.con_Nxt23.sendInt(15);
        this.con_Nxt25.sendInt(15); 
        doneRow = this.con_Nxt23.getInt();
        doneColumn = this.con_Nxt25.getInt();
        
        if (doneRow != 1 && doneColumn != 1) {
            System.out.println("Es wurde nicht der richtige Wert zurückgegeben, Bewegung unerfolgreich");
        }
        System.out.println("Wenn hier 1 1 steht ist alles richtig gelaufen: "+doneRow+" "+doneColumn);
        System.out.println("Zug beendet");
        
        
    }
    
    /*
     * Uses the Move-Object to convert the coordinates in one integer value
     * 
     */
    
    public int createIntForSending() {
        int[] x_y = new int[4];
        
        int FieldFrom = this.movefigure.getFieldFrom();
        int FieldTo = this.movefigure.getFieldTo();
            
        
        if (FieldFrom % 8 == 0) {
          x_y[0] = 8;
          x_y[1] = (int) Math.ceil(FieldFrom / 8.0);
        }
        else  {
          x_y[0] = FieldFrom % 8;
          x_y[1] = (int) Math.ceil(FieldFrom / 8.0);
        }
        
        if (FieldTo % 8 == 0) {
          x_y[2] = 8;
          x_y[3] = (int) Math.ceil(FieldTo / 8.0);
        }
        else  {
          x_y[2] = FieldTo % 8;
          x_y[3] = (int) Math.ceil(FieldTo / 8.0);
        }
        
          

        
        int concatenatedCoords = (((0+x_y[0])*10 + x_y[1])*10 + x_y[2])*10 + x_y[3] ;
        int capture = this.movefigure.isCaptured()? 1:0;
        concatenatedCoords  = concatenatedCoords*10 + capture;
        
        return concatenatedCoords;
      }
    
    /* 
     * Method for setting the existence of the game
     * when false then the robot will stop
     */

    public void setGameExists(boolean gameExists) {
        this.gameExists = gameExists;
    }

    
    /* 
     * setting the current move as the attribute
     * 
     */
    public void setMovefigure(Move movefigure) {
        this.movefigure = movefigure;
    }

}
