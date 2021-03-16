package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetMovementTarget {

    public final Coordinate coordinate;
    public final Property target;

    public FleetMovementTarget(PdxScriptObject o) {
        this.coordinate = o.getObjectAsNullOr("coordinate", Coordinate::new);
        this.target = o.getObjectAsNullOr("target", Property::new);
    }
}
