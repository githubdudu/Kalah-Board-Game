package kalah;

import java.io.Serializable;

public class KalahBoard implements Serializable {

    private final int numberOfHouses;
    private final int seedsPerHouse;

    public KalahBoard(int numberOfHouses, int seedsPerHouse) {
        this.numberOfHouses = numberOfHouses;
        this.seedsPerHouse = seedsPerHouse;
    }

    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    public int getSeedsPerHouse() {
        return seedsPerHouse;
    }
}
