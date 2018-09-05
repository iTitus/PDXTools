package io.github.ititus.pdx.stellaris.view;

import io.github.ititus.pdx.stellaris.user.save.GalacticObject;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;
import io.github.ititus.pdx.util.mutable.MutableInt;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.primitive.IntObjectPair;
import org.eclipse.collections.impl.factory.Sets;

public class GalaxyView extends BorderPane {

    private static final double INFO_PANEL_WIDTH = 200;

    private final MutableSet<VisualHyperlane> hyperlanes = Sets.mutable.empty();
    private final StellarisSave save;
    private final SubScene scene3D, scene2D;
    private final Label infoLabel;

    public GalaxyView(StellarisSave save) {
        this.save = save;

        Group group3D = new Group();
        Group group2D = new Group();

        this.scene3D = new SubScene(group3D, getWidth() - INFO_PANEL_WIDTH, getHeight(), true, SceneAntialiasing.BALANCED);
        this.scene3D.setFill(Color.BLACK);
        PerspectiveCamera camera3D = new PerspectiveCamera(true);
        this.scene3D.setCamera(camera3D);
        group3D.getChildren().add(camera3D);

        this.scene2D = new SubScene(group2D, INFO_PANEL_WIDTH, getHeight());
        this.scene2D.setFill(Color.WHITE);
        PerspectiveCamera camera2D = new PerspectiveCamera();
        this.scene2D.setCamera(camera2D);
        group2D.getChildren().add(camera2D);

        this.setCenter(this.scene3D);
        this.setRight(this.scene2D);

        this.widthProperty().addListener(this::onWidthChange);
        this.heightProperty().addListener(this::onHeightChange);

        camera3D.setFieldOfView(35);
        camera3D.setFarClip(3000);

        Translate translate = new Translate(0, 0, -1500);
        Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
        Rotate yRotate = new Rotate(180, Rotate.Y_AXIS); // To align to Stellaris' coordinate system
        Rotate zRotate = new Rotate(0, Rotate.Z_AXIS);
        camera3D.getTransforms().addAll(
                xRotate,
                yRotate,
                zRotate,
                translate
        );

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(xRotate.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(10),
                        new KeyValue(xRotate.angleProperty(), -89)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        VBox vb = new VBox();
        ProgressBar pb1 = new ProgressBar(ProgressBar.INDETERMINATE_PROGRESS);
        pb1.setMaxWidth(INFO_PANEL_WIDTH);
        Label l1 = new Label();
        l1.setMaxWidth(INFO_PANEL_WIDTH);

        this.infoLabel = new Label();
        infoLabel.setVisible(false);

        vb.getChildren().addAll(pb1, l1, infoLabel);
        group2D.getChildren().add(vb);

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() {
                ImmutableIntObjectMap<GalacticObject> systems = save.getGameState().getGalacticObjects().getGalacticObjects().reject((i, s) -> s == null);

                int SYSTEM_COUNT = systems.size();
                MutableInt progress = new MutableInt();

                for (IntObjectPair<GalacticObject> pair : systems.keyValuesView()) {
                    if (isCancelled()) {
                        break;
                    }

                    updateProgress(progress.getAndIncrement(), SYSTEM_COUNT);
                    updateMessage("Adding system " + pair.getTwo().getName() + " [" + progress.get() + "/" + SYSTEM_COUNT + "]");

                    GalacticObjectFX galacticObjectFX = new GalacticObjectFX(GalaxyView.this, pair);

                    Platform.runLater(() -> group3D.getChildren().add(galacticObjectFX));
                }

                Platform.runLater(() -> {
                    updateProgress(SYSTEM_COUNT, SYSTEM_COUNT);
                    updateMessage("Done");
                    vb.getChildren().removeAll(pb1, l1);
                    infoLabel.setVisible(true);
                    // timeline.play();
                });

                return null;
            }
        };

        pb1.progressProperty().bind(task.progressProperty());
        l1.textProperty().bind(task.messageProperty());

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    public boolean containsHyperlane(VisualHyperlane hyperlane) {
        return !hyperlanes.add(hyperlane);
    }

    public StellarisSave getSave() {
        return save;
    }

    public void onWidthChange(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        scene3D.setWidth(newValue.doubleValue() - INFO_PANEL_WIDTH);
    }

    public void onHeightChange(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        scene3D.setHeight(newValue.doubleValue());
        scene2D.setHeight(newValue.doubleValue());
    }

    public void setInfoText(String text) {
        if (Platform.isFxApplicationThread()) {
            infoLabel.setText(text);
        } else {
            Platform.runLater(() -> infoLabel.setText(text));
        }
    }
}
