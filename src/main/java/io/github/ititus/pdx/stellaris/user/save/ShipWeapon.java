package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;

import java.util.Objects;

public class ShipWeapon {

    private static final Deduplicator<ShipWeapon> DEDUPLICATOR = new Deduplicator<>(w -> w.getTarget() == null && w.getCooldown() == 0 && w.getShotsFired() == 0);

    private final int index, shotsFired;
    private final double cooldown;
    private final String template, componentSlot;
    private final Property target;

    private ShipWeapon(IPdxScript s) {
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

    private ShipWeapon(int index, int shotsFired, double cooldown, String template, String componentSlot, Property target) {
        this.index = index;
        this.shotsFired = shotsFired;
        this.cooldown = cooldown;
        this.template = template;
        this.componentSlot = componentSlot;
        this.target = target;
    }

    public static ShipWeapon of(IPdxScript s) {
        return DEDUPLICATOR.deduplicate(new ShipWeapon(s));
    }

    public static ShipWeapon of(int index, int shotsFired, double cooldown, String template, String componentSlot, Property target) {
        return DEDUPLICATOR.deduplicate(new ShipWeapon(index, shotsFired, cooldown, template, componentSlot, target));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipWeapon)) {
            return false;
        }
        ShipWeapon that = (ShipWeapon) o;
        return index == that.index && shotsFired == that.shotsFired && Double.compare(that.cooldown, cooldown) == 0 && Objects.equals(template, that.template) && Objects.equals(componentSlot, that.componentSlot) && Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, shotsFired, cooldown, template, componentSlot, target);
    }
}
