package engineControl;
 

import lejos.nxt.*;
import lejos.nxt.comm.*;

import java.io.*;

/**
 * Diese Klasse luft auf der Maschine und wartet auf eine eingehende Verbindungsanfrage.
 * Nach Verbindungsaufbau kann sie Integer erhalten und senden.
 * 
 * @author Tobias Hundt, 4049582
 * @version 0.1
 * 
 */
public class ConnAg{

  // Variables
  private DataInputStream dis;
  private DataOutputStream dos;

  /**
   * Constructor of the Connection
   */
  public ConnAg() {
    LCD.drawString("waiting", 0, 0);
    System.out.println("waiting");
    LCD.refresh();

    BTConnection btc = Bluetooth.waitForConnection();

    LCD.clear();
    LCD.drawString("connected", 0, 0);
    System.out.println("connected");
    LCD.refresh();

    dis = btc.openDataInputStream();
    dos = btc.openDataOutputStream();
  }
  
  /**
   * Read next Int out of the buffer
   * @return
   */
  public int getInt(){
    try { 
      return this.dis.readInt();
    } catch (IOException e) {  
      return -1; // Fail!!!!!!
    }
  }
  
  /**
   * Send a new Integer Value via BT
   * @param i
   */
  public void sendInt(int i){
    try {
      this.dos.writeInt(i);
      this.dos.flush();
    } catch (IOException e) {
      // Do nothing
    }
  }
}
