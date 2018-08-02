package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;

public class Properties {

    private final double scale;
    private final ImmutableDoubleList offset;
    private final Coordinate coordinate;
    private final Property attach, entityFaceObject;

    public Properties(PdxScriptObject o) {
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::of);
        this.attach = o.getObject("attach").getAs(Property::new);
        this.offset = o.getList("offset").getAsDoubleList();
        this.scale = o.getDouble("scale");
        this.entityFaceObject = o.getObject("entity_face_object").getAs(Property::new);
    }

    public Properties(double scale, ImmutableDoubleList offset, Coordinate coordinate, Property attach, Property entityFaceObject) {
        this.scale = scale;
        this.offset = offset;
        this.coordinate = coordinate;
        this.attach = attach;
        this.entityFaceObject = entityFaceObject;
    }

    public double getScale() {
        return scale;
    }

    public ImmutableDoubleList getOffset() {
        return offset;
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
