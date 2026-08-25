package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {

    public static void main(String[] args) {
        new Kalah().play(new MockIO());
    }

    public void play(IO io) {
        Game game = new Game();
        BoardPrinter printer = new BoardPrinter(io);
        GameControl gameControl = new GameControl(game, printer);

        // Print board at the start of the game
        do {
            gameControl.printBoard();
            gameControl.printMenu();
        } while (gameControl.playOneRound());

        // Game Over
        if (gameControl.isGameOver()) {
            gameControl.printBoard();
            gameControl.printGameOver();
            // We print the board and result again after the game is over
            gameControl.printBoard();
            gameControl.printResult();
        }
    }

    public void play(IO io, boolean vertical, boolean bmf) {
        // DO NOT CHANGE. Only here for backwards compatibility
        play(io);
    }
}
