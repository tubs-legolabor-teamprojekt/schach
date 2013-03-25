package useful;
import java.util.HashMap;

import rating.PrimitivRating;

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
	static int counter = 0;
	
	public PseudoNextMove() {
		map = new HashMap[8];
		for(int i = 0; i<8; i++)
			map[i] = new HashMap<Integer, Byte>();
	}
	
	public HashMap<Integer,Byte> getMap(int i) {
		
		map[0].put(8, turmW.getFigureAsByte());
		map[0].put(6, laeuferW.getFigureAsByte());
		map[0].put(4, dameW.getFigureAsByte());
		map[0].put(5, koenigW.getFigureAsByte());
		map[0].put(9, bauerW.getFigureAsByte());
		map[0].put(10, bauerW.getFigureAsByte());
		map[0].put(11, bauerW.getFigureAsByte());
		
		map[0].put(49, bauerS.getFigureAsByte());
		map[0].put(52, bauerS.getFigureAsByte());
		map[0].put(53, bauerS.getFigureAsByte());
		map[0].put(54, bauerS.getFigureAsByte());
		map[0].put(57, turmS.getFigureAsByte());
		map[0].put(60, dameS.getFigureAsByte());	
		map[0].put(61, koenigS.getFigureAsByte());
		
		map[1].put(8, turmW.getFigureAsByte());
		map[1].put(6, laeuferW.getFigureAsByte());
		map[1].put(4, dameW.getFigureAsByte());
		map[1].put(5, koenigW.getFigureAsByte());
		map[1].put(9, bauerW.getFigureAsByte());
		map[1].put(10, bauerW.getFigureAsByte());
		map[1].put(11, bauerW.getFigureAsByte());
		
		map[1].put(52, bauerS.getFigureAsByte());
		map[1].put(53, bauerS.getFigureAsByte());
		map[1].put(54, bauerS.getFigureAsByte());
		map[1].put(57, turmS.getFigureAsByte());
		map[1].put(60, dameS.getFigureAsByte());	
		map[1].put(61, koenigS.getFigureAsByte());
		
		map[2].put(8, turmW.getFigureAsByte());
		map[2].put(4, dameW.getFigureAsByte());
		map[2].put(5, koenigW.getFigureAsByte());
		map[2].put(9, bauerW.getFigureAsByte());
		map[2].put(10, bauerW.getFigureAsByte());
		map[2].put(11, bauerW.getFigureAsByte());
		
		map[2].put(52, bauerS.getFigureAsByte());
		map[2].put(53, bauerS.getFigureAsByte());
		map[2].put(54, bauerS.getFigureAsByte());
		map[2].put(57, turmS.getFigureAsByte());
		map[2].put(60, dameS.getFigureAsByte());	
		map[2].put(61, koenigS.getFigureAsByte());
		
		map[3].put(8, turmW.getFigureAsByte());
		map[3].put(4, dameW.getFigureAsByte());
		map[3].put(5, koenigW.getFigureAsByte());
		map[3].put(9, bauerW.getFigureAsByte());
		map[3].put(10, bauerW.getFigureAsByte());
		map[3].put(11, bauerW.getFigureAsByte());
		
		map[3].put(52, bauerS.getFigureAsByte());
		map[3].put(53, bauerS.getFigureAsByte());
		map[3].put(54, bauerS.getFigureAsByte());
		map[3].put(57, turmS.getFigureAsByte());	
		map[3].put(61, koenigS.getFigureAsByte());
		
		map[4].put(8, turmW.getFigureAsByte());
		map[4].put(4, dameW.getFigureAsByte());
		map[4].put(5, koenigW.getFigureAsByte());
		map[4].put(9, bauerW.getFigureAsByte());
		map[4].put(10, bauerW.getFigureAsByte());
		map[4].put(11, bauerW.getFigureAsByte());
		
		map[4].put(52, bauerS.getFigureAsByte());
		map[4].put(53, bauerS.getFigureAsByte());
		map[4].put(54, bauerS.getFigureAsByte());
		map[4].put(57, turmS.getFigureAsByte());
		map[4].put(64, turmS.getFigureAsByte());
		map[4].put(60, dameS.getFigureAsByte());	
		map[4].put(61, koenigS.getFigureAsByte());
			
		return map[i];
	} 
	
	public static void main(String[] args) {
		PseudoNextMove m = new PseudoNextMove();
		PrimitivRating p = new PrimitivRating();
		
		for(int i=0; i<5;i++) {
		System.out.println(TextChessField.fieldToString(m.getMap(i)));
		System.out.println(p.primRate(m.getMap(i)));
		System.out.println();
		}
		
	}
}

