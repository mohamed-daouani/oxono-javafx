package g62275.dev3.oxono.model;

/**
 * Represents a command for inserting a token into the game.
 *
 * Part of the **Command Design Pattern**.
 * Allows execution and undo of token insertion.
 */
public class InsertionCommand implements Command {

    private Game game;
    private Position position;
    private boolean commandExecuted;

    /**
     * Creates a new insertion command.
     *
     * @param game     The game instance.
     * @param position The position where the token will be inserted.
     */
    public InsertionCommand(Game game, Position position) {
        this.game = game;
        this.position = position;
        this.commandExecuted = false;
    }

    /**
     * Executes the insertion command.
     * Sets the game state to INSERT if necessary and attempts to insert a token.
     *
     * @throws IllegalStateException If the insertion fails.
     */
    @Override
    public void execute() {
        if (game.getGameSate() != GameSate.INSERT) {
            game.setGameSate(GameSate.INSERT);
        }
        if (game.insert(position)) {
            commandExecuted = true;
        } else {
            throw new IllegalStateException("Redo failed: Insert could not be executed.");
        }
    }

    /**
     * Undoes the insertion by removing the token from the position.
     */
    @Override
    public void unexecute() {
        if (commandExecuted && game.isValidPosition(position)) {
            game.removeToken(position);
            commandExecuted = false;
            game.setGameSate(GameSate.INSERT);
        }
    }
}
