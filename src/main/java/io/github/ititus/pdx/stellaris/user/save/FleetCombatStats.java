package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.factory.Lists;

import java.time.LocalDate;
import java.util.Objects;

public class FleetCombatStats {

    private static final Deduplicator<FleetCombatStats> DEDUPLICATOR = new Deduplicator<>(s -> s.getEnemy().isEmpty() && s.getDamageIncoming().isEmpty() && s.getDamageOutgoing().isEmpty() && s.getHitRatioIncoming().isEmpty() && s.getHitRatioOutgoing().isEmpty());

    private final ImmutableList<FleetCombatEnemy> enemy;
    private final ImmutableList<FleetCombatDamage> damageIncoming, damageOutgoing;
    private final ImmutableList<FleetCombatHitRatio> hitRatioIncoming, hitRatioOutgoing;

    private FleetCombatStats(PdxScriptObject o) {
        PdxScriptObject o1 = o.getObject("fleet");
        if (o1 == null || o1.size() > 0) {
            // TODO: there are some additional fields here (eg. ship_size_count_lost)
            // throw new RuntimeException("Unexpected fleet: " + o1);
        }
        LocalDate date = o.getDate("date");
        if (!PdxConstants.NULL_DATE.equals(date)) {
            // TODO: not always null date
            // throw new RuntimeException("Unexpected date: " + date);
        }
        PdxScriptList l = o.getList("enemy");
        this.enemy = l != null ? l.getAsList(FleetCombatEnemy::new) : Lists.immutable.empty();
        l = o.getList("damage_incoming");
        this.damageIncoming = l != null ? l.getAsList(FleetCombatDamage::new) : Lists.immutable.empty();
        l = o.getList("damage_outgoing");
        this.damageOutgoing = l != null ? l.getAsList(FleetCombatDamage::new) : Lists.immutable.empty();
        l = o.getList("hit_ratio_incoming");
        this.hitRatioIncoming = l != null ? l.getAsList(FleetCombatHitRatio::new) : Lists.immutable.empty();
        l = o.getList("hit_ratio_outgoing");
        this.hitRatioOutgoing = l != null ? l.getAsList(FleetCombatHitRatio::new) : Lists.immutable.empty();
    }

    private FleetCombatStats(ImmutableList<FleetCombatEnemy> enemy, ImmutableList<FleetCombatDamage> damageIncoming, ImmutableList<FleetCombatDamage> damageOutgoing, ImmutableList<FleetCombatHitRatio> hitRatioIncoming, ImmutableList<FleetCombatHitRatio> hitRatioOutgoing) {
        this.enemy = enemy;
        this.damageIncoming = damageIncoming;
        this.damageOutgoing = damageOutgoing;
        this.hitRatioIncoming = hitRatioIncoming;
        this.hitRatioOutgoing = hitRatioOutgoing;
    }

    public static FleetCombatStats of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new FleetCombatStats(o));
    }

    public static FleetCombatStats of(ImmutableList<FleetCombatEnemy> enemy, ImmutableList<FleetCombatDamage> damageIncoming, ImmutableList<FleetCombatDamage> damageOutgoing, ImmutableList<FleetCombatHitRatio> hitRatioIncoming, ImmutableList<FleetCombatHitRatio> hitRatioOutgoing) {
        return DEDUPLICATOR.deduplicate(new FleetCombatStats(enemy, damageIncoming, damageOutgoing, hitRatioIncoming, hitRatioOutgoing));
    }

    public ImmutableList<FleetCombatEnemy> getEnemy() {
        return enemy;
    }

    public ImmutableList<FleetCombatDamage> getDamageIncoming() {
        return damageIncoming;
    }

    public ImmutableList<FleetCombatDamage> getDamageOutgoing() {
        return damageOutgoing;
    }

    public ImmutableList<FleetCombatHitRatio> getHitRatioIncoming() {
        return hitRatioIncoming;
    }

    public ImmutableList<FleetCombatHitRatio> getHitRatioOutgoing() {
        return hitRatioOutgoing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FleetCombatStats)) {
            return false;
        }
        FleetCombatStats that = (FleetCombatStats) o;
        return Objects.equals(enemy, that.enemy) && Objects.equals(damageIncoming, that.damageIncoming) && Objects.equals(damageOutgoing, that.damageOutgoing) && Objects.equals(hitRatioIncoming, that.hitRatioIncoming) && Objects.equals(hitRatioOutgoing, that.hitRatioOutgoing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enemy, damageIncoming, damageOutgoing, hitRatioIncoming, hitRatioOutgoing);
    }
}
