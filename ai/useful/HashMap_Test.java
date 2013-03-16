package useful;

import game.Move;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HashMap_Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        HashMap<Integer, String> map1 = new HashMap<Integer, String>();
        HashMap<Integer, String> map2 = new HashMap<Integer, String>();
        
        map1.put(2, "König");
        map1.put(1, "Turm");
        map1.put(7, "Dame");
        map1.put(6, "Bauer");
        map1.put(4, "Bauer");
        
//        map2.put(2, "König");
//        map2.put(3, "Turm"); // Turm wurde bewegt: von 1 nach 3
//        map2.put(7, "Dame");
//        map2.put(6, "Bauer");
//        map2.put(4, "Bauer");
        
//        map2.put(2, "König");
//        map2.put(1, "Bauer"); // Bauer schlägt Turm: von 6 nach 1
//        map2.put(7, "Dame");
//        map2.put(4, "Bauer");
        
        map2.put(2, "König");
        map2.put(7, "Dame");
        map2.put(6, "Turm"); // Turm schlägt Bauer: von 1 nach 6
        map2.put(4, "Bauer");
                        
        
        Iterator<Entry<Integer, String>> it1 = map1.entrySet().iterator();
        Iterator<Entry<Integer, String>> it2 = map2.entrySet().iterator();
        Integer[] int1 = new Integer[map1.size()];
        String[] string1 = new String[map1.size()];
        Integer[] int2 = new Integer[map2.size()];
        String[] string2 = new String[map2.size()];
        
        /*
         * Variablen zur Erstellung des Move-Objektes
         */
        int from = 0, to = 0;
        byte colorOfPlayer = 1;
        boolean captured = false;
        boolean pawnPromotion = false;
        char pawnPromotedTo;
        //TODO Rochade
        boolean kingSideCastling = false;
        boolean queenSideCastling = false;
        boolean check = false;
        boolean checkMate = false;
        char figureLetter;
        game.Move move;
        
        int itCount = 0;
        while (it1.hasNext()) {
            Map.Entry<Integer, String> entry1 = (Map.Entry<Integer, String>)it1.next();
            int1[itCount] = entry1.getKey();
            string1[itCount] = entry1.getValue();
//            System.out.println(int1[itCount]+" "+string1[itCount]+" "+itCount);
            itCount++;            
        }// while
        itCount = 0;
//        System.out.println("");
        while (it2.hasNext()) {
            Map.Entry<Integer, String> entry2 = (Map.Entry<Integer, String>)it2.next();
            int2[itCount] = entry2.getKey();
            string2[itCount] = entry2.getValue();
//            System.out.println(int2[itCount]+" "+string2[itCount]+" "+itCount);
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
                    if( ( int1[i] == int2[j] ) && ( string1[i].equals(string2[j]) == false ) ){
                        to=int1[i];
                    }
                }
                contains=false;
            }
        }
        System.out.println("from: "+from);
        System.out.println("to: "+to);
        System.out.println("captured: "+captured);
//        move = new Move(colorOfPlayer, from, to, captured);
        
    }// main

}



































