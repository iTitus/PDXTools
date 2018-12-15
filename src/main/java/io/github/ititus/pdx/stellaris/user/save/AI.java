package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;
import org.eclipse.collections.impl.factory.Lists;

import java.util.Date;

public class AI {

    private final boolean initialized, colonize;
    private final int randomSeed, randomCount, syncedRandomSeed, syncedRandomCount;
    private final double robotColonies, robotColoniesWithFreeBuildings;
    private final Date prepareWarDate;
    private final ImmutableDoubleList budget;
    private final ImmutableList<AIStrategy> strategies;
    private final ImmutableList<AIAttitude> attitudes;

    public AI(PdxScriptObject o) {
        this.initialized = o.getBoolean("initialized");
        this.colonize = o.getBoolean("colonize");
        this.budget = o.getList("budget").getAsDoubleList();
        this.strategies = o.getImplicitList("strategy").getAsList(AIStrategy::new);
        this.prepareWarDate = o.getDate("prepare_war_date");
        this.robotColonies = o.getDouble("robot_colonies");
        this.robotColoniesWithFreeBuildings = o.getDouble("robot_colonies_with_free_buildings");
        PdxScriptList l = o.getList("attitude");
        this.attitudes = l != null ? l.getAsList(AIAttitude::new) : Lists.immutable.empty();
        this.randomSeed = o.getUnsignedInt("random_seed");
        this.randomCount = o.getInt("random_count");
        this.syncedRandomSeed = o.getUnsignedInt("synced_random_seed");
        this.syncedRandomCount = o.getUnsignedInt("synced_random_count");
    }

    public AI(boolean initialized, boolean colonize, int randomSeed, int randomCount, int syncedRandomSeed, int syncedRandomCount, double robotColonies, double robotColoniesWithFreeBuildings, Date prepareWarDate, ImmutableDoubleList budget, ImmutableList<AIStrategy> strategies, ImmutableList<AIAttitude> attitudes) {
        this.initialized = initialized;
        this.colonize = colonize;
        this.randomSeed = randomSeed;
        this.randomCount = randomCount;
        this.syncedRandomSeed = syncedRandomSeed;
        this.syncedRandomCount = syncedRandomCount;
        this.robotColonies = robotColonies;
        this.robotColoniesWithFreeBuildings = robotColoniesWithFreeBuildings;
        this.prepareWarDate = prepareWarDate;
        this.budget = budget;
        this.strategies = strategies;
        this.attitudes = attitudes;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean isColonize() {
        return colonize;
    }

    public int getRandomSeed() {
        return randomSeed;
    }

    public int getRandomCount() {
        return randomCount;
    }

    public int getSyncedRandomSeed() {
        return syncedRandomSeed;
    }

    public int getSyncedRandomCount() {
        return syncedRandomCount;
    }

    public double getRobotColonies() {
        return robotColonies;
    }

    public double getRobotColoniesWithFreeBuildings() {
        return robotColoniesWithFreeBuildings;
    }

    public Date getPrepareWarDate() {
        return prepareWarDate;
    }

    public ImmutableDoubleList getBudget() {
        return budget;
    }

    public ImmutableList<AIStrategy> getStrategies() {
        return strategies;
    }

    public ImmutableList<AIAttitude> getAttitudes() {
        return attitudes;
    }
}
