package kalah.operation;

public class InvalidOperation implements Operation {

    @Override
    public void execute() {
        System.out.println("Invalid operation!");
    }
}
