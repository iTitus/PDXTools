package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class Cluster {

    public final String id;
    public final Coordinate position;
    public final double radius;
    public final ImmutableIntList objects;

    public Cluster(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.id = o.getString("id");
        this.position = o.getObjectAs("position", Coordinate::new);
        this.radius = o.getDouble("radius");
        this.objects = o.getListAsIntList("objects");
    }
}
