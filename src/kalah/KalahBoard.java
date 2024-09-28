package kalah;

import java.io.Serializable;
import java.util.Iterator;

public class KalahBoard implements Serializable {

    /**
     * The number of houses on each side of the board.
     */
    private final int numberOfHouses;
    /**
     * The index of the store for player 1 in the pits array.
     */
    private final int player1StoreIndex;
    /**
     * The index of the store for player 2 in the pits array.
     */
    private final int player2StoreIndex;
    /**
     * The index of the first house for player 1 in the pits array.
     */
    private final int player1HouseStartIndex;
    /**
     * The index of the first house for player 2 in the pits array.
     */
    private final int player2HouseStartIndex;
    private final Pit[] pits;

    public KalahBoard(int numberOfHouses, int seedsPerHouse) {
        this.numberOfHouses = numberOfHouses;
        this.player1StoreIndex = numberOfHouses;
        this.player2StoreIndex = numberOfHouses * 2 + 1;
        this.player1HouseStartIndex = 0;
        this.player2HouseStartIndex = numberOfHouses + 1;

        this.pits = new Pit[numberOfHouses * 2 + 2];
        for (int i = 0; i < numberOfHouses; i++) {
            pits[i + player1HouseStartIndex] = new Pit(i + player1HouseStartIndex, seedsPerHouse);
            pits[i + player2HouseStartIndex] = new Pit(i + player2HouseStartIndex, seedsPerHouse);
        }
        pits[player1StoreIndex] = new Pit(player1StoreIndex, 0);
        pits[player2StoreIndex] = new Pit(player2StoreIndex, 0);
    }

    /**
     * Get the seed count in the specified house of the specified player.
     *
     * @param houseChoice the choice of house of one specific player
     * @return the seed count in the specified house of the specified player
     */
    public int getSeedCount(HouseChoice houseChoice) {
        return pits[getPitIndex(houseChoice)].seeds;
    }

    /**
     * Set the seed count in the specified house of the specified player.
     *
     * @param houseChoice the choice of house of one specific player
     * @param seedCount   the seed count to set
     */
    public void setSeedCount(HouseChoice houseChoice, int seedCount) {
        pits[getPitIndex(houseChoice)].seeds = seedCount;
    }

    /**
     * Get the house of the specified player with the specified house number.
     *
     * @param houseChoice the choice of house of one specific player
     * @return the house of the specified player with the specified house number
     */
    public Pit getHouse(HouseChoice houseChoice) {
        return pits[getPitIndex(houseChoice)];
    }

    /**
     * Get the store of the specified player.
     *
     * @param player the player
     * @return the store of the specified player
     */
    public Pit getStore(Player player) {
        if (player.getPlayerTag() == PlayerTag.P1) {
            return pits[player1StoreIndex];
        } else {
            return pits[player2StoreIndex];
        }
    }

    /**
     * Get the store of the specified player.
     *
     * @param player the player
     * @return the store of the specified player
     */
    public int getStoreCount(Player player) {
        if (player.getPlayerTag() == PlayerTag.P1) {
            return pits[player1StoreIndex].seeds;
        } else {
            return pits[player2StoreIndex].seeds;
        }
    }

    /**
     * Get the index of the pit in the pits array with the specified house number of the specified player.
     *
     * @param houseChoice the choice of house of one specific player
     * @return the index of the pit with the specified house number of the specified player
     */
    private int getPitIndex(HouseChoice houseChoice) {
        int houseNumber = houseChoice.getHouseNumber();
        PlayerTag playerType = houseChoice.getPlayerTag();
        if (playerType == PlayerTag.P1) {
            return houseNumber - 1 + player1HouseStartIndex;
        } else {
            return houseNumber - 1 + player2HouseStartIndex;
        }
    }

    /**
     * Get the iteratorAntiClockwise for the pits of
     * the specified player with the specified house number.
     * It starts from the specified house and runs infinitely.
     *
     * @param houseChoice the choice of house of one specific player
     * @return the iteratorAntiClockwise for the pits of the specified player with the specified
     * house number
     */
    public Iterator<Pit> iteratorAntiClockwise(HouseChoice houseChoice) {
        return new PitIterator(getPitIndex(houseChoice));
    }

    /**
     * Get the pit opposite to the specified pit.
     * The sum of the index of the specified pit and the index of the opposite pit is equal to
     * two times the number of houses on each side of the board.
     *
     * @param pit the pit
     * @return the pit opposite to the specified pit
     */
    public Pit oppositePit(Pit pit) {
        //  |   |12 |11 |10 | 9 | 8 | 7 |   |
        //  |   |---+---+---+---+---+---+   |
        //  |13 | 0 | 1 | 2 | 3 | 4 | 5 | 6 |
        int index = oppositeIndex(pit.index);
        if (index >= 0 && index <= numberOfHouses * 2) {
            return pits[index];
        }
        return null;
    }

    /**
     * Get the index of the pit opposite to the specified index.
     *
     * @param i the index of the pit
     * @return the index of the pit opposite to the specified index
     */
    private int oppositeIndex(int i) {
        return numberOfHouses * 2 - i;
    }

    public int getNumberOfHouses() {
        return this.numberOfHouses;
    }

    public class Pit {
        private final int index;
        private int seeds;

        Pit(int index, int seeds) {
            if (index < 0 || index > numberOfHouses * 2 + 1) {
                throw new IllegalArgumentException(
                        "Invalid pit index, it should be between 0 and " + (numberOfHouses * 2 + 1));
            }
            this.index = 0;
            this.seeds = seeds;
        }

        public boolean isHouse() {
            return !isStore();
        }

        public boolean isStore() {
            return index == player1StoreIndex || index == player2StoreIndex;
        }

        public PlayerTag getOwner() {
            if (index >= player1HouseStartIndex && index < player2HouseStartIndex) {
                return PlayerTag.P1;
            } else {
                return PlayerTag.P2;
            }
        }

        public int getSeeds() {
            return seeds;
        }

        public void setSeeds(int seeds) {
            this.seeds = seeds;
        }
    }

    /**
     * The inner PitIterator class that implements the Iterator interface.
     * This iterator iterates the pits in an anti-clockwise direction.
     * It starts from the specified index and runs infinitely.
     * It is used to iterate the pits of the specified player with the specified house number.
     */
    private class PitIterator implements Iterator<Pit> {
        /**
         * The index of the current pit in the pits array.
         */
        private int index;

        /**
         * Creates a new PitIterator with the specified index.
         *
         * @param index the index of the current pit in the pits array
         */
        public PitIterator(int index) {
            this.index = index;
        }

        /**
         * Returns true if the iteration has more elements.
         * Always returns true because it runs infinitely.
         *
         * @return true if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return true;
        }

        /**
         * Returns the next element in the iteration.
         * It returns the pit at the current index and moves to the next index.
         *
         * @return the next element in the iteration
         */
        @Override
        public Pit next() {
            Pit nextPit = pits[index];
            index = (index + 1) % pits.length;
            return nextPit;
        }

        /**
         * Removes from the underlying collection the last element returned by this iterator.
         * Always throws UnsupportedOperationException because it is not supported.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

}
