package kalah;

import java.io.*;

import static kalah.GameSetting.*;

public class Game {
    private final int numberOfHouses;
    private final int seedsPerHouse;
    private final Player player1;
    private final Player player2;
    private KalahBoard board;
    private Player currentPlayer;

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
            System.out.println(ERROR_MESSAGE_GAME_SAVING);
            e.printStackTrace();
            return null;
        }
    }

    public void restoreSave(GameSave save) {
        try {
            setBoard(save.getBoardState());
            setCurrentPlayer(save.getCurrentPlayerState());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(ERROR_MESSAGE_GAME_LOADING);
            e.printStackTrace();
        }
    }

    public KalahBoard getBoard() {
        return board;
    }

    public void setBoard(KalahBoard board) {
        this.board = board;
        this.player1.setBoard(board);
        this.player2.setBoard(board);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    public int getSeedsPerHouse() {
        return seedsPerHouse;
    }

    public Player getPlayer2() {
        return player2;
    }

    /**
     * Inner class to save the game. Work as memento in Memento Pattern.
     */
    public class GameSave {
        private final byte[] boardMomento;
        private final byte[] currentPlayerMomento;

        public GameSave(KalahBoard board, Player currentPlayer) throws IOException {
            this.boardMomento = objectToBytes(board);
            this.currentPlayerMomento = objectToBytes(currentPlayer.getPlayerTag());
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
            if (bytesToObject(currentPlayerMomento) == PlayerTag.P1) {
                return player1;
            } else {
                return player2;
            }
        }
    }
}
