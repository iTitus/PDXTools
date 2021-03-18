package io.github.ititus.pdx;

import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.stellaris.view.GalaxyView;
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
    public void start(Stage primaryStage) throws Exception {
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

    /*public void lookAt(Point3D lookAtPos, Rotate rX, Rotate rY, Translate translate) {
        Point3D camDirection = lookAtPos.subtract(translate.getX(), translate.getY(), translate.getZ()).normalize();

        double xRotation = Math.toDegrees(Math.asin(-camDirection.getY()));
        double yRotation = Math.toDegrees(Math.atan2(camDirection.getX(), camDirection.getZ()));

        rX.setAngle(xRotation);
        rX.setPivotX(translate.getX());
        rX.setPivotY(translate.getY());
        rX.setPivotZ(translate.getZ());
        rX.setAxis(Rotate.X_AXIS);

        rY.setAngle(yRotation);
        rY.setPivotX(translate.getX());
        rY.setPivotY(translate.getY());
        rY.setPivotZ(translate.getZ());
        rY.setAxis(Rotate.Y_AXIS);
    }*/
}
