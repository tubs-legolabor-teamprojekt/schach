package gameTree;

/**
 * Klasse zum zurckgeben gltiger Schachzge aus einer Situation heraus.
 * bergeben wird das Schachfeld und die Farbe, die ziehen darf.
 * @author tobi
 *
 */
public class ValidMove {

	protected static short[][] ValMove(short[] situation, boolean white){
		
		short[][] possMove = new short[25][32];
		
		for(int i = 0 ; i < 25 ; i++){
			
			for(int j = 0 ; j < 25 ; j++){
				
				/*
				 * Zeit schinden...
				 */
				int h = 0;
				for(int u = 0 ; u < 10000 ; u++){
					if(i<j){
						h++;
					}
				}//ENDE
				
				possMove[i][j]= 800;
				
			}	
			
		}
		
		return possMove;
		
	}
	
	
}
