package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Objects;

public class FleetCombatDamage {


    private final int fleetIndex;
    private final double damageArmor, baseDamageArmor, damageHitpoints, baseDamageHitpoints;
    private final String template;

    public FleetCombatDamage(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.template = o.getString("template");
        this.fleetIndex = o.getInt("fleet_index");
        this.damageArmor = o.getDouble("damage_armor");
        this.baseDamageArmor = o.getDouble("base_damage_armor");
        this.damageHitpoints = o.getDouble("damage_hitpoints");
        this.baseDamageHitpoints = o.getDouble("base_damage_hitpoints");
    }

    public FleetCombatDamage(int fleetIndex, double damageArmor, double baseDamageArmor, double damageHitpoints,
                             double baseDamageHitpoints, String template) {
        this.fleetIndex = fleetIndex;
        this.damageArmor = damageArmor;
        this.baseDamageArmor = baseDamageArmor;
        this.damageHitpoints = damageHitpoints;
        this.baseDamageHitpoints = baseDamageHitpoints;
        this.template = template;
    }

    public int getFleetIndex() {
        return fleetIndex;
    }

    public double getDamageArmor() {
        return damageArmor;
    }

    public double getBaseDamageArmor() {
        return baseDamageArmor;
    }

    public double getDamageHitpoints() {
        return damageHitpoints;
    }

    public double getBaseDamageHitpoints() {
        return baseDamageHitpoints;
    }

    public String getTemplate() {
        return template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FleetCombatDamage)) {
            return false;
        }
        FleetCombatDamage that = (FleetCombatDamage) o;
        return fleetIndex == that.fleetIndex && Double.compare(that.damageArmor, damageArmor) == 0 && Double.compare(that.baseDamageArmor, baseDamageArmor) == 0 && Double.compare(that.damageHitpoints, damageHitpoints) == 0 && Double.compare(that.baseDamageHitpoints, baseDamageHitpoints) == 0 && Objects.equals(template, that.template);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fleetIndex, damageArmor, baseDamageArmor, damageHitpoints, baseDamageHitpoints, template);
    }
}
