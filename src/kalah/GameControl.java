package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.operation.*;

public class GameControl {
    private final IO io;
    private final Game game;
    private Game.GameSave gameSave;

    public GameControl(IO io) {
        this.io = io;
        this.game = new Game();
    }

    private Game.GameSave getGameSave() {
        return this.gameSave;
    }


    public void setGameSave(Game.GameSave save) {
        this.gameSave = save;
    }

    public void resetSave() {
        setGameSave(null);
    }

    public Operation getOperation() {
        String input = io.readFromKeyboard("Choice:");
        switch (input) {
            case "n":
                return new NewGameOperation(game, this);
            case "s":
                return new SaveGameOperation(game, this);
            case "l":
                return new LoadGameOperation(game, this.getGameSave());
            case "q":
                return new QuitGameOperation(game);
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

    public boolean isOperationInvalid(Operation operation) {
        return operation == null || operation instanceof InvalidOperation;
    }

    public void printBoard() {
    }

    public void printMenu() {
    }

    public boolean isGameOver() {
        return false;
    }

    public void printResult() {
    }

}
