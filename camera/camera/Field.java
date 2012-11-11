package camera;

public class Field implements Comparable {

	private Integer position, value;
	
	public Field(int position, int value) {
		this.position = position;
		this.value = value;
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public int getValue() {
		return this.value;
	}
	
	@Override
	public int compareTo(Object v) {
		if(v instanceof Field) {
			return this.value - ((Field) v).getValue();
		}
		return 0;
	}

}
