package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetMovementTarget {

    private final Coordinate coordinate;
    private final Property target;

    public FleetMovementTarget(PdxScriptObject o) {
        PdxScriptObject o1 = o.getObject("coordinate");
        this.coordinate = o1 != null ? o1.getAs(Coordinate::new) : null;
        o1 = o.getObject("target");
        this.target = o1 != null ? o1.getAs(Property::new) : null;
    }

    public FleetMovementTarget(Coordinate coordinate, Property target) {
        this.coordinate = coordinate;
        this.target = target;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Property getTarget() {
        return target;
    }
}
