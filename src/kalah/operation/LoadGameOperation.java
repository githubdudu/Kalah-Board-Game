package kalah.operation;

import kalah.Game;

public class LoadGameOperation implements Operation {
    private final Game game;
    private final Game.GameSave gameSave;

    public LoadGameOperation(Game game, Game.GameSave gameSave) {
        this.game = game;
        this.gameSave = gameSave;
    }

    @Override
    public void execute() {
        // TODO: Implement this method
        if (this.gameSave == null) {
            System.out.println("No game saved.");
            return;
        }
        System.out.println("Loading game...");
        this.game.restoreSave(this.gameSave);
        System.out.println("Game loaded.");
    }
}
