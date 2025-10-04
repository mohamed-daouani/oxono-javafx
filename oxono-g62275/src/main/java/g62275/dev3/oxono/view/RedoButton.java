package g62275.dev3.oxono.view;

import g62275.dev3.oxono.model.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class RedoButton extends Button {

    private final Game game;

    public RedoButton(Game game) {
        super();
        this.game = game;

        try (InputStream inputStream = getClass().getResourceAsStream("/redo.png")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Image resource not found: /redo.png");
            }
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(65);
            imageView.setFitHeight(65);
            this.setGraphic(imageView);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load image for RedoButton", e);
        }

        this.setBackground(null);
        this.setBorder(null);

    }


}