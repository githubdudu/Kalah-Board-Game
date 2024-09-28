package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.operation.Operation;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {

    public static void main(String[] args) {
        new Kalah().play(new MockIO());
    }

    public void play(IO io) {
        GameControl gameControl = new GameControl(io);

        while (true) {
            // Print the board and menu even before the game starts
            gameControl.printBoard();

            // Terminate the game when it is over
            if (gameControl.isGameOver()) {
                gameControl.printGameOver();
                // We print the board and result again after the game is over
                gameControl.printBoard();
                gameControl.printResult();
                break;
            }

            gameControl.printMenu();
            Operation operation = gameControl.getOperation();
            if (gameControl.isQuitOperation(operation)) {
                operation.execute();
                break;
            } else if (gameControl.isOperationInvalid(operation)) {
                break;
            }
            operation.execute();

        }
    }

    public void play(IO io, boolean vertical, boolean bmf) {
        // DO NOT CHANGE. Only here for backwards compatibility
        play(io);
    }
}
