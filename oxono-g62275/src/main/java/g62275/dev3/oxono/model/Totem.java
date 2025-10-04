package g62275.dev3.oxono.model;

/**
 * Represents a totem, which is a movable piece in the game.
 * A totem is associated with a specific position on the board.
 */
public class Totem extends Pawn {

    private Position position;

    /**
     * Constructs a totem with a given symbol and position.
     *
     * @param symbol the symbol of the totem (e.g., X or O)
     * @param position the initial position of the totem
     */
    public Totem(Symbol symbol, Position position) {
        super(symbol);
        this.position = position;
    }

    /**
     * Gets the current position of the totem.
     *
     * @return the position of the totem
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets a new position for the totem.
     *
     * @param position the new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets the row index of the totem's current position.
     *
     * @return the row index
     */
    public int getRow() {
        return position.getRow();
    }

    /**
     * Gets the column index of the totem's current position.
     *
     * @return the column index
     */
    public int getCol() {
        return position.getCol();
    }
}
