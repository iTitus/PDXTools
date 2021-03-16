package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class Nebula {

    public final Coordinate coordinate;
    public final String name;
    public final double radius;
    public final ImmutableIntList galacticObjects;

    public Nebula(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.name = o.getString("name");
        this.radius = o.getDouble("radius");
        this.galacticObjects = o.getImplicitListAsIntList("galactic_object");
    }
}
