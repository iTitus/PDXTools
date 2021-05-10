package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class ShipStrikeCraft {

    public final double count, launchTime;
    public final String template, componentSlot;

    public ShipStrikeCraft(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.template = o.getString("template");
        this.componentSlot = o.getString("component_slot");
        this.count = o.getDouble("count");
        this.launchTime = o.getDouble("launch_time");
    }
}
