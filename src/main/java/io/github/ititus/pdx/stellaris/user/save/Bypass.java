package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.primitive.IntLists;

public class Bypass {

    private final boolean active;
    private final int linkedTo;
    private final String type;
    private final ImmutableIntList connections, activeConnections;
    private final Property owner;

    public Bypass(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.type = o.getString("type");
        this.active = o.getBoolean("active");
        this.linkedTo = o.getInt("linked_to", -1);
        PdxScriptList l = o.getList("connections");
        this.connections = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("active_connections");
        this.activeConnections = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        this.owner = o.getObject("owner").getAs(Property::new);
    }

    public Bypass(boolean active, int linkedTo, String type, ImmutableIntList connections,
                  ImmutableIntList activeConnections, Property owner) {
        this.active = active;
        this.linkedTo = linkedTo;
        this.type = type;
        this.connections = connections;
        this.activeConnections = activeConnections;
        this.owner = owner;
    }

    public boolean isActive() {
        return active;
    }

    public int getLinkedTo() {
        return linkedTo;
    }

    public String getType() {
        return type;
    }

    public ImmutableIntList getConnections() {
        return connections;
    }

    public ImmutableIntList getActiveConnections() {
        return activeConnections;
    }

    public Property getOwner() {
        return owner;
    }
}
