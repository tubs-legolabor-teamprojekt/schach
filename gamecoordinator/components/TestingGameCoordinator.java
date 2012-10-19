package components;

public class TestingGameCoordinator
{
	private Figure[] figures = new Figure[32];
	
	public static void main(String[] args) {
		TestingGameCoordinator test = new TestingGameCoordinator();
		test.createFigures();
		test.writeFigures();
	}
	
	public void createFigures()
	{
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
		this.figures[0] = bk;
		this.figures[1] = bq;
		this.figures[2] = bb1;
		this.figures[3] = bb2;
		this.figures[4] = bk1;
		this.figures[5] = bk2;
		this.figures[6] = br1;
		this.figures[7] = br2;
		this.figures[8] = bp1;
		this.figures[9] = bp2;
		this.figures[10] = bp3;
		this.figures[11] = bp4;
		this.figures[12] = bp5;
		this.figures[13] = bp6;
		this.figures[14] = bp7;
		this.figures[15] = bp8;
		this.figures[16] = wk;
		this.figures[17] = wq;
		this.figures[18] = wb1;
		this.figures[19] = wb2;
		this.figures[20] = wk1;
		this.figures[21] = wk2;
		this.figures[22] = wr1;
		this.figures[23] = wr2;
		this.figures[24] = wp1;
		this.figures[25] = wp2;
		this.figures[26] = wp3;
		this.figures[27] = wp4;
		this.figures[28] = wp5;
		this.figures[29] = wp6;
		this.figures[30] = wp7;
		this.figures[31] = wp8;
	}
	
	public void writeFigures()
	{
		// Alle Figuren ausgeben
		for (Figure f : this.figures) {
			System.out.println(f);
		}
	}

}
