package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetStats {

    public final FleetCombatStats combatStats;

    public FleetStats(PdxScriptObject o) {
        this.combatStats = o.getObjectAs("combat_stats", FleetCombatStats::new);
    }
}
