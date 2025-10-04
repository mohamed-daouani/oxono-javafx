package g62275.dev3.oxono.model;

/**
 * Represents a pawn, which is the base class for both tokens and totems.
 * A pawn is associated with a specific symbol.
 */
public class Pawn {

    private Symbol symbol;

    /**
     * Constructs a pawn with the specified symbol.
     *
     * @param symbol the symbol of the pawn (e.g., X or O)
     */
    public Pawn(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets the symbol of this pawn.
     *
     * @return the symbol of the pawn
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Returns a string representation of this pawn.
     *
     * @return the string representation of the symbol
     */
    @Override
    public String toString() {
        return symbol.toString();
    }
}
