package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Date;

public class FleetMovementPath {

    private final ImmutableList<FleetMovementPathNode> nodes;

    public FleetMovementPath(PdxScriptObject o) {
        this.nodes = o.getImplicitList("node").getAsList(FleetMovementPathNode::new);
        Date date = o.getDate("date");
        if (!PdxConstants.NULL_DATE.equals(date)) {
            throw new RuntimeException("Unexpected date: " + date);
        }
    }

    public FleetMovementPath(ImmutableList<FleetMovementPathNode> nodes) {
        this.nodes = nodes;
    }

    public ImmutableList<FleetMovementPathNode> getNodes() {
        return nodes;
    }
}
