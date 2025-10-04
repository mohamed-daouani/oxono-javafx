package g62275.dev3.oxono.view;

import g62275.dev3.oxono.model.Game;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SurrenderButton extends Button {

    private final Game game;

    public SurrenderButton(Game game) {
        super("Surrender");
        this.game = game;


        this.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        this.setTextFill(Color.rgb(113, 61, 127));
        this.setBackground(new Background(new BackgroundFill(
                Color.WHITE, null, null
        )));

        this.setBorder(new Border(new BorderStroke(
                Color.rgb(113, 61, 127),
                BorderStrokeStyle.SOLID,
                new CornerRadii(10),
                new BorderWidths(2)
        )));

    }
}