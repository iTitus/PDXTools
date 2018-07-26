package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.collection.ViewableList;
import io.github.ititus.pdx.util.collection.ViewableUnmodifiableArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Fleet {

    private final boolean actionInitialized, civilian, station, friendsShouldFollow;
    private final int aggroRangeMeasureFrom, fleetTemplate, owner, previousOwner, orderId;
    private final long sector;
    private final double aggroRange, hitPoints;
    private final String name, groundSupportStance, fleetStance;
    private final ViewableList<Integer> ships, incomingMerges;
    private final FleetActions actions;
    private final FleetCombat combat;
    private final FleetAutoMovement autoMovement;
    private final FleetStats fleetStats;
    private final FleetOrders currentOrder;
    private final Flags flags;
    private final FleetMovementManager movementManager;
    private final FleetMission mission;
    private final FleetOrders orders;
    private final Settings settings;

    public Fleet(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        this.aggroRange = o.getDouble("aggro_range", -1);
        this.aggroRangeMeasureFrom = o.getInt("aggro_range_measure_from", -1);
        this.fleetTemplate = o.getInt("fleet_template", -1);
        this.actionInitialized = o.getBoolean("action_initialized");
        PdxScriptObject o1 = o.getObject("actions");
        this.actions = o1 != null ? o1.getAs(FleetActions::new) : null;
        this.ships = o.getList("ships").getAsIntegerList();
        this.combat = o.getObject("combat").getAs(FleetCombat::new);
        o1 = o.getObject("auto_movement");
        this.autoMovement = o1 != null ? o1.getAs(FleetAutoMovement::new) : null;
        this.fleetStats = o.getObject("fleet_stats").getAs(FleetStats::new);
        o1 = o.getObject("current_order");
        this.currentOrder = o1 != null ? o1.getAs(FleetOrders::new) : null;
        this.owner = o.getInt("owner");
        this.previousOwner = o.getInt("previous_owner", -1);
        this.civilian = o.getBoolean("civilian");
        this.station = o.getBoolean("station");
        this.groundSupportStance = o.getString("ground_support_stance");
        this.fleetStance = o.getString("fleet_stance");
        o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::new) : new Flags(Collections.emptyMap(), Collections.emptyMap());
        this.movementManager = o.getObject("movement_manager").getAs(FleetMovementManager::new);
        o1 = o.getObject("mission");
        this.mission = o1 != null ? o1.getAs(FleetMission::new) : null;
        this.orderId = o.getInt("order_id", -1);
        o1 = o.getObject("order");
        this.orders = o1 != null ? o1.getAs(FleetOrders::new) : null;
        this.sector = o.getLong("sector", -1);
        PdxScriptList l = o.getList("incoming_merges");
        this.incomingMerges = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
        this.friendsShouldFollow = o.getBoolean("friends_should_follow");
        o1 = o.getObject("settings");
        this.settings = o1 != null ? o1.getAs(Settings::new) : new Settings(Collections.emptyMap());
        this.hitPoints = o.getDouble("hit_points");
    }

    public Fleet(boolean actionInitialized, boolean civilian, boolean station, boolean friendsShouldFollow, int aggroRangeMeasureFrom, int fleetTemplate, int owner, int previousOwner, int orderId, long sector, double aggroRange, double hitPoints, String name, String groundSupportStance, String fleetStance, Collection<Integer> ships, Collection<Integer> incomingMerges, FleetActions actions, FleetCombat combat, FleetAutoMovement autoMovement, FleetStats fleetStats, FleetOrders currentOrder, Flags flags, FleetMovementManager movementManager, FleetMission mission, FleetOrders orders, Settings settings) {
        this.actionInitialized = actionInitialized;
        this.civilian = civilian;
        this.station = station;
        this.friendsShouldFollow = friendsShouldFollow;
        this.aggroRangeMeasureFrom = aggroRangeMeasureFrom;
        this.fleetTemplate = fleetTemplate;
        this.owner = owner;
        this.previousOwner = previousOwner;
        this.orderId = orderId;
        this.sector = sector;
        this.aggroRange = aggroRange;
        this.hitPoints = hitPoints;
        this.name = name;
        this.groundSupportStance = groundSupportStance;
        this.fleetStance = fleetStance;
        this.ships = new ViewableUnmodifiableArrayList<>(ships);
        this.incomingMerges = new ViewableUnmodifiableArrayList<>(incomingMerges);
        this.actions = actions;
        this.combat = combat;
        this.autoMovement = autoMovement;
        this.fleetStats = fleetStats;
        this.currentOrder = currentOrder;
        this.flags = flags;
        this.movementManager = movementManager;
        this.mission = mission;
        this.orders = orders;
        this.settings = settings;
    }

    public boolean isActionInitialized() {
        return actionInitialized;
    }

    public boolean isCivilian() {
        return civilian;
    }

    public boolean isStation() {
        return station;
    }

    public boolean isFriendsShouldFollow() {
        return friendsShouldFollow;
    }

    public int getAggroRangeMeasureFrom() {
        return aggroRangeMeasureFrom;
    }

    public int getFleetTemplate() {
        return fleetTemplate;
    }

    public int getOwner() {
        return owner;
    }

    public int getPreviousOwner() {
        return previousOwner;
    }

    public int getOrderId() {
        return orderId;
    }

    public long getSector() {
        return sector;
    }

    public double getAggroRange() {
        return aggroRange;
    }

    public double getHitPoints() {
        return hitPoints;
    }

    public String getName() {
        return name;
    }

    public String getGroundSupportStance() {
        return groundSupportStance;
    }

    public String getFleetStance() {
        return fleetStance;
    }

    public List<Integer> getShips() {
        return ships.getView();
    }

    public List<Integer> getIncomingMerges() {
        return incomingMerges.getView();
    }

    public FleetActions getActions() {
        return actions;
    }

    public FleetCombat getCombat() {
        return combat;
    }

    public FleetAutoMovement getAutoMovement() {
        return autoMovement;
    }

    public FleetStats getFleetStats() {
        return fleetStats;
    }

    public FleetOrders getCurrentOrder() {
        return currentOrder;
    }

    public Flags getFlags() {
        return flags;
    }

    public FleetMovementManager getMovementManager() {
        return movementManager;
    }

    public FleetMission getMission() {
        return mission;
    }

    public FleetOrders getOrders() {
        return orders;
    }

    public Settings getSettings() {
        return settings;
    }
}
