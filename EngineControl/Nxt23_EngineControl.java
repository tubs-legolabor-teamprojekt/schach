import lejos.nxt.Motor;
import lejos.robotics.navigation.TachoPilot;


public class Nxt23_EngineControl {
	
	/*
	 * z1 ist die Strecke zwischen den Felder für das Vorwärts und Rückwärtsbewegen
	 * z2 ist die Strecke für die Hoch/Runter bewegung
	 * z3 ist die Strecke zwischen den Feldern für die Links/rechts bewegung
	 * z4 ist die Rotation für das auf und zu greifen 
	 */
	
	
	final private int ROTATE_FOR_Z2 = 15;
	final private int ROTATE_FOR_Z3 = 10;
	
	private final TachoPilot Mb1_Mb2 = new TachoPilot(3.2f, 11.4f, Motor.A, Motor.B);
	
	private char coordinate_x;
	
	
	Nxt23_EngineControl() {

	}
	
	Nxt23_EngineControl(char coordinate_x) {
		this.coordinate_x = coordinate_x;
	}
	
	void setCoordinateX(char coordinate_x) {
		this.coordinate_x = coordinate_x;
		
	}
	
	boolean moveToColumn() {
		
		
		return true;
	}
	
	boolean moveUp() {
		
		return true;
	}
	
	boolean moveDown() {
		
		return true;
	}
	
	boolean moveToInit() {
		
		return true;
	}

}
