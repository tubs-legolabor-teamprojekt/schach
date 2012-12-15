package game;

import java.io.*;
import java.util.LinkedList;

/*
 * @author Marcel Schubert
 * 
 * Klasse für schnelles einlesen von gespielten Spielen 
 * fuer Testzwecke
 */

public class GameParser {
    private LinkedList<String> stringList = new LinkedList<String>();
    String inputFileName;

    public GameParser(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    /*
     * Liest die zuvor durch den Konstruktor uebermittelte Textdatei ein und
     * liest diese Zeilenweise aus
     */
    public void read() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(inputFileName));
            String line = null;
            while ((line = in.readLine()) != null) {
                splitLine(line);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("GameParser " + inputFileName + " fehlt!");
        }
    }

    /*
     * Filtern der Werte aus dem eingelesenen String sowie anfuegen an
     * LinkedList
     * 
     * Kann modifiziert werden fuer unterschiedliche Notationen ...
     */
    private void splitLine(String line) {
        String[] moveFrom, moveTo;
        moveFrom = line.split("-");
        moveTo = moveFrom[0].split(":");
        stringList.addFirst(moveFrom[1].replaceAll(" ", ""));
        stringList.addFirst(moveTo[1].replaceAll(" ", ""));
    }

    /*
     * @return LinkedListe<String> mit zuvor eingelesenen Zuegen
     */
    public LinkedList getMoves() {
        return stringList;
    }
}
