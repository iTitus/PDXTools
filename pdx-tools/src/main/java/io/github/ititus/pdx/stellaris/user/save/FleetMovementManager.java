package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;

public class FleetMovementManager {

    public final int timeSinceLastPathUpdate;
    public final int ftlWindup;
    public final int ftlTotalWindup;
    public final String state;
    public final ImmutableDoubleList customFormation;
    public final FormationType formation;
    public final Coordinate coordinate;
    public final Coordinate targetCoordinate;
    public final FleetMovementTarget target;
    public final FleetMovementPath path;
    public final FleetMovementOrbit orbit;
    public final FtlJump lastFtlJump;

    public FleetMovementManager(PdxScriptObject o) {
        this.formation = o.getObjectAs("formation", FormationType::new);
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.target = o.getObjectAs("target", FleetMovementTarget::new);
        this.targetCoordinate = o.getObjectAs("target_coordinate", Coordinate::new);
        this.timeSinceLastPathUpdate = o.getInt("time_since_last_path_update", -1);
        this.state = o.getString("state");
        this.ftlWindup = o.getInt("ftl_windup", -1);
        this.path = o.getObjectAs("path", FleetMovementPath::new);
        this.orbit = o.getObjectAs("orbit", FleetMovementOrbit::new);
        this.lastFtlJump = o.getObjectAs("last_ftl_jump", FtlJump::new);
        this.ftlTotalWindup = o.getInt("ftl_total_windup", -1);
        this.customFormation = o.getListAsEmptyOrDoubleList("custom_formation");
    }
}
