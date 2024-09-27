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
        // Replace what's below with your implementation
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("Player 1's turn - Specify house number or 'q' to quit: ");

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
