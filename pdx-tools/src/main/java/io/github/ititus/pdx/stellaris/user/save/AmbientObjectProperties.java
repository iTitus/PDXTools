package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;

public class AmbientObjectProperties {

    public final Coordinate coordinate;
    public final Property attach;
    public final ImmutableDoubleList offset;
    public final double scale;
    public final Property entityFaceObject;

    public AmbientObjectProperties(PdxScriptObject o) {
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.attach = o.getObjectAs("attach", Property::new);
        this.offset = o.getListAsDoubleList("offset");
        this.scale = o.getDouble("scale");
        this.entityFaceObject = o.getObjectAs("entity_face_object", Property::new);
    }
}
