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
            gameControl.printMenu();
            Operation operation = gameControl.getOperation();
            if (gameControl.isOperationInvalid(operation)) {
                break;
            }
            operation.execute();

            // Terminate the game when it is over
            if (gameControl.isGameOver()) {
                gameControl.printBoard();
                gameControl.printResult();
                break;
            }
        }
    }

    public void play(IO io, boolean vertical, boolean bmf) {
        // DO NOT CHANGE. Only here for backwards compatibility
        play(io);
    }
}
