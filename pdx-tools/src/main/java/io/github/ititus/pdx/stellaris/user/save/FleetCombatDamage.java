package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetCombatDamage {

    public final String template;
    public final int fleetIndex;
    public final double damageArmor;
    public final double baseDamageArmor;
    public final double damageHitpoints;
    public final double baseDamageHitpoints;

    public FleetCombatDamage(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.template = o.getString("template");
        this.fleetIndex = o.getInt("fleet_index");
        this.damageArmor = o.getDouble("damage_armor", 0);
        this.baseDamageArmor = o.getDouble("base_damage_armor", 0);
        this.damageHitpoints = o.getDouble("damage_hitpoints", 0);
        this.baseDamageHitpoints = o.getDouble("base_damage_hitpoints", 0);
    }
}
