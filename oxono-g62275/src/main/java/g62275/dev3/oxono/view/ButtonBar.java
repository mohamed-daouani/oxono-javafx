package g62275.dev3.oxono.view;

import g62275.dev3.oxono.model.Game;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

public class ButtonBar extends HBox {
    private UndoButton undoButton;
    private RedoButton redoButton;
    private SurrenderButton surrender;


    public ButtonBar(Game game) {
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);

        surrender = new SurrenderButton(game);

        undoButton = new UndoButton(game);
        redoButton = new RedoButton(game);

        this.getChildren().addAll( undoButton, surrender, redoButton);
    }

    public UndoButton getUndoButton() {
        return undoButton;
    }

    public RedoButton getRedoButton() {
        return redoButton;
    }

    public SurrenderButton getSurrenderButton() {
        return surrender;
    }
}
