package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Date;

public class FleetMovementPath {

    private final Date date;
    private final ImmutableList<FleetMovementPathNode> nodes;

    public FleetMovementPath(PdxScriptObject o) {
        this.nodes = o.getImplicitList("node").getAsList(FleetMovementPathNode::new);
        this.date = o.getDate("date");
    }

    public FleetMovementPath(Date date, ImmutableList<FleetMovementPathNode> nodes) {
        this.date = date;
        this.nodes = nodes;
    }

    public Date getDate() {
        return date;
    }

    public ImmutableList<FleetMovementPathNode> getNodes() {
        return nodes;
    }
}
