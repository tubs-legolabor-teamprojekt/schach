package rating;

import java.util.HashMap;
import java.util.LinkedList;

import alphaBeta.AlphaBetaSearch;
import useful.Fingerprint;
import useful.MoveGenerator;
import useful.SituationWithRating;
import util.ChessfigureConstants;

public class PrimitivKI {
private String [] fingerprint = new String[10];
private MoveGenerator moveGen = new MoveGenerator();
final int PARALLEL = 2;

private LinkedList<SituationWithRating> [] situations = new LinkedList[10];
private int pointer = -1;
	
	private int position(String hash) {
		for(int i=0; i<fingerprint.length; i++) {
			if(hash.equals(fingerprint[i])) {
				return i;
			}
		}
		return -1;
	}

	public int isRated(HashMap<Integer,Byte> map) {
		return position(Fingerprint.getFingerprint(map));
	}
	
	public LinkedList<SituationWithRating> getChildSituations(int position) {
		if(position < situations.length && position>=0) {
			return situations[position];
		}
		else {
			return null;
		}
	}
	
	public void teachSituation(HashMap<Integer,Byte> map, int depth, byte player) {
		LinkedList<HashMap<Integer,Byte>> childSit = moveGen.generateMoves(map, player);
		LinkedList<SituationWithRating> list = new LinkedList<SituationWithRating>();
		while(!childSit.isEmpty()) {
    		list.add(new SituationWithRating(childSit.pollLast(),0));
    	}
		
		byte changePlayer = player==ChessfigureConstants.WHITE?ChessfigureConstants.BLACK:ChessfigureConstants.WHITE;
		LinkedList<SituationWithRating> ratedList = rateChildSituations(changePlayer,list,depth);
		
		LinkedList<SituationWithRating> bestMaps = new LinkedList<SituationWithRating>();
		int max = findMaxRating((LinkedList<SituationWithRating>)ratedList.clone());
		SituationWithRating rating;
		while(ratedList.size()>0) {
			rating = ratedList.pollFirst(); 
			if(rating.getRating() == max) {
				bestMaps.add(rating);
			}
		}
		
		fingerprint[++pointer] = Fingerprint.getFingerprint(map);
		situations[pointer] = bestMaps;
	}
	
	private int findMaxRating(LinkedList<SituationWithRating> map) throws NullPointerException {
		if(map.size() > 0) {
		int max = map.pollFirst().getRating();
		int rating;
		while(map.size()>0) {
			rating = map.pollFirst().getRating();
			if(max<rating) {
				max = rating;
			}
		}
		return max;
		}
		else {
			throw new NullPointerException("Leere Map uebergeben");
		}
	}
	
	 private LinkedList<SituationWithRating> rateChildSituations(byte player, LinkedList<SituationWithRating> list, int depth){
	    	
	    	AlphaBetaSearch[] abThreads= new AlphaBetaSearch[list.size()];
	    	
	    	
	        for (int i = 0; i < list.size(); i++) {
	        	//Thread erstellen mit SituationWithRating,depth,player
	        	abThreads[i] = new AlphaBetaSearch(list.get(i),depth,player);
	        	abThreads[i].setName(""+i);
	        }
	        
	        //Variablen zur Zeitmessung um das Optimum aus der nebenlaeufigkeit herauszuholen
	        long time = System.currentTimeMillis();
	        
	      //startet eine bestimmte Anzahl an Threads
	        orderedThreadStart(abThreads,PARALLEL);
//	        System.out.println("\n Zeit: "+(System.currentTimeMillis() - time)+" Anzahl paralleler Threads "+PARALLEL+"; Suchtiefe "+(DEPTH+1)+"\n");
	        
	        LinkedList<SituationWithRating> helpList = new LinkedList<SituationWithRating>();
	        
	        int i=0;
	        for(AlphaBetaSearch ab:abThreads) {
	        	helpList.add(ab.getSituationWithRating());
//	        	System.out.printf("%-3d %d  ", i++ ,ab.getSituationWithRating().getRating());
//	        	System.out.println("Zug: "+HashMapMoveToText(beforeField, ab.getSituationWithRating().getMap(), player)+" ");
	        }
	        
	        return helpList;
	    }
	    
	    /*
	     * Es sollten nicht alle Suchen gleichzeitig als seperater Thread gestartet werden,
	     * da das Scheduling ansonsten die Sache extrem verlangsamt.
	     * Die Methode orderedThreadStart achtet darauf, dass immer nur eine zuvor
	     * bestimmte Anzahl an Threads gleichzeitig laufen. Wenn diese beendet werden,
	     * dann startet diese Methode automatisch die naechsten Threads aus dem Array
	     * AlphaBetaSearch.
	     * 
	     * @param ab Array von Threads des Typ AlphaBetaSearch
	     * @param parallelValue Anzahl an gleichzeitig laufenden Threads 
	     */
	    public boolean orderedThreadStart(AlphaBetaSearch[] ab, int parallelValue) {
	    	System.out.println("Anzahl an Wurzeln "+ab.length);
	    	
	    	/*wenn anzahl der möglichen Threads kleiner ist als maximale 
	    	anzahl gleichzeitiger Threads
	    	dann gleich alle starten*/
	    	if(parallelValue>=ab.length) {
	    		System.out.println("0");
	    		for(int i=0; i<ab.length; i++) {
	    			ab[i].start();
	    		}
	    	}
	    	else {
	    		int counter = ab.length-1;

	    		//solange Zuege da sind
	    		while(counter>=0) {
	    			//duerfen noch Threads gestartet werden oder laufen genug parallel?
	    			if(ab[0].getNumberOfThreads()<parallelValue) {
	    				ab[counter].start();
	    				counter--;
	    			}
	    			//ein wenig abwarten, damit schleife nicht komplett cpu auslastet
	    			try {
	        			Thread.sleep(1 * parallelValue);
	        			}
	        			catch(Exception e) {
	        			}
	    	}
	    }
	    	//letzten Threads die noch laufen beenden lassen
	    	while(ab[0].getNumberOfThreads()!=0) {
	    		try {
	    			Thread.sleep(1 * parallelValue);
	    			}
	    			catch(Exception e) {
	    			}
	    	}
	    	return true;
	    }
}

