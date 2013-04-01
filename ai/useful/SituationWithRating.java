package useful;

import java.util.HashMap;

public class SituationWithRating {

	private HashMap<Integer, Byte> map;
	private int rating;
/*
 * Wrapped eine HashMap und ihre Bewertung
 * @param map Hashmap(Schachfeld)
 * @param rating Bewertung des Schachfeldes
 */
	public SituationWithRating(HashMap<Integer, Byte> map, int rating) {
		this.map = map;
		this.rating = rating;
	}

	public HashMap<Integer, Byte> getMap() {
		return this.map;
	}

	public int getRating() {
		return this.rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public SituationWithRating clone() {
		return new SituationWithRating((HashMap<Integer,Byte>)this.map.clone(),this.rating);
	}

}
