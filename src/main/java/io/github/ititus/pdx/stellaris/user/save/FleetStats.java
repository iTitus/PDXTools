package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;

import java.util.Objects;

public class FleetStats {

    private static final Deduplicator<FleetStats> DEDUPLICATOR =
            new Deduplicator<>(s -> s.getCombatStats().getEnemy().isEmpty() && s.getCombatStats().getDamageIncoming().isEmpty() && s.getCombatStats().getDamageOutgoing().isEmpty() && s.getCombatStats().getHitRatioIncoming().isEmpty() && s.getCombatStats().getHitRatioOutgoing().isEmpty());

    private final FleetCombatStats combatStats;

    private FleetStats(PdxScriptObject o) {
        this.combatStats = o.getObject("combat_stats").getAs(FleetCombatStats::of);
    }

    private FleetStats(FleetCombatStats combatStats) {
        this.combatStats = combatStats;
    }

    public static FleetStats of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new FleetStats(o));
    }

    public static FleetStats of(FleetCombatStats combatStats) {
        return DEDUPLICATOR.deduplicate(new FleetStats(combatStats));
    }

    public FleetCombatStats getCombatStats() {
        return combatStats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FleetStats)) {
            return false;
        }
        return Objects.equals(combatStats, ((FleetStats) o).combatStats);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(combatStats);
    }
}
