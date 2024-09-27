package kalah.operation;

import kalah.Game;

public class QuitGameOperation implements Operation {
    public QuitGameOperation(Game game) {
    }

    @Override
    public void execute() {
        // TODO: Implement this method
        System.out.println("Quit the game!");
    }
}
