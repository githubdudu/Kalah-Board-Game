package kalah;

import java.io.Serializable;
import java.util.Iterator;

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

    /**
     * Sow seeds in board. Return the last pit where the last seed was sown.
     *
     * @param houseChoice the choice of the house where the sowing starts
     * @return the last pit where the last seed was sown
     */
    public KalahBoard.Pit sow(HouseChoice houseChoice) {

        Iterator<KalahBoard.Pit> pitIterator = board.iteratorAntiClockwise(houseChoice);
        KalahBoard.Pit pit = pitIterator.next();
        int seeds = pit.getSeeds();
        pit.setSeeds(0);

        while (seeds > 0) {
            pit = pitIterator.next();
            if (pit.isStore() && pit.getOwner() != playerTag) {
                continue;
            }
            pit.setSeeds(pit.getSeeds() + 1);
            seeds--;
        }
        return pit;
    }

    /**
     * Capture seeds from the opposite pit and the current pit into store.
     * if the last seed was sown in an empty pit on the player's side.
     * Do nothing if the pit is a store.
     *
     * @param pit the last pit where the last seed was sown
     */
    public void capture(KalahBoard.Pit pit) {
        if (pit.isStore()) {
            return;
        }
        KalahBoard.Pit store = board.getStore(this);
        KalahBoard.Pit oppositePit = board.oppositePit(pit);
        store.setSeeds(store.getSeeds() + pit.getSeeds() + oppositePit.getSeeds());

        pit.setSeeds(0);
        oppositePit.setSeeds(0);
    }
}
