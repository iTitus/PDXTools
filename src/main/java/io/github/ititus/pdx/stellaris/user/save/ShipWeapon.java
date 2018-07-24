package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class ShipWeapon {

    private final int index, shotsFired;
    private final double cooldown;
    private final String template, componentSlot;
    private final Property target;

    public ShipWeapon(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.index = o.getInt("index");
        this.template = o.getString("template");
        this.componentSlot = o.getString("component_slot");
        PdxScriptObject o1 = o.getObject("target");
        this.target = o1 != null ? o1.getAs(Property::new) : null;
        this.cooldown = o.getDouble("cooldown");
        this.shotsFired = o.getInt("shots_fired");
    }

    public ShipWeapon(int index, int shotsFired, double cooldown, String template, String componentSlot, Property target) {
        this.index = index;
        this.shotsFired = shotsFired;
        this.cooldown = cooldown;
        this.template = template;
        this.componentSlot = componentSlot;
        this.target = target;
    }

    public int getIndex() {
        return index;
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public double getCooldown() {
        return cooldown;
    }

    public String getTemplate() {
        return template;
    }

    public String getComponentSlot() {
        return componentSlot;
    }

    public Property getTarget() {
        return target;
    }
}
