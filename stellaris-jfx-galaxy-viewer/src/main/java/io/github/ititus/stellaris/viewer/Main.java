package io.github.ititus.stellaris.viewer;

import io.github.ititus.stellaris.viewer.view.GalaxyView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public Stage primaryStage;
    public VBox vb;
    public StellarisSaveAnalyser analyser;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        vb = new VBox();
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vb, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Stellaris Save Analyser");
        primaryStage.show();

        analyser = new StellarisSaveAnalyser(this);
        Thread t = new Thread(analyser, "StellarisSaveAnalyser");
        t.setDaemon(true);
        t.start();
    }

    public void transition() {
        vb = null;
        System.gc();

        GalaxyView galaxyView = new GalaxyView(analyser.getGame(), analyser.getStellarisSave());

        Scene scene = new Scene(galaxyView);
        primaryStage.setTitle("Galaxy View");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.centerOnScreen();
    }
}
