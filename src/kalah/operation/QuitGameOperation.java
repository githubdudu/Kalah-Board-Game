package kalah.operation;

import kalah.GameControl;

public class QuitGameOperation implements Operation {
    private final GameControl gameControl;

    public QuitGameOperation(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    @Override
    public void execute() {
        gameControl.printGameOver();
        gameControl.printBoard();
    }
}
