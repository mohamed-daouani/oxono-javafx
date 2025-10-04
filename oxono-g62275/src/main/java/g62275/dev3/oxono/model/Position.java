package g62275.dev3.oxono.model;

/**
 * Represents a position on the board with row and column coordinates.
 */
public class Position {

    private int row;
    private int col;

    /**
     * Constructs a position with the specified row and column.
     *
     * @param row the row index (0-based)
     * @param col the column index (0-based)
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the row index of this position.
     *
     * @return the row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column index of this position.
     *
     * @return the column index
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns a string representation of this position.
     * The row and column indices are displayed as 1-based.
     *
     * @return the string representation of the position
     */
    @Override
    public String toString() {
        return "(" + (row + 1) + ", " + (col + 1) + ")";
    }
}
