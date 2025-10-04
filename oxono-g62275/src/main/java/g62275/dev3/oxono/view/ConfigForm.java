package g62275.dev3.oxono.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ConfigForm extends VBox {

    private ComboBox<Integer> boardSizeBox;
    private ComboBox<String> difficultyBox;
    private Button submitButton;

    public ConfigForm() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        Label boardSizeLabel = new Label("Choisissez la taille du tableau :");
        boardSizeBox = new ComboBox<>();
        boardSizeBox.getItems().addAll(4, 6, 8);
        boardSizeBox.setValue(4);
        boardSizeLabel.setTextFill(Color.WHITE);
        boardSizeLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 16));


        Label difficultyLabel = new Label("Choisissez le niveau de difficulté :");
        difficultyBox = new ComboBox<>();
        difficultyBox.getItems().addAll("Facile");
        difficultyBox.setValue("Facile");
        difficultyLabel.setTextFill(Color.WHITE);
        difficultyLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 16));

        submitButton = new Button("Start");

        this.getChildren().addAll(boardSizeLabel, boardSizeBox, difficultyLabel, difficultyBox, submitButton);
    }

    public int getBoardSize() {
        return boardSizeBox.getValue();
    }

    public String getDifficulty() {
        return difficultyBox.getValue();
    }

    public Button getSubmitButton() {
        return submitButton;
    }
}
