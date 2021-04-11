package io.github.ititus.pdx.stellaris.view;

import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.save.*;
import io.github.ititus.pdx.util.mutable.MutableInt;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import org.eclipse.collections.api.block.function.primitive.IntFunction;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.api.map.primitive.MutableIntIntMap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.primitive.IntObjectPair;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.factory.primitive.IntIntMaps;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class GalaxyView extends BorderPane {

    private static final double INFO_PANEL_WIDTH = 200;
    private static final double PADDING = 5;
    private static final double INFO_PANEL_TOTAL_WIDTH = INFO_PANEL_WIDTH + 2 * PADDING;
    private static final Color SYSTEM_INNER_RADIUS_COLOR = Color.hsb(0.0 * 360, 0.0, 1.0);
    private static final Color SYSTEM_OUTER_RADIUS_COLOR = Color.hsb(0.1 * 360, 0.8, 0.9);
    private static final Color ASTEROID_BELT_COLOR = Color.SADDLEBROWN;

    private final StellarisGame game;
    private final StellarisSave save;
    private final SubScene galaxyScene, systemScene, scene2D;
    private final Group galaxyGroup, systemGroup, group2D;
    private final Label infoLabel;
    private final Button viewSystemButton;
    private final MutableSet<VisualHyperlane> hyperlanes = Sets.mutable.empty();
    private final MutableIntIntMap ownerCache = IntIntMaps.mutable.empty();
    private final IntFunction<GalacticObject> CACHE_FUNCTION = system -> {
        int starbaseId = system.starbase;
        if (starbaseId == -1) {
            return -1;
        }
        Starbase starbase = GalaxyView.this.getSave().gameState.starbaseManager.starbases.get(starbaseId);
        if (starbase == null) {
            return -1;
        }
        return starbase.owner;
    };

    private IntObjectPair<GalacticObject> selectedSystem, systemInScene;
    private IntObjectPair<Planet> selectedPlanet;

    public GalaxyView(StellarisGame game, StellarisSave save) {
        this.game = game;
        this.save = save;

        this.galaxyGroup = new Group();
        this.systemGroup = new Group();
        this.group2D = new Group();

        this.galaxyScene = new SubScene(this.galaxyGroup, getWidth() - INFO_PANEL_TOTAL_WIDTH, getHeight(), true,
                SceneAntialiasing.BALANCED);
        this.galaxyScene.setFill(Color.BLACK);
        PerspectiveCamera galaxyCamera = new PerspectiveCamera(true);
        this.galaxyScene.setCamera(galaxyCamera);
        this.galaxyScene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClickInGalaxyView(null));
        this.galaxyGroup.getChildren().add(galaxyCamera);

        this.systemScene = new SubScene(this.systemGroup, getWidth() - INFO_PANEL_TOTAL_WIDTH, getHeight(), true,
                SceneAntialiasing.BALANCED);
        this.systemScene.setFill(Color.BLACK);
        PerspectiveCamera systemCamera = new PerspectiveCamera(true);
        this.systemScene.setCamera(systemCamera);
        this.systemScene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClickInSystemView(null));
        this.systemGroup.getChildren().add(systemCamera);

        this.scene2D = new SubScene(this.group2D, INFO_PANEL_TOTAL_WIDTH, getHeight());
        this.scene2D.setFill(Color.WHITE);
        PerspectiveCamera camera2D = new PerspectiveCamera();
        this.scene2D.setCamera(camera2D);
        this.group2D.getChildren().add(camera2D);

        this.setCenter(new StackPane(this.galaxyScene, this.systemScene));
        this.setRight(this.scene2D);

        this.widthProperty().addListener(this::onWidthChange);
        this.heightProperty().addListener(this::onHeightChange);

        galaxyCamera.setFieldOfView(35);
        galaxyCamera.setFarClip(12000);
        systemCamera.setFieldOfView(35);
        systemCamera.setFarClip(12000);

        Rotate galaxyCameraRotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate galaxyCameraRotateY = new Rotate(180, Rotate.Y_AXIS); // To align to Stellaris' coordinate system
        Rotate galaxyCameraRotateZ = new Rotate(0, Rotate.Z_AXIS);
        Translate galaxyCameraTranslate = new Translate(0, 0, -1500);
        galaxyCamera.getTransforms().addAll(
                galaxyCameraRotateX,
                galaxyCameraRotateY,
                galaxyCameraRotateZ,
                galaxyCameraTranslate
        );

        Rotate systemCameraRotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate systemCameraRotateY = new Rotate(180, Rotate.Y_AXIS); // To align to Stellaris' coordinate system
        Rotate systemCameraRotateZ = new Rotate(0, Rotate.Z_AXIS);
        Translate systemCameraTranslate = new Translate(0, 0, -1000);
        systemCamera.getTransforms().addAll(
                systemCameraRotateX,
                systemCameraRotateY,
                systemCameraRotateZ,
                systemCameraTranslate
        );

        VBox infoVb = new VBox();
        ProgressBar pb1 = new ProgressBar(ProgressBar.INDETERMINATE_PROGRESS);
        pb1.setPrefWidth(INFO_PANEL_WIDTH);
        Label l1 = new Label();
        l1.setPrefWidth(INFO_PANEL_WIDTH);
        l1.setWrapText(true);

        this.infoLabel = new Label();
        this.infoLabel.setPrefWidth(INFO_PANEL_WIDTH);
        this.infoLabel.setWrapText(true);

        infoVb.getChildren().addAll(pb1, l1, this.infoLabel);

        HBox infoButtonHb = new HBox();
        this.viewSystemButton = new Button();
        this.viewSystemButton.setAlignment(Pos.BOTTOM_CENTER);
        infoButtonHb.getChildren().add(this.viewSystemButton);

        BorderPane infoBorderPane = new BorderPane();
        infoBorderPane.setPadding(new Insets(PADDING));
        infoBorderPane.setCenter(infoVb);
        infoBorderPane.setBottom(infoButtonHb);
        infoBorderPane.prefWidthProperty().bind(scene2D.widthProperty());
        infoBorderPane.prefHeightProperty().bind(scene2D.heightProperty());

        this.group2D.getChildren().add(infoBorderPane);

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() {
                ImmutableIntObjectMap<GalacticObject> systems = save.gameState.galacticObjects;
                ImmutableIntObjectMap<Country> countries = save.gameState.countries;

                int maxProgress = systems.size() + countries.size();
                MutableInt progress = new MutableInt();

                for (IntObjectPair<GalacticObject> pair : systems.keyValuesView()) {
                    if (isCancelled()) {
                        break;
                    }

                    updateProgress(progress.getAndIncrement(), maxProgress);
                    updateMessage("Adding system " + pair.getTwo().name + " [" + progress.get() + "/" + maxProgress + "]");

                    GalacticObjectFX galacticObjectFX = new GalacticObjectFX(GalaxyView.this, pair);

                    Platform.runLater(() -> galaxyGroup.getChildren().add(galacticObjectFX));
                }

                for (IntObjectPair<Country> pair : countries.keyValuesView()) {
                    if (isCancelled()) {
                        break;
                    }
                    if (pair.getTwo() == null) {
                        continue;
                    }

                    updateProgress(progress.getAndIncrement(), maxProgress);
                    updateMessage("Adding country " + pair.getTwo().name + " [" + progress.get() + "/" + maxProgress + "]");

                    CountryFX countryFX = new CountryFX(GalaxyView.this, pair);

                    Platform.runLater(() -> galaxyGroup.getChildren().add(countryFX));
                }

                Platform.runLater(() -> {
                    updateProgress(maxProgress, maxProgress);
                    updateMessage("Done");
                    infoVb.getChildren().removeAll(pb1, l1);
                });

                return null;
            }
        };

        pb1.progressProperty().bind(task.progressProperty());
        l1.textProperty().bind(task.messageProperty());

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

        switchToGalaxyView();
    }

    private void switchToGalaxyView() {
        systemScene.setVisible(false);
        galaxyScene.setVisible(true);

        onClickInGalaxyView(selectedSystem);
        viewSystemButton.setText("View System");
        viewSystemButton.setOnAction(event -> switchToSystemView(selectedSystem));
    }

    private void switchToSystemView(IntObjectPair<GalacticObject> systemPair) {
        if (systemPair == null) {
            return;
        }

        galaxyScene.setVisible(false);
        systemScene.setVisible(true);

        infoLabel.setText("");
        viewSystemButton.setDisable(false);
        viewSystemButton.setText("View Galaxy");
        viewSystemButton.setOnAction(event -> switchToGalaxyView());

        if (systemInScene == null || systemPair.getOne() != systemInScene.getOne()) {
            systemInScene = systemPair;
            systemGroup.getChildren().clear();

            Task<Void> task = new Task<>() {

                @Override
                protected Void call() {
                    ImmutableIntObjectMap<Planet> planets = save.gameState.planets.planets;
                    ImmutableList<IntObjectPair<Planet>> systemPlanets =
                            systemPair.getTwo().planets.collect(planetId -> PrimitiveTuples.pair(planetId,
                                    planets.get(planetId)));

                    int maxProgress = systemPlanets.size() + 1;
                    MutableInt progress = new MutableInt();

                    for (IntObjectPair<Planet> pair : systemPlanets) {
                        if (isCancelled()) {
                            break;
                        }

                        updateProgress(progress.getAndIncrement(), maxProgress);
                        updateMessage("Adding planet " + pair.getTwo().name + " [" + progress.get() + "/" + maxProgress + "]");

                        PlanetFX planetFX = new PlanetFX(GalaxyView.this, pair);

                        Platform.runLater(() -> systemGroup.getChildren().add(planetFX));
                    }

                    updateProgress(progress.getAndIncrement(), maxProgress);
                    updateMessage("Adding inner & outer radius [" + progress.get() + "/" + maxProgress + "]");

                    GalacticObject g = systemPair.getTwo();

                    List<Node> nodes = new ArrayList<>();
                    Circle innerRadius = new Circle();
                    innerRadius.setCenterX(0);
                    innerRadius.setCenterY(0);
                    innerRadius.setRadius(g.innerRadius);
                    innerRadius.setFill(null);
                    innerRadius.setStroke(SYSTEM_INNER_RADIUS_COLOR);
                    innerRadius.setStrokeWidth(0.3);
                    // innerRadius.getStrokeDashArray().addAll(10.0, 8.0);
                    nodes.add(innerRadius);

                    Circle outerRadius = new Circle();
                    outerRadius.setCenterX(0);
                    outerRadius.setCenterY(0);
                    outerRadius.setRadius(g.outerRadius);
                    outerRadius.setFill(null);
                    outerRadius.setStroke(SYSTEM_OUTER_RADIUS_COLOR);
                    outerRadius.setStrokeWidth(0.3);
                    //outerRadius.getStrokeDashArray().addAll(10.0, 8.0);
                    nodes.add(outerRadius);

                    for (AsteroidBelt b : g.asteroidBelts) {
                        Circle belt = new Circle();
                        belt.setCenterX(0);
                        belt.setCenterY(0);
                        belt.setRadius(b.innerRadius);
                        belt.setFill(null);
                        belt.setStroke(ASTEROID_BELT_COLOR);
                        belt.setStrokeWidth(2.0);
                        nodes.add(belt);
                    }

                    Platform.runLater(() -> {
                        systemGroup.getChildren().addAll(nodes);
                        updateProgress(maxProgress, maxProgress);
                        updateMessage("Done");
                    });

                    return null;
                }
            };

            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();
        }
    }

    public boolean containsHyperlane(VisualHyperlane hyperlane) {
        return !hyperlanes.add(hyperlane);
    }

    public int getOwnerId(int systemId, GalacticObject system) {
        return ownerCache.getIfAbsentPutWith(systemId, CACHE_FUNCTION, system);
    }

    public StellarisGame getGame() {
        return game;
    }

    public StellarisSave getSave() {
        return save;
    }

    public void onWidthChange(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        galaxyScene.setWidth(newValue.doubleValue() - INFO_PANEL_TOTAL_WIDTH);
        systemScene.setWidth(newValue.doubleValue() - INFO_PANEL_TOTAL_WIDTH);
    }

    public void onHeightChange(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        galaxyScene.setHeight(newValue.doubleValue());
        systemScene.setHeight(newValue.doubleValue());
        scene2D.setHeight(newValue.doubleValue());
    }

    public void onClickInGalaxyView(IntObjectPair<GalacticObject> systemPair) {
        selectedSystem = systemPair;
        viewSystemButton.setDisable(selectedSystem == null && galaxyScene.isVisible());
        StringBuilder text = new StringBuilder();
        if (systemPair != null) {
            ImmutableIntObjectMap<Starbase> starbases = save.gameState.starbaseManager.starbases;
            ImmutableIntObjectMap<Country> countries = save.gameState.countries;
            ImmutableIntObjectMap<GalacticObject> systems = save.gameState.galacticObjects;
            ImmutableIntObjectMap<NaturalWormhole> wormholes = save.gameState.naturalWormholes;
            ImmutableIntObjectMap<Bypass> bypasses = save.gameState.bypasses;
            ImmutableIntObjectMap<Megastructure> megaStructures = save.gameState.megaStructures;
            ImmutableIntObjectMap<Planet> planets = save.gameState.planets.planets;

            MutableList<String> properties = Lists.mutable.empty();
            text.append(systemPair.getTwo().name).append(" (#").append(systemPair.getOne()).append(")\n");

            properties.add("type=" + systemPair.getTwo().type);
            properties.add("initializer=" + systemPair.getTwo().initializer);
            int starbaseId = systemPair.getTwo().starbase;
            if (starbaseId != -1) {
                Starbase starbase = starbases.get(starbaseId);
                if (starbase != null) {
                    int ownerId = starbase.owner;
                    Country owner = countries.get(ownerId);
                    properties.add("owner=" + owner.name + " (#" + ownerId + ")");
                    properties.add("starbase=" + starbase.level + " (#" + starbaseId + ")");
                }
            }

            if (!properties.isEmpty()) {
                properties.forEach(property -> text.append(" - ").append(property).append("\n"));
            }
            properties.clear();

            ImmutableList<Hyperlane> hyperlanes = systemPair.getTwo().hyperlanes.stream()
                    .sorted(Comparator.comparingInt(h -> h.to))
                    .collect(Collectors2.toImmutableList());
            if (!hyperlanes.isEmpty()) {
                if (hyperlanes.size() == 1) {
                    text.append("\nHyperlane to:\n");
                } else {
                    text.append("\nHyperlanes to:\n");
                }
                hyperlanes.stream()
                        .map(hyperlane -> {
                            int id = hyperlane.to;
                            GalacticObject system = systems.get(id);

                            StringBuilder b =
                                    new StringBuilder(" - ").append(system.name).append(" (#").append(id).append(")");

                            properties.add("length=" + Math.round(hyperlane.length));
                            if (hyperlane.bridge) {
                                properties.add("bridge");
                            }

                            if (!properties.isEmpty()) {
                                b.append(" |");
                                properties.forEach(property -> b.append(" ").append(property));
                            }
                            properties.clear();
                            return b.append("\n").toString();
                        })
                        .forEachOrdered(text::append);
            }

            ImmutableList<IntObjectPair<NaturalWormhole>> naturalWormholes =
                    systemPair.getTwo().naturalWormholes.primitiveStream()
                            .sorted()
                            .mapToObj(wormholeId -> PrimitiveTuples.pair(wormholeId, wormholes.get(wormholeId)))
                            .collect(Collectors2.toImmutableList());
            if (!naturalWormholes.isEmpty()) {
                if (naturalWormholes.size() == 1) {
                    text.append("\nNatural Wormhole to:\n");
                } else {
                    text.append("\nNatural Wormholes to:\n");
                }
                naturalWormholes.stream()
                        .map(wormhole -> {
                            int bypassId = wormhole.getTwo().bypass;
                            Bypass bypass = bypasses.get(bypassId);
                            int linkedTo = bypass.linkedTo;

                            int target = wormholes.stream()
                                    .filter(w -> w.bypass == linkedTo)
                                    .findAny()
                                    .get()
                                    .coordinate
                                    .origin;
                            GalacticObject targetSystem = systems.get(target);

                            StringBuilder b =
                                    new StringBuilder(" - ").append(targetSystem.name).append(" (#").append(target).append(")");

                            properties.add("bypass=" + bypass.type + " (#" + bypassId + ")");

                            if (!properties.isEmpty()) {
                                b.append(" |");
                                properties.forEach(property -> b.append(" ").append(property));
                            }
                            properties.clear();
                            return b.append("\n").toString();
                        })
                        .forEachOrdered(text::append);
            }

            ImmutableList<IntObjectPair<Bypass>> systemBypasses = systemPair.getTwo().bypasses.primitiveStream()
                    .sorted()
                    .mapToObj(bypassId -> PrimitiveTuples.pair(bypassId, bypasses.get(bypassId)))
                    .collect(Collectors2.toImmutableList());
            if (!systemBypasses.isEmpty()) {
                if (systemBypasses.size() == 1) {
                    text.append("\nBypass:\n");
                } else {
                    text.append("\nBypasses:\n");
                }
                systemBypasses.stream()
                        .map(bypassPair -> {
                            String type = bypassPair.getTwo().type;
                            int id = bypassPair.getOne();
                            boolean active = bypassPair.getTwo().active;
                            int linkedTo = bypassPair.getTwo().linkedTo;

                            StringBuilder b = new StringBuilder(" - ").append(type).append(" (#").append(id).append(
                                    ")");

                            properties.add("active=" + active);

                            if (linkedTo != -1) {
                                Bypass target = bypasses.get(linkedTo);
                                properties.add("linked_to=" + target.type + " (#" + linkedTo + ")");
                                int targetSystemId = wormholes.stream()
                                        .filter(wormhole -> wormhole.bypass == linkedTo)
                                        .findAny()
                                        .get()
                                        .coordinate
                                        .origin;
                                GalacticObject targetSystem = systems.get(targetSystemId);
                                properties.add("target_system=" + targetSystem.name + " (#" + targetSystemId +
                                        ")");
                            } else {
                                bypassPair.getTwo().connections.primitiveStream()
                                        .sorted()
                                        .mapToObj(tId -> new Object() {
                                            final int targetId = tId;
                                            final Bypass target = bypasses.get(targetId);
                                            final int targetSystemId = megaStructures.stream()
                                                    .filter(megastructure -> megastructure.bypass == targetId)
                                                    .findAny()
                                                    .get()
                                                    .coordinate
                                                    .origin;
                                            final GalacticObject targetSystem = systems.get(targetSystemId);
                                        })
                                        .map(o -> "linked_to=" + o.target.type + " (#" + o.targetId + ") " +
                                                "target_system=" + o.targetSystem.name + " (#" + o.targetSystemId + ")")
                                        .forEachOrdered(properties::add);
                            }

                            if (!properties.isEmpty()) {
                                b.append(" |");
                                properties.forEach(property -> b.append(" ").append(property));
                            }
                            properties.clear();
                            return b.append("\n").toString();
                        })
                        .forEachOrdered(text::append);
            }

            // TODO: sort by type (stars, planets)
            ImmutableList<IntObjectPair<Planet>> systemPlanets = systemPair.getTwo().planets.primitiveStream()
                    .sorted()
                    .mapToObj(planetId -> PrimitiveTuples.pair(planetId, planets.get(planetId)))
                    /*.flatMap(planetPair -> Stream.concat(
                            Stream.of(planetPair),
                            CollectionUtil.stream(planetPair.getTwo().getMoons())
                                    .mapToObj(planetId -> PrimitiveTuples.pair(planetId, planets.get(planetId)))
                    ))*/
                    .collect(Collectors2.toImmutableList());
            if (!systemPlanets.isEmpty()) {
                if (systemPlanets.size() == 1) {
                    text.append("\nPlanet:\n");
                } else {
                    text.append("\nPlanets & Moons:\n");
                }
                systemPlanets.stream()
                        .map(planetPair -> {
                            StringBuilder b = new StringBuilder(" - ").append(planetPair.getTwo().name).append(
                                    " (#").append(planetPair.getOne()).append(")");

                            if (planetPair.getTwo().isMoon) {
                                properties.add("moon");
                            }
                            properties.add(planetPair.getTwo().planetClass);
                            properties.add("size=" + planetPair.getTwo().planetSize);

                            if (!properties.isEmpty()) {
                                b.append(" |");
                                properties.forEach(property -> b.append(" ").append(property));
                            }
                            properties.clear();
                            return b.append("\n").toString();
                        })
                        .forEachOrdered(text::append);
            }

            ImmutableList<IntObjectPair<Megastructure>> systemMegaStructures =
                    systemPair.getTwo().megaStructures.primitiveStream()
                            .sorted()
                            .mapToObj(megaStructureId -> PrimitiveTuples.pair(megaStructureId,
                                    megaStructures.get(megaStructureId)))
                            .collect(Collectors2.toImmutableList());
            if (!systemMegaStructures.isEmpty()) {
                if (systemMegaStructures.size() == 1) {
                    text.append("\nMega Structure:\n");
                } else {
                    text.append("\nMega Structures:\n");
                }
                systemMegaStructures.stream()
                        .map(megaStructurePair -> {
                            String type = megaStructurePair.getTwo().type;
                            int id = megaStructurePair.getOne();
                            // TODO: add more info

                            StringBuilder b = new StringBuilder(" - ").append(type).append(" (#").append(id).append(
                                    ")");

                            if (!properties.isEmpty()) {
                                b.append(" |");
                                properties.forEach(property -> b.append(" ").append(property));
                            }
                            properties.clear();
                            return b.append("\n").toString();
                        })
                        .forEachOrdered(text::append);
            }
        }

        infoLabel.setText(text.toString());
    }

    public void onClickInSystemView(IntObjectPair<Planet> planetPair) {
        selectedPlanet = planetPair;
        StringBuilder text = new StringBuilder();
        if (planetPair != null) {
            text.append(planetPair.getTwo().name).append(" (#").append(planetPair.getOne()).append(")\n\n");

            ImmutableIntObjectMap<Planet> planets = save.gameState.planets.planets;

            text.append("class=").append(planetPair.getTwo().planetClass).append("\n");
            text.append("size=").append(planetPair.getTwo().planetSize).append("\n");
            text.append("orbit=").append(planetPair.getTwo().orbit).append("\n");
            text.append("ring=").append(planetPair.getTwo().hasRing).append("\n");
            if (planetPair.getTwo().isMoon) {
                int moonOfId = planetPair.getTwo().moonOf;
                Planet moonOf = planets.get(moonOfId);
                text.append("moon_of=").append(moonOf.name).append(" (#").append(moonOfId).append(")\n");
            }
            ImmutableIntList moonIds = planetPair.getTwo().moons;
            if (!moonIds.isEmpty()) {
                ImmutableList<IntObjectPair<Planet>> moons =
                        moonIds.collect(planetId -> PrimitiveTuples.pair(planetId, planets.get(planetId)));
                if (moons.size() == 1) {
                    IntObjectPair<Planet> moonPair = moons.get(0);
                    text.append("moon=").append(moonPair.getTwo().name).append(" (#").append(moonPair.getOne()).append(")\n");
                } else {
                    text.append("moons=").append(moons.collect(moonPair -> moonPair.getTwo().name + " (#" + moonPair.getOne() + ")")).append("\n");
                }
            }
        }

        infoLabel.setText(text.toString());
    }
}
