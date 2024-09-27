package kalah;

import java.io.*;

import static kalah.GameSetting.*;

public class Game {
    private final int numberOfHouses;
    private final int seedsPerHouse;
    private KalahBoard board;
    private Player currentPlayer;
    private Player player1;
    private Player player2;

    public Game() {
        this(DEFAULT_NUMBER_OF_HOUSES, DEFAULT_SEEDS_PER_HOUSE, DEFAULT_PLAYER1_NAME,
                DEFAULT_PLAYER2_NAME);
    }

    public Game(int numberOfHouses, int seedsPerHouse, String player1Name, String player2Name) {
        this.board = new KalahBoard(numberOfHouses, seedsPerHouse);
        this.player1 = new Player(player1Name, PlayerTag.P1, board);
        this.player2 = new Player(player2Name, PlayerTag.P2, board);
        this.currentPlayer = player1;

        this.numberOfHouses = numberOfHouses;
        this.seedsPerHouse = seedsPerHouse;
    }

    public GameSave createSave() {
        try {
            return new GameSave(board, currentPlayer);
        } catch (IOException e) {
            System.out.println("Error saving the game.");
            e.printStackTrace();
            return null;
        }
    }

    public void restoreSave(GameSave save) {
        try {
            this.board = save.getBoardState();
            this.currentPlayer = save.getCurrentPlayerState();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error restoring the game.");
            e.printStackTrace();
        }
    }

    public void newGame() {
        this.board = new KalahBoard(numberOfHouses, seedsPerHouse);
        this.currentPlayer = player1;
    }

    /**
     * Inner class to save the game. Work as memento in Memento Pattern.
     */
    public class GameSave {
        private final byte[] boardMomento;
        private final byte[] currentPlayerMomento;

        public GameSave(KalahBoard board, Player currentPlayer) throws IOException {
            this.boardMomento = objectToBytes(board);
            this.currentPlayerMomento = objectToBytes(currentPlayer);
        }

        private byte[] objectToBytes(Object obj) throws IOException {
            ByteArrayOutputStream baStream = new ByteArrayOutputStream();
            ObjectOutputStream ooStream = new ObjectOutputStream(baStream);
            ooStream.writeObject(obj);
            return baStream.toByteArray();
        }

        private Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
            ObjectInputStream oiStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return oiStream.readObject();
        }

        private KalahBoard getBoardState() throws IOException, ClassNotFoundException {
            return (KalahBoard) bytesToObject(boardMomento);
        }

        private Player getCurrentPlayerState() throws IOException, ClassNotFoundException {
            return (Player) bytesToObject(currentPlayerMomento);
        }
    }
}
