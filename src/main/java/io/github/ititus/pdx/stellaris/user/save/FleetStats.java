package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetStats {

    private final FleetCombatStats combatStats;

    public FleetStats(PdxScriptObject o) {
        this.combatStats = o.getObject("combat_stats").getAs(FleetCombatStats::new);
    }

    public FleetStats(FleetCombatStats combatStats) {
        this.combatStats = combatStats;
    }

    public FleetCombatStats getCombatStats() {
        return combatStats;
    }
}
