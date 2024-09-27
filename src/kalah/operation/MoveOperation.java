package kalah.operation;

import kalah.Game;

public class MoveOperation implements Operation {
    private Game game;
    private int house;

    public MoveOperation(Game game, int house) {
        this.game = game;
        this.house = house;
    }

    @Override
    public void execute() {
        // TODO: Implement this method
        System.out.println("Make a move!");
    }
}
