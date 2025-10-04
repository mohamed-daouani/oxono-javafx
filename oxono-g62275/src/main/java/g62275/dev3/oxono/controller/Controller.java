package g62275.dev3.oxono.controller;

import g62275.dev3.oxono.model.*;
import g62275.dev3.oxono.view.*;


public class Controller {

    private Game game;
    private MainView mainView;

    public Controller(Game game, MainView mainView) {
        this.game = game;
        this.mainView = mainView;

        game.registerObserver(mainView);



        mainView.getSurrenderButton().setOnAction(event -> surrender());
        updateSurrenderButtonState();
        mainView.getUndoButton().setOnAction(event -> undo());
        mainView.getRedoButton().setOnAction(event -> redo());
    }

    private void surrender() {
        game.surrender();
        AlertBox.showInfoAlert("Abandon du joueur " + game.getCurrentPlayer() , "Le joueur " + game.getCurrentPlayer() + " a abandonné !");
    }
    private void updateSurrenderButtonState() {
        boolean isPinkTurn = game.getCurrentPlayer() == Color.PINK;
        mainView.getSurrenderButton().setDisable(!isPinkTurn);
    }

    private void undo() {
        if (!game.isGameOver()) {
            game.undo();
            mainView.update(game);
        }
    }

    private void redo() {
        if (!game.isGameOver()) {
            game.redo();
            mainView.update(game);

        }
    }
}

