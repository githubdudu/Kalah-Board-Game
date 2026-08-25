package kalah.operation;

import kalah.Game;
import kalah.GameControl;
import kalah.GameSetting;

public class LoadGameOperation implements Operation {
    private final Game game;
    private final GameControl gc;

    public LoadGameOperation(Game game, GameControl gameControl) {
        this.game = game;
        this.gc = gameControl;
    }

    @Override
    public void execute() {
        if (this.gc.getGameSave() == null) {
            gc.printer.println(GameSetting.NO_SAVED_GAME_MESSAGE);
            return;
        }
        this.game.restoreSave(this.gc.getGameSave());
    }
}
