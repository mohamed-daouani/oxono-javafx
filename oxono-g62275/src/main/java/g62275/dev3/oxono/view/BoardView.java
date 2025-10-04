package g62275.dev3.oxono.view;

import g62275.dev3.oxono.model.*;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class BoardView extends StackPane {

    private Game game;
    private static final int BUTTON_SIZE = 100;
    private Totem selectedTotem;
    private List<Node> highlights;
    private BoardDesign boardDesign;
    private AI ai;

    public BoardView(Game game, AI ai) {
        this.game = game;
        this.ai = ai;
        this.highlights = new ArrayList<>();

        this.boardDesign = new BoardDesign(game);
        this.boardDesign.setScaleX(1.1);
        this.boardDesign.setScaleY(1.1);

        this.getChildren().add(boardDesign);

        GridPane boardOverlay = new GridPane();
        boardOverlay.setAlignment(Pos.CENTER);
        this.getChildren().add(boardOverlay);

        createBoard(boardOverlay);
    }

    private void createBoard(GridPane boardOverlay) {
        boardOverlay.setHgap(7);
        boardOverlay.setVgap(7);
        for (int row = 0; row < game.getBoardSize(); row++) {
            for (int col = 0; col < game.getBoardSize(); col++) {
                Position position = new Position(row, col);

                if (game.getPawnAt(position) instanceof Totem) {
                    Totem totem = (Totem) game.getPawnAt(position);
                    TotemView totemView = new TotemView(totem.getSymbol());

                    totemView.setScaleX(0.9);
                    totemView.setScaleY(0.9);
                    totemView.setTranslateY(-8);
                    totemView.setTranslateX(-3);

                    totemView.setOnMouseClicked(event -> {
                        clearHighlights(boardOverlay);
                        this.selectedTotem = totem;
                        if (game.getGameSate() == GameSate.MOVE) {
                            highlightPossibleMoves(totem, boardOverlay);
                        } else if (game.getGameSate() == GameSate.INSERT) {
                            highlightPossibleInsertions(totem, boardOverlay);
                        }
                    });

                    boardOverlay.add(totemView, col, row);

                } else if (game.getPawnAt(position) instanceof Token) {
                    Token token = (Token) game.getPawnAt(position);
                    TokenView tokenView = new TokenView(token.getSymbol(), token.getColor());

                    tokenView.setScaleX(0.9);
                    tokenView.setScaleY(0.9);
                    tokenView.setTranslateY(-8);
                    tokenView.setTranslateX(-3);

                    boardOverlay.add(tokenView, col, row);
                } else {
                    Button button = new Button();
                    button.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
                    button.setVisible(false);
                    button.setScaleX(0.9);
                    button.setScaleY(0.9);
                    button.setTranslateY(20);
                    button.setTranslateX(6);

                    boardOverlay.add(button, col, row);

                    int finalRow = row;
                    int finalCol = col;

                    button.setOnAction(event -> {
                        playTurn(new Position(finalRow, finalCol), button, boardOverlay);
                    });
                }
            }
        }
    }
    private void playTurn(Position position, Button button, GridPane boardOverlay) {
        if (game.getCurrentPlayer() == Color.PINK) {
            if (this.selectedTotem != null && game.getGameSate() == GameSate.MOVE && isHighlightedForMove(position.getRow(), position.getCol())) {
                game.executeMove(this.selectedTotem.getSymbol(), position);
                this.selectedTotem = null;
                clearHighlights(boardOverlay);
                update(game);
                playAI();
            } else if (game.getGameSate() == GameSate.INSERT && game.isEmpty(position)) {
                Symbol symbol = game.getCurrentPlayer() == g62275.dev3.oxono.model.Color.PINK ? Symbol.O : Symbol.X;
                game.executeInsert(position);

                TokenView tokenView = new TokenView(symbol, Color.PINK);
                boardOverlay.getChildren().remove(button);
                boardOverlay.add(tokenView, position.getCol(), position.getRow());

                clearHighlights(boardOverlay);
                update(game);
                playAI();
            }
        }
    }


    private void highlightPossibleMoves(Totem totem, GridPane boardOverlay) {
        if (game.isGameOver()) {
            return;
        }

        clearHighlights(boardOverlay);
        List<Position> possibleMoves = game.getPossibleMoves(totem);


        for (Position position : possibleMoves) {
            Rectangle highlight = createHighlight(javafx.scene.paint.Color.TRANSPARENT);

            highlight.setTranslateX(5);

            highlight.setOnMouseClicked(event -> {
                if (this.selectedTotem != null && game.getGameSate() == GameSate.MOVE) {
                    game.executeMove(this.selectedTotem.getSymbol(), position);
                    this.selectedTotem = null;
                    clearHighlights(boardOverlay);
                    update(game);

                    playAI();
                }
            });

            boardOverlay.add(highlight, position.getCol(), position.getRow());
        }
    }


    private void highlightPossibleInsertions(Totem totem, GridPane boardOverlay) {
        if (game.isGameOver()) {
            return;
        }

        clearHighlights(boardOverlay);
        List<Position> possibleInsertions = game.positionNextToTotem(totem);
        for (Position position : possibleInsertions) {
            if (game.isEmpty(position)) {
                Rectangle highlight = createHighlight(javafx.scene.paint.Color.rgb(147,144,155));

                highlight.setTranslateX(3);


                highlight.setOnMouseClicked(event -> {
                    if (game.getGameSate() == GameSate.INSERT) {
                        Symbol symbol = game.getCurrentPlayer() == Color.PINK ? Symbol.O : Symbol.X;
                        game.executeInsert(position);

                        TokenView tokenView = new TokenView(symbol, game.getCurrentPlayer());
                        boardOverlay.getChildren().remove(highlight);
                        boardOverlay.add(tokenView, position.getCol(), position.getRow());

                        clearHighlights(boardOverlay);
                        update(game);

                        playAI();
                    }
                });

                boardOverlay.add(highlight, position.getCol(), position.getRow());
            }
        }
    }

    private void playAI() {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> {
            if (!game.isGameOver() && game.getCurrentPlayer() == Color.BLACK) {
                if (game.getGameSate() == GameSate.MOVE) {
                    ai.playMove();
                    update(game);
                    if (!game.isGameOver() && game.getGameSate() == GameSate.INSERT) {
                        PauseTransition insertDelay = new PauseTransition(Duration.seconds(1));
                        insertDelay.setOnFinished(insertEvent -> {
                            ai.playInsert();
                            update(game);
                        });
                        insertDelay.play();
                    }
                }
            }
        });
        delay.play();
    }

    private boolean isHighlightedForMove(int row, int col) {
        if (this.selectedTotem == null) {
            return false;
        }
        List<Position> possibleMoves = game.getPossibleMoves(this.selectedTotem);
        possibleMoves.add(this.selectedTotem.getPosition());
        Position target = new Position(row, col);
        return possibleMoves.contains(target);
    }

    private Rectangle createHighlight(javafx.scene.paint.Color color) {
        Rectangle highlight = new Rectangle(BUTTON_SIZE - 10, BUTTON_SIZE - 10);
        highlight.setFill(javafx.scene.paint.Color.TRANSPARENT);
        highlight.setStroke(color);
        highlight.setStrokeWidth(2);
        highlights.add(highlight);
        return highlight;
    }

    private void clearHighlights(GridPane boardOverlay) {
        for (Node highlight : highlights) {
            boardOverlay.getChildren().remove(highlight);
        }
        highlights.clear();
    }

    public void update(Game game) {
        this.game = game;
        GridPane boardOverlay = (GridPane) this.getChildren().get(1);
        boardOverlay.getChildren().clear();
        createBoard(boardOverlay);

        if (game.isGameOver()) {
            disableInteractions();
        }
    }


    public void disableInteractions() {
        GridPane boardOverlay = (GridPane) this.getChildren().get(1);
        for (Node node : boardOverlay.getChildren()) {
            if (node instanceof Button || node instanceof Rectangle) {
                node.setDisable(true);
            }
        }
    }

}
