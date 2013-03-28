package gameTree;

import game.Move;
import components.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import useful.*;
import alphaBeta.AlphaBetaSearch;


/**
 * Klasse, die als Hauptklasse der AI dienen soll. Hier sind die Aufrufe für den Game-Coordinator zu finden!
 * Diese Klasse muss instanziiert werden.
 * Auf diese Weise können auch mehrere Klassen erstellt werden, um an schließend die KI trainiren zu können.
 * @author tobi
 *
 */
public class NextMove {
   
//################################################################################# Klassenvariable
    
    // Instanz der Alpha-Beta-Klasse
    private AlphaBetaSearch search;
    
    // Instanz der MoveGenerator-Klasse
    private MoveGenerator moveGen;
    
    // Instanz einer LinkesList, in der jeweils die erste Kindgeneration gespeichert ist
//    private LinkedList<HashMap<Integer, Byte>> liste;
    private LinkedList<SituationWithRating> list;
    
    // Array zur Bewertung der einzelnen Einträge aus der Liste    
//    private ArrayList<Integer> rate = new ArrayList<Integer>();
    
    // HashMaps zum Vergleich und zur Rückgabe
    private HashMap<Integer, Byte> beforeField;
    private HashMap<Integer, Byte> afterField;
    
    private static int INFINITY = 2147483647;
        
//################################################################################# Konstruktor
    
    /**
     * Konstruktor der Klasse NextMove
     * Hier werden die nötigen Instanzen initialisiert
     */
    public NextMove(){
        
//        this.search = new AlphaBetaSearch();
        this.moveGen = new MoveGenerator();
        
    }
    
//################################################################################# Methoden

    /**
     * Hauptmethode der Klasse, die vom Game-Coordinator aufgerufen werden sollte.
     * @param field
     * @param player
     * @return
     */
    public Move getNext(Field field, byte player){
        
        // HashMap<Integer, Byte> zusammenbauen
        beforeField = field.getCurrentFieldAsHashMapWithBytes();

        doChildSituations(player);    
//        System.out.println("done1");
        rateChildSituations(player==0?(byte)1:(byte)0);
//        System.out.println("done2");
        findBestSituationInListMax();
//        System.out.println("done3");
        System.out.println(TextChessField.fieldToString(afterField)); //TODO <--------------------------------Ausgabe???
        return HashMapToMove(beforeField, afterField, player);
    }
    
    /**
     * Finde Situation im Feld, die am besten bewertet wurde
     */
    private void findBestSituationInListMax(){
        // Stelle der am besten bewerteten Situation in der ArrayList
        int help = -INFINITY;
        Random rn = new Random();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getRating()> help){
                help=list.get(i).getRating();
            }
        }
        for( int i = 0 ; i < list.size() ; i+=0 ){
            if(list.get(i).getRating()!=help){ 
                list.remove(i);
                i--;
            }
            i++;
        }
        afterField = list.get(rn.nextInt(list.size())).getMap();
    }
        
    /**
     * Bewerte nacheinander alle in der Liste befindlichen Situationen
     * @param player
     */
    private void rateChildSituations(byte player){
    	int helpcount=0;
    	
    	AlphaBetaSearch[] abThreads= new AlphaBetaSearch[list.size()];
    	
    	
        for (int i = 0; i < list.size(); i++) {
        	//Thread erstellen mit SituationWithRating,depth,player
        	abThreads[i] = new AlphaBetaSearch(list.get(i),3,player);
        	abThreads[i].setName(""+i);
        }
        
        //startet eine bestimmte Anzahl an Threads
        int parallelValue=2;
        long time = System.currentTimeMillis();
        orderedThreadStart(abThreads,parallelValue);
        System.out.println("Zeit: "+(System.currentTimeMillis() - time)+" Anzahl T. "+parallelValue);
        
        LinkedList<SituationWithRating> helpList = new LinkedList<SituationWithRating>();
        for(AlphaBetaSearch ab:abThreads) {
        	helpList.add(ab.getSituationWithRating());
        }
        
        this.list = helpList;
        	
//            list.get(i).setRating(search.min(list.get(i).getMap(), 3, player, -INFINITY, INFINITY));
            
//            System.out.printf("%-3d %d  ", i+1 ,list.get(i).getRating());
//            System.out.print("Zug: "+HashMapMoveToText(beforeField, list.get(i).getMap(), player)+" ");
//            System.out.println("Knoten: " + search.count);
//            helpcount+=search.count;
//            search.count=0;
//        }
//        System.out.println("\n"+"Knotenzahl gesamt: "+helpcount);
    }
    
    /*
     * startet eine bestimmte Anzahl an Threads
     * @param ab Array von Threads des Typ AlphaBetaSearch
     * @param parallelValue Anzahl an gleichzeitig laufenden Threads 
     */
    public boolean orderedThreadStart(AlphaBetaSearch[] ab, int parallelValue) {
    	System.out.println("pv "+parallelValue);
    	System.out.println("ab "+ab.length);
    	//wenn anzahl der möglichen T. kleiner ist als maximale anzahl gleichzeitiger T.
    	//dann gleich alle starten
    	if(parallelValue>=ab.length) {
    		System.out.println("0");
    		for(int i=0; i<ab.length; i++) {
    			ab[i].start();
    		}
    	}
    	else {
    		int counter = ab.length-1;

    		//solange Zuege da sind
    		while(counter>=0) {
    			//duerfen noch Threads gestartet werden?
    			if(ab[0].getNumberOfThreads()<parallelValue) {
    				ab[counter].start();
    				counter--;
    			}
    			//ein wenig abwarten, damit schleife nicht komplett cpu auslastet
    			try {
        			Thread.sleep(1 * parallelValue);
        			}
        			catch(Exception e) {
        			}
    	}
    }
    	//letzten Threads die noch laufen beenden lassen
    	while(ab[0].getNumberOfThreads()!=0) {
    		try {
    			Thread.sleep(1 * parallelValue);
    			}
    			catch(Exception e) {
    			}
    	}
    	return true;
    }
    
    /**
     * Fülle die Liste mit allen möglichen Kindsituationen
     * @param player
     */
    private void doChildSituations(byte player){
    	LinkedList<HashMap<Integer,Byte>> childSit = moveGen.generateMoves(beforeField, player); 
    	LinkedList<SituationWithRating> listConversion = new LinkedList<SituationWithRating>();
    	while(!childSit.isEmpty()) {
    		listConversion.add(new SituationWithRating(childSit.pollLast(),0));
    	}
        list = listConversion;

    }
    
    /**
     * Finde aus dem Unterschied zwischen "beforeMap" und afterMap" ein Move-Objekt
     * @param map1
     * @param map2
     * @param color
     * @return
     */
    private Move HashMapToMove(HashMap<Integer, Byte> map1, HashMap<Integer, Byte> map2, byte color){
        Iterator<Entry<Integer, Byte>> it1 = map1.entrySet().iterator();
        Iterator<Entry<Integer, Byte>> it2 = map2.entrySet().iterator();
        Integer[] int1 = new Integer[map1.size()];
        Byte[] byte1 = new Byte[map1.size()];
        Integer[] int2 = new Integer[map2.size()];
        Byte[] byte2 = new Byte[map2.size()];
        
        /*
         * Variablen zur Erstellung des Move-Objektes
         */
        int from = 0, to = 0;
        byte colorOfPlayer = color;
        boolean captured = false;
        //TODO Rochade
        
        int itCount = 0;
        while (it1.hasNext()) {
            Map.Entry<Integer, Byte> entry1 = (Map.Entry<Integer, Byte>)it1.next();
            int1[itCount] = entry1.getKey();
            byte1[itCount] = entry1.getValue();
            itCount++;            
        }// while
        itCount = 0;
        while (it2.hasNext()) {
            Map.Entry<Integer, Byte> entry2 = (Map.Entry<Integer, Byte>)it2.next();
            int2[itCount] = entry2.getKey();
            byte2[itCount] = entry2.getValue();
            itCount++;            
        }// while
        
        /*
         *  Auswertung der beiden Schachfelder, Unterscheidung nach "einfacher Zug" und "Schlag"
         */        
        if(int1.length == int2.length){ 
            /*
             *  Beide Felder gleiche Länge --> einfacher Zug, kein Schlag
             *  
             *  iff key_1 in int1 und nicht key_1 in int2 folgt key_1 ist "from"
             *  iff key_2 in int2 und nicht key_2 in int1 folgt key_2 ist "to" 
             */
            //TODO Bauernwumwandlung
            captured = false;
            boolean contains= false;
            for( int i = 0 ; i < int1.length ; i++ ){
                for( int j = 0 ; j < int2.length ; j++){
                    if(int1[i]== int2[j]){
                        contains=true;
                    }
                }
                if(!contains){
                    from=int1[i];
                }
                contains = false;
            }
            contains = false;
            for( int j = 0 ; j < int2.length ; j++ ){
                for( int i = 0 ; i < int1.length ; i++){
                    if(int1[i]== int2[j]){
                        contains=true;
                    }
                }
                if(!contains){
                    to=int2[j];                    
                }
                contains = false;
            }
            
        }else{ 
            /*
             *  Beide Felder unterschiedliche Länge --> Zug und Schlag
             */
            captured = true;
            boolean contains = false;
            for( int i = 0 ; i < int1.length ; i++ ){
                for( int j = 0 ; j < int2.length ; j++){
                    if(int1[i]== int2[j]){
                        contains = true;
                    }
                }
                if(!contains){
                    from = int1[i];
                }
                contains=false;
            }    
            for( int j = 0 ; j < int2.length ; j++ ){
                for( int i = 0 ; i < int1.length ; i++ ){
                    if( ( int1[i] == int2[j] ) && ( byte1[i].equals(byte2[j]) == false ) ){
                        to=int1[i];
                    }
                }
                contains=false;
            }
        }
        return new Move(colorOfPlayer, from, to, captured);
        

    }
    
    private String HashMapMoveToText(HashMap<Integer, Byte> map1, HashMap<Integer, Byte> map2, byte color){
        Iterator<Entry<Integer, Byte>> it1 = map1.entrySet().iterator();
        Iterator<Entry<Integer, Byte>> it2 = map2.entrySet().iterator();
        Integer[] int1 = new Integer[map1.size()];
        Byte[] byte1 = new Byte[map1.size()];
        Integer[] int2 = new Integer[map2.size()];
        Byte[] byte2 = new Byte[map2.size()];
        
        /*
         * Variablen zur Erstellung des Move-Objektes
         */
        int from = 0, to = 0;
        byte colorOfPlayer = color;
        boolean captured = false;
        //TODO Rochade
        
        int itCount = 0;
        while (it1.hasNext()) {
            Map.Entry<Integer, Byte> entry1 = (Map.Entry<Integer, Byte>)it1.next();
            int1[itCount] = entry1.getKey();
            byte1[itCount] = entry1.getValue();
            itCount++;            
        }// while
        itCount = 0;
        while (it2.hasNext()) {
            Map.Entry<Integer, Byte> entry2 = (Map.Entry<Integer, Byte>)it2.next();
            int2[itCount] = entry2.getKey();
            byte2[itCount] = entry2.getValue();
            itCount++;            
        }// while
        
        /*
         *  Auswertung der beiden Schachfelder, Unterscheidung nach "einfacher Zug" und "Schlag"
         */        
        if(int1.length == int2.length){ 
            /*
             *  Beide Felder gleiche Länge --> einfacher Zug, kein Schlag
             *  
             *  iff key_1 in int1 und nicht key_1 in int2 folgt key_1 ist "from"
             *  iff key_2 in int2 und nicht key_2 in int1 folgt key_2 ist "to" 
             */
            //TODO Bauernwumwandlung
            captured = false;
            boolean contains= false;
            for( int i = 0 ; i < int1.length ; i++ ){
                for( int j = 0 ; j < int2.length ; j++){
                    if(int1[i]== int2[j]){
                        contains=true;
                    }
                }
                if(!contains){
                    from=int1[i];
                }
                contains = false;
            }
            contains = false;
            for( int j = 0 ; j < int2.length ; j++ ){
                for( int i = 0 ; i < int1.length ; i++){
                    if(int1[i]== int2[j]){
                        contains=true;
                    }
                }
                if(!contains){
                    to=int2[j];                    
                }
                contains = false;
            }
            
        }else{ 
            /*
             *  Beide Felder unterschiedliche Länge --> Zug und Schlag
             */
            captured = true;
            boolean contains = false;
            for( int i = 0 ; i < int1.length ; i++ ){
                for( int j = 0 ; j < int2.length ; j++){
                    if(int1[i]== int2[j]){
                        contains = true;
                    }
                }
                if(!contains){
                    from = int1[i];
                }
                contains=false;
            }    
            for( int j = 0 ; j < int2.length ; j++ ){
                for( int i = 0 ; i < int1.length ; i++ ){
                    if( ( int1[i] == int2[j] ) && ( byte1[i].equals(byte2[j]) == false ) ){
                        to=int1[i];
                    }
                }
                contains=false;
            }
        }
        String s = "";
//        return new Move(colorOfPlayer, from, to, captured);
        return s+from+" "+to;
        

    }

    
//################################################################################# Getter/Setter
    
}
