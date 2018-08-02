package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;

public class FleetMovementManager {

    private final int timeSinceLastPathUpdate, ftlWindup, ftlTotalWindup;
    private final String state;
    private final ImmutableDoubleList customFormation;
    private final FormationType formation;
    private final Coordinate coordinate, targetCoordinate;
    private final FleetMovementTarget target;
    private final FleetMovementPath path;
    private final FleetMovementOrbit orbit;
    private final FTLJump lastFTLJump;

    public FleetMovementManager(PdxScriptObject o) {
        this.formation = o.getObject("formation").getAs(FormationType::new);
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::of);
        this.target = o.getObject("target").getAs(FleetMovementTarget::new);
        this.targetCoordinate = o.getObject("target_coordinate").getAs(Coordinate::of);
        this.timeSinceLastPathUpdate = o.getInt("time_since_last_path_update", -1);
        this.state = o.getString("state");
        this.ftlWindup = o.getInt("ftl_windup", -1);
        this.path = o.getObject("path").getAs(FleetMovementPath::new);
        this.orbit = o.getObject("orbit").getAs(FleetMovementOrbit::new);
        this.lastFTLJump = o.getObject("last_ftl_jump").getAs(FTLJump::new);
        this.ftlTotalWindup = o.getInt("ftl_total_windup", -1);
        PdxScriptList l = o.getList("custom_formation");
        this.customFormation = l != null ? l.getAsDoubleList() : DoubleLists.immutable.empty();
    }

    public FleetMovementManager(int timeSinceLastPathUpdate, int ftlWindup, int ftlTotalWindup, String state, ImmutableDoubleList customFormation, FormationType formation, Coordinate coordinate, Coordinate targetCoordinate, FleetMovementTarget target, FleetMovementPath path, FleetMovementOrbit orbit, FTLJump lastFTLJump) {
        this.timeSinceLastPathUpdate = timeSinceLastPathUpdate;
        this.ftlWindup = ftlWindup;
        this.ftlTotalWindup = ftlTotalWindup;
        this.state = state;
        this.customFormation = customFormation;
        this.formation = formation;
        this.coordinate = coordinate;
        this.targetCoordinate = targetCoordinate;
        this.target = target;
        this.path = path;
        this.orbit = orbit;
        this.lastFTLJump = lastFTLJump;
    }

    public int getTimeSinceLastPathUpdate() {
        return timeSinceLastPathUpdate;
    }

    public int getFtlWindup() {
        return ftlWindup;
    }

    public int getFtlTotalWindup() {
        return ftlTotalWindup;
    }

    public String getState() {
        return state;
    }

    public ImmutableDoubleList getCustomFormation() {
        return customFormation;
    }

    public FormationType getFormation() {
        return formation;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Coordinate getTargetCoordinate() {
        return targetCoordinate;
    }

    public FleetMovementTarget getTarget() {
        return target;
    }

    public FleetMovementPath getPath() {
        return path;
    }

    public FleetMovementOrbit getOrbit() {
        return orbit;
    }

    public FTLJump getLastFTLJump() {
        return lastFTLJump;
    }
}
