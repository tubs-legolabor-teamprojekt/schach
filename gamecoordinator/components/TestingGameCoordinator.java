package components;

public class TestingGameCoordinator
{
	public static void main(String[] args) {
		// Schwarze figuren erstellen
		byte color = util.ChessfigureConstants.BLACK;
		
		FigureKing bk = new FigureKing(color);
		
		FigureQueen bq = new FigureQueen(color);
		
		FigureBishop bb1 = new FigureBishop(color);
		FigureBishop bb2 = new FigureBishop(color);
		
		FigureKnight bk1 = new FigureKnight(color);
		FigureKnight bk2 = new FigureKnight(color);
		
		FigureRook br1 = new FigureRook(color);
		FigureRook br2 = new FigureRook(color);
		
		FigurePawn bp1 = new FigurePawn(color);
		FigurePawn bp2 = new FigurePawn(color);
		FigurePawn bp3 = new FigurePawn(color);
		FigurePawn bp4 = new FigurePawn(color);
		FigurePawn bp5 = new FigurePawn(color);
		FigurePawn bp6 = new FigurePawn(color);
		FigurePawn bp7 = new FigurePawn(color);
		FigurePawn bp8 = new FigurePawn(color);
		
		
		// Weisse Figuren erstellen
		color = util.ChessfigureConstants.WHITE;
		
		FigureKing wk = new FigureKing(color);
		
		FigureQueen wq = new FigureQueen(color);
		
		FigureBishop wb1 = new FigureBishop(color);
		FigureBishop wb2 = new FigureBishop(color);
		
		FigureKnight wk1 = new FigureKnight(color);
		FigureKnight wk2 = new FigureKnight(color);
		
		FigureRook wr1 = new FigureRook(color);
		FigureRook wr2 = new FigureRook(color);
		
		FigurePawn wp1 = new FigurePawn(color);
		FigurePawn wp2 = new FigurePawn(color);
		FigurePawn wp3 = new FigurePawn(color);
		FigurePawn wp4 = new FigurePawn(color);
		FigurePawn wp5 = new FigurePawn(color);
		FigurePawn wp6 = new FigurePawn(color);
		FigurePawn wp7 = new FigurePawn(color);
		FigurePawn wp8 = new FigurePawn(color);
		
		// Figuren einem Array hinzufuegen
		Figure[] figures = {bk, bq, bb1, bb2, bk1, bk2, br1, br2, bp1, bp2, bp3, bp4, bp5, bp6, bp7, bp8,
				wk, wq, wb1, wb2, wk1, wk2, wr1, wr2, wp1, wp2, wp3, wp4, wp5, wp6, wp7, wp8
		};
		
		// Alle Figuren ausgeben
		for (Figure f : figures) {
			System.out.println(f);
		}
	}

}
