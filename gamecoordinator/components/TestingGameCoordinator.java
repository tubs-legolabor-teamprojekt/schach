package components;

public class TestingGameCoordinator {

    public static void main(String[] args) {
        TestingGameCoordinator testGame = new TestingGameCoordinator();
        testGame.doStuff();
    }

    public void doStuff() {
        Field field = Field.getInstance();
        System.out.println(field.getCurrentField());
    }

}
