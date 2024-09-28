package kalah;

public class GameSetting {
    public static final int DEFAULT_NUMBER_OF_HOUSES = 6;
    public static final int DEFAULT_SEEDS_PER_HOUSE = 4;

    public static final String DEFAULT_PLAYER1_NAME = "player 1";
    public static final String DEFAULT_PLAYER2_NAME = "player 2";
    public static final String MOVE_AGAIN_MESSAGE = "House is empty. Move again.";
    public static final String NO_SAVED_GAME_MESSAGE = "No saved game";
    public static final String GAME_OVER_MESSAGE = "Game over";
    public static final String GAME_OVER_DRAW_MESSAGE = "A tie!";
    public static final String GAME_OVER_WINNER_MESSAGE = "Player %s wins!";
    public static final String INVALID_HOUSE_MESSAGE = "House number must be 1 to %d";
    public static final String INVALID_INPUT_MESSAGE = "Invalid input";
    public static final String INVALID_OPERATION_MESSAGE = "Invalid operation!";
    public static final String ERROR_MESSAGE_GAME_SAVING = "Error saving the game.";
    public static final String ERROR_MESSAGE_GAME_LOADING = "Error restoring the game.";

    public static final class MENU_MESSAGE {
        public static final String PLAYER_TAG = "Player";
        public static final String HOUSE_CHOICE = "house number for move";
        public static final String NEW_GAME = "New game";
        public static final String SAVE_GAME = "Save game";
        public static final String LOAD_GAME = "Load game";
        public static final String QUIT = "Quit";
        public static final String CHOICE_PROMPT = "Choice:";
    }
}
