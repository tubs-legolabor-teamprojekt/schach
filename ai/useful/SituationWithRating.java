package useful;

import java.util.HashMap;

public class SituationWithRating {

	private HashMap<Integer, Byte> map;
	private int rating;

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

}
