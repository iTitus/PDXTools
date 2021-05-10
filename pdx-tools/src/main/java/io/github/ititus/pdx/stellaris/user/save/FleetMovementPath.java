package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.time.LocalDate;

public class FleetMovementPath {

    public final LocalDate date;
    public final ImmutableList<FleetMovementPathNode> nodes;

    public FleetMovementPath(PdxScriptObject o) {
        this.nodes = o.getImplicitListAsList("node", FleetMovementPathNode::new);
        this.date = o.getDate("date");
    }
}
