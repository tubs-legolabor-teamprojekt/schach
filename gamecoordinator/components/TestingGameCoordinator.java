package components;

public class TestingGameCoordinator
{
	private Figure[] figures = new Figure[32];
	
	private Field field = new Field();
	
	public static void main(String[] args) {
//		TestingGameCoordinator test = new TestingGameCoordinator();
		// nur zu Testzwecken, ist momentan sehr langsam
//		test.createFigures();
//		test.writeFigures();
//		test.putFiguresOnField();
		
		for (int field=1; field <= 64; field++) {
			System.out.println("Feld " + (field) + ": " + Field.getFieldName(field));
		}
	}
	
	public void putFiguresOnField()
	{
		for (int i = 0; i < 32; i++) {
			if (i < 16) {
				// weisse figuren
				if (!this.field.putFigureAt(i+1, this.figures[i]))
					System.out.println("Konnte nicht setzen auf: " + i);
//				System.out.println("Pos: " + (i+1) + "-----" + this.figures[i].toString());
			} else {
				int newFieldNumber = 33  +i;
				// schwarze figuren
				if (!this.field.putFigureAt(newFieldNumber, this.figures[i])) {
					System.out.println("Konnte nicht setzen auf: " + newFieldNumber);
				}
				
//				System.out.println("Pos: " + (33+i) + "-----" + this.figures[i].toString());
			}
		}
		System.out.println(this.field.toString());
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
		this.figures[0] = wr1;
		this.figures[1] = wk1;
		this.figures[2] = wb1;
		this.figures[3] = wq;
		this.figures[4] = wk;
		this.figures[5] = wb2;
		this.figures[6] = wk2;
		this.figures[7] = wr2;
		this.figures[8] = wp1;
		this.figures[9] = wp2;
		this.figures[10] = wp3;
		this.figures[11] = wp4;
		this.figures[12] = wp5;
		this.figures[13] = wp6;
		this.figures[14] = wp7;
		this.figures[15] = wp8;
		
		this.figures[16] = bp1;
		this.figures[17] = bp2;
		this.figures[18] = bp3;
		this.figures[19] = bp4;
		this.figures[20] = bp5;
		this.figures[21] = bp6;
		this.figures[22] = bp7;
		this.figures[23] = bp8;
		this.figures[24] = br1;
		this.figures[25] = bk1;
		this.figures[26] = bb1;
		this.figures[27] = bk;
		this.figures[28] = bq;
		this.figures[29] = bb2;
		this.figures[30] = bk2;
		this.figures[31] = br2;
	}
	
	public void writeFigures()
	{
		// Alle Figuren ausgeben
		for (Figure f : this.figures) {
			System.out.println(f);
		}
	}

}
