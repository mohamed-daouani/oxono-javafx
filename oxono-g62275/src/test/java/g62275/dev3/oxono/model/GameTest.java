package g62275.dev3.oxono.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {

    private Game game = new Game(6);

    @Test
    void testConstructor() {
        assertEquals(Color.PINK, game.getCurrentPlayer(), "The first player should be PINK");
        assertEquals(GameSate.MOVE, game.getGameSate(), "The initial game state should be MOVE");
        assertEquals(6, game.getBoardSize(), "The board size should be initialized to 6");
    }

    @Test
    void testMoveValid() {
        Totem totemX = game.getTotem(Symbol.X);
        Position newPosition = new Position(2, 1);

        assertTrue(game.move(totemX, newPosition), "The move should be valid");
        assertEquals(GameSate.INSERT, game.getGameSate(), "The game state should change to INSERT after a valid move");
    }

    @Test
    void testMoveInvalid() {
        Totem totemX = game.getTotem(Symbol.X);
        Position invalidPosition = new Position(6, 6);

        assertThrows(IllegalArgumentException.class, () -> {
            game.move(totemX, invalidPosition);
        }, "An invalid move should throw an IllegalArgumentException");
        assertEquals(GameSate.MOVE, game.getGameSate(), "The game state should remain MOVE after an invalid move");
    }

    @Test
    void testInsertValid() {
        Totem totemX = game.getTotem(Symbol.X);
        Position totemPosition = new Position(2, 1);
        Position insertPosition = new Position(2, 2);

        game.move(totemX, totemPosition);
        assertTrue(game.insert(insertPosition), "The token should be inserted successfully");
        assertEquals(GameSate.MOVE, game.getGameSate(), "The game state should return to MOVE after a valid insert");
        assertEquals(Color.BLACK, game.getCurrentPlayer(), "The current player should switch to BLACK");
    }

    @Test
    void testInsertInvalid() {
        Totem totemX = game.getTotem(Symbol.X);
        Position totemPosition = new Position(2, 1);
        Position invalidPosition = new Position(6, 6);

        assertDoesNotThrow(() -> {
            game.move(totemX, totemPosition);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            game.insert(invalidPosition);
        }, "An invalid insert should throw an IllegalArgumentException");
        assertEquals(GameSate.INSERT, game.getGameSate(), "The game state should remain INSERT after an invalid insert");
    }

    @Test
    void testUndoMove() {
        Position newPosition = new Position(2, 1);

        game.executeMove(Symbol.X, newPosition);
        assertTrue(game.canUndo(), "Undo should be available after a move");

        game.undo();
        assertEquals(GameSate.MOVE, game.getGameSate(), "The game state should return to MOVE after undo");
        assertTrue(game.isEmpty(newPosition), "The position should be empty after undoing the move");
    }

    @Test
    void testRedoMove() {
        Position newPosition = new Position(2, 1);

        game.executeMove(Symbol.X, newPosition);
        game.undo();
        game.redo();

        assertEquals(GameSate.INSERT, game.getGameSate(), "The game state should return to INSERT after redo");
        assertFalse(game.isEmpty(newPosition), "The position should be occupied after redoing the move");
    }

    @Test
    void testSurrender() {
        game.surrender();
        assertTrue(game.isGameOver(), "The game should be over after a surrender");
    }

    @Test
    void testGameOverOnWin() {
        game.setGameSate(GameSate.INSERT);
        for (int i = 0; i < 4; i++) {
            game.executeMove(Symbol.X, new Position(2,i));
            game.executeInsert(new Position(1, i));
        }

        assertTrue(game.isGameOver(), "The game should be over when a player wins");
    }

}