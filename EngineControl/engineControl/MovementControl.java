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
    
    //Singleton-Contructor creates connection to nxt25 and nxt23 when used
    private MovementControl() {
        this.con_Nxt25 = new ConnServ("NXT_25");
        //this.con_Nxt23 = new ConnServ("NXT_23");
         
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
        Move testmove1 = new Move((byte)1,9,25,false);
        m.setMovefigure(testmove1);
        
        m.MoveRobot();
        
        Move testmove2 = new Move((byte) 1,2,13,false);
        
        m.setMovefigure(testmove2);
        m.MoveRobot();
        
        
        

       
        
    }
    
    /*
     * Methode zur Roboterbewegung des aktuellen move objektes
     * Sendet den Koordianten Integer an die beiden NXT-Bloecke
     * 
     */
    
    public void MoveRobot() {
        int concatenatedCoords = this.createIntForSending();
        
        
        System.out.println("Sending: "+concatenatedCoords);
        this.con_Nxt25.sendInt(concatenatedCoords);
        
        
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
