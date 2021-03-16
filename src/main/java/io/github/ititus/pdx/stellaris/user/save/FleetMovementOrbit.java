package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetMovementOrbit {

    public final Orbitable orbitable;
    public final int index;

    public FleetMovementOrbit(PdxScriptObject o) {
        this.orbitable = o.getObjectAsNullOr("orbitable", Orbitable::new);
        this.index = o.getInt("index", -1);
    }
}
