package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetMovementPathNode {

    private final String ftl;
    private final Coordinate coordinate;

    public FleetMovementPathNode(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException();
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.coordinate = o.getObject("coordinate").getAs(Coordinate::of);
        this.ftl = o.getString("ftl");
    }

    public FleetMovementPathNode(String ftl, Coordinate coordinate) {
        this.ftl = ftl;
        this.coordinate = coordinate;
    }

    public String getFtl() {
        return ftl;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
