package kalah;

import kalah.operation.*;

public class GameControl {
    private final Game game;
    public final BoardPrinter printer;
    private Game.GameSave gameSave;

    public GameControl(Game game, BoardPrinter printer) {
        this.game = game;
        this.printer = printer;
    }

    public void setGameSave(Game.GameSave save) {
        this.gameSave = save;
    }

    public Game.GameSave getGameSave() {
        return this.gameSave;
    }

    public void clearGameSave() {
        this.gameSave = null;
    }

    public Operation getOperation() {
        String input = printer.readFromKeyboard(GameSetting.MENU_MESSAGE.CHOICE_PROMPT);
        switch (input) {
            case "n":
            case "N":
                return new NewGameOperation(game, this);
            case "s":
            case "S":
                return new SaveGameOperation(game, this);
            case "l":
            case "L":
                return new LoadGameOperation(game, this);
            case "q":
            case "Q":
                return new QuitGameOperation();
            default:
                try {
                    MoveOperation mop = new MoveOperation(game, this);
                    mop.GetAction(input);
                    return mop;
                } catch (IllegalArgumentException e) {
                    printer.println("Invalid input!");
                    printer.println(e.getMessage());
                    return new InvalidOperation();
                }
        }
    }

    public boolean isQuitOperation(Operation operation) {
        return operation instanceof QuitGameOperation;
    }

    public boolean isOperationInvalid(Operation operation) {
        return operation == null || operation instanceof InvalidOperation;
    }

    public void printBoard() {
        printer.displayBoard(game.getPlayer1().getStoreSeeds(), game.getPlayer2().getStoreSeeds(), game.getPlayer1().getHouseSeedsList(), game.getPlayer2().getHouseSeedsList());
    }

    /**
     * Print the menu for the current player.
     */
    public void printMenu() {
        printer.println(GameSetting.MENU_MESSAGE.PLAYER_TAG + " " + game.getCurrentPlayer().getPlayerTag());
        printer.println("    (1-6) - " + GameSetting.MENU_MESSAGE.HOUSE_CHOICE);
        printer.println("    N - " + GameSetting.MENU_MESSAGE.NEW_GAME);
        printer.println("    S - " + GameSetting.MENU_MESSAGE.SAVE_GAME);
        printer.println("    L - " + GameSetting.MENU_MESSAGE.LOAD_GAME);
        printer.println("    q - " + GameSetting.MENU_MESSAGE.QUIT);
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
        printer.println(GameSetting.GAME_OVER_MESSAGE);
    }

    public void printResult() {
        int player1Score = game.getPlayer1().getPlayerScore();
        int player2Score = game.getPlayer2().getPlayerScore();
        printer.println("\t" + game.getPlayer1().getPlayerName() + ":" + player1Score);
        printer.println("\t" + game.getPlayer2().getPlayerName() + ":" + player2Score);

        if (player1Score == player2Score) {
            printer.println(GameSetting.GAME_OVER_DRAW_MESSAGE);
        } else {
            String winner = player1Score > player2Score ? "1" : "2";
            String result = String.format(GameSetting.GAME_OVER_WINNER_MESSAGE, winner);
            printer.println(result);
        }
    }

    public boolean playOneRound() {
        Operation op = getOperation();
        if (isOperationInvalid(op)) {
            return true;
        }
        if (isQuitOperation(op)) {
            this.printGameOver();
            this.printBoard();
            return false;
        }

        op.execute();

        return !isGameOver();
    }
}
