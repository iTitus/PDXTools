package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.collection.ViewableList;

import java.util.Collections;

public class Fleet {

    private final String name;
    private final double aggroRange;
    private final int aggroRangeMeasureFrom;
    private final int fleetTemplate;
    private final boolean actionInitialized;
    // private final FleetActions actions;
    private final ViewableList<Integer> ships;
    // private final FleetCombat combat;
    // private final FleetAutoMovement autoMovement;
    // private final FleetStats fleetStats;
    // private final FleetOrders currentOrder;
    private final int owner;
    private final int previousOwner;
    private final boolean civilian;
    private final boolean station;
    private final String groundSupportStance;
    private final String fleetStance;
    private final Flags flags;
    // private final FleetMovementManager movementManager;
    // private final FleetMission mission;
    private final int orderId;
    // private final FleetOrders order;
    private final long sector;
    private final ViewableList<Integer> incomingMerges;
    private final boolean friendsShouldFollow;
    // private final Settings settings;
    private final double hitPoints;

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
        this.ships = o.getList("ships").getAsIntegerList();
        this.owner = o.getInt("owner");
        this.previousOwner = o.getInt("previous_owner", -1);
        this.civilian = o.getBoolean("civilian");
        this.station = o.getBoolean("station");
        this.groundSupportStance = o.getString("ground_support_stance");
        this.fleetStance = o.getString("fleet_stance");
        PdxScriptObject o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::new) : new Flags(Collections.emptyMap(), Collections.emptyMap());
        this.orderId = o.getInt("order_id", -1);
        this.sector = o.getLong("sector", -1);
        PdxScriptList l = o.getList("incoming_merges");
        this.incomingMerges = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
        this.friendsShouldFollow = o.getBoolean("friends_should_follow");
        this.hitPoints = o.getDouble("hit_points");
    }
}
