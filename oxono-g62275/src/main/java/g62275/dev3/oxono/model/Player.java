package g62275.dev3.oxono.model;

/**
 * Represents a player in the game.
 * Each player has a color and a limited number of tokens for each symbol (X and O).
 */
public class Player {

    /** The color of the player. */
    private Color color;

    /** Remaining tokens of type X. */
    private int tokensLeftX;

    /** Remaining tokens of type O. */
    private int tokensLeftO;

    /**
     * Creates a new player with the specified color.
     * Each player starts with 8 tokens of type X and 8 tokens of type O.
     *
     * @param color The color of the player.
     */
    public Player(Color color) {
        this.color = color;
        this.tokensLeftX = 8;
        this.tokensLeftO = 8;
    }

    /**
     * Decreases the number of tokens left for the specified symbol.
     *
     * @param symbol The symbol for which to decrease the token count (X or O).
     */
    public void decrease(Symbol symbol) {
        if (symbol == Symbol.O && tokensLeftO > 0) {
            tokensLeftO--;
        } else if (symbol == Symbol.X && tokensLeftX > 0) {
            tokensLeftX--;
        }
    }

    /**
     * Checks if the player has tokens left for the specified symbol.
     *
     * @param symbol The symbol to check (X or O).
     * @return true if tokens are available, false otherwise.
     */
    public boolean hasTokens(Symbol symbol) {
        if (symbol == Symbol.O) {
            return tokensLeftO > 0;
        } else if (symbol == Symbol.X) {
            return tokensLeftX > 0;
        }
        return false;
    }

    /**
     * Gets the color of the player.
     *
     * @return The player's color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the number of tokens left for symbol X.
     *
     * @return The number of tokens left for X.
     */
    public int getTokensLeftX() {
        return tokensLeftX;
    }

    /**
     * Gets the number of tokens left for symbol O.
     *
     * @return The number of tokens left for O.
     */
    public int getTokensLeftO() {
        return tokensLeftO;
    }
}
