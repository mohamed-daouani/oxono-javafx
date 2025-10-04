package g62275.dev3.oxono.view;

import g62275.dev3.oxono.controller.Controller;
import g62275.dev3.oxono.model.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends VBox implements Observer {
    private BoardView boardView;
    private ButtonBar buttonBar;
    private TokenStack userTokenStackX;
    private TokenStack userTokenStackO;

    private TokenStack aiTokenStackX;
    private TokenStack aiTokenStackO;
    private Stage stage;
    private HBox mainContent;

    public MainView(Game game, Stage stage, AI ai){
        this.stage = stage;
        this.boardView = new BoardView(game, ai);
        this.buttonBar = new ButtonBar(game);
        this.userTokenStackX = new TokenStack(Color.PINK, Symbol.X);
        this.userTokenStackO = new TokenStack(Color.PINK, Symbol.O);
        userTokenStackX.setScaleX(0.6);
        userTokenStackX.setScaleY(0.6);

        userTokenStackO.setScaleX(0.6);
        userTokenStackO.setScaleY(0.6);

        VBox tokenStackBox = new VBox(10);
        tokenStackBox.setPadding(new Insets(10));
        tokenStackBox.setTranslateY(100);
        tokenStackBox.setTranslateX(-50);

        tokenStackBox.getChildren().addAll(userTokenStackX, userTokenStackO);
        this.mainContent = new HBox(20);
        this.mainContent.setPadding(new Insets(10));
        this.mainContent.getChildren().addAll(tokenStackBox, boardView);

        this.setMargin(buttonBar, new Insets(20, 0, 0, 0));
        this.getChildren().addAll(mainContent, buttonBar);
    }

    public Button getUndoButton(){
        return buttonBar.getUndoButton();
    }

    public Button getRedoButton(){
        return buttonBar.getRedoButton();
    }
    public Button getSurrenderButton(){
        return buttonBar.getSurrenderButton();
    }

    @Override
    public void update(Game game) {
        boardView.update(game);

        if (game.getGameSate() == GameSate.INSERT) {
            Symbol symbolToInsert = game.getToInsert();
            if (game.getCurrentPlayer() == Color.PINK) {
                if (symbolToInsert == Symbol.X) {
                    userTokenStackX.removeToken();
                } else if (symbolToInsert == Symbol.O) {
                    userTokenStackO.removeToken();
                }
            }
        }

        buttonBar.getSurrenderButton().setDisable(game.getCurrentPlayer() == Color.BLACK);
        buttonBar.getUndoButton().setDisable(!game.canUndo());
        buttonBar.getRedoButton().setDisable(!game.canRedo());

        if (game.isGameOver()) {
            String message;
            if (game.won()) {
                message = "Le joueur " + game.getOpposingPlayer() + " a gagné !";
            } else if (game.draw()) {
                message = "Match nul !";
            } else {
                return;
            }
            AlertBox.showInfoAlert("Fin du jeu", message);
            boardView.disableInteractions();
        }
    }

}
