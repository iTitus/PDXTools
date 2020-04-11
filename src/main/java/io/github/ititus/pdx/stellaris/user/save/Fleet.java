package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.time.LocalDate;

public class Fleet {

    private final boolean actionInitialized, civilian, station, mia;
    private final int aggroRangeMeasureFrom, fleetTemplate, owner, previousOwner, orderId;
    private final double aggroRange, hitPoints;
    private final String name, groundSupportStance, fleetStance, miaType;
    private final LocalDate returnDate;
    private final ImmutableIntList ships, incomingMerges;
    private final FleetActions actions;
    private final FleetCombat combat;
    private final FleetAutoMovement autoMovement;
    private final FleetStats fleetStats;
    private final FleetOrders currentOrder, orders;
    private final Coordinate miaFrom;
    private final Flags flags;
    private final FleetMovementManager movementManager;
    private final FleetMission mission;
    private final FleetSettings settings;

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
        this.ships = o.getList("ships").getAsIntList();
        this.combat = o.getObject("combat").getAs(FleetCombat::of);
        o1 = o.getObject("auto_movement");
        this.autoMovement = o1 != null ? o1.getAs(FleetAutoMovement::new) : null;
        this.fleetStats = o.getObject("fleet_stats").getAs(FleetStats::of);
        o1 = o.getObject("current_order");
        this.currentOrder = o1 != null ? o1.getAs(FleetOrders::new) : null;
        this.owner = o.getInt("owner");
        this.previousOwner = o.getInt("previous_owner", -1);
        this.civilian = o.getBoolean("civilian");
        this.station = o.getBoolean("station");
        this.groundSupportStance = o.getString("ground_support_stance");
        this.fleetStance = o.getString("fleet_stance");
        this.mia = o.getBoolean("mia");
        this.miaType = o.getString("mia_type");
        this.returnDate = o.getDate("return_date");
        o1 = o.getObject("mia_from");
        this.miaFrom = o1 != null ? o1.getAs(Coordinate::of) : null;
        o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::of) : null;
        this.movementManager = o.getObject("movement_manager").getAs(FleetMovementManager::new);
        o1 = o.getObject("mission");
        this.mission = o1 != null ? o1.getAs(FleetMission::new) : null;
        this.orderId = o.getInt("order_id", -1);
        o1 = o.getObject("order");
        this.orders = o1 != null ? o1.getAs(FleetOrders::new) : null;
        PdxScriptList l = o.getList("incoming_merges");
        this.incomingMerges = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        o1 = o.getObject("settings");
        this.settings = o1 != null ? o1.getAs(FleetSettings::new) : null;
        this.hitPoints = o.getDouble("hit_points");
    }

    public Fleet(boolean actionInitialized, boolean civilian, boolean station, boolean mia, int aggroRangeMeasureFrom
            , int fleetTemplate, int owner, int previousOwner, int orderId, double aggroRange, double hitPoints,
                 String name, String groundSupportStance, String fleetStance, String miaType, LocalDate returnDate,
                 ImmutableIntList ships, ImmutableIntList incomingMerges, FleetActions actions, FleetCombat combat,
                 FleetAutoMovement autoMovement, FleetStats fleetStats, FleetOrders currentOrder, FleetOrders orders,
                 Coordinate miaFrom, Flags flags, FleetMovementManager movementManager, FleetMission mission,
                 FleetSettings settings) {
        this.actionInitialized = actionInitialized;
        this.civilian = civilian;
        this.station = station;
        this.mia = mia;
        this.aggroRangeMeasureFrom = aggroRangeMeasureFrom;
        this.fleetTemplate = fleetTemplate;
        this.owner = owner;
        this.previousOwner = previousOwner;
        this.orderId = orderId;
        this.aggroRange = aggroRange;
        this.hitPoints = hitPoints;
        this.name = name;
        this.groundSupportStance = groundSupportStance;
        this.fleetStance = fleetStance;
        this.miaType = miaType;
        this.returnDate = returnDate;
        this.ships = ships;
        this.incomingMerges = incomingMerges;
        this.actions = actions;
        this.combat = combat;
        this.autoMovement = autoMovement;
        this.fleetStats = fleetStats;
        this.currentOrder = currentOrder;
        this.orders = orders;
        this.miaFrom = miaFrom;
        this.flags = flags;
        this.movementManager = movementManager;
        this.mission = mission;
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

    public boolean isMia() {
        return mia;
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

    public String getMiaType() {
        return miaType;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public ImmutableIntList getShips() {
        return ships;
    }

    public ImmutableIntList getIncomingMerges() {
        return incomingMerges;
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

    public FleetOrders getOrders() {
        return orders;
    }

    public Coordinate getMiaFrom() {
        return miaFrom;
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

    public FleetSettings getSettings() {
        return settings;
    }
}
