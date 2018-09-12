package io.github.ititus.pdx.stellaris.view;

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

public class PlanetFX extends Group {

    private static final double PLANET_STANDARD_SCALE = 11;

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

        this.planetSphere = new Sphere(getPlanetVisualSize(planetPair));
        this.planetSphere.getTransforms().add(new Translate(planetPair.getTwo().getCoordinate().getX(), planetPair.getTwo().getCoordinate().getY(), 0));
        // this.planetSphere.setMaterial(new PhongMaterial(this.color));
        this.planetSphere.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            galaxyView.onClickInSystemView(planetPair);
            event.consume();
        });

        this.planetTooltip = new Tooltip(planetPair.getTwo().getName() + " (#" + planetPair.getOne() + ")");

        Circle orbit = createOrbit(Color.LIGHTGRAY);

        Platform.runLater(() -> {
            Tooltip.install(this.planetSphere, this.planetTooltip);
            this.getChildren().addAll(this.planetSphere, orbit);
        });
    }

    private Circle createOrbit(Color color) {
        Coordinate c = planetPair.getTwo().getCoordinate();
        Point3D p = new Point3D(c.getX(), c.getY(), 0);

        int centerId = planetPair.getTwo().isMoon() ? planetPair.getTwo().getMoonOf() : -1;
        Point3D center;
        if (centerId != -1) {
            Coordinate centerC = galaxyView.getSave().getGameState().getPlanets().getPlanets().get(centerId).getCoordinate();
            center = new Point3D(centerC.getX(), centerC.getY(), 0);
        } else {
            center = Point3D.ZERO;
        }

        Circle orbit = new Circle();
        orbit.setCenterX(center.getX());
        orbit.setCenterY(center.getY());
        orbit.setRadius(p.distance(center));
        orbit.setFill(null);
        orbit.setStroke(color);
        orbit.setStrokeWidth(0.3);

        return orbit;
    }

    private double getPlanetVisualSize(IntObjectPair<Planet> planetPair) {
        double scale = 1D / 30;

        String pC = planetPair.getTwo().getPlanetClass();
        String pE = planetPair.getTwo().getEntityName();
        if (pE == null) {
            pE = "";
        }

        double entityScale;
        if (pC.equals("pc_t_star")) {
            entityScale = 30;
        } else if (pC.endsWith("_star") || pC.equals("pc_black_hole") || pC.equals("pc_pulsar")) {
            entityScale = 20;
        } else if (pC.equals("pc_gas_giant") || pC.equals("pc_crystal_asteroid")) {
            entityScale = 14;
        } else if (pC.equals("pc_asteroid") || pC.equals("pc_ice_asteroid") || pC.equals("pc_crystal_asteroid_2")) {
            entityScale = 1.5;
        } else if (pC.equals("pc_cybrex") || pC.startsWith("pc_ringworld_") || pC.startsWith("pc_habitat")) {
            entityScale = 1;
        } else {
            entityScale = PLANET_STANDARD_SCALE;
        }

        double assetScale;
        if (pE.equals("asteroid_01_entity") || pC.contains("_asteroid") /*|| pC.equals("pc_asteroid")*/) {
            assetScale = 30;
        } /*else if (pE.equals("black_hole_entity")) {
            assetScale = 20;
        }*/ else if (pE.equals("pulsar_outbursts_entity")) {
            assetScale = 2;
        } else if (pC.equals("pc_b_star")) {
            assetScale = 1.8;
        } else if (pC.equals("pc_a_star")) {
            assetScale = 1.6;
        } else if (pC.equals("pc_f_star_class_star_entity")) {
            assetScale = 1.5;
        } else if (pE.equals("neutron_outbursts_entity")) {
            assetScale = 1.4;
        } else if (pC.equals("pc_g_star") || pE.equals("neutron_star_entity")) {
            assetScale = 1.3;
        } else if (pC.equals("pc_k_star") || pC.equals("pc_gas_giant")) {
            assetScale = 1.2;
        } else if (pE.equals("asteroid_entity") || pE.equals("meteor_entity") /*|| pC.equals("pc_asteroid")*/) {
            assetScale = 0.5;
        } else {
            assetScale = 1;
        }

        double size = planetPair.getTwo().getPlanetSize();

        return entityScale * assetScale * size * scale / 2D;
    }
}
