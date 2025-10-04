package g62275.dev3.oxono.view;

import g62275.dev3.oxono.model.Game;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public class BoardDesign extends StackPane {

    private static final int CELL_SIZE = 125;

    public BoardDesign(Game game) {
        int boardWidth = game.getBoardSize() * CELL_SIZE;
        int boardHeight = game.getBoardSize() * CELL_SIZE;

        Rectangle background = new Rectangle(boardWidth, boardHeight);
        background.setFill(Color.rgb(113, 61, 127));
        background.setStroke(Color.rgb(51, 48, 106));
        background.setStrokeWidth(17);

        Rectangle innerBorder = new Rectangle(boardWidth - 60, boardHeight - 60);
        innerBorder.setFill(Color.TRANSPARENT);
        innerBorder.setStroke(Color.rgb(101, 182, 163));
        innerBorder.setStrokeWidth(3);

        if (game.getBoardSize() == 4) {
            Rectangle topLeftCorner = new Rectangle(boardWidth / 12, boardHeight / 12);
            topLeftCorner.setTranslateX(-boardWidth / 2 + 30);
            topLeftCorner.setTranslateY(-boardHeight / 2 + 30);
            topLeftCorner.setFill(Color.rgb(113, 61, 127));

            Rectangle bottomRightCorner = new Rectangle(boardWidth / 12, boardHeight / 12);
            bottomRightCorner.setTranslateX(boardWidth / 2 - 30);
            bottomRightCorner.setTranslateY(boardHeight / 2 - 30);
            bottomRightCorner.setFill(Color.rgb(113, 61, 127));

            StackPane xStackPane = createXShape(5, 3);
            xStackPane.setTranslateX(-boardWidth / 2 + 27);
            xStackPane.setTranslateY(-boardHeight / 2 + 27);

            StackPane oStackPane = createOShape(6);
            oStackPane.setTranslateX(boardWidth / 2 - 27);
            oStackPane.setTranslateY(boardHeight / 2 - 30);

            boardWidth += 40;
            boardHeight += 40;

            this.getChildren().addAll(background, innerBorder, topLeftCorner, bottomRightCorner, xStackPane, oStackPane);

        } else if (game.getBoardSize() == 6) {
            Rectangle topLeftCorner = new Rectangle(boardWidth / 14, boardHeight / 14);
            topLeftCorner.setTranslateX(-boardWidth / 2 + 40);
            topLeftCorner.setTranslateY(-boardHeight / 2 + 40);
            topLeftCorner.setFill(Color.rgb(113, 61, 127));

            Rectangle bottomRightCorner = new Rectangle(boardWidth / 14, boardHeight / 14);
            bottomRightCorner.setTranslateX(boardWidth / 2 - 40);
            bottomRightCorner.setTranslateY(boardHeight / 2 - 40);
            bottomRightCorner.setFill(Color.rgb(113, 61, 127));

            StackPane xStackPane = createXShape(10, 6);
            xStackPane.setTranslateX(-boardWidth / 2 + 35);
            xStackPane.setTranslateY(-boardHeight / 2 + 35);

            StackPane oStackPane = createOShape(10);
            oStackPane.setTranslateX(boardWidth / 2 - 30);
            oStackPane.setTranslateY(boardHeight / 2 - 30);

            boardWidth -= 20;
            boardHeight -= 20;

            this.getChildren().addAll(background, innerBorder, topLeftCorner, bottomRightCorner, xStackPane, oStackPane);

        } else if (game.getBoardSize() == 8) {
            Rectangle topLeftCorner = new Rectangle(boardWidth / 12, boardHeight / 12);
            topLeftCorner.setTranslateX(-boardWidth / 2 + 50);
            topLeftCorner.setTranslateY(-boardHeight / 2 + 50);
            topLeftCorner.setFill(Color.rgb(113, 61, 127));

            Rectangle bottomRightCorner = new Rectangle(boardWidth / 12, boardHeight / 12);
            bottomRightCorner.setTranslateX(boardWidth / 2 - 50);
            bottomRightCorner.setTranslateY(boardHeight / 2 - 50);
            bottomRightCorner.setFill(Color.rgb(113, 61, 127));

            StackPane xStackPane = createXShape(15, 8);
            xStackPane.setTranslateX(-boardWidth / 2 + 45);
            xStackPane.setTranslateY(-boardHeight / 2 + 45);

            StackPane oStackPane = createOShape(15);
            oStackPane.setTranslateX(boardWidth / 2 - 40);
            oStackPane.setTranslateY(boardHeight / 2 - 40);

            boardWidth -= 75;
            boardHeight -= 75;

            this.getChildren().addAll(background, innerBorder, topLeftCorner, bottomRightCorner, xStackPane, oStackPane);
        }




        GridPane grid = createGrid(game.getBoardSize());
        grid.setAlignment(Pos.CENTER);
        Circle bluePoint1 = new Circle(2, Color.rgb(14,175,201));
        Circle bluePoint2 = new Circle(2, Color.rgb(14,175,201));
        bluePoint1.setTranslateX(45);
        bluePoint2.setTranslateX(45);


        int middleCell1 = game.getBoardSize() / 2;
        int middleCell2 = (game.getBoardSize() / 2) - 1;

        grid.add(bluePoint1, middleCell1, middleCell1);
        grid.add(bluePoint2, middleCell2, middleCell2);
        this.getChildren().add(grid);


        Rectangle gridBorder = createGridBorder(boardWidth, boardHeight);
        this.getChildren().add(gridBorder);
    }

    private StackPane createXShape(int size, int thickness) {
        Line line1 = createLine(-size, -size, size, size, Color.rgb(101, 182, 163), thickness);
        Line line2 = createLine(-size, size, size, -size, Color.rgb(101, 182, 163), thickness);
        Line horizontalLine = createLine(-size, 0, size, 0, Color.rgb(113, 61, 127), 3);
        Line verticalLine = createLine(0, -size, 0, size, Color.rgb(113, 61, 127), 3);

        StackPane xStackPane = new StackPane(line1, line2, horizontalLine, verticalLine);
        return xStackPane;
    }

    private StackPane createOShape(int size){
        Line horizontalLine = new Line(-size*2, 0, size*2, 0);
        Line verticalLine = new Line(0, -size*2, 0, size*2);

        horizontalLine.setStroke(Color.rgb(113, 61, 127));
        verticalLine.setStroke(Color.rgb(113, 61, 127));

        horizontalLine.setStrokeWidth(3);
        verticalLine.setStrokeWidth(3);

        Circle innerCircle = new Circle(size, Color.TRANSPARENT);
        innerCircle.setStroke(Color.rgb(101, 182, 163));
        innerCircle.setStrokeWidth(size);

        StackPane oStackPane = new StackPane(innerCircle, horizontalLine, verticalLine);
        return oStackPane;
    }

    private GridPane createGrid(int gridSize) {
        GridPane grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(0);

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE - 30, CELL_SIZE - 30);
                cell.setFill(Color.TRANSPARENT);
                cell.setStroke(Color.rgb(209, 49, 128));
                cell.setStrokeWidth(1);

                grid.add(cell, col, row);
            }
        }

        return grid;
    }

    private Line createLine(double startX, double startY, double endX, double endY, Color color, int thickness) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(color);
        line.setStrokeWidth(thickness);
        return line;
    }

    private Rectangle createGridBorder(int width, int height) {
        Rectangle rectangle = new Rectangle(width - 150, height - 150, Color.TRANSPARENT);
        rectangle.setStroke(Color.rgb(113, 61, 127));

        rectangle.setStrokeWidth(15);
        return rectangle;
    }
}
