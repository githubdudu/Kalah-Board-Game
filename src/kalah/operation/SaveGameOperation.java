package kalah.operation;

import kalah.Game;

public class SaveGameOperation implements Operation {
    private Game game;

    public SaveGameOperation(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

    }
}
