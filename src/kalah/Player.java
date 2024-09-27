package kalah;

import java.io.Serializable;

public class Player implements Serializable {
    private final String playerName;
    private final PlayerTag playerTag;
    private final KalahBoard board;
    private final int numberOfHouses;

    /**
     * Create a player with a name, a tag and a board.
     *
     * @param playerName the name of the player
     * @param playerTag  the tag of the player
     * @param board      the board where the player plays
     */
    public Player(String playerName, PlayerTag playerTag, KalahBoard board) {
        this.playerName = playerName;
        this.playerTag = playerTag;
        this.board = board;
        this.numberOfHouses = board.getNumberOfHouses();
        initHouseChoicesList();
    }

    /**
     * TODO: delete it?
     */
    private void initHouseChoicesList() {
    }

    public PlayerTag getPlayerTag() {
        return this.playerTag;
    }
}
