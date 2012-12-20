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
    

    public static void main(String[] args) {

        MovementControl m = new MovementControl();
        //m.con_Nxt25 = new ConnServ("NXT_25");
        //m.con_Nxt23 = new ConnServ("NXT_23");

        int concatenatedCoords = m.createIntForSending();
        
        System.out.println(concatenatedCoords);
        /*
        int i = 0;
        while (m.gameExists) {
            m.con_Nxt25.sendInt(i);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (i == 5) {
                m.gameExists = false;
            }
            i++;
            
            
            
            //m.gameExists = false;
        }
        */
    }
    
    public void MoveRobot() {
        int concatenatedCoords = this.createIntForSending();
        
        System.out.println(concatenatedCoords);     
        this.con_Nxt25 = new ConnServ("NXT_25");
        
        int i = 0;
        while (this.gameExists) {
            this.con_Nxt25.sendInt(i);
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
    }
    
    public int createIntForSending() {
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
        
          

        
        int concatenatedCoords = (((0+x_y[0])*10 + x_y[1])*10 + x_y[2])*10 + x_y[3] ;
        int capture = this.movefigure.isCaptured()? 1:0;
        concatenatedCoords  = concatenatedCoords*10 + capture;
        
        return concatenatedCoords;
      }

    public void setGameExists(boolean gameExists) {
        this.gameExists = gameExists;
    }
    
    public 

    public void setMovefigure(Move movefigure) {
        this.movefigure = movefigure;
    }

}
