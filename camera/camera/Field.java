package camera;

/* 
 * Feldobjekt, welches die Position auf dem Feld sowie einen
 * Wert beinhaltet, welcher sich aus den RGB berechnungen
 * ergibt
 * 
 * @author Marcel Schubert
 */
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
			return ((Field) v).getValue() - this.value;
		}
		return 0;
	}

}
