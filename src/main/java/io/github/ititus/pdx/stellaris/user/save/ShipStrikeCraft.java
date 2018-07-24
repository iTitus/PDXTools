package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class ShipStrikeCraft {

    private final double count, launchTime;
    private final String template, componentSlot;

    public ShipStrikeCraft(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.template = o.getString("template");
        this.componentSlot = o.getString("component_slot");
        this.count = o.getDouble("count");
        this.launchTime = o.getDouble("launch_time");
    }

    public ShipStrikeCraft(double count, double launchTime, String template, String componentSlot) {
        this.count = count;
        this.launchTime = launchTime;
        this.template = template;
        this.componentSlot = componentSlot;
    }

    public double getCount() {
        return count;
    }

    public double getLaunchTime() {
        return launchTime;
    }

    public String getTemplate() {
        return template;
    }

    public String getComponentSlot() {
        return componentSlot;
    }
}
