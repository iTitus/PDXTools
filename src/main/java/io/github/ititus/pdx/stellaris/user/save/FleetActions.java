package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetActions {

    public final FleetActionRepeat repeat;

    public FleetActions(PdxScriptObject o) {
        this.repeat = o.getObjectAsNullOr("repeat", FleetActionRepeat::new);
    }
}
