package kalah.operation;

import kalah.Game;
import kalah.GameControl;

public class NewGameOperation implements Operation {
    private final Game game;
    private final GameControl gameControl;

    public NewGameOperation(Game game, GameControl gameControl) {
        this.game = game;
        this.gameControl = gameControl;
    }

    @Override
    public void execute() {
        gameControl.resetSave();
        // TODO: Implement this method
        System.out.println("new game!");
        game.newGame();
    }
}
