package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetActions {

    private final FleetActionRepeat repeat;

    public FleetActions(PdxScriptObject o) {
        this.repeat = o.getObject("repeat").getAs(FleetActionRepeat::new);
    }

    public FleetActions(FleetActionRepeat repeat) {
        this.repeat = repeat;
    }

    public FleetActionRepeat getRepeat() {
        return repeat;
    }
}
