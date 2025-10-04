package g62275.dev3.oxono.model;

/**
 * Represents a token, which is a piece placed on the board during the game.
 * A token is associated with a specific color and symbol.
 */
public class Token extends Pawn {

    private Color color;

    /**
     * Constructs a token with a given symbol and color.
     *
     * @param symbol the symbol of the token (e.g., X or O)
     * @param color the color of the token (e.g., PINK or BLACK)
     */
    public Token(Symbol symbol, Color color) {
        super(symbol);
        this.color = color;
    }

    /**
     * Gets the color of the token.
     *
     * @return the color of the token
     */
    public Color getColor() {
        return color;
    }
}
