package game;

import java.util.LinkedList;
import java.util.List;

import util.ChessfigureConstants;

import components.Field;

/**
 * Testklasse um aus gegebenen Feldnummern die Zug-Objekte zu erstellen.
 * Die Feldnummern kommen entweder von der Kamera oder vom manuellen Einlesen.
 * @author Florian Franke
 * 16.12.2012
 *
 */
public class TestingConvertFieldnumbersToMoves
{

    public static void main(String[] args) {
        
        List<Integer> fieldnumbers = new LinkedList<Integer>();
        
//        fieldnumbers.add(Field.getFieldNumber("e1"));
//        fieldnumbers.add(Field.getFieldNumber("c1"));
//        fieldnumbers.add(Field.getFieldNumber("a1"));
//        fieldnumbers.add(Field.getFieldNumber("d1"));
        
        fieldnumbers.add(Field.getFieldNumber("a2"));
        fieldnumbers.add(Field.getFieldNumber("a3"));
        
        List<Move> moves = convertFieldnumbersToMoves(ChessfigureConstants.WHITE, fieldnumbers);
        
        for(Move move : moves) {
            System.out.println(move);
        }
        
    }
    
    public static List<Move> convertFieldnumbersToMoves(byte colorOfPlayer, List<Integer> fieldnumbers)
    {
        List<Move> moves = new LinkedList<Move>();
        Field f = Field.getInstance();
        
        if (fieldnumbers.size() == 2) {
            // Normaler Zug
            
            int fieldFrom = -1,
                fieldTo = -1;
            
            // Welches ist das From- und To-Feld?
            if (    f.isFigureOnField(fieldnumbers.get(0)) && // Figur auf fieldnumbers[0] vorhanden
                    f.getFigureAt(fieldnumbers.get(0)).getColor() == colorOfPlayer // eigene Figur auf fieldnumbers[0]
                ) {
                fieldFrom = fieldnumbers.get(0);
                fieldTo = fieldnumbers.get(1);
            } else if (f.isFigureOnField(fieldnumbers.get(1)) && // Figur auf fieldnumbers[1] vorhanden
                       f.getFigureAt(fieldnumbers.get(1)).getColor() == colorOfPlayer // eigene Figur auf fieldnumbers[1]
                       ) {
                fieldFrom = fieldnumbers.get(1);
                fieldTo = fieldnumbers.get(0);
            } else {
                System.out.println("Felder konnten nicht zugeordnet werden!");
            }
            
            // Wird geschmissen?
            if (f.isFigureOnField(fieldTo) // Figur auf To-Feld vorhanden?
               ) {
                if (f.getFigureAt(fieldTo).getColor() != colorOfPlayer) // Gegner auf To-Feld?
                {
                    // Neues Zug erstellen und der Liste hinzufuegen
                    moves.add(new Move(fieldFrom, fieldTo, true));
                } else {
                    // Eigene Figur kann nicht geschmissen werden
                    System.out.println("Die eigene Figur kann nicht geschmissen werden!");
                }
                
            } else {
                moves.add(new Move(fieldFrom, fieldTo));
            }
            
            
            
        } else if (fieldnumbers.size() == 4) {
            // Rochade-Zug
            
            // Darf der Spieler noch eine Rochade spielen?
            if ( (colorOfPlayer == ChessfigureConstants.WHITE && f.isRochadeWhitePossible()) ||
                 (colorOfPlayer == ChessfigureConstants.BLACK && f.isRochadeBlackPossible())
                ) {
                // Spieler spielt Rochade
                if (isKingSideCastling(colorOfPlayer, fieldnumbers)) {
                    // Kurzen Rochade-Zug erstellen und der Liste hinzufuegen
                    Move kscMove = new Move(1, 1); // Unwichtige Werte
                    kscMove.setKingSideCastling(true);
                    kscMove.setPlayerColor(colorOfPlayer);
                    moves.add(kscMove);
                } else if (isQueenSideCastling(colorOfPlayer, fieldnumbers)) {
                    // Langen Rochade-Zug erstellen und der Liste hinzufuegen
                    Move qscMove = new Move(1, 1); // Unwichtige Werte
                    qscMove.setQueenSideCastling(true);
                    qscMove.setPlayerColor(colorOfPlayer);
                    moves.add(qscMove);
                } else {
                    System.out.println("Ungueltige Rochade angegeben");
                }
            } else {
                System.out.println("Keine Rochade moeglich!");
            }
        } else {
            System.out.println("Ungueltige Anzahl an uebergebenen Feldnummern");
        }
        
        return moves;
    }
    
    public static boolean isKingSideCastling(byte colorOfPlayer, List<Integer> fieldnumbers)
    {
        if (fieldnumbers.size() == 4) {
            // Weiss
            if (colorOfPlayer == ChessfigureConstants.WHITE) {
                // Pruefe ob benoetige Felder uebergeben sind
                if (!(    fieldnumbers.contains(Field.getFieldNumber("e1")) &&
                          fieldnumbers.contains(Field.getFieldNumber("g1")) &&
                          fieldnumbers.contains(Field.getFieldNumber("h1")) &&
                          fieldnumbers.contains(Field.getFieldNumber("f1"))
                )) {
                    return false;
                }
                
            } else
            // Schwarz
            if (colorOfPlayer == ChessfigureConstants.BLACK) {
                // Pruefe ob benoetige Felder uebergeben sind
                if (!(    fieldnumbers.contains(Field.getFieldNumber("e8")) &&
                          fieldnumbers.contains(Field.getFieldNumber("g8")) &&
                          fieldnumbers.contains(Field.getFieldNumber("h8")) &&
                          fieldnumbers.contains(Field.getFieldNumber("f8"))
                )) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            System.out.println("Fehlerhafte Anzahl an uebergebenen Feldern");
            return false;
        }
        
        return true;
    }
    
    public static boolean isQueenSideCastling(byte colorOfPlayer, List<Integer> fieldnumbers)
    {
        if (fieldnumbers.size() == 4) {
            // Weiss
            if (colorOfPlayer == ChessfigureConstants.WHITE) {
                // Pruefe ob benoetige Felder uebergeben sind
                if (!(    fieldnumbers.contains(Field.getFieldNumber("e1")) &&
                          fieldnumbers.contains(Field.getFieldNumber("c1")) &&
                          fieldnumbers.contains(Field.getFieldNumber("a1")) &&
                          fieldnumbers.contains(Field.getFieldNumber("d1"))
                )) {
                    return false;
                }
                
            } else
            // Schwarz
            if (colorOfPlayer == ChessfigureConstants.BLACK) {
                // Pruefe ob benoetige Felder uebergeben sind
                if (!(    fieldnumbers.contains(Field.getFieldNumber("e8")) &&
                          fieldnumbers.contains(Field.getFieldNumber("c8")) &&
                          fieldnumbers.contains(Field.getFieldNumber("a8")) &&
                          fieldnumbers.contains(Field.getFieldNumber("d8"))
                )) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            System.out.println("Fehlerhafte Anzahl an uebergebenen Feldern");
            return false;
        }
        
        return true;
    }

}
