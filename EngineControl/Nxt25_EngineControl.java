import lejos.nxt.Motor;
import lejos.nxt.Motor.Regulator;
import lejos.robotics.navigation.TachoPilot;


public class Nxt25_EngineControl {

	/*
	 * z1 ist die Strecke zwischen den Felder für das Vorwärts und Rückwärtsbewegen
	 * z2 ist die Strecke für die Hoch/Runter bewegung
	 * z3 ist die Strecke zwischen den Feldern für die Links/rechts bewegung
	 * z4 ist die Rotation für das auf und zu greifen 
	 */
	
	
	final private int ROTATE_FOR_Z1 = 10;
	final private int ROTATE_FOR_Z4 = 5;
	
	private final TachoPilot Ma1_Ma2 = new TachoPilot(3.2f, 11.4f, Motor.A, Motor.B);
	private final Motor MC2 = Motor.C;
	
	
	private int coordinate_y;
	
	
	Nxt25_EngineControl() {

	}
	
	Nxt25_EngineControl(int coordinate_y) {
		this.coordinate_y = coordinate_y;
	}
	
	void setCoordinateY(int coordinate_y) {
		this.coordinate_y = coordinate_y;
		
	}
	
	boolean moveToRow() {
		Ma1_Ma2.travel(coordinate_y*ROTATE_FOR_Z1);

		return true;
	}
	
	boolean grabfigure() {
		MC2.rotate(ROTATE_FOR_Z4);
		
		return true;
	}
	
	boolean dropfigure() {
		
		return true;
	}
	
	
	
	
	boolean moveToInit() {
		
		return true;
	}
	
}
