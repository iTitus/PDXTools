package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.map.ImmutableMap;

import java.time.LocalDate;

public class Fleet {

    public final boolean actionInitialized, civilian, station, mia;
    public final int aggroRangeMeasureFrom, fleetTemplate, owner, previousOwner, orderId;
    public final double aggroRange, hitPoints;
    public final String name, groundSupportStance, fleetStance, miaType;
    public final LocalDate returnDate;
    public final ImmutableIntList ships, incomingMerges;
    public final FleetActions actions;
    public final FleetCombat combat;
    public final FleetAutoMovement autoMovement;
    public final FleetStats fleetStats;
    public final FleetOrders currentOrder, orders;
    public final Coordinate miaFrom;
    public final ImmutableMap<String, FlagData> flags;
    public final FleetMovementManager movementManager;
    public final FleetMission mission;
    public final FleetSettings settings;

    public Fleet(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
        this.aggroRange = o.getDouble("aggro_range", -1);
        this.aggroRangeMeasureFrom = o.getInt("aggro_range_measure_from", -1);
        this.fleetTemplate = o.getInt("fleet_template", -1);
        this.actionInitialized = o.getBoolean("action_initialized", false);
        this.actions = o.getObjectAsNullOr("actions", FleetActions::new);
        this.ships = o.getListAsIntList("ships");
        this.combat = o.getObjectAs("combat", FleetCombat::new);
        this.autoMovement = o.getObjectAsNullOr("auto_movement", FleetAutoMovement::new);
        this.fleetStats = o.getObjectAs("fleet_stats", FleetStats::new);
        this.currentOrder = o.getObjectAsNullOr("current_order", FleetOrders::new);
        this.owner = o.getInt("owner");
        this.previousOwner = o.getInt("previous_owner", -1);
        this.civilian = o.getBoolean("civilian", false);
        this.station = o.getBoolean("station", false);
        this.groundSupportStance = o.getString("ground_support_stance");
        this.fleetStance = o.getString("fleet_stance", null);
        this.mia = o.getBoolean("mia", false);
        this.miaType = o.getString("mia_type", null);
        this.returnDate = o.getDate("return_date", null);
        this.miaFrom = o.getObjectAsNullOr("mia_from", Coordinate::new);
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
        this.movementManager = o.getObjectAs("movement_manager", FleetMovementManager::new);
        this.mission = o.getObjectAsNullOr("mission", FleetMission::new);
        this.orderId = o.getInt("order_id", -1);
        this.orders = o.getObjectAsNullOr("order", FleetOrders::new);
        this.incomingMerges = o.getListAsEmptyOrIntList("incoming_merges");
        this.settings = o.getObjectAsNullOr("settings", FleetSettings::new);
        this.hitPoints = o.getDouble("hit_points");
    }
}
