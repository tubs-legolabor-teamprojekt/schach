package rules;

import game.Move;
import components.Field;
import components.Figure;

import util.*;

/**
 * Klasse zur überprüfung der Regeln.
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
      
      // false zurückgeben, wenn zu schlagende Figur eine eigene Figur ist oder dort keine steht
      if(move.isCaptured()){
          if(!currentField.isFigureOnField(move.getFieldTo())){
                  return false;
          }
      // false zurückgeben, wenn eine Figur geschlagen werden würde    
      }
      else{
          if(currentField.isFigureOnField(move.getFieldTo())){
              return false;
          }
      }
      // false zurückgeben, wenn zu bewegende Figur nicht existiert oder nicht die eigene Figur ist
      if(!currentField.isFigureOnField(move.getFieldFrom())){
          return false;
      }
      
      /**
       * Positionen speichern
       */
      currentX = Field.getXPositionFromFieldnumber(move.getFieldFrom());
      currentY = Field.getYPositionFromFieldnumber(move.getFieldFrom());
      x = Field.getXPositionFromFieldnumber(move.getFieldTo());
      y = Field.getYPositionFromFieldnumber(move.getFieldTo());
      
      switch(currentField.getFigureAt(move.getFieldFrom()).getFigureType() ) {
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
              if(currentField.getFigureAt(move.getFieldFrom()).getColor() == ChessfigureConstants.WHITE){
                  whiteKingMoved = true;
                  if(currentY == y && currentX - x == 2){
                      whiteLeftRookMoved = true;
                  }
                  else if(currentY == y && currentX - x == - 2){
                      whiteRightRookMoved = true;
                  }
              }
              else if(currentField.getFigureAt(move.getFieldFrom()).getColor() == ChessfigureConstants.BLACK){
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
                          currentField.getKingPosition(currentField.getFigureAt(move.getFieldFrom()).getColor()));
      }
      else{
          return false;
      }
      
  }
  
  /**
   * überprüft, ob ein Bauernzug gültig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuführender Schachzug
   * @return True: Bauer darf dem Move-Objekt entsprechend bewegt werden
   *         False: Irgendwas stimmt hier nicht!
   */
  private boolean checkPawnMove(Field currentField, Move move)
  {
      int i;
      if(currentField.getFigureAt(move.getFieldFrom()).getColor() == ChessfigureConstants.BLACK){
          i = 1;
      }
      else if(currentField.getFigureAt(move.getFieldFrom()).getColor()== ChessfigureConstants.WHITE){
          i = -1;
      }
      else{
          return false;
      }
      // Bauer schlügt nicht
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
      // Bauer schlügt
      else if(Math.abs(currentX - x) == 1
              && move.isCaptured()
              && currentField.isFigureOnField(move.getFieldTo())
              && currentY - y == i){
          return true;
      }
      
      return false;
  }
  
  /**
   * überprüft, ob ein Turmzug gültig ist.
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
              //überprüfung, dass keine Figur zwischen Start- und Zielfeld steht
              for(int i = 1; currentY - i > y; i++){
                  if(currentField.isFigureOnField(Field.getFieldNumber(currentX, currentY - i))){
                      return false;
                  }
              }
          }
          else if(currentY + 1 < y){
            //überprüfung, dass keine Figur zwischen Start- und Zielfeld steht
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
            //überprüfung, dass keine Figur zwischen Start- und Zielfeld steht
              for(int i = 1; currentX - i > x; i++){
                  if(currentField.isFigureOnField(Field.getFieldNumber(currentX - i, currentY))){
                      return false;
                  }
              }
          }
          else if(currentX + 1 < x){
            //überprüfung, dass keine Figur zwischen Start- und Zielfeld steht
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
   * überprüft, ob ein Springerzug gültig ist.
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
   * überprüft, ob ein Lüufernzug gültig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuführender Schachzug
   * @return True: Lüufer darf dem Move-Objekt entsprechend bewegt werden
   *         False: Irgendwas stimmt hier nicht!
   */
  private boolean checkBishopMove(Field currentField, Move move)
  {
      int xDif = currentX - x;
      int yDif = currentY - y;
      //Lüufer bewegt sich schräg
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
          //keine Figur im Weg oder Lüufer hat sich nur ein Feld bewegt
          return true;
      }
      return false;
  }
  
  /**
   * überprüft, ob ein Damenzug gültig ist.
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
   * überprüft, ob ein Königszug gültig ist.
   * @param currentField aktuelles Spielfeld
   * @param move auszuführender Schachzug
   * @return True: König darf dem Move-Objekt entsprechend bewegt werden
             False: Irgendwas stimmt hier nicht!
   */
  private boolean checkKingMove(Field currentField, Move move)
  {
      if(currentX == x 
              && Math.abs(currentY - y) == 1){
      }
      else if(currentX == x + 1
              && Math.abs(currentY - y) <= 1){
      }
      else if(currentX == x - 1
              && Math.abs(currentY - y) <= 1){
      }
      //Rochade
      else if(currentY == y && Math.abs(currentX - x) == 2){
    	  //sind die Felder zwischen Turm und König frei
          if(currentX > x && currentField.getFigureAt(Field.getFieldNumber(1, currentY)).getFigureType() == ChessfigureConstants.ROOK){
        	  for(int i = currentX - 1; i > 1; i--){
        		  if(currentField.isFigureOnField(Field.getFieldNumber(i, y))){
        			  return false;
        		  }
        	  } 
          }
          else if(currentX < x && currentField.getFigureAt(Field.getFieldNumber(8, currentY)).getFigureType() == ChessfigureConstants.ROOK){
        	  for(int i = currentX + 1; i < 8; i++){
        		  if(currentField.isFigureOnField(Field.getFieldNumber(i, y))){
        			  return false;
        		  }
        	  }
          //Falls kein Turm zur Rochade vorhanden ist
          }
          else{
        	  return false;
          }
          return !isCheck(currentField, move, true, move.getFieldTo());
      }else{
          return false;
      }
      return !isCheck(currentField, move, false, move.getFieldTo());
  }

  /**
   * Stellt das Ausgangschachfeld wieder her.
   * @param currentField aktuelles wiederherzustellendes Spielfeld
   * @param move Spielzug, der zu diesem Feld geführt hat
   * @param castling true, falls der Zug eine Rochade war, false sonst
   * @param capturedFigure Figur, die geschlagen wurde; falls keine Figur geschlagen wurde null
   */
  private void retractChanges(Field currentField, Move move, boolean castling, Figure capturedFigure){
      
      currentField.moveFigure(move.getFieldTo(), move.getFieldFrom());
      if(move.isCaptured()){
          currentField.putFigureAt(move.getFieldTo(), capturedFigure);
      }
      
      //Falls Rochade, Turm zurücksetzen
      if(castling){
          if(currentX > x){
              currentField.moveFigure(Field.getFieldNumber(4, y), Field.getFieldNumber(1, y));
          }
          else if(currentX < x){
              currentField.moveFigure(Field.getFieldNumber(6, y), Field.getFieldNumber(8, y));
          }
      }
  }
  
  /**
   * überprüft, ob sich der König im Schach befindet.
   * @param currentField aktuelles Spielfeld
   * @param move auszuführender Schachzug
   * @param castling soll eine Rochade überprüft werden?
   * @return False: der eigene König steht nicht im Schach
             True: Der eigene König ist durch den Zug geführdet.
   */
  private boolean isCheck(Field currentField, Move move, boolean castling, int position)
  {
      
      //Zwischenspeichern der Figur, die geschlagen wird, um sie später wieder auf das Spielfeld zu stellen
	  Figure capturedF = null;
      if(move.isCaptured()){
    	  capturedF = currentField.getFigureAt(move.getFieldTo());
          currentField.removeFigureAt(move.getFieldTo());
      }
      //Figur versetzen
      currentField.moveFigure(move.getFieldFrom(), move.getFieldTo());
      
      //wenn der König versetzt wird, muss natürlich die Königsposition verändert werden, aber nicht bei der Rochade
      if(currentField.getFigureAt(move.getFieldTo()).getFigureType() == ChessfigureConstants.KING && !castling){
          position = move.getFieldTo();
      }
      //Turm bei Rochade versetzen
      if(castling){
    	  if(currentX > x){
    		  currentField.moveFigure(Field.getFieldNumber(1, y), Field.getFieldNumber(4, y));
    	  }
    	  else if(currentX < x){
    		  currentField.moveFigure(Field.getFieldNumber(8, y), Field.getFieldNumber(6, y));
    	  }
      }
      
      byte colour = currentField.getFigureAt(move.getFieldTo()).getColor();
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
                          || (figType == ChessfigureConstants.KING && position + 1 == i)
                          || figType == ChessfigureConstants.QUEEN)){
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
          else{
              break;
          }
      }
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
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
          else{
              break;
          }
      }
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
                              && position + 8 == i)
                          || figType == ChessfigureConstants.QUEEN)){
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
          else{
              break;
          }
      }
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
                              && position - 8 == i)
                          || figType == ChessfigureConstants.QUEEN)){
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
          else{
              break;
          }
      }
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
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
          else{
              break;
          }
      }
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
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
          else{
              break;
          }
      }
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
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
          else{
              break;
          }
      }
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
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
          else{
              break;
          }
      }
      //Springer prüfen
      //TODO: übersichtlicher gestallten???????????
      if(xAxis + 2 < 9 && yAxis + 1 < 9){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis + 2, yAxis + 1));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
      }
      if(xAxis + 1 < 9 && yAxis + 2 < 9){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis + 1, yAxis + 2));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
      }
      if(xAxis - 2 > 0 && yAxis + 1 < 9){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis - 2, yAxis + 1));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
      }
      if(xAxis + 1 < 9 && yAxis - 2 > 0){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis + 1, yAxis - 2));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
      }
      if(xAxis - 2 > 0 && yAxis - 1 > 0){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis - 2, yAxis - 1));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
      }
      if(xAxis - 1 > 0  && yAxis - 2 > 0){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis - 1, yAxis - 2));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
      }
      if(xAxis - 1 > 0 && yAxis + 2 < 9){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis - 1, yAxis + 2));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
      }
      if(xAxis + 2 < 9 && yAxis - 1 > 0){
          fig = currentField.getFigureAt(Field.getFieldNumber(xAxis + 2, yAxis - 1));
          if(fig != null && fig.getColor() != colour && fig.getFigureType() == ChessfigureConstants.KNIGHT){
              retractChanges(currentField, move, castling, capturedF);
              return true;
          }
      }
      
      //Feld wieder in Ausgangsstellung bringen.
      retractChanges(currentField, move, castling, capturedF);
      
      //TODO: blackkingmoved etc. evtl. in die checkkingmove methode verlagern
      // Position von isCheck entsprechend anpassen
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
      
      return false;
  }
  
}//class
