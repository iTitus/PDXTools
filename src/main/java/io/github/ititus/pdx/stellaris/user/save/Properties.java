package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Arrays;

public class Properties {

    private final double scale;
    private final double[] offset;
    private final Coordinate coordinate;
    private final Property attach, entityFaceObject;

    public Properties(PdxScriptObject o) {
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::new);
        this.attach = o.getObject("attach").getAs(Property::new);
        this.offset = o.getList("offset").getAsDoubleArray();
        this.scale = o.getDouble("scale");
        this.entityFaceObject = o.getObject("entity_face_object").getAs(Property::new);
    }

    public Properties(double scale, double[] offset, Coordinate coordinate, Property attach, Property entityFaceObject) {
        this.scale = scale;
        this.offset = Arrays.copyOf(offset, offset.length);
        this.coordinate = coordinate;
        this.attach = attach;
        this.entityFaceObject = entityFaceObject;
    }

    public double getScale() {
        return scale;
    }

    public double[] getOffset() {
        return Arrays.copyOf(offset, offset.length);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Property getAttach() {
        return attach;
    }

    public Property getEntityFaceObject() {
        return entityFaceObject;
    }
}
