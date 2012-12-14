import lejos.nxt.Motor;
import lejos.robotics.navigation.TachoPilot;

public class Nxt23_EngineControl {

    /*
     * z1 ist die Strecke zwischen den Felder f�r das Vorw�rts und
     * R�ckw�rtsbewegen z2 ist die Strecke f�r die Hoch/Runter bewegung z3 ist
     * die Strecke zwischen den Feldern f�r die Links/rechts bewegung z4 ist die
     * Rotation f�r das auf und zu greifen
     */

    final private int ROTATE_FOR_Z2 = 15;
    final private int ROTATE_FOR_Z3 = 10;

    private final TachoPilot MB1_MB2 = new TachoPilot(3.2f, 11.4f, Motor.A, Motor.B);
    private final Motor MC1 = Motor.C;

    private int columnFrom;
    private int columnTo;

    private int movedDistance;

    Nxt23_EngineControl() {
        this.movedDistance = 0;
    }

    Nxt23_EngineControl(int columnFrom, int columnTo) {
        this.columnFrom = columnFrom;
        this.columnTo = columnTo;

        this.movedDistance = 0;
    }

    void setColumn(int columnFrom, int columnTo) {
        this.columnFrom = columnFrom;
        this.columnTo = columnTo;

    }

    boolean moveToColumnFrom() {
        int distance = this.columnFrom - this.movedDistance;

        this.MC1.rotate(this.movedDistance * ROTATE_FOR_Z3);
        this.movedDistance += distance;

        return true;
    }

    boolean moveToColumnTo() {
        int distance = this.columnTo - this.movedDistance;

        this.MC1.rotate(this.movedDistance * ROTATE_FOR_Z3);
        this.movedDistance += distance;

        return true;
    }

    boolean moveUp() {
        this.MB1_MB2.travel(-ROTATE_FOR_Z2);
        return true;
    }

    boolean moveDown() {
        this.MB1_MB2.travel(ROTATE_FOR_Z2);
        return true;
    }

    boolean moveToInit() {
        this.MC1.rotate(-this.movedDistance * ROTATE_FOR_Z3);
        this.movedDistance = 0;
        return true;
    }

}
