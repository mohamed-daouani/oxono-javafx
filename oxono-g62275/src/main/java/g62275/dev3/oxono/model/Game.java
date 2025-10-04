/**
 * Represents the main Game class which handles the core logic of the Oxono game.
 * Implements the Observable interface for notifying observers about game state changes.
 */
package g62275.dev3.oxono.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Manages the game logic, player actions, and interactions with the board.
 */
public class Game implements Observable {

    /**
     * List of observers monitoring game state changes.
     */
    private List<Observer> observers;

    /**
     * The player using the black pieces.
     */
    private Player black;

    /**
     * The player using the pink pieces.
     */
    private Player pink;

    /**
     * The board on which the game is played.
     */
    private Board board;

    /**
     * Indicates whether the game is over.
     */
    private boolean isGameOver;

    /**
     * The current state of the game.
     */
    private GameSate gameSate;

    /**
     * The symbol to be inserted during an insertion phase.
     */
    private Symbol toInsert;

    /**
     * The color of the player whose turn it is to play.
     */
    private Color toPlay;

    /**
     * Manages the commands for undo/redo functionality.
     */
    private CommandManager commandManager;

    /**
     * AI for automated player actions (optional, not fully implemented).
     */
    private AI ai;
    /**
     * The last symbol removed
     */
    private Symbol lastRemovedSymbol;
    /**
     * The last symbol removed
     */
    private Color lastRemovedColor;


    /**
     * Initializes the game with the specified board size.
     * @param boardSize the size of the board.
     */
    public Game(int boardSize) {
        this.observers = new ArrayList<>();
        this.board = new Board(boardSize);
        this.isGameOver = false;
        this.gameSate = GameSate.MOVE;
        this.black = new Player(Color.BLACK);
        this.pink = new Player(Color.PINK);
        this.toPlay = Color.PINK;
        this.commandManager = new CommandManager();
    }

    /**
     * Moves a totem to a new position.
     * @param totem the totem to be moved.
     * @param position the position to move the totem to.
     * @return true if the move is valid.
     * @throws IllegalStateException if the game is not in the move state.
     * @throws IllegalArgumentException if the move is invalid.
     */
    public boolean move(Totem totem, Position position) {
        if (gameSate != GameSate.MOVE) {
            throw new IllegalStateException("Vous ne pouvez pas déplacer un totem maintenant !");
        }

        if (!board.move(totem, position)) {
            throw new IllegalArgumentException("Déplacement invalide.");
        }

        toInsert = totem.getSymbol();
        gameSate = GameSate.INSERT;
        return true;
    }

    /**
     * Inserts a token at a specified position.
     * @param position the position to insert the token.
     * @return true if the insertion is valid.
     * @throws IllegalStateException if the game is not in the insert state or the player has no tokens.
     * @throws IllegalArgumentException if the insertion is invalid.
     */
    public boolean insert(Position position) {
        if (gameSate != GameSate.INSERT) {
            throw new IllegalStateException("Vous ne pouvez pas insérer une pièce maintenant !");
        }

        Player currentPlayer = (toPlay == Color.PINK) ? pink : black;

        if (!currentPlayer.hasTokens(toInsert)) {
            throw new IllegalStateException("Le joueur " + currentPlayer.getColor() + " n'a plus de jetons pour " + toInsert + " !");
        }

        Token token = new Token(toInsert, toPlay);
        if (!board.insert(token, position)) {
            throw new IllegalArgumentException("Insertion invalide.");
        }

        currentPlayer.decrease(toInsert);
        lastRemovedSymbol = toInsert;
        lastRemovedColor = toPlay;

        toPlay = (toPlay == Color.PINK) ? Color.BLACK : Color.PINK;
        gameSate = GameSate.MOVE;
        return true;
    }

    /**
     * Executes a move command with undo/redo support.
     * @param symbol the symbol of the totem to move.
     * @param newPosition the new position of the totem.
     */
    public void executeMove(Symbol symbol, Position newPosition) {
        Totem totem = getTotem(symbol);
        Position oldPosition = new Position(totem.getRow(), totem.getCol());
        MoveCommand command = new MoveCommand(this, oldPosition, newPosition, symbol);
        commandManager.doCommand(command);
        notifyObservers();
        checkGameOver();
    }

    /**
     * Executes an insert command with undo/redo support.
     * @param position the position to insert a token.
     */
    public void executeInsert(Position position) {
        InsertionCommand command = new InsertionCommand(this, position);
        commandManager.doCommand(command);
        notifyObservers();
        checkGameOver();
    }

    /**
     * Undoes the last command.
     */
    public void undo() {
        if (gameSate == GameSate.INSERT) {
            gameSate = GameSate.MOVE;
        } else {
            gameSate = GameSate.INSERT;
        }
        commandManager.undo();
        notifyObservers();
        checkGameOver();
    }

    /**
     * Redoes the last undone command.
     */
    public void redo() {
        if (gameSate == GameSate.INSERT) {
            gameSate = GameSate.MOVE;
        } else {
            gameSate = GameSate.INSERT;
        }
        commandManager.redo();
        notifyObservers();
        checkGameOver();
    }

    /**
     * Checks if undo is possible.
     * @return true if undo is possible, false otherwise.
     */
    public boolean canUndo() {
        return commandManager.canUndo();
    }

    /**
     * Checks if redo is possible.
     * @return true if redo is possible, false otherwise.
     */
    public boolean canRedo() {
        return commandManager.canRedo();
    }

    /**
     * Checks if the current player has won.
     * @return true if the current player has won, false otherwise.
     */
    public boolean won() {
        return board.checkWin();
    }

    /**
     * Checks if the game is a draw.
     * @return true if the board is full and no player has won.
     */
    public boolean draw() {
        return board.isFull();
    }

    /**
     * Ends the game due to a player surrendering.
     */
    public void surrender() {
        isGameOver = true;
        notifyObservers();
    }

    /**
     * Gets the opposing player's color.
     * @return the color of the opposing player.
     */
    public Color getOpposingPlayer() {
        return (toPlay == Color.PINK) ? Color.BLACK : Color.PINK;
    }

    /**
     * Gets the current player's color.
     * @return the color of the current player.
     */
    public Color getCurrentPlayer() {
        return toPlay;
    }

    /**
     * Gets the current game state.
     * @return the current game state.
     */
    public GameSate getGameSate() {
        return gameSate;
    }

    /**
     * Retrieves a totem based on its symbol.
     * @param symbol the symbol of the totem.
     * @return the totem with the specified symbol.
     */
    public Totem getTotem(Symbol symbol) {
        return board.getTotem(symbol);
    }

    /**
     * Removes a token from the specified position on the board.
     * @param position the position to remove the token.
     */
    public void removeToken(Position position) {
        board.removeToken(position);
    }

    /**
     * Checks if the game is over.
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Checks if the game is over and updates the game state accordingly.
     */
    public void checkGameOver() {
        if (won() || draw()) {
            isGameOver = true;
            notifyObservers();
        }
    }

    /**
     * Checks if the specified position is valid on the board.
     * @param position the position to check.
     * @return true if the position is valid, false otherwise.
     */
    public boolean isValidPosition(Position position) {
        return board.isValidPosition(position);
    }

    /**
     * Checks if the specified position is empty on the board.
     * @param position the position to check.
     * @return true if the position is empty, false otherwise.
     */
    public boolean isEmpty(Position position) {
        return board.isEmpty(position);
    }

    /**
     * Checks if a totem can move horizontally or vertically to a position.
     * @param totem the totem to check.
     * @param position the position to move to.
     * @return true if the totem can move horizontally or vertically to the position.
     */
    public boolean isHorizontalOrVertical(Totem totem, Position position) {
        return board.isHorizontalOrVertical(totem, position);
    }

    /**
     * Checks if the path between two positions is clear on the board.
     * @param position1 the starting position.
     * @param position2 the ending position.
     * @return true if the path is clear, false otherwise.
     */
    public boolean isPathClear(Position position1, Position position2) {
        return board.isPathClear(position1, position2);
    }

    /**
     * Gets the list of possible moves for a specified totem.
     * @param totem the totem to check moves for.
     * @return the list of possible moves.
     */
    public List<Position> getPossibleMoves(Totem totem) {
        return board.getPossibleMoves(totem);
    }

    /**
     * Gets the positions adjacent to a specified totem.
     * @param totem the totem to check adjacency for.
     * @return the list of adjacent positions.
     */
    public List<Position> positionNextToTotem(Totem totem) {
        return board.positionNextToTotem(totem);
    }

    /**
     * Gets the pawn at a specified position on the board.
     * @param position the position to check.
     * @return the pawn at the position.
     */
    public Pawn getPawnAt(Position position) {
        return board.getPawnAt(position);
    }

    /**
     * Gets the size of the board.
     * @return the size of the board.
     */
    public int getBoardSize() {
        return board.getBoardSize();
    }

    /**
     * Sets the game state.
     * @param gameSate the new game state.
     */
    public void setGameSate(GameSate gameSate) {
        this.gameSate = gameSate;
    }

    /**
     * Registers an observer to be notified of game state changes.
     * @param observer the observer to register.
     */
    @Override
    public void registerObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes an observer from the notification list.
     * @param observer the observer to remove.
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers of a game state change.
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
    /**
     * Gets the symbol to insert
     * @return the symbol to play
     */
    public Symbol getToInsert() {
        return toInsert;
    }
    /**
     * Gets the last symbol removed
     * @return the last symbol removed
     */
    public Symbol getLastRemovedSymbol() {
        return lastRemovedSymbol;
    }
    /**
     * Gets the last color removed
     * @return the last color removed
     */
    public Color getLastRemovedColor() {
        return lastRemovedColor;
    }

}
