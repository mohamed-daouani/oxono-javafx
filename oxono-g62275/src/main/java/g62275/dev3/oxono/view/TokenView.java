package g62275.dev3.oxono.view;

import g62275.dev3.oxono.model.Symbol;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class TokenView extends StackPane {

    public TokenView(Symbol symbol, g62275.dev3.oxono.model.Color color) {
        if(color == g62275.dev3.oxono.model.Color.PINK){
            Circle circle = new Circle(50, Color.rgb(214,22,114));
            DropShadow dropShadow = new DropShadow();
            dropShadow.setOffsetX(0.0);
            dropShadow.setOffsetY(8.0);
            dropShadow.setColor(Color.rgb(106, 5, 54));
            dropShadow.setBlurType(BlurType.values()[0]);
            circle.setEffect(dropShadow);

            if (symbol == Symbol.X) {
                Line line1 = new Line(-20, -20, 20, 20);
                Line line2 = new Line(-20, 20, 20, -20);
                line1.setStroke(Color.rgb(177,166,142));
                line2.setStroke(Color.rgb(177,166,142));
                line1.setStrokeWidth(13);
                line2.setStrokeWidth(13);
                this.getChildren().addAll(circle, line1, line2);
            } else {
                Circle innerCircle = new Circle(20, Color.TRANSPARENT);
                innerCircle.setStroke(Color.rgb(177,166,142));
                innerCircle.setStrokeWidth(13);
                this.getChildren().addAll(circle, innerCircle);
            }
        } else if (color == g62275.dev3.oxono.model.Color.BLACK) {
            Circle circle = new Circle(50, Color.rgb(47,51,75));
            DropShadow dropShadow = new DropShadow();
            dropShadow.setOffsetX(0.0);
            dropShadow.setOffsetY(8.0);
            dropShadow.setColor(Color.rgb(1, 0, 8));
            dropShadow.setBlurType(BlurType.values()[0]);
            circle.setEffect(dropShadow);

            if (symbol == Symbol.X) {
                Line line1 = new Line(-20, -20, 20, 20);
                Line line2 = new Line(-20, 20, 20, -20);
                line1.setStroke(Color.rgb(177,166,142));
                line2.setStroke(Color.rgb(177,166,142));
                line1.setStrokeWidth(13);
                line2.setStrokeWidth(13);
                this.getChildren().addAll(circle, line1, line2);
            } else {
                Circle innerCircle = new Circle(20, Color.TRANSPARENT);
                innerCircle.setStroke(Color.rgb(177,166,142));
                innerCircle.setStrokeWidth(13);
                this.getChildren().addAll(circle, innerCircle);
            }
        }

    }
}
