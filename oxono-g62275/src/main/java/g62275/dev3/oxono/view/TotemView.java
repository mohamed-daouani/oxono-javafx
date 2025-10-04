package g62275.dev3.oxono.view;

import g62275.dev3.oxono.model.Symbol;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class TotemView extends StackPane {

    public TotemView(Symbol symbol) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(8.0);
        dropShadow.setColor(Color.rgb(6, 90, 104));
        dropShadow.setBlurType(BlurType.values()[0]);

        Rectangle rectangle = new Rectangle(100,100, Color.rgb(14,175,201));

        rectangle.setEffect(dropShadow);
        rectangle.setArcWidth(30.0);
        rectangle.setArcHeight(20.0);

        if (symbol == Symbol.X) {
            Line line1 = new Line(-20, -20, 20, 20);
            Line line2 = new Line(-20, 20, 20, -20);
            Line horizontalLine = new Line(-20, 0, 20, 0);
            Line verticalLine = new Line(0, -20, 0, 20);

            line1.setStroke(Color.rgb(177,166,142));
            line2.setStroke(Color.rgb(177,166,142));
            horizontalLine.setStroke(Color.rgb(14,175,201));
            verticalLine.setStroke(Color.rgb(14,175,201));

            line1.setStrokeWidth(13);
            line2.setStrokeWidth(13);
            horizontalLine.setStrokeWidth(5);
            verticalLine.setStrokeWidth(5);

            this.getChildren().addAll(rectangle, line1, line2, horizontalLine, verticalLine);
        } else{

            Line horizontalLine = new Line(-40, 0, 40, 0);
            Line verticalLine = new Line(0, -40, 0, 40);

            horizontalLine.setStroke(Color.rgb(14,175,201));
            verticalLine.setStroke(Color.rgb(14,175,201));

            horizontalLine.setStrokeWidth(5);
            verticalLine.setStrokeWidth(5);

            Circle innerCircle = new Circle(20, Color.TRANSPARENT);
            innerCircle.setStroke(Color.rgb(177,166,142));
            innerCircle.setStrokeWidth(13);


            this.getChildren().addAll(rectangle, innerCircle, horizontalLine, verticalLine);
        }
    }
}
