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
        
        //Künstliches Move Objekt
        Move testmove1 = new Move((byte)1,9,25,false);
        m.setMovefigure(testmove1);
        
        m.MoveRobot();
        
        Move testmove2 = new Move((byte) 1,2,13,false);
        
        m.setMovefigure(testmove2);
        m.MoveRobot();
        
        
        

       
        
    }
    
    public void MoveRobot() {
        int concatenatedCoords = this.createIntForSending();
        
        this.con_Nxt25.sendInt(concatenatedCoords);
        
       
        
        /*
        int i = 0;
        while (this.gameExists) {
            if (i != 1) {
                this.con_Nxt25.sendInt(i);
                //this.con_Nxt23.sendInt(i);
            }
            else {
                this.con_Nxt25.sendInt(concatenatedCoords);
                //this.con_Nxt23.sendInt(concatenatedCoords);
            }
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (i == 5) {
                this.gameExists = false;
            }
            i++;
            
            
            
            //m.gameExists = false;
        }
        */
    }
    
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

    public void setGameExists(boolean gameExists) {
        this.gameExists = gameExists;
    }

    public void setMovefigure(Move movefigure) {
        this.movefigure = movefigure;
    }

}
