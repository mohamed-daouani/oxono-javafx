package g62275.dev3.oxono;

import g62275.dev3.oxono.controller.Controller;
import g62275.dev3.oxono.model.AI;
import g62275.dev3.oxono.model.EasyStrategy;
import g62275.dev3.oxono.model.Game;
import g62275.dev3.oxono.view.MainView;
import g62275.dev3.oxono.view.ConfigForm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        showConfigurationWindow(primaryStage);
    }

    private void showConfigurationWindow(Stage primaryStage) {
        Stage configStage = new Stage();
        ConfigForm formulaire = new ConfigForm();
        formulaire.setBackground(new Background(new BackgroundFill(Color.rgb(113, 61, 127), null, null)));

        formulaire.getSubmitButton().setOnAction(event -> {
            int boardSize = formulaire.getBoardSize();
            String difficulty = formulaire.getDifficulty();

            configStage.close();

            showGameWindow(primaryStage, boardSize, difficulty);
        });

        Scene configScene = new Scene(formulaire, 500, 300);
        configStage.setScene(configScene);
        configStage.setTitle("Configuration du jeu Oxono");
        configStage.show();
    }

    private void showGameWindow(Stage primaryStage, int boardSize, String difficulty) {
        Game game = new Game(boardSize);

        AI ai = new AI(game, new EasyStrategy());

        MainView mainView = new MainView(game, primaryStage, ai);
        mainView.setScaleX(0.8);
        mainView.setScaleY(0.8);

        Controller controller = new Controller(game, mainView);

        mainView.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        int sceneWidth, sceneHeight;

        if (boardSize == 4) {
            sceneWidth = 800;
            sceneHeight = 600;
        } else if (boardSize == 6) {
            sceneWidth = 1000;
            sceneHeight = 850;
        } else if (boardSize == 8) {
            sceneWidth = 1500;
            sceneHeight = 1100;
            mainView.setScaleX(0.7);
            mainView.setScaleY(0.7);

        } else {
            sceneWidth = 1000;
            sceneHeight = 600;
        }



        Scene scene = new Scene(mainView, sceneWidth, sceneHeight);


        primaryStage.setTitle("Oxono");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
