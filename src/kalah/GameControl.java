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
        String input = io.readFromKeyboard(GameSetting.MENU_MESSAGE.CHOICE_PROMPT);
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
                    io.println(String.format(GameSetting.INVALID_HOUSE_MESSAGE,
                            game.getNumberOfHouses()));
                    return new InvalidOperation();
                } catch (NumberFormatException e) {
                    io.println(GameSetting.INVALID_INPUT_MESSAGE);
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
        io.println(
                GameSetting.MENU_MESSAGE.PLAYER_TAG + " " + game.getCurrentPlayer().getPlayerTag());
        io.println("    (1-6) - " + GameSetting.MENU_MESSAGE.HOUSE_CHOICE);
        io.println("    N - " + GameSetting.MENU_MESSAGE.NEW_GAME);
        io.println("    S - " + GameSetting.MENU_MESSAGE.SAVE_GAME);
        io.println("    L - " + GameSetting.MENU_MESSAGE.LOAD_GAME);
        io.println("    q - " + GameSetting.MENU_MESSAGE.QUIT);
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
        io.println(GameSetting.GAME_OVER_MESSAGE);
    }

    public void printResult() {
        int player1Score = game.getPlayer1().getPlayerScore();
        int player2Score = game.getPlayer2().getPlayerScore();
        io.println("\t" + game.getPlayer1().getPlayerName() + ":" + player1Score);
        io.println("\t" + game.getPlayer2().getPlayerName() + ":" + player2Score);

        if (player1Score == player2Score) {
            io.println(GameSetting.GAME_OVER_DRAW_MESSAGE);
        } else {
            String winner = player1Score > player2Score ? "1" : "2";
            String result = String.format(GameSetting.GAME_OVER_WINNER_MESSAGE, winner);
            io.println(result);
        }
    }
}
