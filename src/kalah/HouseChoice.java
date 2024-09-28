package kalah;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a house choice in the Kalah game.
 * Specified by the house number and the player tag.
 */
public class HouseChoice implements Serializable {
    /**
     * The player tag. Either P1 or P2.
     */
    private final PlayerTag playerTag;
    /**
     * The number of the house. The house number is 1-based.
     */
    private final int houseNumber;

    /**
     * Constructs a house choice with the given house number and player.
     *
     * @param houseNumber the number of the house
     * @param player      the player
     */
    public HouseChoice(int houseNumber, Player player) {
        this(houseNumber, player.getPlayerTag());
    }

    /**
     * Constructs a house choice with the given house number and player tag.
     *
     * @param houseNumber the number of the house
     * @param playerTag   the player tag
     */
    public HouseChoice(int houseNumber, PlayerTag playerTag) {
        this.houseNumber = houseNumber;
        this.playerTag = playerTag;
    }

    /**
     * Returns the number of the house.
     *
     * @return the number of the house
     */
    public int getHouseNumber() {
        return houseNumber;
    }

    /**
     * Returns the player tag.
     *
     * @return the player tag
     */
    public PlayerTag getPlayerTag() {
        return playerTag;
    }

    /**
     * Check equality of two house choices.
     *
     * @param obj the object to compare
     * @return true if the house choices are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof HouseChoice)) {
            return false;
        }
        HouseChoice other = (HouseChoice) obj;
        return houseNumber == other.houseNumber && playerTag == other.playerTag;
    }

    /**
     * Returns the hash code of the house choice.
     *
     * @return the hash code of the house choice
     */
    @Override
    public int hashCode() {
        return Objects.hash(houseNumber, playerTag);
    }
}
