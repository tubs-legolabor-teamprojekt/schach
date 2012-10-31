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
  private boolean blackKingMoved = false;
  private boolean whiteKingMoved = false;
  private byte currentX;
  private byte currentY;
  private byte x;
  private byte y;
  /**
   * Diese Methode �berpr�ft, ob ein Schachzug g�ltig ist oder nicht.
   * @param currentField Das aktuelle Spielfeld.
   * @param move Der Schachzug, der �berpr�ft werden soll.
   * @return True: G�ltiger Schachzug
   *         False: Ung�ltiger Schachzug
   */
  public boolean checkMove(Field currentField, Move move)
  {
      /**
       * Hilfsvariablen
       */
      boolean legalMove;
      
      // false zur�ckgeben, wenn Start- oder Zielfeld nicht existiert
      if(!Field.isValidFieldnumber(move.getFieldFrom())
                  || !Field.isValidFieldnumber(move.getFieldTo())){
          return false;
      }
      
      // false zur�ckgeben, wenn zu schlagende Figur eine eigene Figur ist oder dort keine steht
      if(move.isCaptured()){
          if(!currentField.isFigureOnField(move.getFieldTo())
                  || currentField.getFigureAt(move.getFieldTo()).getColor() == move.getFigure().getColor()){
                  return false;
          }
      // false zur�ckgeben, wenn eine Figur geschlagen werden w�rde    
      }
      else{
          if(currentField.isFigureOnField(move.getFieldTo())){
              return false;
          }
      }
      // false zur�ckgeben, wenn zu bewegende Figur nicht existiert oder nicht die eigene Figur ist
      if(!currentField.isFigureOnField(move.getFieldFrom())
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
          if(move.getFigure().getColor() == ChessfigureConstants.WHITE){
              whiteKingMoved = true;
          }
          else if(move.getFigure().getColor() == ChessfigureConstants.BLACK){
              blackKingMoved = true;
          }
          break;
          
      default:
          legalMove = false;
          break;
      }
      
      if(legalMove){
          return isCheck(currentField, move);
      }
      else{
          return false;
      }
      
  }
  
  /**
   * �berpr�ft, ob ein Bauernzug g�ltig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuf�hrender Schachzug
   * @return True: Bauer darf dem Move-Objekt entsprechend bewegt werden
   *         False: Irgendwas stimmt hier nicht!
   */
  private boolean checkPawnMove(Field currentField, Move move)
  {
      int i;
      if(move.getFigure().getColor() == ChessfigureConstants.BLACK){
          i = 1;
      }
      else if(move.getFigure().getColor() == ChessfigureConstants.WHITE){
          i = -1;
      }
      else{
          return false;
      }
      // Bauer schl�gt nicht
      if(currentX == x
              && !move.isCaptured()){
          // Bauer ein Feld vor
          if(currentY - y == i){
              return true;
          }
          //Bauer zwei Felder vor (vom Startfeld), auf dem Feld dazwischen steht keine Figur
          else if(currentY - y == 2*i
                  && currentY == 4.5 + 2.5*i
                  && !currentField.isFigureOnField(Field.getFieldNumber(currentX, currentY - i))){
              return true;
          }
      }
      // Bauer schl�gt
      else if(Math.abs(currentX - x) == 1
              && move.isCaptured()
              && currentY - y == i){
          return true;
      }
      
      return false;
  }
  
  /**
   * �berpr�ft, ob ein Turmzug g�ltig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuf�hrender Schachzug
   * @return True: Turm darf dem Move-Objekt entsprechend bewegt werden
   *         False: Irgendwas stimmt hier nicht!
   */
  private boolean checkRookMove(Field currentField, Move move)
  {
      //Turm bewegt sich in y-Richtung
      if(currentX == x
              && currentY != y){
          if(currentY - 1 > y){
              //�berpr�fung, dass keine Figur zwischen Start- und Zielfeld steht
              for(int i = 1; currentY - i > y; i++){
                  if(currentField.isFigureOnField(Field.getFieldNumber(currentX, currentY - i))){
                      return false;
                  }
              }
          }
          else if(currentY + 1 < y){
            //�berpr�fung, dass keine Figur zwischen Start- und Zielfeld steht
              for(int i = 1; currentY + i < y; i++){
                  if(currentField.isFigureOnField(Field.getFieldNumber(currentX, currentY + i))){
                      return false;
                  }
              }
          }
          return true;
      }
      //Turm bewegt sich in x-Richtung
      else if(currentY == y
              && currentX != x){
          if(currentX - 1 > x){
            //�berpr�fung, dass keine Figur zwischen Start- und Zielfeld steht
              for(int i = 1; currentX - i > x; i++){
                  if(currentField.isFigureOnField(Field.getFieldNumber(currentX - i, currentY))){
                      return false;
                  }
              }
          }
          else if(currentX + 1 < x){
            //�berpr�fung, dass keine Figur zwischen Start- und Zielfeld steht
              for(int i = 1; currentX + i < x; i++){
                  if(currentField.isFigureOnField(Field.getFieldNumber(currentX + i, currentY))){
                      return false;
                  }
              }
          }
          //bewegt sich ein Turm, darf er nicht mehr Teil der Rochade sein
          //da Damenbewegung auch �ber diese Methode �berpr�ft wird, darf die Dame hier nicht "reinpfuschen" :)
          if(move.getFigure().getFigureType() != ChessfigureConstants.QUEEN){
              if(move.getFigure().getColor() == ChessfigureConstants.BLACK){
                  if(move.getFieldFrom() == 57){
                      this.blackLeftRookMoved = true;
                  }
                  else if(move.getFieldFrom() == 64){
                      this.blackRightRookMoved = true;
                  }
              }
              else if(move.getFigure().getColor() == ChessfigureConstants.WHITE){
                  if(move.getFieldFrom() == 1){
                      this.whiteLeftRookMoved = true;
                  }
                  else if(move.getFieldFrom() == 8){
                      this.whiteRightRookMoved = true;
                  }
              }
          }
          //Keine Figur im Weg oder Turm hat sich nur ein Feld weit bewegt
          return true;
      }
      return false;
  }
  
  /**
   * �berpr�ft, ob ein Springerzug g�ltig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuf�hrender Schachzug
   * @return True: Springer darf dem Move-Objekt entsprechend bewegt werden
             False: Irgendwas stimmt hier nicht!
   */
  private boolean checkKnightMove(Field currentField, Move move)
  {
      if((Math.abs(currentX - x) == 1 
              && Math.abs(currentY - y) == 2)     
              || (Math.abs(currentX - x) == 2
              && Math.abs(currentY - y) == 1)){
          return true;
      }
      return false;
  }
  
  /**
   * �berpr�ft, ob ein L�ufernzug g�ltig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuf�hrender Schachzug
   * @return True: L�ufer darf dem Move-Objekt entsprechend bewegt werden
   *         False: Irgendwas stimmt hier nicht!
   */
  private boolean checkBishopMove(Field currentField, Move move)
  {
      int xDif = currentX - x;
      int yDif = currentY - y;
      //L�ufer bewegt sich schr�g
      if(Math.abs(xDif) == Math.abs(yDif)
              && xDif != 0){
          //nach unten
          if(xDif > 1){
              //links
              if(yDif > 1){
                  for(int i = 1; currentY - i > y; i++){
                      if(currentField.isFigureOnField(Field.getFieldNumber(currentX - i, currentY - i))){
                          return false;
                      }
                  }
              }
              //rechts
              else if (yDif < 1){
                  for(int i = 1; currentY + i < y; i++){
                      if(currentField.isFigureOnField(Field.getFieldNumber(currentX - i, currentY + i))){
                          return false;
                      }
                  }
              }
          }
          //nach oben
          else if(xDif < 1){
              //links
              if(yDif > 1){
                  for(int i = 1; currentY - i > y; i++){
                      if(currentField.isFigureOnField(Field.getFieldNumber(currentX + i, currentY - i))){
                          return false;
                      }
                  }
              }
              //rechts
              else if (yDif < 1){
                  for(int i = 1; currentY + i < y; i++){
                      if(currentField.isFigureOnField(Field.getFieldNumber(currentX + i, currentY + i))){
                          return false;
                      }
                  }
              }
          }
          //keine Figur im Weg oder L�ufer hat sich nur ein Feld bewegt
          return true;
      }
      return false;
  }
  
  /**
   * �berpr�ft, ob ein Damenzug g�ltig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuf�hrender Schachzug
   * @return True: Dame darf dem Move-Objekt entsprechend bewegt werden
   *         False: Irgendwas stimmt hier nicht!
   */
  private boolean checkQueenMove(Field currentField, Move move)
  {
      return (checkRookMove(currentField, move)||(checkBishopMove(currentField, move)));
  }
  
  /**
   * �berpr�ft, ob ein K�nigszug g�ltig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuf�hrender Schachzug
   * @return True: K�nig darf dem Move-Objekt entsprechend bewegt werden
             False: Irgendwas stimmt hier nicht!
   */
  private boolean checkKingMove(Field currentField, Move move)
  {
      if(currentX == x 
              && Math.abs(currentY - y) == 1){
          return true;
      }
      else if(currentX == x + 1
              && Math.abs(currentY - y) <= 1){
          
      }
      else if(currentX == x - 1
              && Math.abs(currentY - y) <= 1){
          return true;
      }
      //Rochade
      if(currentY == y && Math.abs(currentX - x) == 2){
          return isCastlingPossible(currentField, move);
      }
      return false;
  }
  
  /**
   * �berpr�ft, ob Rochade m�glich ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuf�hrender Schaczug
   * @return True: Rochade entsprechend des Move-Objekts m�glich
   *         False: Irgendwas stimmt hier nicht!
   */
  private boolean isCastlingPossible(Field currentField, Move move)
  {
      if(!whiteKingMoved 
              && move.getFigure().getColor() == ChessfigureConstants.WHITE){
          if(currentY + 2 == y
                  && !whiteRightRookMoved){
              //TODO: wie
              //return isCastlingDangerous();
          }
          else if(currentY - 2 == y
                  && !whiteLeftRookMoved){
              return true;
          }
      }
      //des schwarzen K�nigs, der sich noch nicht bewegt hat und nicht im Schach steht
      else if(!blackKingMoved 
              && move.getFigure().getColor() == ChessfigureConstants.BLACK){
          if(currentY + 2 == y
                  && !blackRightRookMoved){
              return true;
          }
          else if(currentY - 2 == y
                  && !blackLeftRookMoved){
              return true;
          }
      }
      return false;
  }

  /**
   * �berpr�ft, ob sich der K�nig im Schach befindet.
   * @param currentField aktuelles Spielfeld
   * @param move auszuf�hrender Schachzug
   * @return True: der eigene K�nig steht nicht im Schach
             False: Der eigene K�nig ist durch den Zug gef�hrdet.
   */
  private boolean isCheck(Field currentField, Move move)
  {
      
      return true;
  }
  
}//class
