package io.github.ititus.pdx.stellaris.view;

import io.github.ititus.pdx.stellaris.game.common.planet_classes.PlanetClass;
import io.github.ititus.pdx.stellaris.game.gfx.Entity;
import io.github.ititus.pdx.stellaris.user.save.Coordinate;
import io.github.ititus.pdx.stellaris.user.save.Planet;
import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;
import org.eclipse.collections.api.tuple.primitive.IntObjectPair;

import java.util.Objects;
import java.util.Optional;

public class PlanetFX extends Group {

    private static final double BASE_SIZE_MULTIPLIER = 1.0 / 60.0;
    private static final double MEGASTRUCTURE_TO_PLANET_SCALE = 1.0 / 0.045;
    private static final Color ORBIT_COLOR = Color.hsb(0.44 * 360, 0.8, 0.6);
    private static final double MOON_SCALE = 0.7;

    // private final Color color;

    private final GalaxyView galaxyView;
    private final IntObjectPair<Planet> planetPair;

    private final Sphere planetSphere;
    private final Tooltip planetTooltip;

    public PlanetFX(GalaxyView galaxyView, IntObjectPair<Planet> planetPair) {
        this.galaxyView = galaxyView;
        this.planetPair = planetPair;

        // int hash = planetPair.hashCode();
        // hash = (hash ^ (hash >>> 8)) & 0xFFFFFF;
        // this.color = Color.rgb((hash >>> 16) & 0xFF, (hash >>> 8) & 0xFF, hash & 0xFF);

        this.planetSphere = new Sphere(calculatePlanetVisualSize());
        Coordinate c = planetPair.getTwo().coordinate;
        this.planetSphere.getTransforms().add(new Translate(c.x, c.y, 0));
        // this.planetSphere.setMaterial(new PhongMaterial(this.color));
        this.planetSphere.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            galaxyView.onClickInSystemView(planetPair);
            event.consume();
        });

        this.planetTooltip = new Tooltip(planetPair.getTwo().name + " (#" + planetPair.getOne() + ")");

        Circle orbit = createOrbit();

        Platform.runLater(() -> {
            Tooltip.install(this.planetSphere, this.planetTooltip);
            this.getChildren().add(this.planetSphere);
            if (orbit != null) {
                this.getChildren().add(orbit);
            }
        });
    }

    private Circle createOrbit() {
        PlanetClass planetClass = galaxyView.getGame().getCommon().planetClasses.planetClasses.get(planetPair.getTwo().planetClass);
        if (planetClass.star || !planetClass.orbitLines) {
            return null;
        }

        Coordinate c = planetPair.getTwo().coordinate;
        Point3D p = new Point3D(c.x, c.y, 0);

        Point3D center;
        if (planetPair.getTwo().isMoon) { // moon
            Coordinate centerC = galaxyView.getSave().gameState.planets.planets.get(planetPair.getTwo().moonOf).coordinate;
            center = new Point3D(centerC.x, centerC.y, 0);
        } else if (planetPair.getTwo().moonOf != -1) { // more than 1 star in system, this planet is not orbiting the central one
            Coordinate centerC = galaxyView.getSave().gameState.planets.planets.get(planetPair.getTwo().moonOf).coordinate;
            center = new Point3D(centerC.x, centerC.y, 0);
        } else {
            center = Point3D.ZERO;
        }

        double calculatedRadius = p.distance(center);

        Circle orbit = new Circle();
        orbit.setCenterX(center.getX());
        orbit.setCenterY(center.getY());
        orbit.setRadius(calculatedRadius);
        orbit.setFill(null);
        orbit.setStroke(ORBIT_COLOR);
        orbit.setStrokeWidth(0.2);

        return orbit;
    }

    private double calculatePlanetVisualSize() {
        Planet planet = planetPair.getTwo();

        int planetSize = planet.planetSize;

        String planetClassName = planet.planetClass;
        PlanetClass planetClass = galaxyView.getGame().getCommon().planetClasses.planetClasses.get(planetClassName);
        double entityScaleFromPlanetClass = planetClass.entityScale;

        String entityName = planetClass.entity;
        Optional<Entity> entity = galaxyView.getGame().getGfx().entities.stream()
                .filter(e -> Objects.equals(entityName, e.name))
                .findAny();
        if (entity.isEmpty()) {
            entity = galaxyView.getGame().getGfx().entities.stream()
                    .filter(e -> Objects.equals(entityName + "_01_entity", e.name))
                    .findAny();
        }
        double entityScaleFromEntity = entity.map(value -> value.scale).orElse(1.0);

        double size = BASE_SIZE_MULTIPLIER * planetSize * entityScaleFromPlanetClass * entityScaleFromEntity;
        if (planet.isMoon) {
            size *= MOON_SCALE;
        }

        if (planetClassName.startsWith("pc_ringworld") || planetClassName.startsWith("pc_habitat")) {
            size *= MEGASTRUCTURE_TO_PLANET_SCALE;
        }

        return size;
    }
}
