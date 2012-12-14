
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Sound;
import lejos.pc.comm.*;

/**
 * Diese Klasse luft auf dem Server und stellt eine Verbindung zum bereits
 * wartenden Gert her. Der Name, der bei der Initialisierung des Objektes
 * bergeben wird, ist der Name des Gertes. Hier wre also "NXT_23" ein
 * geeigneter Parameter.
 * 
 * @author Tobias Hundt, 4049582
 * @version 0.1
 * 
 */
public class ConnServ {

    // create Variables
    private NXTConnector conn = new NXTConnector();

    @SuppressWarnings("unused")
    private boolean connected;

    private DataOutputStream dos;
    private DataInputStream dis;

    /**
     * Constructor of the Connection
     */
    public ConnServ(String name) {
        connected = conn.connectTo("" + name);
        this.dos = conn.getDataOut();
        this.dis = conn.getDataIn();
    }

    /**
     * Get next Int out of the buffer
     * 
     * @return
     */
    public int getInt() {
        try {
            return this.dis.readInt();
        } catch (IOException e) {
            return -2; // Fehler!!!!!!
        }
    }

    /**
     * Write next int to the BT-OutStream
     * 
     * @param i
     */
    public void sendInt(int i) {
        try {
            this.dos.writeInt(i);
            this.dos.flush();
            System.out.println("Wertbertragen");
        } catch (IOException e) {
            // Do nothing
        }
    }

}
