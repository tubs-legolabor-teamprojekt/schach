package useful;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import util.ChessfigureConstants;

/*
 * Diese Klasse stellt Methoden zur Verfügung,
 * um aus einer gegeben Schachspielfeldsituation,
 * alle möglichen Folge-Schachfelder zu erstellen.
 * @author Florian Hallensleben
 */
public class MoveGenerator implements Serializable
{
    
    /**
     * Generiert alle möglichen Folge-Schachfelder aus einem gegebenen Schachfeld.
     * 
     * @param field das aktuelle Schachfeld
     * @param colour Farbe des Spielers, der am Zug ist
     * @return LinkedList aller möglichen Folge-Schachfelder.
     */
    public LinkedList<HashMap<Integer, Byte>> generateMoves(HashMap<Integer, Byte> field, byte colour){
        //Liste, in der alle Folge-Schachfelder gespeichert werden
        LinkedList<HashMap<Integer, Byte>> nextMoves = new LinkedList<HashMap<Integer, Byte>>();
        //Clonen des aktuellen Schachfelds
        HashMap<Integer, Byte> b = new HashMap<Integer, Byte>();
        b = (HashMap<Integer, Byte>)field.clone();
        
        byte figureValue;
        //alle Felder überprüfen
        for(int i = 1; i <= 64; i++){
            //falls Figur auf Feld steht
            if(field.containsKey(i)){
                //Wert der Figur zwischenspeichern
                figureValue = field.get(i);
                //falls Farbe der Figur gleich Spielerfarbe
                if(getColour(figureValue) == colour){
                    
                    //je nach Figurtyp muss die Figur anders gesetzt werden.
                    switch(getFigureType(figureValue)){
                        case 1:
                            nextMoves.addAll(movePawn(field, i, colour));
                            break;
                        case 2:
                            nextMoves.addAll(moveRook(field, i, colour));
                            break;
                        case 3:
                            nextMoves.addAll(moveKnight(field, i, colour));
                            break;
                        case 4:
                            nextMoves.addAll(moveBishop(field, i, colour));
                            break;
                        case 5:
                            nextMoves.addAll(moveQueen(field, i, colour));
                            break;
                        case 6:
                            nextMoves.addAll(moveKing(field, i , colour));
                            break;
                        default://throw NotAFigureException
                            break;
                    }//endswitch
                }//endif
            }//endif
        }//endfor
        
        
        return sortMoves(nextMoves);
    }
    //TODO: Bauernumwandlung
    /**
     * Liefert alle Folge-Schachfelder, die ein bestimmter Bauer auf dem übergebenen Feld ausführen kann.
     * @param field zu betrachtendes Schachfeld
     * @param key Key (= Feld) des zu bewegenden Bauerns in der HashMap
     * @param colour Farbe des Spielers
     * @return alle möglichen Folge-Schachfelder, die durch Bewegung des Bauerns enstehen können
     */
    private LinkedList<HashMap<Integer, Byte>> movePawn(HashMap<Integer, Byte> field, int key, byte colour){
        LinkedList<HashMap<Integer, Byte>> pawnMoves = new LinkedList<HashMap<Integer, Byte>>();
        HashMap<Integer, Byte> newField = new HashMap<Integer, Byte>();
        boolean change;// ein neues Feld nur hinzufügen, wenn sich etwas ändert
        int sgn;
        if(colour == ChessfigureConstants.WHITE){
            sgn = 1;
        }
        else{
            sgn = -1;
        }
        //zwei nach vorne, ein nach vorne und schlagen prüfen beachten
        for(int a = 0; a < 4; a++){
            change = false;
            newField = (HashMap<Integer, Byte>)field.clone();
            switch(a){
                case 0://zwei Felder vor (noch nicht bewegt)
                       //Feld dazwischen frei und Zielfeld frei
                    if(!wasMoved(field.get(key))
                            && !field.containsKey(key + sgn*16)
                            && !field.containsKey(key + sgn* 8)){
                        //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                        newField.put(key + sgn*16, setMoved(newField.remove(key)));
                        change = true;
                    }
                    break;
                case 1://ein Feld vor, Zielfeld frei, Grenzen nicht überschritten
                    if(!field.containsKey(key + sgn*8)
                            && 0 < key + sgn*8 && key + sgn*8 < 65){
                        //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                        newField.put(key + sgn*8, setMoved(newField.remove(key)));
                        change = true;
                    }
                    break;
                case 2://in Spielrichtung rechts schlagen
                       //Feldgrenzen werden überschritten
                    if((sgn == -1 && key % 8 == 1)
                            || (sgn == 1 && key % 8 == 0)){
                        break;
                    }
                    //Zielfeld mit gegnerischer Figur besetzt
                    if(field.containsKey(key + sgn*9) 
                            && getColour(field.get(key + sgn*9)) != colour){
                        //gegnerische Figur entfernen
                        newField.remove(key + sgn*9);
                        //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                        newField.put(key + sgn*9, setMoved(newField.remove(key)));
                        change = true;
                    }
                    break;
                case 3://in Spielrichtung links schlagen
                    //Feldgrenzen werden überschritten
                 if((sgn == -1 && key % 8 == 0)
                         || (sgn == 1 && key % 8 == 1)){
                     break;
                 }
                 //Zielfeld mit gegnerischer Figur besetzt
                 if(field.containsKey(key + sgn*7) 
                         && getColour(field.get(key + sgn*7)) != colour){
                     //gegnerische Figur entfernen
                     newField.remove(key + sgn*7);
                     //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                     newField.put(key + sgn*7, setMoved(newField.remove(key)));
                     change = true;
                 }
                    break;
                default:
                    break;
            }
            //neues Feld hinzufügen, falls dadurch nicht in Schach geraten
            if(change && !isCheck(newField, colour)){
                pawnMoves.add(newField);
//                System.out.println("added Pawn");
            }
        }
        
        return pawnMoves;
    }
    
    /**
     * Liefert alle Folge-Schachfelder, die ein bestimmter Turm auf dem übergebenen Feld ausführen kann.
     * @param field zu betrachtendes Schachfeld
     * @param key Key (= Feld) des zu bewegenden Turms in der HashMap
     * @param colour Farbe des Spielers
     * @return alle möglichen Folge-Schachfelder, die durch Bewegung des Turms enstehen können
     */
    private LinkedList<HashMap<Integer, Byte>> moveRook(HashMap<Integer, Byte> field, int key, byte colour){
        LinkedList<HashMap<Integer, Byte>> rookMoves = new LinkedList<HashMap<Integer, Byte>>();
        HashMap<Integer, Byte> newField = new HashMap<Integer, Byte>();
        boolean captured = false;
        
        //Nach oben bewegen
        for(int i = 1; i < 8; i++){
            newField = (HashMap<Integer, Byte>)field.clone();
            //Grenze nicht überschritten
            if(key + 8*i > 64){
                break;
            }
            //Feld frei
            if(!field.containsKey(key + 8*i)){
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + 8*i, setMoved(newField.remove(key)));
            }
            //Feld mit eigener Figur belegt
            else if(getColour(field.get(key + 8*i)) == colour){
                break;
            }
            //Feld mit fremder Figur belegt
            else if(getColour(field.get(key + 8*i)) != colour){
                //gegnerische Figur entfernen
                newField.remove(key + 8*i);
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + 8*i, setMoved(newField.remove(key)));
                //eine Figur wurde geschlagen
                captured = true;
            }
            //neues Feld hinzufügen, falls dadurch nicht in Schach geraten
            if(!isCheck(newField, colour)){
                rookMoves.add(newField);
//                System.out.println("added Rook");
            }
            //falls Figur geschlagen wurde, darf Schlagende Figur nicht weiterziehen
            if(captured){
                break;
            }
        }
        captured = false;
        //Nach unten bewegen
        for(int i = -1; i > -8; i--){
            newField = (HashMap<Integer, Byte>)field.clone();
            //Grenze nicht überschritten
            if(key + 8*i < 1){
                break;
            }
            //Feld frei
            if(!field.containsKey(key + 8*i)){
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + 8*i, setMoved(newField.remove(key)));
            }
            //Feld mit eigener Figur belegt
            else if(getColour(field.get(key + 8*i)) == colour){
                break;
            }
            //Feld mit fremder Figur belegt
            else if(getColour(field.get(key + 8*i)) != colour){
                //gegnerische Figur entfernen
                newField.remove(key + 8*i);
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + 8*i, setMoved(newField.remove(key)));
                captured = true;
            }
            //neues Feld hinzufügen, falls dadurch nicht in Schach geraten
            if(!isCheck(newField, colour)){
                rookMoves.add(newField);
//                System.out.println("added Rook");
            }
            //falls Figur geschlagen wurde, darf Schlagende Figur nicht weiterziehen
            if(captured){
                break;
            }
        }
        captured = false;
        //Nach rechts bewegen
        for(int i = 1; i < 8; i++){
            newField = (HashMap<Integer, Byte>)field.clone();
            //Grenze nicht überschreiten
            if((key + i) % 8 == 1){
                break;
            }
            //Feld frei
            if(!field.containsKey(key + i)){
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + i, setMoved(newField.remove(key)));
            }
            //Feld mit eigener Figur belegt
            else if(getColour(field.get(key + i)) == colour){
                break;
            }
            //Feld mit fremder Figur belegt
            else if(getColour(field.get(key + i)) != colour){
                //gegnerische Figur entfernen
                newField.remove(key + i);
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + i, setMoved(newField.remove(key)));
                captured = true;
            }
            //neues Feld hinzufügen, falls dadurch nicht in Schach geraten
            if(!isCheck(newField, colour)){
                rookMoves.add(newField);
//                System.out.println("added Rook");
            }
            //falls Figur geschlagen wurde, darf Schlagende Figur nicht weiterziehen
            if(captured){
                break;
            }
        }
        captured = false;
        //Nach links bewegen
        for(int i = -1; i > -8; i--){
            newField = (HashMap<Integer, Byte>)field.clone();
            //Grenze nicht überschreiten
            if((key + i) % 8 == 0){
                break;
            }
            //Feld frei
            if(!field.containsKey(key + i)){
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + i, setMoved(newField.remove(key)));
            }
            //Feld mit eigener Figur belegt
            else if(getColour(field.get(key + i)) == colour){
                break;
            }
            //Feld mit fremder Figur belegt
            else if(getColour(field.get(key + i)) != colour){
                //gegnerische Figur entfernen
                newField.remove(key + i);
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + i, setMoved(newField.remove(key)));
                captured = true;
            }
            //neues Feld hinzufügen, falls dadurch nicht in Schach geraten
            if(!isCheck(newField, colour)){
                rookMoves.add(newField);
//                System.out.println("added Rook");
            }
            //falls Figur geschlagen wurde, darf Schlagende Figur nicht weiterziehen
            if(captured){
                break;
            }
        }
        
        return rookMoves;
    }

    /**
     * Liefert alle Folge-Schachfelder, die ein bestimmter Springer auf dem übergebenen Feld ausführen kann.
     * @param field zu betrachtendes Schachfeld
     * @param key Key (= Feld) des zu bewegenden Springers in der HashMap
     * @param colour Farbe des Spielers
     * @return alle möglichen Folge-Schachfelder, die durch Bewegung des Springers enstehen können
     */
    private LinkedList<HashMap<Integer, Byte>> moveKnight(HashMap<Integer, Byte> field, int key, byte colour){
        LinkedList<HashMap<Integer, Byte>> knightMoves = new LinkedList<HashMap<Integer, Byte>>();
        HashMap<Integer, Byte> newField = new HashMap<Integer, Byte>();
        int change; //ein neues Feld nur hinzufügen, wenn sich etwas ändert
        
        for(int a = 1; a < 9; a++){
            change = -1;
            newField = (HashMap<Integer, Byte>)field.clone();
            switch(a){
                case 1: //zwei links einen hoch(+6)
                    //Grenze überschritten, Zielfeld durch eigene Figur belegt
                    if((key - 1) % 8 < 2
                            || key > 56
                            || (field.containsKey(key + 6) && getColour(field.get(key + 6)) == colour)){
                        break;
                    }
                    change = 6;
                    break;
                case 2: //zwei rechts einen runter(-6)
                    if((key - 1) % 8 > 5
                            || key < 9
                            || (field.containsKey(key - 6) && getColour(field.get(key - 6)) == colour)){
                        break;
                    }
                    change = -6;
                    break;
                case 3: //zwei rechts einen hoch(+10)
                    if((key - 1) % 8 > 5
                            || key > 56
                            || (field.containsKey(key + 10) && getColour(field.get(key + 10)) == colour)){
                        break;
                    }
                    change = 10;
                    break;
                case 4: //zwei links einen runter(-10)
                    if((key - 1) % 8 < 2
                            || key < 9
                            || (field.containsKey(key -10) && getColour(field.get(key -10)) == colour)){
                        break;
                    }
                    change = -10;
                    break;
                case 5: //zwei hoch einen links(+15)
                    if(key % 8  == 1
                            || key > 48
                            || (field.containsKey(key + 15) && getColour(field.get(key + 15)) == colour)){
                        break;
                    }
                    change = 15;
                    break;
                case 6: //zwei runter einen rechts(-15)
                    if(key % 8 == 0
                            || key < 17
                            || (field.containsKey(key - 15) && getColour(field.get(key - 15)) == colour)){
                        break;
                    }
                    change = -15;
                    break;
                case 7: //zwei hoch einen rechts(+17)
                    if(key % 8 == 0
                            || key > 48
                            || (field.containsKey(key + 17) && getColour(field.get(key + 17)) == colour)){
                        break;
                    }
                    change = 17;
                    break;
                case 8: //zwei runter einen links(-17)
                    if(key % 8  == 1
                            || key < 17
                            || (field.containsKey(key - 17) && getColour(field.get(key - 17)) == colour)){
                        break;
                    }
                    change = -17;
                    break;
                default: //throw something went wrong exception
                    break;
            }
            
            //obige Bedingung erfüllt, Zielfeld frei
            if(change != -1 && !field.containsKey(key + change)){
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + change, setMoved(newField.remove(key)));
            }
            else if(change != -1 && getColour(field.get(key + change)) != colour){
                //gegnerische Figur entfernen
                newField.remove(key + change);
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + change, setMoved(newField.remove(key)));
            }
            
            //neues Feld hinzufügen, falls dadurch nicht in Schach geraten
            if(change != -1 && !isCheck(newField, colour)){
                knightMoves.add(newField);
//                System.out.println("added Knight");
            }
        }
        return knightMoves;
    }

    /**
     * Liefert alle Folge-Schachfelder, die ein bestimmter Läufer auf dem übergebenen Feld ausführen kann.
     * @param field zu betrachtendes Schachfeld
     * @param key Key (= Feld) des zu bewegenden Läufers in der HashMap
     * @param colour Farbe des Spielers
     * @return alle möglichen Folge-Schachfelder, die durch Bewegung des Läufers enstehen können
     */
    private LinkedList<HashMap<Integer, Byte>> moveBishop(HashMap<Integer, Byte> field, int key, byte colour){
        LinkedList<HashMap<Integer, Byte>> bishopMoves = new LinkedList<HashMap<Integer, Byte>>();
        HashMap<Integer, Byte> newField = new HashMap<Integer, Byte>();
        boolean captured = false;
        
        //Nach oben rechts bewegen
        for(int i = 1; i < 8; i++){
            newField = (HashMap<Integer, Byte>)field.clone();
            //Grenze nicht überschritten
            if(key + 9*i > 64
                    || (key + 9*i) % 8 == 1){
                break;
            }
            //Feld frei
            if(!field.containsKey(key + 9*i)){
                newField.put(key + 9*i, setMoved(newField.remove(key)));
            }
            //Feld mit eigener Figur belegt
            else if(getColour(field.get(key + 9*i)) == colour){
                break;
            }
            //Feld mit fremder Figur belegt
            else if(getColour(field.get(key + 9*i)) != colour){
                //gegnerische Figur entfernen
                newField.remove(key + 9*i);
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + 9*i, setMoved(newField.remove(key)));
                captured = true;
            }
            //neues Feld hinzufügen, falls dadurch nicht in Schach geraten
            if(!isCheck(newField, colour)){
                bishopMoves.add(newField);
//                System.out.println("added Bishop");
            }
            //falls Figur geschlagen wurde, darf Schlagende Figur nicht weiterziehen
            if(captured){
                break;
            }
        }
        captured = false;
        //nach oben links bewegen
        for(int i = 1; i < 8; i++){
            newField = (HashMap<Integer, Byte>)field.clone();
            //Grenze nicht überschritten
            if(key + 7*i > 64
                    || (key + 7*i) % 8 == 0){
                break;
            }
            //Feld frei
            if(!field.containsKey(key + 7*i)){
                newField.put(key + 7*i, setMoved(newField.remove(key)));
            }
            //Feld mit eigener Figur belegt
            else if(getColour(field.get(key + 7*i)) == colour){
                break;
            }
            //Feld mit fremder Figur belegt
            else if(getColour(field.get(key + 7*i)) != colour){
                //gegnerische Figur entfernen
                newField.remove(key + 7*i);
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + 7*i, setMoved(newField.remove(key)));
                captured = true;
            }
            //neues Feld hinzufügen, falls dadurch nicht in Schach geraten
            if(!isCheck(newField, colour)){
                bishopMoves.add(newField);
//                System.out.println("added Bishop");
            }
            //falls Figur geschlagen wurde, darf Schlagende Figur nicht weiterziehen
            if(captured){
                break;
            }
        }
        captured = false;
        //nach unten rechts bewegen
        for(int i = 1; i < 8; i++){
            newField = (HashMap<Integer, Byte>)field.clone();
            //Grenze nicht überschritten
            if(key - 7*i < 1
                    || (key - 7*i) % 8 == 1){
                break;
            }
            //Feld frei
            if(!field.containsKey(key - 7*i)){
                newField.put(key - 7*i, setMoved(newField.remove(key)));
            }
            //Feld mit eigener Figur belegt
            else if(getColour(field.get(key - 7*i)) == colour){
                break;
            }
            //Feld mit fremder Figur belegt
            else if(getColour(field.get(key - 7*i)) != colour){
                //gegnerische Figur entfernen
                newField.remove(key - 7*i);
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key - 7*i, setMoved(newField.remove(key)));
                captured = true;
            }
            //neues Feld hinzufügen, falls dadurch nicht in Schach geraten
            if(!isCheck(newField, colour)){
                bishopMoves.add(newField);
//                System.out.println("added Bishop");
            }
            //falls Figur geschlagen wurde, darf Schlagende Figur nicht weiterziehen
            if(captured){
                break;
            }
        }
        captured = false;
        //nach unten links bewegen
        for(int i = 1; i < 8; i++){
            newField = (HashMap<Integer, Byte>)field.clone();
            //Grenze nicht überschritten
            if(key - 9*i < 1
                    || (key - 9*i) % 8 == 0){
                break;
            }
            //Feld frei
            if(!field.containsKey(key - 9*i)){
                newField.put(key - 9*i, setMoved(newField.remove(key)));
            }
            //Feld mit eigener Figur belegt
            else if(getColour(field.get(key - 9*i)) == colour){
                break;
            }
            //Feld mit fremder Figur belegt
            else if(getColour(field.get(key - 9*i)) != colour){
                //gegnerische Figur entfernen
                newField.remove(key - 9*i);
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key - 9*i, setMoved(newField.remove(key)));
                captured = true;
            }
            //neues Feld hinzufügen, falls dadurch nicht in Schach geraten
            if(!isCheck(newField, colour)){
                bishopMoves.add(newField);
//                System.out.println("added Bishop");
            }
            //falls Figur geschlagen wurde, darf Schlagende Figur nicht weiterziehen
            if(captured){
                break;
            }
        }
        
        return bishopMoves;
    }
    
    /**
     * Liefert alle Folge-Schachfelder, die die eigene Königin auf dem übergebenen Feld ausführen kann.
     * @param field zu betrachtendes Schachfeld
     * @param key Key (= Feld) der zu bewegenden Königin in der HashMap
     * @param colour Farbe des Spielers
     * @return alle möglichen Folge-Schachfelder, die durch Bewegung der Königin enstehen können
     */
    private LinkedList<HashMap<Integer, Byte>> moveQueen(HashMap<Integer, Byte> field, int key, byte colour){
        LinkedList<HashMap<Integer, Byte>> queenMoves = new LinkedList<HashMap<Integer, Byte>>();
        //Königin kann solche Züge ausführen, die Turm und Läufer ausführen können
//        System.out.println("<Queen begin>");
        queenMoves.addAll(moveRook(field, key, colour));
        queenMoves.addAll(moveBishop(field, key, colour));
//        System.out.println("<Queen end>");
        return queenMoves;
    }

    /**
     * Liefert alle Folge-Schachfelder, die der eigene König auf dem übergebenen Feld ausführen kann.
     * @param field zu betrachtendes Schachfeld
     * @param key Key (=Feld) des zu bewegenden Königs in der HashMap
     * @param colour Farbe des Spielers
     * @return alle möglichen Folge-Schachfelder, die durch Bewegung des Königs enstehen können
     */
    private LinkedList<HashMap<Integer, Byte>> moveKing(HashMap<Integer, Byte> field, int key, byte colour){
        LinkedList<HashMap<Integer, Byte>> kingMoves = new LinkedList<HashMap<Integer, Byte>>();
        HashMap<Integer, Byte> newField = new HashMap<Integer, Byte>();
        int change; //ein neues Feld nur hinzufügen, wenn sich etwas ändert
        
        for(int a = 1; a < 9; a++){
            change = 0;
            newField = (HashMap<Integer, Byte>)field.clone();
            switch(a){
                case 1: //nach oben
                    //Grenze überschritten, Zielfeld durch eigene Figur belegt
                    if((key + 8) > 64
                            || (field.containsKey(key + 8) && getColour(field.get(key + 8)) == colour)){
                        break;
                    }
                    change = 8;
                    break;
                case 2: //nach rechts nach oben
                    if((key + 9) > 64
                            || (key % 8) == 0
                            || (field.containsKey(key + 9) && getColour(field.get(key + 9)) == colour)){
                        break;
                    }
                    change = 9;
                    break;
                case 3: //nach rechts
                    if((key % 8) == 0
                            || (field.containsKey(key + 1) && getColour(field.get(key + 1)) == colour)){
                        break;
                    }
                    change = 1;
                    break;
                case 4: //nach rechts unten
                    if((key - 7) < 1
                            || (key % 8) == 0
                            || (field.containsKey(key - 7) && getColour(field.get(key - 7)) == colour)){
                        break;
                    }
                    change = -7;
                    break;
                case 5: //nach unten
                    if((key - 8) < 1
                            || (field.containsKey(key - 8) && getColour(field.get(key - 8)) == colour)){
                        break;
                    }
                    change = -8;
                    break;
                case 6: //nach links unten
                    if((key % 8) == 1
                            || (key - 9) < 1
                            || (field.containsKey(key - 9) && getColour(field.get(key - 9)) == colour)){
                        break;
                    }
                    change = -9;
                    break;
                case 7: //nach links
                    if((key % 8) == 1
                            || (field.containsKey(key - 1) && getColour(field.get(key - 1)) == colour)){
                        break;
                    }
                    change = -1;
                    break;
                case 8: //nach links oben
                    if(key % 8  == 1
                            || (key + 7) > 64
                            || (field.containsKey(key + 7) && getColour(field.get(key + 7)) == colour)){
                        break;
                    }
                    change = 7;
                    break;
                default: //throw something went wrong exception
                    break;
            }
            
            //obige Bedingung erfüllt, Zielfeld frei
            if(change != 0 && !field.containsKey(key + change)){
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + change, setMoved(newField.remove(key)));
            }
            else if(change != 0 && getColour(field.get(key + change)) != colour){
                //gegnerische Figur entfernen
                newField.remove(key + change);
                //alten Wert mit neuem Feld assozieren, gleichzeitig altes Feld löschen
                newField.put(key + change, setMoved(newField.remove(key)));
            }
            
            //neues Feld hinzufügen, falls dadurch nicht in Schach geraten
            if(change != 0 && !isCheck(newField, colour)){
                kingMoves.add(newField);
//                System.out.println("added King");
            }
        } 
        newField = (HashMap<Integer, Byte>)field.clone();
        //Rochade rechtsseitig (kurze Rochade)
        //König nicht im Schach, König und Turm nicht bewegt, Zwischenfelder frei
        if(!isCheck(field, colour)
                && !wasMoved(field.get(key))
                && field.containsKey(key + 3)
                && getColour(field.get(key + 3)) == colour
                && !wasMoved(field.get(key + 3))
                && !field.containsKey(key + 1)
                && !field.containsKey(key + 2)){
            newField.put(key + 1, newField.remove(key));
            //vom König zu überquerendes Feld nicht bedroht
            if(!isCheck(newField, colour)){
                //König auf richtige Position setzen
                newField.put(key + 2, newField.remove(key + 1));
                newField.put(key + 1, newField.remove(key + 3));
                //neues Feld hinzufügen, falls dadurch nicht in Schach geraten
                if(!isCheck(newField, colour)){
                    kingMoves.add(newField);
//                    System.out.println("added kingSideCastling");
                }
            }
        }
        
        newField = (HashMap<Integer, Byte>)field.clone();
        //Rochade linksseitig (lange Rochade)
        //König nicht im Schach, König und Turm nicht bewegt, Zwischenfelder frei
        if(!isCheck(field, colour)
                && !wasMoved(field.get(key))
                && field.containsKey(key - 4)
                && getColour(field.get(key - 4)) == colour
                && !wasMoved(field.get(key - 4))
                && !field.containsKey(key - 1)
                && !field.containsKey(key - 2)
                && !field.containsKey(key - 3)){
            newField.put(key - 1, newField.remove(key));
            //vom König zu überquerendes Feld nicht bedroht
            if(!isCheck(newField, colour)){
                //König auf richtige Position setzen
                newField.put(key - 2, newField.remove(key - 1));
                newField.put(key - 1, newField.remove(key - 4));
                //neues Feld hinzufügen, falls dadurch nicht in Schach geraten
                if(!isCheck(newField, colour)){
                    kingMoves.add(newField);
//                    System.out.println("added queenSideCastling");
                }
            }
        }
        
        return kingMoves;
    }
    
    /**
     * Überprüft ein Spielfeld darauf, ob der König der übergebenen Farbe im Schach steht. 
     * @param field Schachfeld, dass auf eine Schachsituation geprüft werden soll
     * @param colour Farbe des Spielers, der den letzten Zug gemacht hat
     * @return true, falls der Spieler im Schach steht, false sonst
     */
    private boolean isCheck(HashMap<Integer, Byte> field, byte colour){
        byte kingValue;
        int key = 0;
        byte figType;
        for(int i = 1; i < 65; i++){
            //falls König der Spielerfarbe auf dem Feld steht, Key und Wert merken
            if(field.containsKey(i)
                    && getColour(field.get(i)) == colour
                    && getFigureType(field.get(i)) == ChessfigureConstants.KING){
                key = i;
                kingValue = field.get(key);
            }
        }
        //x-Achse prüfen
        //nach links
        for(int j = 1; (key - j) % 8 != 0; j++){
            //wenn Feld leer, weitermachen
            if(!field.containsKey(key - j)){
                continue;
            }
            //wenn Feld eigene Figur enthält, aufhören Reihe zu prüfen
            if(getColour(field.get(key - j)) == colour){
                break;
            }
            figType = getFigureType(field.get(key - j));
            //im Schach, wenn eine bestimmte Figur auf dem Feld steht
            if(figType == ChessfigureConstants.QUEEN
                    || figType == ChessfigureConstants.ROOK
                    || (figType == ChessfigureConstants.KING && j == 1)){
                return true;
            }
            else{
                break;
            }
        }
        //nach rechts
        for(int j = 1; (key + j) % 8 != 1; j++){
            //wenn Feld leer, weitermachen
            if(!field.containsKey(key + j)){
                continue;
            }
            //wenn Feld eigene Figur enthält, aufhören Reihe zu prüfen
            if(getColour(field.get(key + j)) == colour){
                break;
            }
            figType = getFigureType(field.get(key + j));
            //im Schach, wenn eine bestimmte Figur auf dem Feld steht
            if(figType == ChessfigureConstants.QUEEN
                    || figType == ChessfigureConstants.ROOK
                    || (figType == ChessfigureConstants.KING && j == 1)){
                return true;
            }
            else{
                break;
            }
        }
        //y-Achse prüfen
        //nach oben
        for(int j = 1; key + 8*j < 65; j++){
            //wenn Feld leer, weitermachen
            if(!field.containsKey(key + 8*j)){
                continue;
            }
            //wenn Feld eigene Figur enthält, aufhören Reihe zu prüfen
            if(getColour(field.get(key + 8*j)) == colour){
                break;
            }
            figType = getFigureType(field.get(key + 8*j));
            //im Schach, wenn eine bestimmte Figur auf dem Feld steht
            if(figType == ChessfigureConstants.QUEEN
                    || figType == ChessfigureConstants.ROOK
                    || (figType == ChessfigureConstants.KING && j == 1)){
                return true;
            }
            else{
                break;
            }
        }
        //nach unten
        for(int j = 1; key - 8*j > 0; j++){
            //wenn Feld leer, weitermachen
            if(!field.containsKey(key - 8*j)){
                continue;
            }
            //wenn Feld eigene Figur enthält, aufhören Reihe zu prüfen
            if(getColour(field.get(key - 8*j)) == colour){
                break;
            }
            figType = getFigureType(field.get(key - 8*j));
            //im Schach, wenn eine bestimmte Figur auf dem Feld steht
            if(figType == ChessfigureConstants.QUEEN
                    || figType == ChessfigureConstants.ROOK
                    || (figType == ChessfigureConstants.KING && j == 1)){
                return true;
            }
            else{
                break;
            }
        }
        //schräg prüfen
        //nach rechts oben
        for(int j = 1; key + 9*j < 65 && (key + 9*j) % 8 != 1; j++){
            //wenn Feld leer, weitermachen
            if(!field.containsKey(key + 9*j)){
                continue;
            }
            //wenn Feld eigene Figur enthält, aufhören Reihe zu prüfen
            if(getColour(field.get(key + 9*j)) == colour){
                break;
            }
            figType = getFigureType(field.get(key + 9*j));
            //im Schach, wenn eine bestimmte Figur auf dem Feld steht
            if(figType == ChessfigureConstants.QUEEN
                    || figType == ChessfigureConstants.BISHOP
                    || (figType == ChessfigureConstants.KING && j == 1)
                    || (figType == ChessfigureConstants.PAWN && j == 1
                        && getColour(field.get(key + 9*j)) == ChessfigureConstants.BLACK)){
                return true;
            }
            else{
                break;
            }
        }
        //nach rechts unten
        for(int j = 1; key - 7*j > 0 && (key - 7*j) % 8 != 1; j++){
            //wenn Feld leer, weitermachen
            if(!field.containsKey(key - 7*j)){
                continue;
            }
            //wenn Feld eigene Figur enthält, aufhören Reihe zu prüfen
            if(getColour(field.get(key - 7*j)) == colour){
                break;
            }
            figType = getFigureType(field.get(key - 7*j));
            //im Schach, wenn eine bestimmte Figur auf dem Feld steht
            if(figType == ChessfigureConstants.QUEEN
                    || figType == ChessfigureConstants.BISHOP
                    || (figType == ChessfigureConstants.KING && j == 1)
                    || (figType == ChessfigureConstants.PAWN && j == 1
                        && getColour(field.get(key - 7*j)) == ChessfigureConstants.WHITE)){
                return true;
            }
            else{
                break;
            }
        }
        //nach links oben
        for(int j = 1; key + 7*j < 65 && (key + 7*j) % 8 != 0; j++){
            //wenn Feld leer, weitermachen
            if(!field.containsKey(key + 7*j)){
                continue;
            }
            //wenn Feld eigene Figur enthält, aufhören Reihe zu prüfen
            if(getColour(field.get(key + 7*j)) == colour){
                break;
            }
            figType = getFigureType(field.get(key + 7*j));
            //im Schach, wenn eine bestimmte Figur auf dem Feld steht
            if(figType == ChessfigureConstants.QUEEN
                    || figType == ChessfigureConstants.BISHOP
                    || (figType == ChessfigureConstants.KING && j == 1)
                    || (figType == ChessfigureConstants.PAWN && j == 1
                        && getColour(field.get(key + 7*j)) == ChessfigureConstants.BLACK)){
                return true;
            }
            else{
                break;
            }
        }
        //nach links unten
        for(int j = 1; key - 9*j > 0 && (key - 9*j) % 8 != 0; j++){
            //wenn Feld leer, weitermachen
            if(!field.containsKey(key - 9*j)){
                continue;
            }
            //wenn Feld eigene Figur enthält, aufhören Reihe zu prüfen
            if(getColour(field.get(key - 9*j)) == colour){
                break;
            }
            figType = getFigureType(field.get(key - 9*j));
            //im Schach, wenn eine bestimmte Figur auf dem Feld steht
            if(figType == ChessfigureConstants.QUEEN
                    || figType == ChessfigureConstants.BISHOP
                    || (figType == ChessfigureConstants.KING && j == 1)
                    || (figType == ChessfigureConstants.PAWN && j == 1
                        && getColour(field.get(key - 9*j)) == ChessfigureConstants.WHITE)){
                return true;
            }
            else{
                break;
            }
        }
        
        //Springer prüfen
        int change;
        for(int a = 1; a < 9; a++){
            change = 0;
            switch(a){
                case 1: //zwei links einen hoch(+6)
                    //Grenze überschritten, Zielfeld durch eigene Figur belegt
                    if((key - 1) % 8 < 2
                            || key > 56
                            || (field.containsKey(key + 6) && getColour(field.get(key + 6)) == colour)){
                        break;
                    }
                    change = 6;
                    break;
                case 2: //zwei rechts einen runter(-6)
                    if((key - 1) % 8 > 5
                            || key < 9
                            || (field.containsKey(key - 6) && getColour(field.get(key - 6)) == colour)){
                        break;
                    }
                    change = -6;
                    break;
                case 3: //zwei rechts einen hoch(+10)
                    if((key - 1) % 8 > 5
                            || key > 56
                            || (field.containsKey(key + 10) && getColour(field.get(key + 10)) == colour)){
                        break;
                    }
                    change = 10;
                    break;
                case 4: //zwei links einen runter(-10)
                    if((key - 1) % 8 < 2
                            || key < 9
                            || (field.containsKey(key -10) && getColour(field.get(key -10)) == colour)){
                        break;
                    }
                    change = -10;
                    break;
                case 5: //zwei hoch einen links(+15)
                    if(key % 8  == 1
                            || key > 48
                            || (field.containsKey(key + 15) && getColour(field.get(key + 15)) == colour)){
                        break;
                    }
                    change = 15;
                    break;
                case 6: //zwei runter einen rechts(-15)
                    if(key % 8 == 0
                            || key < 17
                            || (field.containsKey(key - 15) && getColour(field.get(key - 15)) == colour)){
                        break;
                    }
                    change = -15;
                    break;
                case 7: //zwei hoch einen rechts(+17)
                    if(key % 8 == 0
                            || key > 48
                            || (field.containsKey(key + 17) && getColour(field.get(key + 17)) == colour)){
                        break;
                    }
                    change = 17;
                    break;
                case 8: //zwei runter einen links(-17)
                    if(key % 8  == 1
                            || key < 17
                            || (field.containsKey(key - 17) && getColour(field.get(key - 17)) == colour)){
                        break;
                    }
                    change = -17;
                    break;
                default: //throw something went wrong exception
                    break;
            }
            //wenn Springer auf der Position, Schach
            if(change != 0
                    && field.containsKey(key + change)
                    && getFigureType(field.get(key + change)) == ChessfigureConstants.KNIGHT){
                return true;
            }
        }
            
        return false;
    }
    

    /**
     * Extrahiert die Farbe aus dem Wert einer Figur.
     * @param figureValue Wert der Figur, deren Farbe ermittelt werden soll
     * @return Farbe der Figur (entsprechend ChessfigureConstants)
     */
    private byte getColour(byte value){
        return (byte)((value >> 3) % 2);
    }
    
    /**
     * Extrahiert den Typ aus dem Wert einer Figur.
     * @param value Wert der Figur, deren Typ ermittelt werden soll
     * @return Typ der Figur (entsprechend ChessfigureConstants)
     */
    private byte getFigureType(byte value){
        return (byte)(value % 8);
    }
    
    /**
     * Prüft, ob eine Figur bereits bewegt wurde.
     * @param value Wert der Figur, die überprüft werden soll
     * @return true, falls Figur bereits bewegt wurde, false sonst
     */
    private boolean wasMoved(byte value){
        return (value >> 4) == 1;
    }
    
    /**
     * Setzt das Bit für bewegt bzw. nicht bewegt auf 1.
     * @param value Wert der Figur, die bewegt wird.
     * @return Wert der Figur mit "bewegt-Bit" auf 1
     */
    private byte setMoved(byte value){
        return (byte)((value % 16) + 16);
    }
    
    /**
     * Sortiert die Folge-Schachfelder nach bestimmten Kriterien (momentan Anzahl der Figuren).
     * @param moves vom MoveGenerator berechnete Folge-Schachfelder eines beliebigen Schachfelds
     * @return sortierte Folge-Schachfelder
     */
    public LinkedList<HashMap<Integer, Byte>> sortMoves(LinkedList<HashMap<Integer, Byte>> moves){
        LinkedList<HashMap<Integer, Byte>> sortedMoves = new LinkedList<HashMap<Integer, Byte>>();
        int max = 0;
        if(!moves.isEmpty()){
            max = moves.get(0).size();
        }
        
        for(int i = 0; i < moves.size(); i++){
            //die Größe der zur Zeit größten HashMap merken;
            if(moves.get(i).size() > max){
                max = moves.get(i).size();
            }
            //Wenn Größe der HashMap kleiner ist als das Maximum, vorne einfügen
            if(moves.get(i).size() < max){
                sortedMoves.push(moves.get(i));
            }
            //sonst hinten anhängen
            else{
                sortedMoves.add(moves.get(i));
            }
        }
        
        return sortedMoves;
    }
    
    public static void main(String[] args){
        HashMap<Integer, Byte> test = new HashMap<Integer, Byte>();
        test.put(1, (byte)2); //linker, weißer Turm
        test.put(2, (byte)3); //linker, weißer Springer
        test.put(3, (byte)4); //linker, weißer Läufer
        test.put(4, (byte)5); //weiße Dame
        test.put(5, (byte)6); //weißer König
        test.put(6, (byte)4); //rechter, weißer Läufer
        test.put(7, (byte)3); //rechter, weißer Springer
        test.put(8, (byte)2); //rechter, weißer Turm
        test.put(9, (byte)1); //weißer Bauer
        test.put(10, (byte)1); //weißer Bauer
        test.put(11, (byte)1); //weißer Bauer
        test.put(12, (byte)1); //weißer Bauer
        test.put(13, (byte)1); //weißer Bauer
        test.put(14, (byte)1); //weißer Bauer
        test.put(15, (byte)1); //weißer Bauer
        test.put(16, (byte)1); //weißer Bauer
        
        test.put(49, (byte)9); //schwarzer Bauer
        test.put(50, (byte)9); //schwarzer Bauer
        test.put(51, (byte)9); //schwarzer Bauer
        test.put(52, (byte)9); //schwarzer Bauer
        test.put(53, (byte)9); //schwarzer Bauer
        test.put(54, (byte)9); //schwarzer Bauer
        test.put(55, (byte)9); //schwarzer Bauer
        test.put(56, (byte)9); //schwarzer Bauer
        test.put(57, (byte)10); //rechter, schwarzer Turm
        test.put(58, (byte)11); //rechter, schwarzer Springer
        test.put(59, (byte)12); //rechter, schwarzer Läufer
        test.put(60, (byte)13); //schwarze Dame
        test.put(61, (byte)14); //schwarzer König
        test.put(62, (byte)12); //linker, schwarzer Läufer
        test.put(63, (byte)11); //linker, schwarzer Springer
        test.put(64, (byte)10); //linker, schwarzer Turm
        MoveGenerator toast = new MoveGenerator();
//        System.out.println(s.fieldToString(test));
        LinkedList<HashMap<Integer, Byte>> a = new LinkedList<HashMap<Integer, Byte>>();
        LinkedList<HashMap<Integer, Byte>> b = new LinkedList<HashMap<Integer, Byte>>();
        LinkedList<HashMap<Integer, Byte>> c = new LinkedList<HashMap<Integer, Byte>>();
        LinkedList<HashMap<Integer, Byte>> d = new LinkedList<HashMap<Integer, Byte>>();
        LinkedList<HashMap<Integer, Byte>> e = new LinkedList<HashMap<Integer, Byte>>();
        LinkedList<HashMap<Integer, Byte>> f = new LinkedList<HashMap<Integer, Byte>>();
        int v, w, x, y, z;
        
        a = toast.generateMoves(test, (byte)1);
        v = a.size();
        long time = System.currentTimeMillis();
//        for(int i = 1; i <= v; i++){
//            b = toast.generateMoves(a.poll(), (byte)0);
//            w = b.size();
////            System.out.println(System.currentTimeMillis() - time);
//            for(int j = 1; j <= w; j++){
//                c = toast.generateMoves(b.poll(), (byte)1);
//                x = c.size();
////                System.out.println((System.currentTimeMillis() - time)/1000);
//                for(int k = 1; k <= x; k++){
//                    d = toast.generateMoves(c.poll(), (byte)0);
//                    y = d.size();
////                    System.out.println(System.currentTimeMillis() - time);
//                    for(int l = 1; l <= y; l++){
//                        e = toast.generateMoves(d.poll(), (byte)1);
//                        z = e.size();
////                        System.out.println(z);
//                        for(int m = 1; m <= z; m++){
//                            f = toast.generateMoves(e.poll(), (byte)0);
////                            System.out.println(System.currentTimeMillis() - time);
////                            System.out.println(f.size());
////                            System.out.println(m);
//                        }
//                    }
//                }
//            }
//        }
//        for(int i = 1; i <= x; i++){
//            System.out.println("\n --------- \n");
//            System.out.println(TextChessField.fieldToString(xyz.poll()));
//        }
//        
//        ListIterator<HashMap<Integer, Byte>> itr = xyz.listIterator();
//        while(itr.hasNext())
//        {
//          System.out.println(TextChessField.fieldToString(itr.next()));
//          System.out.println("\n\n-----\n\n");
//        }
        
    }

}
