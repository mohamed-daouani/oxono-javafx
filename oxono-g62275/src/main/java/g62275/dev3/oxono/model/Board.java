/**
 * Represents the game board for the Oxono game.
 * Manages the board state, placement of pawns, and game rules.
 */
package g62275.dev3.oxono.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the Oxono game board operations, including pawn placement, movement, and win condition checks.
 */
public class Board {

    /**
     * The size of the board.
     */
    private int boardSize;

    /**
     * The grid representing the board state.
     */
    private Pawn[][] grid;

    /**
     * The totem for the X symbol.
     */
    private Totem totemX;

    /**
     * The totem for the O symbol.
     */
    private Totem totemO;

    /**
     * Constructs a new Board with the specified size.
     * @param boardSize the size of the board (4, 6, or 8).
     * @throws IllegalArgumentException if the size is not 4, 6, or 8.
     */
    public Board(int boardSize) {
        if (boardSize != 4 && boardSize != 6 && boardSize != 8) {
            throw new IllegalArgumentException("Board can only be 4x4, 6x6, or 8x8!");
        }
        this.boardSize = boardSize;
        this.grid = new Pawn[boardSize][boardSize];

        int center = boardSize / 2;
        this.totemX = new Totem(Symbol.X, new Position(center - 1, center - 1));
        this.totemO = new Totem(Symbol.O, new Position(center, center));

        grid[totemX.getRow()][totemX.getCol()] = this.totemX;
        grid[totemO.getRow()][totemO.getCol()] = this.totemO;
    }

    /**
     * Inserts a token at the specified position.
     * @param pawn the token to insert.
     * @param position the position to insert the token.
     * @return true if the insertion is successful.
     * @throws IllegalArgumentException if the pawn is not a token or the position is invalid.
     * @throws IllegalStateException if the position is occupied or the token is not next to its totem.
     */
    public boolean insert(Pawn pawn, Position position) {
        if (pawn instanceof Totem) {
            throw new IllegalArgumentException("Only tokens can be inserted on the board.");
        }
        if (!(pawn instanceof Token)) {
            throw new IllegalArgumentException("Invalid pawn type: Only tokens are allowed.");
        }
        Token token = (Token) pawn;
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }
        if (!isEmpty(position)) {
            throw new IllegalStateException("Position already occupied: " + position);
        }
        Totem correspondingTotem = getTotem(token.getSymbol());
        if (!isNextToTotem(correspondingTotem, position)) {
            throw new IllegalStateException("Token must be placed next to the corresponding totem.");
        }
        grid[position.getRow()][position.getCol()] = token;
        return true;
    }

    /**
     * Moves a totem to a new position.
     * @param totem the totem to move.
     * @param position the new position of the totem.
     * @return true if the move is successful.
     * @throws IllegalArgumentException if the position is invalid.
     * @throws IllegalStateException if the position is occupied or the path is blocked.
     */
    public boolean move(Totem totem, Position position) {
        if (totem.getPosition().equals(position)) {
            return true;
        }

        if (isEnclave(totem.getPosition())) {
            handleEnclaveByJump(totem);
            return true;
        }

        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }
        if (!isEmpty(position)) {
            throw new IllegalStateException("Position already occupied: " + position);
        }
        if (!isHorizontalOrVertical(totem, position)) {
            throw new IllegalStateException("Totem can only move horizontally or vertically.");
        }
        if (!isPathClear(totem.getPosition(), position)) {
            throw new IllegalStateException("Totem cannot jump over a pawn.");
        }

        grid[position.getRow()][position.getCol()] = grid[totem.getRow()][totem.getCol()];
        grid[totem.getRow()][totem.getCol()] = null;
        totem.setPosition(position);
        return true;
    }

    /**
     * Checks if the specified position is empty.
     * @param position the position to check.
     * @return true if the position is empty, false otherwise.
     */
    public boolean isEmpty(Position position) {
        return isValidPosition(position) && grid[position.getRow()][position.getCol()] == null;
    }

    /**
     * Checks if the specified position is valid on the board.
     * @param position the position to check.
     * @return true if the position is valid, false otherwise.
     */
    public boolean isValidPosition(Position position) {
        return position.getRow() >= 0 && position.getRow() < boardSize &&
                position.getCol() >= 0 && position.getCol() < boardSize;
    }

    /**
     * Checks if a totem can move horizontally or vertically to a position.
     * @param totem the totem to check.
     * @param position the position to move to.
     * @return true if the totem can move horizontally or vertically, false otherwise.
     */
    public boolean isHorizontalOrVertical(Totem totem, Position position) {
        return totem.getRow() == position.getRow() || totem.getCol() == position.getCol();
    }

    /**
     * Checks if a position is adjacent to a totem.
     * @param totem the totem to check.
     * @param position the position to check.
     * @return true if the position is adjacent to the totem, false otherwise.
     */
    public boolean isNextToTotem(Totem totem, Position position) {
        int rowDiff = Math.abs(totem.getRow() - position.getRow());
        int colDiff = Math.abs(totem.getCol() - position.getCol());
        return (rowDiff + colDiff) == 1;
    }

    /**
     * Checks if the path between two positions is clear.
     * @param begin the starting position.
     * @param end the ending position.
     * @return true if the path is clear, false otherwise.
     */
    public boolean isPathClear(Position begin, Position end) {
        int deltaX = Integer.signum(end.getRow() - begin.getRow());
        int deltaY = Integer.signum(end.getCol() - begin.getCol());
        int x = begin.getRow() + deltaX;
        int y = begin.getCol() + deltaY;
        while (x != end.getRow() || y != end.getCol()) {
            if (!isValidPosition(new Position(x, y)) || grid[x][y] != null) {
                return false;
            }
            x += deltaX;
            y += deltaY;
        }
        return true;
    }

    /**
     * Checks if a position is completely enclosed.
     * @param position the position to check.
     * @return true if the position is enclosed, false otherwise.
     */
    public boolean isEnclave(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        boolean upBlocked = row == 0 || !isEmpty(new Position(row - 1, col));
        boolean downBlocked = row == boardSize - 1 || !isEmpty(new Position(row + 1, col));
        boolean leftBlocked = col == 0 || !isEmpty(new Position(row, col - 1));
        boolean rightBlocked = col == boardSize - 1 || !isEmpty(new Position(row, col + 1));
        return upBlocked && downBlocked && leftBlocked && rightBlocked;
    }

    /**
     * Handles a totem jump for an enclosed position.
     * @param totem the totem to move.
     */
    public void handleEnclaveByJump(Totem totem) {
        Position start = totem.getPosition();

        List<Position> possibleJumpPositions = getPossibleMoves(totem);

        for (Position target : possibleJumpPositions) {
            if (isEmpty(target) && isPathClear(start, target)) {
                grid[target.getRow()][target.getCol()] = totem;
                grid[start.getRow()][start.getCol()] = null;
                totem.setPosition(target);
                System.out.println("Totem jumped from " + start + " to " + target);
                return;
            }
        }
        throw new IllegalStateException("No valid position available for the totem to jump.");
    }

    /**
     * Gets all positions adjacent to a totem.
     * @param totem the totem to check.
     * @return a list of positions adjacent to the totem.
     */
    public List<Position> positionNextToTotem(Totem totem) {
        List<Position> possiblePosition = new ArrayList<>();

        int[][] directions = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };

        for (int[] direction : directions) {
            int newRow = totem.getRow() + direction[0];
            int newCol = totem.getCol() + direction[1];
            Position position = new Position(newRow, newCol);

            if (isValidPosition(position)) {
                possiblePosition.add(position);
            }
        }

        return possiblePosition;
    }

    /**
     * Gets all possible moves for a totem.
     * @param totem the totem to check.
     * @return a list of valid positions the totem can move to.
     */
    public List<Position> getPossibleMoves(Totem totem) {
        List<Position> possibleMoves = new ArrayList<>();

        int currentRow = totem.getPosition().getRow();
        int currentCol = totem.getPosition().getCol();

        for (int row = 0; row < boardSize; row++) {
            Position newPosition = new Position(row, currentCol);
            if (isValidPosition(newPosition) && isEmpty(newPosition) && isPathClear(totem.getPosition(), newPosition)) {
                possibleMoves.add(newPosition);
            }
        }

        for (int col = 0; col < boardSize; col++) {
            Position newPosition = new Position(currentRow, col);
            if (isValidPosition(newPosition) && isEmpty(newPosition) && isPathClear(totem.getPosition(), newPosition)) {
                possibleMoves.add(newPosition);
            }
        }

        return possibleMoves;
    }

    /**
     * Checks if the board is full.
     * @return true if the board is full, false otherwise.
     */
    public boolean isFull() {
        for (Pawn[] row : grid) {
            for (Pawn cell : row) {
                if (cell == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if there is a winning alignment on the board.
     * @return true if a winning alignment exists, false otherwise.
     */
    public boolean checkWin() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (checkAlignmentBySymbol(row, col, 0, 1) || checkAlignmentBySymbol(row, col, 1, 0)) {
                    return true;
                }
                if (checkAlignmentByColor(row, col, 0, 1) || checkAlignmentByColor(row, col, 1, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks for symbol alignment in a specific direction.
     * @param row the starting row.
     * @param col the starting column.
     * @param rowDir the row direction.
     * @param colDir the column direction.
     * @return true if the alignment is found, false otherwise.
     */
    private boolean checkAlignmentBySymbol(int row, int col, int rowDir, int colDir) {
        if (!isValidPosition(new Position(row, col)) || grid[row][col] == null || grid[row][col] instanceof Totem) {
            return false;
        }
        Symbol symbol = grid[row][col].getSymbol();
        for (int i = 1; i < 4; i++) {
            int newRow = row + i * rowDir;
            int newCol = col + i * colDir;
            if (!isValidPosition(new Position(newRow, newCol)) || grid[newRow][newCol] == null ||
                    grid[newRow][newCol] instanceof Totem || grid[newRow][newCol].getSymbol() != symbol) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for color alignment in a specific direction.
     * @param row the starting row.
     * @param col the starting column.
     * @param rowDir the row direction.
     * @param colDir the column direction.
     * @return true if the alignment is found, false otherwise.
     */
    private boolean checkAlignmentByColor(int row, int col, int rowDir, int colDir) {
        if (!isValidPosition(new Position(row, col)) || grid[row][col] == null ||
                !(grid[row][col] instanceof Token)) {
            return false;
        }
        Color color = ((Token) grid[row][col]).getColor();
        for (int i = 1; i < 4; i++) {
            int newRow = row + i * rowDir;
            int newCol = col + i * colDir;
            if (!isValidPosition(new Position(newRow, newCol)) || !(grid[newRow][newCol] instanceof Token) ||
                    ((Token) grid[newRow][newCol]).getColor() != color) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves the totem corresponding to a symbol.
     * @param symbol the symbol of the totem.
     * @return the totem with the specified symbol.
     */
    public Totem getTotem(Symbol symbol) {
        return (symbol == Symbol.O) ? totemO : totemX;
    }

    /**
     * Gets the size of the board.
     * @return the board size.
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Removes a token from the specified position.
     * @param position the position to remove the token.
     * @throws IllegalArgumentException if the position is invalid.
     */
    public void removeToken(Position position) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }
        grid[position.getRow()][position.getCol()] = null;
    }

    /**
     * Gets the pawn at a specified position.
     * @param position the position to check.
     * @return the pawn at the specified position.
     */
    public Pawn getPawnAt(Position position) {
        return grid[position.getRow()][position.getCol()];
    }
}
