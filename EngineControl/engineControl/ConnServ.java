package engineControl;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Sound;
import lejos.pc.comm.*;

/**
 * Diese Klasse laeuft auf dem Server und stellt eine Verbindung zum bereits wartenden Geraet her.
 * Der Name, der bei der Initialisierung des Objektes bergeben wird, ist der Name des Geraetes.
 * Hier waere also "NXT_23" ein geeigneter Parameter.
 * 
 * @author Tobias Hundt, 4049582
 * @version 0.1
 *
 */
public class ConnServ{

  // create Variables
  private NXTConnector conn = new NXTConnector();
  
  @SuppressWarnings("unused")
  private boolean connected;
  
  private DataOutputStream dos;
  private DataInputStream dis;
  
  private NXTInfo connectionInformation;

  /**
   *  Constructor of the Connection
   */
  public ConnServ(String name) {
    connected = conn.connectTo(""+name);
    this.connectionInformation = conn.getNXTInfo();
    this.dos = conn.getDataOut();
    this.dis = conn.getDataIn();
  }
  
  /**
   * Get Methode für die ConnectionInfo
   */
  public NXTInfo getConnectionInformation() {
      return this.connectionInformation;
  }
  
  /**
   * Get next Int out of the buffer
   * @return
   */
  public int getInt(){
    try {
      return this.dis.readInt();
    } catch (IOException e) {
      return -1; // Fehler!!!!!!
    }
  }
  
  /**
   * Write next int to the BT-OutStream
   * @param i
   */
  public void sendInt(int i){
    try {
      this.dos.writeInt(i);
      this.dos.flush();
    } catch (IOException e) {
      // Do nothing
    } catch (NullPointerException nullE) {
        System.out.println("Senden nicht möglich, Verbindung konnte nicht hergestellt werden.");
        System.exit(0);
    }
  }

}
