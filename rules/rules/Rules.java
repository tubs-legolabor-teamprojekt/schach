package rules;

import game.Move;
import components.Field;
import components.Figure;

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
  private boolean blackKingMoved = false;
  private boolean whiteKingMoved = false;
  private byte currentX;
  private byte currentY;
  private byte x;
  private byte y;
  /**
   * Diese Methode überprüft, ob ein Schachzug gültig ist oder nicht.
   * @param currentField Das aktuelle Spielfeld.
   * @param move Der Schachzug, der überprüft werden soll.
   * @return True: Gültiger Schachzug
   *         False: Ungültiger Schachzug
   */
  public boolean checkMove(Field currentField, Move move)
  {
      /**
       * Hilfsvariablen
       */
      boolean legalMove;
      
      // false zurückgeben, wenn Start- oder Zielfeld nicht existiert
      if(!Field.isValidFieldnumber(move.getFieldFrom())
                  || !Field.isValidFieldnumber(move.getFieldTo())){
          return false;
      }
      System.out.println("FAIL 1");
      
      // false zurückgeben, wenn zu schlagende Figur eine eigene Figur ist oder dort keine steht
      if(move.isCaptured()){
          if(!currentField.isFigureOnField(move.getFieldTo())
                  //TODO: wird entfernt, da move-objekt kein figure-objekt enthalten wird/soll
                  /*|| currentField.getFigureAt(move.getFieldTo()).getColor() == move.getFigure().getColor()*/){
                  return false;
          }
      // false zurückgeben, wenn eine Figur geschlagen werden würde    
      }
      else{
          if(currentField.isFigureOnField(move.getFieldTo())){
              return false;
          }
      }
      System.out.println("FAIL 2");
      // false zurückgeben, wenn zu bewegende Figur nicht existiert oder nicht die eigene Figur ist
      if(!currentField.isFigureOnField(move.getFieldFrom())
              //TODO: siehe weiter oben
              /*|| currentField.getFigureAt(move.getFieldFrom()).getColor() != move.getFigure().getColor()*/){
          return false;
      }
      System.out.println("FAIL 3");
      
      /**
       * Positionen speichern
       */
      currentX = Field.getXPositionFromFieldnumber(move.getFieldFrom());
      currentY = Field.getYPositionFromFieldnumber(move.getFieldFrom());
      x = Field.getXPositionFromFieldnumber(move.getFieldTo());
      y = Field.getYPositionFromFieldnumber(move.getFieldTo());
      
      //TODO: siehe oben
      switch(currentField.getFigureAt(move.getFieldFrom()).getFigureType() /*move.getFigure().getFigureType()*/) {
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
          if(legalMove){
              //TODO: siehe oben
              if(currentField.getFigureAt(move.getFieldFrom()).getColor() /*move.getFigure().getColor()*/ == ChessfigureConstants.WHITE){
                  whiteKingMoved = true;
                  if(currentY == y && currentX - x == 2){
                      whiteLeftRookMoved = true;
                  }
                  else if(currentY == y && currentX - x == - 2){
                      whiteRightRookMoved = true;
                  }
              }
              //TODO: siehe oben
              else if(currentField.getFigureAt(move.getFieldFrom()).getColor() /*move.getFigure().getColor()*/ == ChessfigureConstants.BLACK){
                  blackKingMoved = true;
                  if(currentY == y && currentX - x == 2){
                      blackLeftRookMoved = true;
                  }
                  else if(currentY == y && currentX - x == - 2){
                      blackRightRookMoved = true;
                  }
              }
          }
          return legalMove;
          
      default:
          legalMove = false;
          break;
      }
      
      if(legalMove){
          return !isCheck(currentField, move, false,
                  //TODO: siehe oben
                          currentField.getKingPosition(currentField.getFigureAt(move.getFieldFrom()).getColor() /*move.getFigure().getColor()*/));
      }
      else{
          return false;
      }
      
  }
  
  /**
   * Überprüft, ob ein Bauernzug gültig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuführender Schachzug
   * @return True: Bauer darf dem Move-Objekt entsprechend bewegt werden
   *         False: Irgendwas stimmt hier nicht!
   */
  private boolean checkPawnMove(Field currentField, Move move)
  {
      int i;
      //TODO: siehe oben
      if(/*move.getFigure().getColor()*/ currentField.getFigureAt(move.getFieldFrom()).getColor() == ChessfigureConstants.BLACK){
          i = 1;
      }
      //TODO: siehe oben
      else if(/*move.getFigure().getColor()*/ currentField.getFigureAt(move.getFieldFrom()).getColor()== ChessfigureConstants.WHITE){
          i = -1;
      }
      else{
          return false;
      }
      // Bauer schlägt nicht
      if(currentX == x
              && !move.isCaptured()
              && !currentField.isFigureOnField(move.getFieldTo())){
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
      // Bauer schlägt
      else if(Math.abs(currentX - x) == 1
              && move.isCaptured()
              && currentField.isFigureOnField(move.getFieldTo())
              && currentY - y == i){
          return true;
      }
      
      return false;
  }
  
  /**
   * Überprüft, ob ein Turmzug gültig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuführender Schachzug
   * @return True: Turm darf dem Move-Objekt entsprechend bewegt werden
   *         False: Irgendwas stimmt hier nicht!
   */
  private boolean checkRookMove(Field currentField, Move move)
  {
      //Turm bewegt sich in y-Richtung
      if(currentX == x
              && currentY != y){
          if(currentY - 1 > y){
              //Überprüfung, dass keine Figur zwischen Start- und Zielfeld steht
              for(int i = 1; currentY - i > y; i++){
                  if(currentField.isFigureOnField(Field.getFieldNumber(currentX, currentY - i))){
                      return false;
                  }
              }
          }
          else if(currentY + 1 < y){
            //Überprüfung, dass keine Figur zwischen Start- und Zielfeld steht
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
            //Überprüfung, dass keine Figur zwischen Start- und Zielfeld steht
              for(int i = 1; currentX - i > x; i++){
                  if(currentField.isFigureOnField(Field.getFieldNumber(currentX - i, currentY))){
                      return false;
                  }
              }
          }
          else if(currentX + 1 < x){
            //Überprüfung, dass keine Figur zwischen Start- und Zielfeld steht
              for(int i = 1; currentX + i < x; i++){
                  if(currentField.isFigureOnField(Field.getFieldNumber(currentX + i, currentY))){
                      return false;
                  }
              }
          }
          //bewegt sich ein Turm, darf er nicht mehr Teil der Rochade sein
          //da Damenbewegung auch über diese Methode überprüft wird, darf die Dame hier nicht "reinpfuschen" :)
          if(/*move.getFigure().getFigureType()*/ currentField.getFigureAt(move.getFieldFrom()).getFigureType() != ChessfigureConstants.QUEEN){
              if(/*move.getFigure().getColor()*/ currentField.getFigureAt(move.getFieldFrom()).getColor() == ChessfigureConstants.BLACK){
                  if(move.getFieldFrom() == 57){
                      this.blackLeftRookMoved = true;
                  }
                  else if(move.getFieldFrom() == 64){
                      this.blackRightRookMoved = true;
                  }
              }
              else if(/*move.getFigure().getColor()*/ currentField.getFigureAt(move.getFieldFrom()).getColor() == ChessfigureConstants.WHITE){
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
   * Überprüft, ob ein Springerzug gültig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuführender Schachzug
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
   * Überprüft, ob ein Läufernzug gültig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuführender Schachzug
   * @return True: Läufer darf dem Move-Objekt entsprechend bewegt werden
   *         False: Irgendwas stimmt hier nicht!
   */
  private boolean checkBishopMove(Field currentField, Move move)
  {
      int xDif = currentX - x;
      int yDif = currentY - y;
      //Läufer bewegt sich schräg
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
          //keine Figur im Weg oder Läufer hat sich nur ein Feld bewegt
          return true;
      }
      return false;
  }
  
  /**
   * Überprüft, ob ein Damenzug gültig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuführender Schachzug
   * @return True: Dame darf dem Move-Objekt entsprechend bewegt werden
   *         False: Irgendwas stimmt hier nicht!
   */
  private boolean checkQueenMove(Field currentField, Move move)
  {
      return (checkRookMove(currentField, move)||(checkBishopMove(currentField, move)));
  }
  
  /**
   * Überprüft, ob ein Königszug gültig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuführender Schachzug
   * @return True: König darf dem Move-Objekt entsprechend bewegt werden
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
          return isCheck(currentField, move, true, move.getFieldFrom());
      }
      return isCheck(currentField, move, false, move.getFieldFrom());
  }

  /**
   * Überprüft, ob sich der König im Schach befindet.
   * @param currentField aktuelles Spielfeld
   * @param move auszuführender Schachzug
   * @param castling soll eine Rochade überprüft werden?
   * @return False: der eigene König steht nicht im Schach
             True: Der eigene König ist durch den Zug gefährdet.
   */
  private boolean isCheck(Field currentField, Move move, boolean castling, int position)
  {
      byte colour = /*move.getFigure().getColor()*/ currentField.getFigureAt(move.getFieldFrom()).getColor();
      Figure fig;
      byte figType;
      byte xAxis = Field.getXPositionFromFieldnumber(position);
      byte yAxis = Field.getYPositionFromFieldnumber(position);
      //x-Achse prüfen
      //Bewegung nach rechts
      //ein Feld neben dem König anfangen, solange das Feld in der gleichen Zeile ist
      for(int i = position + 1; (i - 1)/8 == (position - 1)/8; i++){
          fig = currentField.getFigureAt(i);

          if(fig == null){
              continue;
          }
          if((fig.getColor() == colour && i != move.getFieldTo()) || i == move.getFieldTo()){
              break;
          }
          
          figType = fig.getFigureType();
          if(fig.getColor() != colour
                  && (figType == ChessfigureConstants.ROOK
                          || (figType == ChessfigureConstants.PAWN && position + 1 == i)
                          || figType == ChessfigureConstants.QUEEN)){
              return true;
          }
      }
      System.out.println("FAIL 4");
      //Bewegung nach links
      for(int i = position - 1; (i - 1)/8 == (position - 1)/8; i--){
          fig = currentField.getFigureAt(i);
          
          if(fig == null){
              continue;
          }
          if((fig.getColor() == colour && i != move.getFieldTo()) || i == move.getFieldTo()){
              break;
          }
          
          figType = fig.getFigureType();
          if(fig.getColor() != colour
                  && (figType == ChessfigureConstants.ROOK
                          || (figType == ChessfigureConstants.KING
                              && position - 1 == i)
                          || figType == ChessfigureConstants.QUEEN)){
              return true;
          }
      }
      System.out.println("FAIL 5");
      //y-Achse prüfen
      //nach oben
      for(int i = position + 8; i > 0 && i <= 64; i += 8){
          fig = currentField.getFigureAt(i);

          if(fig == null){
              continue;
          }
          if((fig.getColor() == colour && i != move.getFieldTo()) || i == move.getFieldTo()){
              break;
          }
          
          figType = fig.getFigureType();
          if(fig.getColor() != colour
                  && (figType == ChessfigureConstants.ROOK
                          || (figType == ChessfigureConstants.KING
                              && position - 8 == i)
                          || figType == ChessfigureConstants.QUEEN)){
              return true;
          }
      }
      System.out.println("FAIL 6");
      //nach unten
      for(int i = position - 8; i > 0 && i <= 64; i -= 8){
          fig = currentField.getFigureAt(i);
          
          if(fig == null){
              continue;
          }
          if((fig.getColor() == colour && i != move.getFieldTo()) || i == move.getFieldTo()){
              break;
          }
          
          figType = fig.getFigureType();
          if(fig.getColor() != colour
                  && (figType == ChessfigureConstants.ROOK
                          || (figType == ChessfigureConstants.KING
                              && position + 8 == i)
                          || figType == ChessfigureConstants.QUEEN)){
              return true;
          }
      }
      System.out.println("FAIL 7");
      //schräg prüfen
      //nach rechts oben
      int fieldNo;
      for(int i = 1; xAxis + i < 9 && yAxis < 9; i++){
          fieldNo = Field.getFieldNumber(xAxis + i, yAxis + i);
          fig = currentField.getFigureAt(fieldNo);
          if(fig == null){
              continue;
          }
          if((fig.getColor() == colour && fieldNo != move.getFieldTo())
                  || fieldNo == move.getFieldTo()){
              break;
          }
          figType = fig.getFigureType();
          if(fig.getColor() != colour
                  && (figType == ChessfigureConstants.BISHOP
                  || figType == ChessfigureConstants.QUEEN
                  || (figType == ChessfigureConstants.KING && i == 1)
                  || (figType == ChessfigureConstants.PAWN && i == 1 
                      && fig.getColor() == ChessfigureConstants.BLACK))){
              return true;
          }
      }
      System.out.println("FAIL 8");
      //nach rechts unten
      for(int i = 1; xAxis + i < 9 && yAxis - i > 0; i++){
          fieldNo = Field.getFieldNumber(xAxis + i, yAxis - i);
          fig = currentField.getFigureAt(fieldNo);
          if(fig == null){
              continue;
          }
          if((fig.getColor() == colour && fieldNo != move.getFieldTo()) || fieldNo == move.getFieldTo()){
              break;
          }
          figType = fig.getFigureType();
          if(fig.getColor() != colour
                  && (figType == ChessfigureConstants.BISHOP
                  || figType == ChessfigureConstants.QUEEN
                  || (figType == ChessfigureConstants.KING && i == 1)
                  || (figType == ChessfigureConstants.PAWN && i == 1 
                      && fig.getColor() == ChessfigureConstants.WHITE))){
              return true;
          }
      }
      System.out.println("FAIL 9");
      //nach links oben
      for(int i = 1; xAxis - i > 0 && yAxis + i < 9; i++){
          fieldNo = Field.getFieldNumber(xAxis - i, yAxis + i);
          fig = currentField.getFigureAt(fieldNo);
          if(fig == null){
              continue;
          }
          if((fig.getColor() == colour && fieldNo != move.getFieldTo()) || fieldNo == move.getFieldTo()){
              break;
          }
          figType = fig.getFigureType();
          if(fig.getColor() != colour
                  && (figType == ChessfigureConstants.BISHOP
                  || figType == ChessfigureConstants.QUEEN
                  || (figType == ChessfigureConstants.KING && i == 1)
                  || (figType == ChessfigureConstants.PAWN && i == 1 
                      && fig.getColor() == ChessfigureConstants.BLACK))){
              return true;
          }
      }
      System.out.println("FAIL 10");
      //nach links unten (eigentlich links oben?!)
      for(int i = 1; xAxis - i > 0 && yAxis - i > 0; i++){
          fieldNo = Field.getFieldNumber(xAxis - i, yAxis - i);
          fig = currentField.getFigureAt(fieldNo);
          if(fig == null){
              continue;
          }
          if((fig.getColor() == colour && fieldNo != move.getFieldTo()) || fieldNo == move.getFieldTo()){
              break;
          }
          figType = fig.getFigureType();
          if(fig.getColor() != colour
                  && (figType == ChessfigureConstants.BISHOP
                  || figType == ChessfigureConstants.QUEEN
                  || (figType == ChessfigureConstants.KING && i == 1)
                  || (figType == ChessfigureConstants.PAWN && i == 1 
                      && fig.getColor() == ChessfigureConstants.WHITE))){
              return true;
          }
      }
      System.out.println("FAIL 11");
      //Springer prüfen
      //TODO: übersichtlicher gestallten???????????
      if(xAxis + 2 < 9 && yAxis + 1 < 9){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis + 2, yAxis + 1));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              return true;
          }
      }
      System.out.println("FAIL 12");
      if(xAxis + 1 < 9 && yAxis + 2 < 9){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis + 1, yAxis + 2));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              return true;
          }
      }
      System.out.println("FAIL 13");
      if(xAxis - 2 > 0 && yAxis + 1 < 9){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis + 2, yAxis + 1));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              return true;
          }
      }
      System.out.println("FAIL 14");
      if(xAxis + 1 < 9 && yAxis - 2 > 0){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis + 2, yAxis + 1));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              return true;
          }
      }
      System.out.println("FAIL 15");
      if(xAxis - 2 > 0 && yAxis - 1 > 0){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis + 2, yAxis + 1));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              return true;
          }
      }
      System.out.println("FAIL 16");
      if(xAxis - 1 > 0  && yAxis - 2 > 0){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis + 2, yAxis + 1));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              return true;
          }
      }
      System.out.println("FAIL 17");
      if(xAxis - 1 > 0 && yAxis + 2 < 9){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis + 2, yAxis + 1));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              return true;
          }
      }
      System.out.println("FAIL 18");
      if(xAxis + 2 < 9 && yAxis - 1 > 0){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis + 2, yAxis + 1));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              return true;
          }
      }
      System.out.println("FAIL 19");
      
      if(castling && x > currentX){
          if((colour == ChessfigureConstants.WHITE && !whiteKingMoved && !whiteRightRookMoved)
                  || (colour == ChessfigureConstants.BLACK && !blackKingMoved && !blackRightRookMoved)){
              return isCheck(currentField, move, false, position + 1)
                      && isCheck(currentField, move, false, position + 2);
          }
      }
      else if(castling && x < currentX){
          if((colour == ChessfigureConstants.WHITE && !whiteKingMoved && !whiteLeftRookMoved)
                  || (colour == ChessfigureConstants.BLACK && !blackKingMoved && !blackLeftRookMoved)){
              return isCheck(currentField, move, false, position - 1)
                      && isCheck(currentField, move, false, position - 2);
          }
      }
      System.out.println("FAIL 20");
      
      return false;
  }
  
}//class
