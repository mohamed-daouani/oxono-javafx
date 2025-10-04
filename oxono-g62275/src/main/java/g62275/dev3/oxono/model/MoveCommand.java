package g62275.dev3.oxono.model;

/**
 * Represents a command for moving a token in the game.
 *
 * Part of the **Command Design Pattern**.
 * Allows execution and undo of token movement.
 */
public class MoveCommand implements Command {

    private Game game;
    private Position newPosition;
    private Position oldPosition;
    private Symbol symbol;
    private boolean commandExecuted;

    /**
     * Creates a new move command.
     *
     * @param game        The game instance.
     * @param oldPosition The initial position of the token.
     * @param newPosition The target position for the token.
     * @param symbol      The symbol being moved.
     */
    public MoveCommand(Game game, Position oldPosition, Position newPosition, Symbol symbol) {
        this.game = game;
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.symbol = symbol;
        this.commandExecuted = false;
    }

    /**
     * Executes the move command.
     * Sets the game state to MOVE if necessary and attempts to move the token.
     *
     * @throws IllegalStateException If the move fails.
     */
    @Override
    public void execute() {
        if (game.getGameSate() != GameSate.MOVE) {
            game.setGameSate(GameSate.MOVE);
        }
        if (game.move(game.getTotem(symbol), newPosition)) {
            commandExecuted = true;
        } else {
            throw new IllegalStateException("Redo failed: Move could not be executed.");
        }
    }

    /**
     * Undoes the move by moving the token back to its original position.
     */
    @Override
    public void unexecute() {
        if (commandExecuted && oldPosition != null) {
            if (game.move(game.getTotem(symbol), oldPosition)) {
                commandExecuted = false;
                game.setGameSate(GameSate.MOVE);
            }
        }
    }
}
