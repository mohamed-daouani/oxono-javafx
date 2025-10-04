package g62275.dev3.oxono.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board = new Board(6);
    private Token tokenX = new Token(Symbol.X, Color.BLACK);
    private Token tokenO = new Token(Symbol.O, Color.PINK);

    @Test
    void testInsertValidPosition() {
        Position position = new Position(2, 3);
        assertTrue(board.insert(tokenX, position), "Token should be inserted successfully at a valid position");
        assertFalse(board.isEmpty(position), "Position should no longer be empty after insertion");
    }

    @Test
    void testInsertTotemNotAllowed() {
        Totem totemX = board.getTotem(Symbol.X);
        Position position = new Position(2, 3);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            board.insert(totemX, position);
        });

        assertEquals("Only tokens can be inserted on the board.", exception.getMessage());
    }

    @Test
    void testInsertInvalidPosition() {
        Position invalidPosition = new Position(6, 6);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            board.insert(tokenX, invalidPosition);
        });
        assertEquals("Invalid position: " + invalidPosition, exception.getMessage());
    }

    @Test
    void testInsertNextToTotem() {
        Position farPosition = new Position(5, 5);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            board.insert(tokenX, farPosition);
        });
        assertEquals("Token must be placed next to the corresponding totem.", exception.getMessage());
    }

    @Test
    void testMoveTotemValid() {
        Totem totemX = board.getTotem(Symbol.X);
        Position newPosition = new Position(2, 4);
        assertTrue(board.move(totemX, newPosition), "Totem should be moved successfully");
        assertEquals(newPosition, totemX.getPosition(), "Totem's position should be updated correctly");
    }

    @Test
    void testMoveTotemInvalidPosition() {
        Totem totemX = board.getTotem(Symbol.X);
        Position invalidPosition = new Position(6, 6);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            board.move(totemX, invalidPosition);
        });
        assertEquals("Invalid position: " + invalidPosition, exception.getMessage());
    }

    @Test
    void testCheckWinHorizontal() {
        Totem totemX = board.getTotem(Symbol.X);

        for (int i = 0; i < 4; i++) {
            board.move(totemX, new Position(2, i));
            board.insert(new Token(Symbol.X, Color.PINK), new Position(1, i));
        }

        assertTrue(board.checkWin(), "Winning condition should be detected for a horizontal alignment");
    }

    @Test
    void testCheckWinVertical() {
        Totem totemX = board.getTotem(Symbol.X);

        for (int i = 0; i < 4; i++) {
            board.move(totemX, new Position(i, 2));
            board.insert(new Token(Symbol.X, Color.PINK), new Position(i, 1));
        }
        assertTrue(board.checkWin(), "Winning condition should be detected for a vertical alignment");
    }


    @Test
    void testInsertAtOccupiedPosition() {
        Position position = new Position(2, 3);
        board.insert(tokenX, position);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            board.insert(tokenO, position);
        });
        assertEquals("Position already occupied: " + position, exception.getMessage());
    }

}