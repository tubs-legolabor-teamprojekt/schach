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
        m.con_Nxt25 = new ConnServ("NXT_25");
        m.con_Nxt23 = new ConnServ("NXT_23");

        

        while (m.gameExists) {
            m.con_Nxt25.sendInt(1);
            
            
            m.gameExists = false;
        }

    }

    public void setGameExists(boolean gameExists) {
        this.gameExists = gameExists;
    }

    public void setMovefigure(Move movefigure) {
        this.movefigure = movefigure;
    }

}
