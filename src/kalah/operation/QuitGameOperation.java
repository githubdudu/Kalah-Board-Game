package kalah.operation;

import com.qualitascorpus.testsupport.IO;
import kalah.GameControl;

public class QuitGameOperation implements Operation {
    private final GameControl gameControl;
    private final IO io;

    public QuitGameOperation(GameControl gameControl, IO io) {
        this.gameControl = gameControl;
        this.io = io;
    }

    @Override
    public void execute() {
        io.println("Game over");
        gameControl.printBoard();
    }
}
