package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetAutoMovement {

    private final boolean clearOnNewOrders, clearAutoMoveOnArrival, destroyOnArrival, hasArrived;
    private final int autoMoveTarget;
    private final String type;

    public FleetAutoMovement(PdxScriptObject o) {
        this.type = o.getString("type");
        this.autoMoveTarget = o.getInt("auto_move_target");
        this.clearOnNewOrders = o.getBoolean("clear_on_new_orders");
        this.clearAutoMoveOnArrival = o.getBoolean("clear_auto_move_on_arrival");
        this.destroyOnArrival = o.getBoolean("destroy_on_arrival");
        this.hasArrived = o.getBoolean("has_arrived");
    }

    public FleetAutoMovement(boolean clearOnNewOrders, boolean clearAutoMoveOnArrival, boolean destroyOnArrival, boolean hasArrived, int autoMoveTarget, String type) {
        this.clearOnNewOrders = clearOnNewOrders;
        this.clearAutoMoveOnArrival = clearAutoMoveOnArrival;
        this.destroyOnArrival = destroyOnArrival;
        this.hasArrived = hasArrived;
        this.autoMoveTarget = autoMoveTarget;
        this.type = type;
    }

    public boolean isClearOnNewOrders() {
        return clearOnNewOrders;
    }

    public boolean isClearAutoMoveOnArrival() {
        return clearAutoMoveOnArrival;
    }

    public boolean isDestroyOnArrival() {
        return destroyOnArrival;
    }

    public boolean isHasArrived() {
        return hasArrived;
    }

    public int getAutoMoveTarget() {
        return autoMoveTarget;
    }

    public String getType() {
        return type;
    }
}
