package useful;
import java.util.HashMap;

import components.Field;
import components.*;
import util.*;

public class PseudoNextMove {
	Figure bauerW = new FigurePawn(ChessfigureConstants.WHITE);
	Figure bauerS= new FigurePawn(ChessfigureConstants.BLACK);
	
	Figure turmW= new FigureRook(ChessfigureConstants.WHITE);
	Figure turmS= new FigureRook(ChessfigureConstants.BLACK);
	
	Figure laeuferW= new FigureBishop(ChessfigureConstants.WHITE);
	Figure laeuferS= new FigureBishop(ChessfigureConstants.BLACK);
	
	Figure springerW= new FigureKnight(ChessfigureConstants.WHITE);
	Figure springerS= new FigureKnight(ChessfigureConstants.BLACK);
	
	Figure koenigW= new FigureKing(ChessfigureConstants.WHITE);
	Figure koenigS= new FigureKing(ChessfigureConstants.BLACK);
	
	Figure dameW= new FigureQueen(ChessfigureConstants.WHITE);
	Figure dameS= new FigureQueen(ChessfigureConstants.BLACK);
	
	HashMap<Integer, Byte>[] map;
	
	
	public PseudoNextMove() {
		map = new HashMap[8];
		for(int i = 0; i<8; i++)
			map[i] = new HashMap<Integer, Byte>();
	}
	
	public HashMap<Integer,Byte> getMap() {
		
		map[0].put(1, turmW.getFigureAsByte());
		map[0].put(8, turmW.getFigureAsByte());
		map[0].put(2, springerW.getFigureAsByte());
		map[0].put(7, springerW.getFigureAsByte());
		map[0].put(3, laeuferW.getFigureAsByte());
		map[0].put(6, laeuferW.getFigureAsByte());
		map[0].put(4, dameW.getFigureAsByte());
		map[0].put(5, koenigW.getFigureAsByte());
		map[0].put(9, bauerW.getFigureAsByte());
		
			
		
	  
	  
	  System.out.println(TextChessField.fieldToString(map[0]));
	  
		
		return null;
	}
	
	public static void main(String[] args) {
		PseudoNextMove m = new PseudoNextMove();
		m.getMap();
	}
}

