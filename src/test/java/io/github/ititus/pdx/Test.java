package io.github.ititus.pdx;

import io.github.ititus.math.time.DurationFormatter;
import io.github.ititus.math.time.StopWatch;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.StellarisUserData;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;
import io.github.ititus.pdx.stellaris.view.GalaxyView;
import io.github.ititus.pdx.util.mutable.MutableString;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.tuple.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Test extends Application {

    private static final Path USER_HOME = Path.of(System.getProperty("user.home"));
    private static final Path SAVE = USER_HOME.resolve("Desktop/pdx/nico_2.3.3");
    private static final Path DEBUG_OUT = USER_HOME.resolve("Desktop/pdx/out.txt");
    private static final Path USER_DATA_DIR = USER_HOME.resolve("Documents/Paradox Interactive/Stellaris");
    private static final Path INSTALL_DIR = Path.of("C:", "Program Files (x86)", "Steam", "steamapps", "common", "Stellaris");

    public static void main(String[] args) {
        launch(args);
    }

    private static StellarisGame getStellarisGame() {
        StopWatch s = StopWatch.createRunning();

        MutableString lastMessage = new MutableString();
        StopWatch stepWatch = StopWatch.create();
        StellarisGame game = new StellarisGame(INSTALL_DIR, 0, (index, visible, workDone, totalWork, msg) -> {
            if (index == 0) {
                if (stepWatch.isRunning()) {
                    System.out.println(lastMessage.get() + ": " + DurationFormatter.formatSeconds(stepWatch.stop()));
                } else {
                    System.out.println("Loading Game Data");
                }

                if (!msg.equals("Done")) {
                    lastMessage.set(msg);
                    stepWatch.start();
                }
            }
            // System.out.printf("%d %b %d/%d %s%n", index, visible, workDone, totalWork, msg);
        });
        System.out.println("Game Data Load Time: " + DurationFormatter.formatSeconds(s.stop()));
        return game;
    }

    private static StellarisUserData getStellarisUserData() {
        StopWatch s = StopWatch.createRunning();
        StellarisUserData userData = new StellarisUserData(USER_DATA_DIR, 1, (index, visible, workDone, totalWork, msg) -> {
            // System.out.printf("%d %b %d/%d %s%n", index, visible, workDone, totalWork, msg);
        });
        System.out.println("User Data Load Time: " + DurationFormatter.formatSeconds(s.stop()));
        return userData;
    }

    private static StellarisSave getStellarisSave() {
        StopWatch s = StopWatch.createRunning();
        StellarisSave save = new StellarisSave(SAVE);
        System.out.println("Test Save Load Time: " + DurationFormatter.formatSeconds(s.stop()));
        return save;
    }

    @Override
    public void start(Stage primaryStage) {
        StopWatch s = StopWatch.createRunning();

        StellarisGame game = getStellarisGame();
        StellarisUserData userData = null; // getStellarisUserData();
        StellarisSave save = getStellarisSave();

        ImmutableList<String> unknownLiterals = PdxScriptParser.getUnknownLiterals();
        ImmutableList<Pair<String, Throwable>> gameErrors = game != null && game.getRawDataLoader() != null ? game.getRawDataLoader().getErrors() : null;
        ImmutableMap<String, ImmutableMap<String, String>> missingLocalisation = game != null && game.getLocalisation() != null ? game.getLocalisation().getMissingLocalisation() : null;
        ImmutableMap<String, ImmutableMap<String, String>> extraLocalisation = game != null && game.getLocalisation() != null ? game.getLocalisation().getExtraLocalisation() : null;
        ImmutableList<Pair<String, Throwable>> userDataErrors = userData != null && userData.getRawDataLoader() != null ? userData.getRawDataLoader().getErrors() : null;
        ImmutableList<String> saveParseErrors = save != null ? save.getErrors() : null;

        try {
            Files.write(DEBUG_OUT, new byte[0]);
            if (saveParseErrors != null) {
                Files.write(DEBUG_OUT, saveParseErrors, StandardOpenOption.APPEND);
                saveParseErrors.forEach(System.out::println);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Total Loading Time: " + DurationFormatter.formatSeconds(s.stop()));

        if (save != null) {
            GalaxyView galaxyView = new GalaxyView(game, save);

            Scene scene = new Scene(galaxyView);
            primaryStage.setTitle("Galaxy View");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } else {
            Platform.exit();
        }

        System.out.println("done");
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