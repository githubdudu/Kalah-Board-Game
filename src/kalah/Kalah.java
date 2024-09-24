package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.operation.Operation;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
	private Game game;
	private GameControl gameControl;

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

		this.game = new Game();
		this.gameControl = new GameControl(io);

		// Initialize the game
		gameControl.initGame(game);

		while(true) {
			// Print the board and menu even before the game starts
			gameControl.printBoard(game);
			gameControl.printMenu();
			Operation operation = gameControl.getOperation(game);
			if(operation == null) {
				break;
			}
			operation.execute();

			// Terminate the game when it is over
			if (gameControl.isGameOver(game)) {
				gameControl.printBoard(game);
				gameControl.printResult(game);
				break;
			}
		}

	}
	public void play(IO io, boolean vertical, boolean bmf) {
		// DO NOT CHANGE. Only here for backwards compatibility
	        play(io);
	}
}
