package engineControl;

import game.Move;

import java.io.IOException;

import lejos.nxt.Button;
import lejos.pc.comm.NXTCommBluecove;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTInfo;

/**
 * Die Klasse MovementControl ist für die Bewegung des Roboters zuständig
 * Dabei handelt es sich um ein Singleton-Entwurfsmuster, damit nur eine Instanz der Klasse erstellt werden kann
 * Beim erstellen der Instanz wird eine Bluetooth Verbindung zwischen Rechner und beiden NXT-Blöcken etabliert
 * Bei Übergabe eines Move-Objektes und einem Aufruf von moveRobot wird die Bewegung ausgeführt
 * 
 * @author Rudolf Martin
 *
 */

public class MovementControl {

    /**
     *  Verbindung zum NXT-25
     */
    private ConnServ con_Nxt25;
    
    /**
     * Verbindung zum NXT-23
     */
    private ConnServ con_Nxt23;
    
    /**
     * 
     */
    private boolean gameExists = true;
    
    
    /**
     * Move-Objekt mit den Informationen über den aktuellen Zug
     */
    private Move movefigure;
    
    /**
     * Instanz der Klasse
     */
    
    private static MovementControl instance = null;
    
   /**
    * Singleton Konstruktor
    * Erstellt Verbindung zu NXT23 und 25
    */
    private MovementControl() {
        this.con_Nxt25 = new ConnServ("NXT_25");
        this.con_Nxt23 = new ConnServ("NXT_23");
         
    }
    
    /**
     * Erstellt eine Instanz der Klasse, falls bereits eine existiert wird auf diese verwiesen
     * 
     * @return MovementControl-Instanz
     */
    public static MovementControl getInstance() {
        if (instance == null) {
            instance = new MovementControl();
            
        }
        return instance;
    }
    
    /*
     *Main-Methode dient nur zu Tests 
     */

    public static void main(String[] args) {

        /*
         * Erstelle Instanz der Klasse zum testen
         */
        //Move testmove1;
        //Move testmove2;
        //Move testmove3;
        //Move testCapture;
        Move testgame;
        
        for (int i = 0;i<11;i++) {
            MovementControl m = MovementControl.getInstance();
            switch(i) {
            case 0: 
                testgame = new Move((byte)1,9,25,false);
                m.setMovefigure(testgame);
                break;
            
            case 1: 
                testgame = new Move((byte)1,13,29,false);
                m.setMovefigure(testgame);
                break;
            case 2: 
                testgame = new Move((byte)1,7,24,false);
                m.setMovefigure(testgame);
                break;
            case 3: 
                testgame = new Move((byte)1,5,32,false);
                m.setMovefigure(testgame);
                break;
            case 4: 
                testgame = new Move((byte)1,9,25,false);
                testgame.setQueenSideCastling(true);
                m.setMovefigure(testgame);
                break;
            case 5: 
                testgame = new Move((byte)1,2,19,false);
                m.setMovefigure(testgame);
                break;
            case 6: 
                testgame = new Move((byte)1,3,10,true);
                m.setMovefigure(testgame);
                break;
            case 7: 
                testgame = new Move((byte)1,1,25,true);
                m.setMovefigure(testgame);
                break;
            case 8: 
                testgame = new Move((byte)1,25,57,false);
                m.setMovefigure(testgame);
                break;
            case 9: 
                testgame = new Move((byte)1,57,1,false);
                m.setMovefigure(testgame);
                break;
            case 10: 
                testgame = new Move((byte)1,32,60,false);
                m.setMovefigure(testgame);
                break;
            
                
            }
            
            
            m.moveRobot();
            
        }
        
        

       
        
    }
    
    /**
     * Methode die den Befehl zur Bewegung nach unten versendet 
     * 
     */
    
    private void moveDown() {
        //An den NXT wird der befehl zur Runterbewegung gesendet
        //Zehnerstelle ist unwichtig, die 3 sagt dem NXT welchen motor er ansprechen muss
        this.con_Nxt23.sendInt(13);
        if(this.con_Nxt23.getInt() == -1) {
            System.out.println("Fehler Bewege Runter");
            System.out.println("Es wurde nicht der richtige Rückgabewert übermittelt");
        }        
    }
    /**
     * Methode die den Befehl zur Bewegung nach oben versendet 
     * 
     */
    
    private void moveUp() {
        //An den NXT wird der befehl zur Runterbewegung gesendet
        //Zehnerstelle ist unwichtig, die 4 sagt dem NXT welchen motor er ansprechen muss
        this.con_Nxt23.sendInt(14);
        if(this.con_Nxt23.getInt() == -1) {
            System.out.println("Fehler Bewege Hoch");
            System.out.println("Es wurde nicht der richtige Rückgabewert übermittelt");
        }
    }
    
    private void grabFigure() {
    	this.con_Nxt25.sendInt(13);
        if (this.con_Nxt25.getInt()==-1) {
            System.out.println("Fehler Greife Figur");
        }
    }
    
    private void dropFigure() {
    	this.con_Nxt25.sendInt(14);
        if(this.con_Nxt25.getInt()==-1) System.out.println("Fehler Figur loslassen"); 
    }
    
    /**
     * Methode zur Bewegung zum übergebenen Feld
     * 
     * @param row Zielreihe
     * @param column Zielspalte
     */
    private void moveToField(int row, int column) {
        row = row*10+1;
        column = column*10+1;
        
        System.out.println("Sende Koordinaten...");
        this.con_Nxt23.sendInt(row);
        this.con_Nxt25.sendInt(column);
        
        
        System.out.println("Warte auf Antwort...");        
        int doneRow = this.con_Nxt23.getInt();
        int doneColumn = this.con_Nxt25.getInt();
        
        if (doneRow != 1 && doneColumn != 1) {
            System.out.println("Es wurde nicht der richtige Wert zurückgegeben, Bewegung unerfolgreich");
        }
        else {
        	System.out.println("Bewegung erfolgreich");
        }
        
    	
    }
    
    /**
     * Methode zur Bewegung zum Startpunkt
     */
    
    private void moveToInit() {
    	
    	//Zehnerstelle ist irrelevant, 5 sagt dem Roboter, dass er moveToInit verwenden soll
    	this.con_Nxt23.sendInt(15);
        this.con_Nxt25.sendInt(15);
        
        
        int doneRow = this.con_Nxt23.getInt();
        int doneColumn = this.con_Nxt25.getInt();
        
        if (doneRow != 1 && doneColumn != 1) {
            System.out.println("Es wurde nicht der richtige Wert zurückgegeben, Bewegung unerfolgreich");
        }
        else {
        	System.out.println("Bewegung erfolgreich"); 
        }
    }
    
    /**
     * Methode zur Roboterbewegung des aktuellen move objektes
     * Sendet Koordinaten an die beiden NXT-Bloecke
     * 
     * @return True bei erfolgreicher Bewegung
     */
    
    public boolean moveRobot() {        
        /*
         * @see createIntForSending
         */
        int concatenatedCoords = this.createIntForSending();
        
        /*
         * Prüft ob es sich um eine kleine Rochade handelt und führt diese  aus
         */
        
        if(this.movefigure.isKingSideCastling()) {
            this.doKingSideCastling();
            return true;
        }
        
        /*
         * Prüft ob es sich um eine große Rochade handelt und führt diese  aus
         */
        if(this.movefigure.isQueenSideCastling()) {
            this.doQueenSideCastling();
            return true;
            
        }
        
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
      
       rowTo = rowTo*10+1;
        columnTo = columnTo*10+1;
        
        try {
            System.out.println("Warte 5 Sekunden vor dem Senden...");
            Thread.sleep(5000);            
        } catch (InterruptedException e) {
           System.out.println("Thread_Sleep wurde unterbrochen");
        }
        
        //Teste ob auf dem Zielfeld die Figur geschlagen wurde
        //Wenn ja bringe sie erst weg bevor der Zug ausgeführt wird
        if(this.movefigure.isCaptured()) {
            this.removeCapturedFigure(rowTo, columnTo);
        }
        
        
        //Fahre zur ersten Koordinate
        
        /* System.out.println("Sende Koordinaten...");
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
        */
        
        this.moveToField(rowFrom, columnFrom);
        
        //Bewege Runter
        /*this.con_Nxt23.sendInt(13);
        if(this.con_Nxt23.getInt() == -1) {
            System.out.println("Fehler Bewege Runter");
        }*/
        this.moveDown();
       
       
        
       
        
        //Greife Figur
       /* this.con_Nxt25.sendInt(13);
        if (this.con_Nxt25.getInt()==-1) {
            System.out.println("Fehler Greife Figur");
        }*/
        
        this.grabFigure();
        
        
        //Bewege Hoch
       /* this.moveUp();
        this.con_Nxt23.sendInt(14);
        if (this.con_Nxt23.getInt()==-1) {
            System.out.println("Fehler Bewege Hoch");
        }*/
        
        this.moveUp();
        
        //Fahre zur 2.Koordinate
        /*System.out.println("Sende Koordinaten2...");
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
        doneColumn = 0;*/
        
        this.moveToField(rowTo, columnTo);
        
        //Fahre Runter
       /* this.con_Nxt23.sendInt(13);
        if(this.con_Nxt23.getInt()==-1) System.out.println("Fehler Bewege Runter");
        */
        this.moveDown();
        
        //Lasse Figur los
//        this.con_Nxt25.sendInt(14);
//        if(this.con_Nxt25.getInt()==-1) System.out.println("Fehler Figur loslassen");
        this.dropFigure();
        
        //Fahre hoch
//        this.con_Nxt23.sendInt(14);
//        if(this.con_Nxt23.getInt()==-1) System.out.println("Fehler Bewege Hoch");
        this.moveUp();
        
        
        //Bewege zum start
        /*this.con_Nxt23.sendInt(15);
        this.con_Nxt25.sendInt(15); 
        doneRow = this.con_Nxt23.getInt();
        doneColumn = this.con_Nxt25.getInt();
        
        if (doneRow != 1 && doneColumn != 1) {
            System.out.println("Es wurde nicht der richtige Wert zurückgegeben, Bewegung unerfolgreich");
        }
        System.out.println("Wenn hier 1 1 steht ist alles richtig gelaufen: "+doneRow+" "+doneColumn);
        */
        
        this.moveToInit();
        System.out.println("Zug beendet");
        return true;
    }
    
    /* 
     * 
     * Methode für die kleine Rochade
     * Werte sind hart gecoded
     * 
     * 
     */
    public void doKingSideCastling() {
        
        
        //Koordinaten für die erste Bewegung (König von Feld  4 auf Feld 2)
        int rowFrom = 11;
        int columnFrom = 41;
                
        int rowTo = 11;
        int columnTo = 21;
        
        
        //Rückgabewerte des NXT nach Beenden der Bewegung
        int doneRow;
        int doneColumn;
        
        //Warte 5 Sekunden vorm Senden um sicherzustellen dass eine Verbindung vorhanden ist               
       try {
            System.out.println("Warte 5 Sekunden vor dem Senden...");
            Thread.sleep(5000);            
        } catch (InterruptedException e) {
           System.out.println("Thread_Sleep wurde unterbrochen");
        }
        
       
        /*
         * In der Schleife wird zunächst der König bewegt, im zweiten Durchgang dann der Turm
         * 
         */
        for(int i = 0;i<2;i++) {   
            if  (i == 1) {
                //Koordinaten um Turm von Feld 1 auf Feld 3 zu setzen 
                columnFrom = 11;
                columnTo = 31;
            }
            //Fahre zur ersten Koordinate
            System.out.println("Sende Koordinaten...");
            this.con_Nxt23.sendInt(rowFrom);
            this.con_Nxt25.sendInt(columnFrom);
            
            
            System.out.println("Warte auf Antwort...");        
            doneRow = this.con_Nxt23.getInt();
            doneColumn = this.con_Nxt25.getInt();
            
            
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
        }    
        
        //Fahre zurück zum start
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
     * 
     * Methode für die große Rochade
     * Werte sind hart gecoded
     * 
     * 
     */
    
    public void doQueenSideCastling() {
      //Koordinaten für die erste Bewegung (König von Feld  4 auf Feld 6)
        int rowFrom = 11;
        int columnFrom = 41;
                
        int rowTo = 11;
        int columnTo = 61;
        
        
        //Rückgabewerte des NXT nach Beenden der Bewegung
        int doneRow;
        int doneColumn;
        
        //Warte 5 Sekunden vorm Senden um sicherzustellen dass eine Verbindung vorhanden ist               
       try {
            System.out.println("Warte 5 Sekunden vor dem Senden...");
            Thread.sleep(5000);            
        } catch (InterruptedException e) {
           System.out.println("Thread_Sleep wurde unterbrochen");
        }
        
       
        /*
         * In der Schleife wird zunächst der König bewegt, im zweiten Durchgang dann der Turm
         * 
         */
        for(int i = 0;i<2;i++) {   
            if  (i == 1) {
                //Koordinaten um Turm von Feld 8 auf Feld 5 zu setzen 
                columnFrom = 81;
                columnTo = 51;
            }
            //Fahre zur ersten Koordinate
            System.out.println("Sende Koordinaten...");
            this.con_Nxt23.sendInt(rowFrom);
            this.con_Nxt25.sendInt(columnFrom);
            
            
            System.out.println("Warte auf Antwort...");        
            doneRow = this.con_Nxt23.getInt();
            doneColumn = this.con_Nxt25.getInt();
            
            
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
        }    
        
        //Fahre zurück zum start
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
     * Methode, welche die geschlagene Figur auf Init-Pos schmeisst
     * 
     */
    public void removeCapturedFigure(int rowTo, int columnTo) {
        int doneRow;
        int doneColumn;
        
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
        
        //Bewege Runter
        this.con_Nxt23.sendInt(13);
        if(this.con_Nxt23.getInt() == -1) System.out.println("Fehler Bewege Runter");
        
        //Greife Figur
        this.con_Nxt25.sendInt(13);
        if (this.con_Nxt25.getInt()==-1) System.out.println("Fehler Greife Figur") ;
        
        
        //Bewege Hoch
        this.con_Nxt23.sendInt(14);
        if (this.con_Nxt23.getInt()==-1) System.out.println("Fehler Bewege Hoch");
        
      //Bewege zum start
        this.con_Nxt23.sendInt(15);
        this.con_Nxt25.sendInt(15); 
        doneRow = this.con_Nxt23.getInt();
        doneColumn = this.con_Nxt25.getInt();
        
        if (doneRow != 1 && doneColumn != 1) {
            System.out.println("Es wurde nicht der richtige Wert zurückgegeben, Bewegung unerfolgreich");
        }
        System.out.println("Wenn hier 1 1 steht ist alles richtig gelaufen: "+doneRow+" "+doneColumn);
        doneRow = 0;
        doneColumn = 0;
        
      //Lasse Figur los
        this.con_Nxt25.sendInt(14);
        if(this.con_Nxt25.getInt()==-1) System.out.println("Fehler Figur loslassen");
        
        
         
                
    }
    
    
    /*
     * Liest FieldFrom und FieldTo aus dem Move Objekt raus und schreibt es in einen
     * konkatenierten Integer Wert der Form: (RowFrom)+(ColumnFrom)+(RowTo)+(ColumnTo)+(Capture) 
     * 
     */
    
    public int createIntForSending() {
        int[] x_y = new int[4];
        
        int FieldFrom = 65-this.movefigure.getFieldFrom();
        int FieldTo = 65-this.movefigure.getFieldTo();
            
        
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
     * Set-Methode für das attribut gameExists
     */

    public void setGameExists(boolean gameExists) {
        this.gameExists = gameExists;
    }

    
    /* 
     * Set-Methode für das Move-Objekt
     * 
     */
    public void setMovefigure(Move movefigure) {
        this.movefigure = movefigure;
    }

}
