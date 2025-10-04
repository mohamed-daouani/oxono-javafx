package g62275.dev3.oxono.model;

/**
 * Part of the **Observer Design Pattern**.
 * Receives updates from an observable object.
 */
public interface Observer {

    /**
     * Updates the observer with game changes.
     * @param game The updated game.
     */
    void update(Game game);
}
