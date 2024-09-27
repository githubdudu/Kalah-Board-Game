package kalah.operation;


import com.qualitascorpus.testsupport.IO;
import kalah.*;

public class MoveOperation implements Operation {
    private Game game;
    private HouseChoice houseChoice;
    private IO io;

    public MoveOperation(Game game, HouseChoice houseChoice, IO io) {
        this.game = game;
        this.houseChoice = houseChoice;
        this.io = io;
    }

    @Override
    public void execute() {
        KalahBoard board = game.getBoard();
        int seeds = board.getSeedCount(houseChoice);
        // Player should choose again if chosen House is empty.
        if (seeds == 0) {
            this.displayMoveAgain();
            return;
        }

        Player currentPlayer = game.getCurrentPlayer();
        KalahBoard.Pit lastPit = currentPlayer.sow(houseChoice);
        if (canCapture(currentPlayer, board, lastPit)) {
            currentPlayer.capture(lastPit);
        }
        if (!isPlayerGetAnotherMove(currentPlayer, lastPit)) {
            switchPlayer(currentPlayer, game);
        }
    }

    private void displayMoveAgain() {
        io.println("House is empty. Move again.");
    }


    /**
     * Returns whether the current status meets the conditions for capturing seeds.
     *
     * @return true if the current player can capture seeds, false otherwise
     */
    private boolean canCapture(Player currentPlayer, KalahBoard board, KalahBoard.Pit lastPit) {
        return lastPit.isHouse()
                && lastPit.getOwner() == currentPlayer.getPlayerTag()
                && lastPit.getSeeds() == 1
                && board.oppositePit(lastPit).getSeeds() != 0;
    }


    /**
     * Returns whether the current player gets another move.
     *
     * @return true if the current player gets another move, false otherwise
     */
    private boolean isPlayerGetAnotherMove(Player currentPlayer, KalahBoard.Pit lastPit) {
        return lastPit.isStore() && (lastPit.getOwner() == currentPlayer.getPlayerTag());
    }

    /**
     * Switch the current player.
     */
    private void switchPlayer(Player currentPlayer, Game game) {
        if (currentPlayer.getPlayerTag() == PlayerTag.P1) {
            game.setCurrentPlayer(game.getPlayer2());
        } else {
            game.setCurrentPlayer(game.getPlayer1());
        }
    }
}
