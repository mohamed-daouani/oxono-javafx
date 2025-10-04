package g62275.dev3.oxono.model;

/**
 * Part of the **Command Design Pattern**.
 * Encapsulates an action with undo capability.
 */
public interface Command {

    /**
     * Executes the command.
     */
    void execute();

    /**
     * Undoes the command.
     */
    void unexecute();
}
