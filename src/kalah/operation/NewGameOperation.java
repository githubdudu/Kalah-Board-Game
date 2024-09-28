package kalah.operation;

import kalah.Game;
import kalah.GameControl;
import kalah.KalahBoard;

public class NewGameOperation implements Operation {
    private final Game game;
    private final GameControl gameControl;

    public NewGameOperation(Game game, GameControl gameControl) {
        this.game = game;
        this.gameControl = gameControl;
    }

    @Override
    public void execute() {
        gameControl.clearSave();
        KalahBoard newBoard = new KalahBoard(game.getNumberOfHouses(), game.getSeedsPerHouse());
        game.setBoard(newBoard);
        game.setCurrentPlayer(game.getPlayer1());
    }
}
