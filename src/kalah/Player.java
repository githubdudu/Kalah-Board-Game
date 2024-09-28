package kalah;

import java.io.Serializable;
import java.util.Iterator;

public class Player implements Serializable {
    private final String playerName;
    private final PlayerTag playerTag;
    private final int numberOfHouses;
    /**
     * The list of house choices for the player.
     */
    protected HouseChoice[] housesChoicesList;
    private KalahBoard board;

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
     * Initialize the house choices list.
     */
    private void initHouseChoicesList() {
        this.housesChoicesList = new HouseChoice[numberOfHouses];
        for (int i = 0; i < numberOfHouses; i++) {
            this.housesChoicesList[i] = new HouseChoice(i + 1, this);
        }
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public PlayerTag getPlayerTag() {
        return this.playerTag;
    }

    public void setBoard(KalahBoard board) {
        this.board = board;
    }

    /**
     * Get the player's house seeds list
     *
     * @return the player's house seeds list
     */
    public int[] getHouseSeedsList() {
        int[] houseSeedsList = new int[numberOfHouses];
        for (int i = 0; i < numberOfHouses; i++) {
            houseSeedsList[i] = board.getSeedCount(housesChoicesList[i]);
        }
        return houseSeedsList;
    }

    /**
     * Get the player's store seeds.
     *
     * @return the player's store seeds
     */
    public int getStoreSeeds() {
        return board.getStore(this).getSeeds();
    }

    /**
     * Get the player's total score.
     *
     * @return the player's total score
     */
    public int getPlayerScore() {
        return getStoreSeeds() + getHousesSeeds();
    }

    /**
     * Get the player's score from the houses.
     *
     * @return the player's score from the houses
     */
    public int getHousesSeeds() {
        int score = 0;
        for (int i : getHouseSeedsList()) {
            score += i;
        }
        return score;
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
