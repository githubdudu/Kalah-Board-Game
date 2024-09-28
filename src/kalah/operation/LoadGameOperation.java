package kalah.operation;

import com.qualitascorpus.testsupport.IO;
import kalah.Game;

public class LoadGameOperation implements Operation {
    private final Game game;
    private final Game.GameSave gameSave;
    private final IO io;

    public LoadGameOperation(Game game, Game.GameSave gameSave, IO io) {
        this.game = game;
        this.gameSave = gameSave;
        this.io = io;
    }

    @Override
    public void execute() {
        if (this.gameSave == null) {
            io.println("No saved game");
            return;
        }
        this.game.restoreSave(this.gameSave);
    }
}
