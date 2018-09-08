import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;
import io.github.ititus.pdx.stellaris.view.GalaxyView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        long time = System.currentTimeMillis();
        GalaxyView galaxyView = new GalaxyView(getStellarisGame(), getStellarisSave());

        Scene scene = new Scene(galaxyView);
        primaryStage.setTitle("Galaxy View");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        System.out.println("Total Time: " + (System.currentTimeMillis() - time) / 1000D + " s");
    }

    private StellarisGame getStellarisGame() {
        long time = System.currentTimeMillis();
        StellarisGame game = null; // TODO:  maybe hack something together here
        System.out.println("Game Data Load Time: " + (System.currentTimeMillis() - time) / 1000D + " s");
        return game;
    }

    private static StellarisSave getStellarisSave() {
        long time = System.currentTimeMillis();
        StellarisSave save = new StellarisSave(new File("C:/Users/Vella/Desktop/test_save"));
        System.out.println("Test Save Load Time: " + (System.currentTimeMillis() - time) / 1000D + " s");
        return save;
    }

    /*@Override
    public void start(Stage primaryStage) {
        PerspectiveCamera camera = new PerspectiveCamera(true);

        Group root = new Group();
        root.getChildren().add(camera);

        Sphere s1 = new Sphere(2.5);
        Sphere s2 = new Sphere(7.5);

        Point3D from = new Point3D(-20, 16, 0);
        Point3D to = new Point3D(15, -4, 0);

        Translate fromTranslate = new Translate(from.getX(), from.getY(), from.getZ());
        s1.getTransforms().add(fromTranslate);
        Tooltip.install(s1, new Tooltip("Test #1"));

        Translate toTranslate = new Translate(to.getX(), to.getY(), to.getZ());
        s2.getTransforms().add(toTranslate);
        Tooltip.install(s2, new Tooltip("Test #2"));

        Cylinder c = new Cylinder(0.5, 0);

        Point3D length = to.subtract(from);
        Point3D middle = from.add(length.multiply(0.5));

        c.setHeight(length.magnitude());

        Translate translate = new Translate(middle.getX(), middle.getY(), middle.getZ());
        Rotate rZ = new Rotate(length.angle(NEG_Y), translate.getX(), translate.getY(), translate.getZ(), Rotate.Z_AXIS);
        c.getTransforms().addAll(rZ, translate);

        Tooltip.install(c, new Tooltip("Test #C"));

        /*Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(c.heightProperty(), 25),
                        new KeyValue(c.radiusProperty(), 5)
                ),
                new KeyFrame(
                        Duration.seconds(5),
                        new KeyValue(c.heightProperty(), 75),
                        new KeyValue(c.radiusProperty(), 15)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();*/

        /*root.getChildren().addAll(s1, s2, c);

        camera.setFieldOfView(35);
        camera.setFarClip(1000);

        Translate camTranslate = new Translate(0, 0, -100);
        Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        camera.getTransforms().addAll(
                xRotate,
                yRotate,
                camTranslate
        );

        Timeline camTimeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(yRotate.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(10),
                        new KeyValue(yRotate.angleProperty(), 360)
                )
        );
        camTimeline.setCycleCount(Timeline.INDEFINITE);
        camTimeline.setCycleCount(Timeline.INDEFINITE);
        camTimeline.setAutoReverse(true);
        // camTimeline.play();

        Scene scene = new Scene(root, 640, 480, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);
        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }*/
}
