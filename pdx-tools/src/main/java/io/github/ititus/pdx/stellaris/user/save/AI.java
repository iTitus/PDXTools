package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;

import java.time.LocalDate;

public class AI {

    public final boolean initialized;
    public final boolean colonize;
    public final ImmutableDoubleList budget;
    public final ImmutableList<AIStrategy> strategies;
    public final LocalDate prepareWarDate;
    public final boolean war;
    public final int robotColonies;
    public final int robotColoniesWithFreeBuildings;
    public final int target;
    public final ImmutableList<AIAttitude> attitudes;
    public final int randomSeed;
    public final int randomCount;
    public final int syncedRandomSeed;
    public final int syncedRandomCount;
    public final ImmutableList<DiploAction> lastDiploActions;

    public AI(PdxScriptObject o) {
        this.initialized = o.getBoolean("initialized");
        this.colonize = o.getBoolean("colonize", false);
        this.budget = o.getListAsDoubleList("budget");
        this.strategies = o.getImplicitListAsList("strategy", AIStrategy::new);
        this.prepareWarDate = o.getDate("prepare_war_date");
        this.war = o.getBoolean("war", false);
        this.robotColonies = o.getInt("robot_colonies");
        this.robotColoniesWithFreeBuildings = o.getInt("robot_colonies_with_free_buildings");
        this.target = o.getInt("target", -1);
        this.attitudes = o.getListAsEmptyOrList("attitude", AIAttitude::new);
        this.randomSeed = o.getUnsignedInt("random_seed");
        this.randomCount = o.getInt("random_count");
        this.syncedRandomSeed = o.getUnsignedInt("synced_random_seed");
        this.syncedRandomCount = o.getUnsignedInt("synced_random_count");
        this.lastDiploActions = o.getListAsEmptyOrList("last_diplo_actions", DiploAction::new);
    }
}
