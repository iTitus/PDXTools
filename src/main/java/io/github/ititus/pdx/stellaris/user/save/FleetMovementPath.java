package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.ViewableList;
import io.github.ititus.pdx.util.collection.ViewableUnmodifiableArrayList;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class FleetMovementPath {

    private final Date date;
    private final ViewableList<FleetMovementPathNode> nodes;

    public FleetMovementPath(PdxScriptObject o) {
        this.nodes = o.getImplicitList("node").getAsList(FleetMovementPathNode::new);
        this.date = o.getDate("date");
    }

    public FleetMovementPath(Date date, Collection<FleetMovementPathNode> nodes) {
        this.date = date;
        this.nodes = new ViewableUnmodifiableArrayList<>(nodes);
    }

    public Date getDate() {
        return date;
    }

    public List<FleetMovementPathNode> getNodes() {
        return nodes.getView();
    }
}
