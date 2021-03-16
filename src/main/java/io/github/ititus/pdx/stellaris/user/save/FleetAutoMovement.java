package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetAutoMovement {

    public final String type;
    public final int autoMoveTarget;
    public final boolean clearOnNewOrders;
    public final boolean clearAutoMoveOnArrival;
    public final boolean destroyOnArrival;
    public final String arrivalEffect;
    public final boolean hasArrived;

    public FleetAutoMovement(PdxScriptObject o) {
        this.type = o.getString("type");
        this.autoMoveTarget = o.getInt("auto_move_target");
        this.clearOnNewOrders = o.getBoolean("clear_on_new_orders", false);
        this.clearAutoMoveOnArrival = o.getBoolean("clear_auto_move_on_arrival", false);
        this.destroyOnArrival = o.getBoolean("destroy_on_arrival", false);
        this.arrivalEffect = o.getString("arrival_effect", null);
        this.hasArrived = o.getBoolean("has_arrived", false);
    }
}
