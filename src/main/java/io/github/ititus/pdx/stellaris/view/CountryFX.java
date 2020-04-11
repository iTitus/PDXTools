package io.github.ititus.pdx.stellaris.view;

import io.github.ititus.pdx.stellaris.user.save.Coordinate;
import io.github.ititus.pdx.stellaris.user.save.Country;
import io.github.ititus.pdx.stellaris.user.save.GalacticObject;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.api.tuple.primitive.IntObjectPair;

public class CountryFX extends Group {

    private final GalaxyView galaxyView;
    private final IntObjectPair<Country> countryPair;

    public CountryFX(GalaxyView galaxyView, IntObjectPair<Country> countryPair) {
        this.galaxyView = galaxyView;
        this.countryPair = countryPair;

        ImmutableIntObjectMap<GalacticObject> systems =
                galaxyView.getSave().getGameState().getGalacticObjects().getGalacticObjects();
        ImmutableIntObjectMap<GalacticObject> countrySystems =
                systems.select((systemId, system) -> galaxyView.getOwnerId(systemId, system) == countryPair.getOne());

        // TODO: proper graphics with proper colors
        int hash = countryPair.getTwo().getFlag().hashCode();
        hash = (hash ^ (hash >>> 8)) & 0xFFFFFF;

        Color fillColor = Color.rgb((hash >>> 16) & 0xFF, (hash >>> 8) & 0xFF, hash & 0xFF, 0.25);
        ImmutableBag<Circle> circles = countrySystems.collect(system -> {
            Coordinate coord = system.getCoordinate();

            Circle c = new Circle();
            c.setCenterX(coord.getX());
            c.setCenterY(coord.getY());
            c.setRadius(10);
            c.setStroke(null);
            c.setFill(fillColor);
            return c;
        });

        Platform.runLater(() -> getChildren().addAll(circles.castToCollection()));
    }
}
