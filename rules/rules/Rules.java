package rules;

import game.Move;
import components.Field;

import util.*;

/**
 * Klasse zur �berpr�fung der Regeln.
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
   * Diese Methode �berpr�ft, ob ein Schachzug g�ltig ist oder nicht.
   * @param currentField Das aktuelle Spielfeld.
   * @param move Der Schachzug, der �berpr�ft werden soll.
   * @return True: G�ltiger Schachzug, False: Ung�ltiger Schachzug
   */
  public boolean checkMove(Field currentField, Move move)
  {
      /**
       * Hilfsvariablen
       */
      boolean legalMove;

      
      // false zur�ckgeben, wenn zu schlagende Figur eine eigene Figur ist oder dort keine steht
      if(move.isCaptured()){
          if(currentField.getFigureAt(move.getFieldTo()) == null
                  || currentField.getFigureAt(move.getFieldTo()).getColor() == move.getFigure().getColor()){
                  return false;
          }
      // false zur�ckgeben, wenn eine Figur geschlagen werden w�rde    
      }else{
          if(currentField.getFigureAt(move.getFieldTo()) != null){
              return false;
          }
      }
      // false zur�ckgeben, wenn zu bewegende Figur nicht existiert oder nicht die eigene Figur ist
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
   * �berpr�ft, ob ein Bauernzug g�ltig ist.
   * @param currentField
   * @param move
   * @return
   */
  private boolean checkPawnMove(Field currentField, Move move)
  {
      return true;
  }
  
  /**
   * �berpr�ft, ob ein Turmzug g�ltig ist.
   * @param currentField
   * @param move
   * @return
   */
  private boolean checkRookMove(Field currentField, Move move)
  {
      return true;
  }
  
  /**
   * �berpr�ft, ob ein Springerzug g�ltig ist.
   * @param currentField
   * @param move
   * @return
   */
  private boolean checkKnightMove(Field currentField, Move move)
  {
      return true;
  }
  
  /**
   * �berpr�ft, ob ein L�ufernzug g�ltig ist.
   * @param currentField
   * @param move
   * @return
   */
  private boolean checkBishopMove(Field currentField, Move move)
  {
      return true;
  }
  
  /**
   * �berpr�ft, ob ein Damenzug g�ltig ist.
   * @param currentField
   * @param move
   * @return
   */
  private boolean checkQueenMove(Field currentField, Move move)
  {
      return (checkRookMove(currentField, move)||(checkBishopMove(currentField, move)));
  }
  
  /**
   * �berpr�ft, ob ein K�nigszug g�ltig ist.
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
   * �berpr�ft, ob sich der K�nig im Schach befindet.
   * @param currentField
   * @param move
   * @return
   */
  private boolean isCheck(Field currentField, Move move)
  {
      return true;
  }
  
}//class
