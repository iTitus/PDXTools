package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class Bypass {

    public final String type;
    public final boolean active;
    public final int linkedTo;
    public final ImmutableIntList connections;
    public final ImmutableIntList activeConnections;
    public final Property owner;

    public Bypass(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.type = o.getString("type");
        this.active = o.getBoolean("active");
        this.linkedTo = o.getInt("linked_to", -1);
        this.connections = o.getListAsEmptyOrIntList("connections");
        this.activeConnections = o.getListAsEmptyOrIntList("active_connections");
        this.owner = o.getObjectAs("owner", Property::new);
    }
}
