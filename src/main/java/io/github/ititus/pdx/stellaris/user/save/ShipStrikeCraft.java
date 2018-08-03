package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipStrikeCraft)) {
            return false;
        }
        ShipStrikeCraft that = (ShipStrikeCraft) o;
        return Double.compare(that.count, count) == 0 && Double.compare(that.launchTime, launchTime) == 0 && Objects.equals(template, that.template) && Objects.equals(componentSlot, that.componentSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, launchTime, template, componentSlot);
    }
}
