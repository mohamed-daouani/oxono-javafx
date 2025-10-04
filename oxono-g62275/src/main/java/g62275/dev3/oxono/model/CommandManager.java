package g62275.dev3.oxono.model;

import java.util.Stack;

/**
 * Manages commands for undo and redo operations.
 *
 * Part of the **Command Design Pattern**.
 * Maintains two stacks: one for undo operations and one for redo operations.
 */
public class CommandManager {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    /**
     * Creates a new CommandManager with empty undo and redo stacks.
     */
    public CommandManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    /**
     * Executes a command and pushes it onto the undo stack.
     * Clears the redo stack after a new command is executed.
     *
     * @param command The command to execute.
     */
    public void doCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    /**
     * Undoes the last executed command by moving it to the redo stack.
     *
     * @throws IllegalStateException If there are no commands to undo.
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.unexecute();
            redoStack.push(command);
        } else {
            throw new IllegalStateException("No commands to undo.");
        }
    }

    /**
     * Redoes the last undone command by moving it back to the undo stack.
     *
     * @throws IllegalStateException If there are no commands to redo.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        } else {
            throw new IllegalStateException("No commands to redo.");
        }
    }

    /**
     * Checks if there are commands available to undo.
     *
     * @return true if there are commands to undo, false otherwise.
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * Checks if there are commands available to redo.
     *
     * @return true if there are commands to redo, false otherwise.
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}
