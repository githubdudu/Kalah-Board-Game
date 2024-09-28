package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.operation.*;

public class GameControl {
    private final IO io;
    private final Game game;
    private final BoardPrinter printer;
    private Game.GameSave gameSave;

    public GameControl(IO io) {
        this.io = io;
        this.game = new Game();
        printer = new BoardPrinter(io, game.getPlayer1(), game.getPlayer2());
    }

    public void setGameSave(Game.GameSave save) {
        this.gameSave = save;
    }

    public void clearSave() {
        setGameSave(null);
    }

    public Operation getOperation() {
        String input = io.readFromKeyboard("Choice:");
        switch (input) {
            case "n":
            case "N":
                return new NewGameOperation(game, this);
            case "s":
            case "S":
                return new SaveGameOperation(game, this);
            case "l":
            case "L":
                return new LoadGameOperation(game, this.gameSave, io);
            case "q":
            case "Q":
                return new QuitGameOperation(this);
            default:
                try {
                    int houseNumber = Integer.parseInt(input);
                    if (houseNumber >= 1 && houseNumber <= 6) {
                        HouseChoice houseChoice = new HouseChoice(houseNumber,
                                game.getCurrentPlayer());
                        return new MoveOperation(game, houseChoice, io);
                    }
                    io.println("House number must be 1 to 6");
                    return new InvalidOperation();
                } catch (NumberFormatException e) {
                    io.println("Invalid input");
                }

                return new InvalidOperation();
        }
    }

    public boolean isQuitOperation(Operation operation) {
        return operation instanceof QuitGameOperation;
    }

    public boolean isOperationInvalid(Operation operation) {
        return operation == null || operation instanceof InvalidOperation;
    }

    public void printBoard() {
        printer.displayBoard();
    }

    /**
     * Print the menu for the current player.
     */
    public void printMenu() {
        io.println("Player " + game.getCurrentPlayer().getPlayerTag());
        io.println("    (1-6) - house number for move");
        io.println("    N - New game");
        io.println("    S - Save game");
        io.println("    L - Load game");
        io.println("    q - Quit");
    }

    /**
     * Returns whether the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        if (game.getPlayer1().equals(game.getCurrentPlayer())) {
            return game.getPlayer1().getHousesSeeds() == 0;
        } else {
            return game.getPlayer2().getHousesSeeds() == 0;
        }
    }

    public void printGameOver() {
        io.println("Game over");
    }

    public void printResult() {
        int player1Score = game.getPlayer1().getPlayerScore();
        int player2Score = game.getPlayer2().getPlayerScore();
        io.println(String.format("\tplayer 1:%d", player1Score));
        io.println(String.format("\tplayer 2:%d", player2Score));

        if (player1Score == player2Score) {
            io.println("A tie!");
        } else {
            String winner = player1Score > player2Score ? "Player 1" : "Player 2";
            String result = String.format("%s wins!", winner);
            io.println(result);
        }
    }
}
