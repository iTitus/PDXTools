package io.github.ititus.pdx.stellaris.view;

import io.github.ititus.pdx.stellaris.user.save.Country;
import io.github.ititus.pdx.stellaris.user.save.GalacticObject;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

import java.util.ArrayList;
import java.util.List;

public class CountryFX extends Group {

    private final GalaxyView galaxyView;
    private final Country country;

    public CountryFX(GalaxyView galaxyView, Country country) {
        this.galaxyView = galaxyView;
        this.country = country;

        ImmutableIntObjectMap<GalacticObject> systems = galaxyView.getSave().gameState.galacticObjects;
        ImmutableIntObjectMap<GalacticObject> countrySystems = systems.select((systemId, system) -> galaxyView.getOwnerId(systemId, system) == country.id);

        // TODO: proper graphics with proper colors
        int hash = country.flag.hashCode();
        hash = (hash ^ (hash >>> 8)) & 0xFFFFFF;
        Color fillColor = Color.rgb((hash >>> 16) & 0xFF, (hash >>> 8) & 0xFF, hash & 0xFF, 0.25);

        List<Circle> circles = new ArrayList<>(countrySystems.size());
        countrySystems.forEach(system -> {
            Circle c = new Circle();
            c.setCenterX(system.coordinate.x);
            c.setCenterY(system.coordinate.y);
            c.setRadius(10);
            c.setStroke(null);
            c.setFill(fillColor);
            circles.add(c);
        });

        Platform.runLater(() -> getChildren().addAll(circles));
    }
}
