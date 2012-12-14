package game;

import java.io.*;
import java.util.LinkedList;

public class GameParser{
    private String inputFileName = "text.txt";
    private LinkedList<String> stringList = new LinkedList<String>();

    public GameParser(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public void read() {
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader(inputFileName));
            String line = null;
            while ((line = in.readLine()) != null) {
                splitLine(line);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("GameParser " + inputFileName + " fehlt!");
        }
    }

    private String splitLine(String line) {
        String[] geteilt2, geteilt1;
        geteilt1 = line.split("-");
        geteilt2 = geteilt1[0].split(":");
        stringList.addFirst(geteilt1[1].replaceAll(" ", ""));
        stringList.addFirst(geteilt2[1].replaceAll(" ", ""));
        
        return line + "\n";
    }
    
    public LinkedList getMoves() {
        return stringList;
    }

    /*
    // in Datei "outputFileName" schreiben
    public void writeText(String text) {
        try {
            File file = new File(outputFileName);
            FileWriter fw = new FileWriter(file);
            fw.write(text);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    
        //f.read();
        //f.getMoves();
    
}

