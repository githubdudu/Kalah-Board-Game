package kalah;

import com.qualitascorpus.testsupport.IO;

public class BoardPrinter {
    private IO io;

    public BoardPrinter(IO io) {
        this.io = io;
    }

    /**
     * Display a horizontal string representing the game board on console.
     * The display has been configured by the length of the house number.
     * In this way, the display is more flexible and can be used for different board sizes.
     * For example, a board with 6 houses on each side:
     * <pre>
     * +----+-------+-------+-------+-------+-------+-------+----+
     * | P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |
     * |    |-------+-------+-------+-------+-------+-------|    |
     * |  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |
     * +----+-------+-------+-------+-------+-------+-------+----+
     * </pre>
     */
    public void displayBoard(int player1StoreSeeds, int player2StoreSeeds, int[] player1HouseSeeds, int[] player2HouseSeeds) {
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");

        String[] lineP2 = generateStringList(player1StoreSeeds, player2HouseSeeds,
                "P2");
        io.println(generateLineHorizontal(reverse(lineP2)));

        io.println("|    |-------+-------+-------+-------+-------+-------|    |");

        String[] lineP1 = generateStringList(player2StoreSeeds, player1HouseSeeds,
                "P1");
        io.println(generateLineHorizontal(lineP1));

        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }

    private String[] generateStringList(int otherStoreSeeds, int[] houseSeedsList, String tag) {
        String[] lineP2 = new String[houseSeedsList.length + 2];

        lineP2[0] = String.format("%2d", otherStoreSeeds);
        for (int i = 0; i < houseSeedsList.length; i++) {
            lineP2[i + 1] = String.format("%d[%2d]", i + 1, houseSeedsList[i]);
        }
        lineP2[lineP2.length - 1] = tag;

        return lineP2;
    }

    private String[] reverse(String[] array) {
        String[] reversedArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            reversedArray[i] = array[array.length - 1 - i];
        }
        return reversedArray;
    }

    /**
     * Returns a string representing the line of the game board.
     * The line is displayed as follows,
     * <pre>
     * | P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |
     * </pre>
     *
     * @param line the string list of the line
     * @return a string representing the line of the game board
     */
    private String generateLineHorizontal(String[] line) {
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        for (String s : line) {
            sb.append(String.format(" %s |", s));
        }
        return sb.toString();
    }

    public String readFromKeyboard(String choicePrompt) {
        return io.readFromKeyboard(choicePrompt);
    }

    public void println(String s) {
        io.println(s);
    }

}
