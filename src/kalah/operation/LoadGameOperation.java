package kalah.operation;

import com.qualitascorpus.testsupport.IO;
import kalah.Game;
import kalah.GameSetting;

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
            io.println(GameSetting.NO_SAVED_GAME_MESSAGE);
            return;
        }
        this.game.restoreSave(this.gameSave);
    }
}
