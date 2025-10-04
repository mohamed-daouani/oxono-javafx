package g62275.dev3.oxono.model;

/**
 * Part of the **Strategy Design Pattern**.
 * Defines game strategies for inserting and moving symbols.
 */
public interface Strategy {

    /**
     * Inserts a symbol using a strategy.
     * @param game   The game instance.
     * @param symbol The symbol to insert.
     * @return true if successful, false otherwise.
     */
    Boolean insert(Game game, Symbol symbol);

    /**
     * Moves a symbol using a strategy.
     * @param game   The game instance.
     * @param symbol The symbol to move.
     * @return true if successful, false otherwise.
     */
    Boolean move(Game game, Symbol symbol);
}
