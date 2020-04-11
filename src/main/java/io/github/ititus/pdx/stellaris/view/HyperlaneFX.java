package io.github.ititus.pdx.stellaris.view;

import io.github.ititus.pdx.stellaris.user.save.Coordinate;
import io.github.ititus.pdx.stellaris.user.save.GalacticObject;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class HyperlaneFX extends Group {

    private final GalaxyView galaxyView;
    private final VisualHyperlane hyperlane;
    private final Cylinder hyperlaneCylinder;

    public HyperlaneFX(GalaxyView galaxyView, VisualHyperlane hyperlane) {
        this.galaxyView = galaxyView;
        this.hyperlane = hyperlane;

        ImmutableIntObjectMap<GalacticObject> galacticObjects =
                galaxyView.getSave().getGameState().getGalacticObjects().getGalacticObjects();
        GalacticObject fromGO = galacticObjects.get(hyperlane.getFrom());
        GalacticObject toGO = galacticObjects.get(hyperlane.getTo());

        Coordinate fromC = fromGO.getCoordinate();
        Coordinate toC = toGO.getCoordinate();

        Point3D from = new Point3D(fromC.getX(), fromC.getY(), 0);
        Point3D to = new Point3D(toC.getX(), toC.getY(), 0);
        Point3D length = to.subtract(from);
        Point3D middle = from.add(length.multiply(0.5));

        this.hyperlaneCylinder = new Cylinder(0.5, length.magnitude());
        this.hyperlaneCylinder.setMaterial(hyperlane.getType().getMaterial());

        Translate translate = new Translate(middle.getX(), middle.getY(), middle.getZ());
        double angle = length.angle(Rotate.Y_AXIS);
        if (length.getX() > 0) {
            angle *= -1;
        }
        Rotate rZ = new Rotate(angle, Rotate.Z_AXIS);
        this.hyperlaneCylinder.getTransforms().addAll(translate, rZ);

        this.getChildren().add(this.hyperlaneCylinder);
    }
}
