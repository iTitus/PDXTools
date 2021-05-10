package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class PoI {

    public final String id;
    public final String name;
    public final String desc;
    public final String eventChain;
    public final String location;
    public final Scope scope;
    public final boolean hasLocation;

    public PoI(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.id = o.getString("id");
        this.name = o.getString("name");
        this.desc = o.getString("desc");
        this.eventChain = o.getString("event_chain");
        this.location = o.getString("location");
        this.scope = o.getObjectAs("scope", Scope::new);
        this.hasLocation = o.getBoolean("has_location");
    }
}
