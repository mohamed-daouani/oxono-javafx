package g62275.dev3.oxono.model;

/**
 * Part of the **Observer Design Pattern**.
 * Allows objects to register, remove, and notify observers.
 */
public interface Observable {

    /**
     * Registers an observer.
     * @param o The observer to add.
     */
    void registerObserver(Observer o);

    /**
     * Removes an observer.
     * @param o The observer to remove.
     */
    void removeObserver(Observer o);

    /**
     * Notifies all observers.
     */
    void notifyObservers();
}
