package g62275.dev3.oxono.model;

import java.util.Random;

/**
 * Represents an AI player in the game.
 *
 * - Plays moves and insertions using the provided strategy.
 * - Operates only when the current player is BLACK.
 */
public class AI {
    private final Game game;
    private Symbol symbol;
    private Strategy strategy;
    private Random random = new Random();

    /**
     * Creates an AI player.
     *
     * @param game     The game instance.
     * @param strategy The strategy to use for moves and insertions.
     */
    public AI(Game game, Strategy strategy) {
        this.game = game;
        this.symbol = chooseRandomSymbol();
        this.strategy = strategy;
    }

    /**
     * Executes a move if the current state is MOVE and the current player is BLACK.
     */
    public void playMove() {
        if (game.getGameSate() == GameSate.MOVE && game.getCurrentPlayer() == Color.BLACK) {
            strategy.move(game, symbol);
        }
    }

    /**
     * Executes an insertion if the current state is INSERT and the current player is BLACK.
     * After insertion, selects a random symbol for the next turn.
     */
    public void playInsert() {
        if (game.getGameSate() == GameSate.INSERT && game.getCurrentPlayer() == Color.BLACK) {
            strategy.insert(game, symbol);
            symbol = chooseRandomSymbol();
        }
    }

    /**
     * Randomly selects a symbol (X or O).
     *
     * @return The chosen symbol.
     */
    private Symbol chooseRandomSymbol() {
        return random.nextBoolean() ? Symbol.X : Symbol.O;
    }
}
