package kalah;

import com.qualitascorpus.testsupport.IO;

public class BoardPrinter {
    /**
     * The IO object to use for input and output.
     */
    private final IO io;
    private final Player player1;
    private final Player player2;

    /**
     * Constructs a new printer.
     *
     * @param io      the IO object to use for input and output
     * @param player1 the player 1
     * @param player2 the player 2
     */
    public BoardPrinter(IO io, Player player1, Player player2) {
        this.io = io;
        this.player1 = player1;
        this.player2 = player2;
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
    public void displayBoard() {
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");

        String[] lineP2 = generateStringList(player1.getStoreSeeds(), player2.getHouseSeedsList(),
                "P2");
        io.println(generateLineHorizontal(reverse(lineP2)));

        io.println("|    |-------+-------+-------+-------+-------+-------|    |");

        String[] lineP1 = generateStringList(player2.getStoreSeeds(), player1.getHouseSeedsList(),
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
}
