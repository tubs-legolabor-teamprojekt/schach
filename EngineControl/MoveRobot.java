
public class MoveRobot {
	
	private String destination = "";
	private Nxt25_EngineControl Engine1;
	private Nxt23_EngineControl Engine2;
	
	MoveRobot(String destination) {
		this.destination = destination;
		
		Engine1 = new Nxt25_EngineControl();
		Engine2 = new Nxt23_EngineControl();
		
	}

	void setDestination(String destination) {
		this.destination = destination;
	}

	boolean startrobot() {
		
		
		
		Nxt25_EngineControl Engine1 = new Nxt25_EngineControl('A');
		Nxt23_EngineControl Engine2 = new Nxt23_EngineControl('1');
		
		
		
		
		
		
		
		
		
		return true;
	}
	
	char[] get_coordinates() {
		char[] coordinates = null;
		
		
		
		return coordinates;
	}
	
	
	

}
