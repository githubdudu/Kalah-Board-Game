package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.operation.*;

public class GameControl {
    private final IO io;

    public GameControl(IO io) {
        this.io = io;
    }

    public void initGame(Game game) {
        Operation operation = new NewGameOperation(game);
        operation.execute();
    }

    public Operation getOperation(Game game) {
        String input = io.readFromKeyboard("Choice:");
        switch (input) {
            case "n":
                return new NewGameOperation(game);
            case "s":
                return new SaveGameOperation(game);
            case "l":
                return new LoadGameOperation(game);
            case "q":
                return null;
            default:
                try {
                    int houseNumber = Integer.parseInt(input);
                    if (houseNumber >= 1 && houseNumber <= 6) {
                        return new MoveOperation(game, houseNumber);
                    }
                    io.println("House number must be 1 to 6");
                    return null;
                } catch (NumberFormatException e) {
                    io.println("Invalid input");
                }
                return null;
        }
    }

    public void printBoard(Game game) {
    }

    public void printMenu() {
    }

    public boolean isGameOver(Game game) {
        return false;
    }

    public void printResult(Game game) {
    }
}
