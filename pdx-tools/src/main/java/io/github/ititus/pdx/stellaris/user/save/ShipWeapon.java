package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class ShipWeapon {

    public final int index;
    public final String template;
    public final String componentSlot;
    public final Property target;
    public final double cooldown;
    public final int shotsFired;

    public ShipWeapon(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.index = o.getInt("index");
        this.template = o.getString("template");
        this.componentSlot = o.getString("component_slot");
        this.target = o.getObjectAsNullOr("target", Property::new);
        this.cooldown = o.getDouble("cooldown", 0);
        this.shotsFired = o.getInt("shots_fired", 0);
    }
}
