package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetMission {

    private final String mission;

    public FleetMission(PdxScriptObject o) {
        this.mission = o.getString("mission");
    }

    public FleetMission(String mission) {
        this.mission = mission;
    }

    public String getMission() {
        return mission;
    }
}
