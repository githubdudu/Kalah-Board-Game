package kalah.operation;

import kalah.Game;
import kalah.GameControl;

public class SaveGameOperation implements Operation {
    private final Game game;
    private final GameControl gameControl;

    public SaveGameOperation(Game game, GameControl gameControl) {
        this.game = game;
        this.gameControl = gameControl;
    }

    @Override
    public void execute() {
        gameControl.setGameSave(game.createSave());
    }
}
