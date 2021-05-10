package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetMovementPathNode {

    public final String ftl;
    public final Coordinate coordinate;

    public FleetMovementPathNode(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.ftl = o.getString("ftl");
    }
}
