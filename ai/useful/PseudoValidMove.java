package useful;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import dataStructure.ChessField;

import util.ChessfigureConstants;

import dataStructure.ChessField;

/**
 * Pseudoklasse zur ValidMove
 * 
 * Diese Klasse sollte instanziierbar bleiben, um mehrere Threads versorgen zu
 * können.
 * 
 * @author tobi
 * 
 */
public class PseudoValidMove {
    

    LinkedList<Byte> figures = new LinkedList<Byte>();
    LinkedList<Byte> position = new LinkedList<Byte>();
    public static final int NUMBEROFCHILDS = 30;
    /**
     * Gibt eine Lite mit 10 identischen Elementen zurück... Nur zum Test der
     * Alpha-Beta-Suche
     * 
     * @param list
     * @return
     */
    
    
    public PseudoValidMove() {
        for(int i=0; i<8; i++) {
            figures.add(ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.PAWN, false));
            figures.add(ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.PAWN, false));
        }
        
        for(int i=0; i<2; i++) {
            figures.add(ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.BISHOP, false));
            figures.add(ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.BISHOP, false));
        }
        
        for(int i=0; i<2; i++) {
            figures.add(ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.QUEEN, false));
            figures.add(ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.QUEEN, false));
        }
        
        for(int i=0; i<2; i++) {
            figures.add(ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.KNIGHT, false));
            figures.add(ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.KNIGHT, false));
        }
        
        for(int i=0; i<2; i++) {
            figures.add(ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.ROOK, false));
            figures.add(ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.ROOK, false));
        }
        
        for(int i=1; i<=64; i++) {
            position.add((byte)i);
        }
    }
    
    public ChessField getValidMove(int value) {
        ChessField field = new ChessField();
        if(value>32) 
            value=32;
        else if(value<3)
            value=3;
        
        int random;
        Random pos = new Random();
        int key;
        byte figure;
        
        //Koenig hinzufügen
        for(int i=0; i<2; i++) {
        random = pos.nextInt(position.size());
        key = position.get(random);
        position.remove(random);
        
        if(i==0)
            figure = ChessfigureConstants.makeFigureByte(ChessfigureConstants.BLACK, ChessfigureConstants.KING, false);
        else
            figure = ChessfigureConstants.makeFigureByte(ChessfigureConstants.WHITE, ChessfigureConstants.KING, false);
        
        field.put(key, figure);
        
        }
        
        //uebrige Felder auffuellen
        for(int i=0; i<value-2; i++) {
            random = pos.nextInt(position.size());
            key = position.get(random);
            position.remove(random);
            
            random = pos.nextInt(figures.size());
            figure = figures.get(random);
            figures.remove(random);
            
            field.put(key, figure);
        }
        return field;
    }
    
    
    
    
    
    
    public LinkedList<ChessField> move(ChessField list, int numberOfFigures) {

        /*
         * Erstellt eine neue Liste, die vom generischen Typen ABTree ist
         */
        LinkedList<ChessField> liste = new LinkedList<ChessField>();

        ChessField map = new ChessField();
        map.equipStartField();

        /*
         * die übergebene Situation wird vervielfältigt und in die neue Liste
         * gespeichert
         */

        for (int i = 1; i <= NUMBEROFCHILDS; i++) {

            liste.add(getValidMove(numberOfFigures));
        }

        /*
         * Die nun gefülte Liste mit 10 identischen Situationen wird an die
         * aufrufende Methode zurückgegeben
         */
        return liste;
    }

}
