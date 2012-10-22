package rules;

import game.Move;
import components.Field;

import util.*;

/**
 * Klasse zur Überprüfung der Regeln.
 * @author Florian Hallensleben
 *
 */
public class Rules {
  private boolean whiteRightRookMoved = false;
  private boolean whiteLeftRookMoved = false;
  private boolean blackRightRookMoved = false;
  private boolean blackLeftRookMoved = false;
  private boolean kingMoved = false;
  byte currentX;
  byte currentY;
  byte x;
  byte y;
  /**
   * Diese Methode überprüft, ob ein Schachzug gültig ist oder nicht.
   * @param currentField Das aktuelle Spielfeld.
   * @param move Der Schachzug, der überprüft werden soll.
   * @return True: Gültiger Schachzug, False: Ungültiger Schachzug
   */
  public boolean checkMove(Field currentField, Move move)
  {
      /**
       * Hilfsvariablen
       */
      boolean legalMove;

      
      // false zurückgeben, wenn zu schlagende Figur eine eigene Figur ist oder dort keine steht
      if(move.isCaptured()){
          if(currentField.getFigureAt(move.getFieldTo()) == null
                  || currentField.getFigureAt(move.getFieldTo()).getColor() == move.getFigure().getColor()){
                  return false;
          }
      // false zurückgeben, wenn eine Figur geschlagen werden würde    
      }else{
          if(currentField.getFigureAt(move.getFieldTo()) != null){
              return false;
          }
      }
      // false zurückgeben, wenn zu bewegende Figur nicht existiert oder nicht die eigene Figur ist
      if(currentField.getFigureAt(move.getFieldFrom())== null
              || currentField.getFigureAt(move.getFieldFrom()).getColor() != move.getFigure().getColor()){
          return false;
      }
      
      /**
       * Positionen speichern
       */
      currentX = Field.getXPositionFromFieldnumber(move.getFieldFrom());
      currentY = Field.getYPositionFromFieldnumber(move.getFieldFrom());
      x = Field.getXPositionFromFieldnumber(move.getFieldTo());
      y = Field.getYPositionFromFieldnumber(move.getFieldTo());
      
      
      switch(move.getFigure().getFigureType()) {
      case ChessfigureConstants.PAWN:
          legalMove = checkPawnMove(currentField, move);
          break;
          
      case ChessfigureConstants.ROOK:
          legalMove = checkRookMove(currentField, move);
          break;
          
      case ChessfigureConstants.KNIGHT:
          legalMove = checkKnightMove(currentField, move);
          break;
          
      case ChessfigureConstants.BISHOP:
          legalMove = checkBishopMove(currentField, move);
          break;
          
      case ChessfigureConstants.QUEEN:
          legalMove = checkQueenMove(currentField, move);
          break;
          
      case ChessfigureConstants.KING:
          legalMove = checkKingMove(currentField, move);
          kingMoved = true;
          break;
          
      default:
          legalMove = false;
          break;
      }
      
      if(legalMove){
          return isCheck(currentField, move);
      }else{
          return true;
      }
      
  }
  
  /**
   * Überprüft, ob ein Bauernzug gültig ist.
   * @param currentField
   * @param move
   * @return
   */
  private boolean checkPawnMove(Field currentField, Move move)
  {
      return true;
  }
  
  /**
   * Überprüft, ob ein Turmzug gültig ist.
   * @param currentField
   * @param move
   * @return
   */
  private boolean checkRookMove(Field currentField, Move move)
  {
      return true;
  }
  
  /**
   * Überprüft, ob ein Springerzug gültig ist.
   * @param currentField
   * @param move
   * @return
   */
  private boolean checkKnightMove(Field currentField, Move move)
  {
      return true;
  }
  
  /**
   * Überprüft, ob ein Läufernzug gültig ist.
   * @param currentField
   * @param move
   * @return
   */
  private boolean checkBishopMove(Field currentField, Move move)
  {
      return true;
  }
  
  /**
   * Überprüft, ob ein Damenzug gültig ist.
   * @param currentField
   * @param move
   * @return
   */
  private boolean checkQueenMove(Field currentField, Move move)
  {
      return (checkRookMove(currentField, move)||(checkBishopMove(currentField, move)));
  }
  
  /**
   * Überprüft, ob ein Königszug gültig ist.
   * @param currentField
   * @param move
   * @return
   */
  private boolean checkKingMove(Field currentField, Move move)
  {
      if(currentX == x 
              &&(currentY - 1 == y || currentY + 1 == y)){
          return true;
      }else if(currentY == y
              && (currentX - 1 == x || currentX + 1 == x)){
          return true;
      }else if(!kingMoved){
          return true;
      }
      return false;
  }
  
  /**
   * Überprüft, ob sich der König im Schach befindet.
   * @param currentField
   * @param move
   * @return
   */
  private boolean isCheck(Field currentField, Move move)
  {
      return true;
  }
  
}//class
