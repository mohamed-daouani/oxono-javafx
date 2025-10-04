package g62275.dev3.oxono.model;

import java.util.List;
import java.util.Random;

/**
 * A simple AI strategy for the game.
 *
 * - In the **insert** phase, it places a token next to the corresponding totem.
 * - In the **move** phase, it moves the totem randomly along valid paths.
 */
public class EasyStrategy implements Strategy {
    private final Random random = new Random();

    /**
     * Inserts a token next to the corresponding totem.
     *
     * @param game   The current game instance.
     * @param symbol The symbol to insert (X or O).
     * @return true if the insertion was successful.
     * @throws IllegalStateException If no valid positions are available.
     */
    @Override
    public Boolean insert(Game game, Symbol symbol) {
        Totem totem = game.getTotem(symbol);
        List<Position> possiblePositions = game.positionNextToTotem(totem);

        for (Position position : possiblePositions) {
            if (game.isValidPosition(position) && game.isEmpty(position)) {
                if (game.insert(position)) {
                    return true;
                }
            }
        }

        throw new IllegalStateException("No valid position found for insertion.");
    }

    /**
     * Moves a totem to a random valid position.
     *
     * @param game   The current game instance.
     * @param symbol The symbol of the totem to move.
     * @return true if the move was successful.
     */
    @Override
    public Boolean move(Game game, Symbol symbol) {
        Totem totem = game.getTotem(symbol);

        while (true) {
            int randomRow = random.nextInt(game.getBoardSize());
            int randomCol = random.nextInt(game.getBoardSize());

            Position position = new Position(randomRow, randomCol);

            if (game.isValidPosition(position) && game.isEmpty(position)
                    && game.isHorizontalOrVertical(totem, position)
                    && game.isPathClear(totem.getPosition(), position)) {
                if (game.move(totem, position)) {
                    return true;
                }
            }
        }
    }
}
