package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetMission {

    public final String mission;

    public FleetMission(PdxScriptObject o) {
        this.mission = o.getString("mission");
    }
}
