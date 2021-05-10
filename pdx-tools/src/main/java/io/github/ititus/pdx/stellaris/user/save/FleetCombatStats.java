package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.time.LocalDate;

public class FleetCombatStats {

    public final FleetCombatatant fleet;
    public final LocalDate date;
    public final ImmutableList<FleetCombatatant> enemy;
    public final ImmutableList<FleetCombatDamage> damageIncoming;
    public final ImmutableList<FleetCombatDamage> damageOutgoing;
    public final ImmutableList<FleetCombatHitRatio> hitRatioIncoming;
    public final ImmutableList<FleetCombatHitRatio> hitRatioOutgoing;

    public FleetCombatStats(PdxScriptObject o) {
        this.fleet = o.getObjectAs("fleet", FleetCombatatant::new);
        this.date = o.getDate("date");
        this.enemy = o.getListAsEmptyOrList("enemy", FleetCombatatant::new);
        this.damageIncoming = o.getListAsEmptyOrList("damage_incoming", FleetCombatDamage::new);
        this.damageOutgoing = o.getListAsEmptyOrList("damage_outgoing", FleetCombatDamage::new);
        this.hitRatioIncoming = o.getListAsEmptyOrList("hit_ratio_incoming", FleetCombatHitRatio::new);
        this.hitRatioOutgoing = o.getListAsEmptyOrList("hit_ratio_outgoing", FleetCombatHitRatio::new);
    }
}
