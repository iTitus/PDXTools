package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;
import org.eclipse.collections.impl.factory.Lists;

import java.util.Date;

public class AI {

    private final boolean initialized, initializedBudget, colonize, navyDepleted, deficitSpending, building, canBuildRobots;
    private final int target, randomSeed, randomCount, syncedRandomSeed, syncedRandomCount;
    private final double robotColonies, robotColoniesWithFreeBuildings;
    private final Date prepareWarDate;
    private final ImmutableDoubleList budget, minerals, maintenance, maintenanceMinerals, energy, influence;
    private final ImmutableList<AIStrategy> strategies;
    private final ImmutableList<AIAttitude> attitudes;
    private final Budget coreSectorBudget;

    public AI(PdxScriptObject o) {
        this.initialized = o.getBoolean("initialized");
        this.initializedBudget = o.getBoolean("initialized_budget");
        this.colonize = o.getBoolean("colonize");
        this.navyDepleted = o.getBoolean("navy_depleted");
        this.budget = o.getList("budget").getAsDoubleList();
        this.strategies = o.getImplicitList("strategy").getAsList(AIStrategy::new);
        this.prepareWarDate = o.getDate("prepare_war_date");
        this.deficitSpending = o.getBoolean("deficit_spending");
        this.building = o.getBoolean("building", true);
        this.canBuildRobots = o.getBoolean("can_build_robots");
        this.robotColonies = o.getDouble("robot_colonies");
        this.robotColoniesWithFreeBuildings = o.getDouble("robot_colonies_with_free_buildings");
        this.target = o.getInt("target", -1);
        this.minerals = o.getList("minerals").getAsDoubleList();
        this.maintenance = o.getList("maintenance").getAsDoubleList();
        this.maintenanceMinerals = o.getList("maintenance_minerals").getAsDoubleList();
        this.energy = o.getList("energy").getAsDoubleList();
        this.influence = o.getList("influence").getAsDoubleList();
        this.coreSectorBudget = o.getObject("core_sector_budget").getAs(Budget::new);
        PdxScriptList l = o.getList("attitude");
        this.attitudes = l != null ? l.getAsList(AIAttitude::new) : Lists.immutable.empty();
        this.randomSeed = o.getUnsignedInt("random_seed");
        this.randomCount = o.getInt("random_count");
        this.syncedRandomSeed = o.getUnsignedInt("synced_random_seed");
        this.syncedRandomCount = o.getUnsignedInt("synced_random_count");
    }

    public AI(boolean initialized, boolean initializedBudget, boolean colonize, boolean navyDepleted, boolean deficitSpending, boolean building, boolean canBuildRobots, int target, int randomSeed, int randomCount, int syncedRandomSeed, int syncedRandomCount, double robotColonies, double robotColoniesWithFreeBuildings, Date prepareWarDate, ImmutableDoubleList budget, ImmutableDoubleList minerals, ImmutableDoubleList maintenance, ImmutableDoubleList maintenanceMinerals, ImmutableDoubleList energy, ImmutableDoubleList influence, ImmutableList<AIStrategy> strategies, ImmutableList<AIAttitude> attitudes, Budget coreSectorBudget) {
        this.initialized = initialized;
        this.initializedBudget = initializedBudget;
        this.colonize = colonize;
        this.navyDepleted = navyDepleted;
        this.deficitSpending = deficitSpending;
        this.building = building;
        this.canBuildRobots = canBuildRobots;
        this.target = target;
        this.randomSeed = randomSeed;
        this.randomCount = randomCount;
        this.syncedRandomSeed = syncedRandomSeed;
        this.syncedRandomCount = syncedRandomCount;
        this.robotColonies = robotColonies;
        this.robotColoniesWithFreeBuildings = robotColoniesWithFreeBuildings;
        this.prepareWarDate = prepareWarDate;
        this.budget = budget;
        this.minerals = minerals;
        this.maintenance = maintenance;
        this.maintenanceMinerals = maintenanceMinerals;
        this.energy = energy;
        this.influence = influence;
        this.strategies = strategies;
        this.attitudes = attitudes;
        this.coreSectorBudget = coreSectorBudget;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean isInitializedBudget() {
        return initializedBudget;
    }

    public boolean isColonize() {
        return colonize;
    }

    public boolean isNavyDepleted() {
        return navyDepleted;
    }

    public boolean isDeficitSpending() {
        return deficitSpending;
    }

    public boolean isBuilding() {
        return building;
    }

    public boolean isCanBuildRobots() {
        return canBuildRobots;
    }

    public int getTarget() {
        return target;
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

    public ImmutableDoubleList getMinerals() {
        return minerals;
    }

    public ImmutableDoubleList getMaintenance() {
        return maintenance;
    }

    public ImmutableDoubleList getMaintenanceMinerals() {
        return maintenanceMinerals;
    }

    public ImmutableDoubleList getEnergy() {
        return energy;
    }

    public ImmutableDoubleList getInfluence() {
        return influence;
    }

    public ImmutableList<AIStrategy> getStrategies() {
        return strategies;
    }

    public ImmutableList<AIAttitude> getAttitudes() {
        return attitudes;
    }

    public Budget getCoreSectorBudget() {
        return coreSectorBudget;
    }
}
