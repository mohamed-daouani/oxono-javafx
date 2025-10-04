package g62275.dev3.oxono.view;

import g62275.dev3.oxono.model.Game;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;

public class UndoButton extends Button {

    private final Game game;

    public UndoButton(Game game) {
        super();
        this.game = game;

        try (InputStream inputStream = getClass().getResourceAsStream("/undo.png")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Image resource not found: /undo.png");
            }
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(65);
            imageView.setFitHeight(65);
            this.setGraphic(imageView);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load image for UndoButton", e);
        }

        this.setBackground(null);
        this.setBorder(null);


    }

}