package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class PoI {

    private final boolean hasLocation;
    private final String id, name, desc, eventChain, location;
    private final Scope scope;

    public PoI(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.id = o.getString("id");
        this.name = o.getString("name");
        this.desc = o.getString("desc");
        this.eventChain = o.getString("event_chain");
        this.location = o.getString("location");
        this.scope = o.getObject("scope").getAs(Scope::new);
        this.hasLocation = o.getBoolean("has_location");
    }

    public PoI(boolean hasLocation, String id, String name, String desc, String eventChain, String location,
               Scope scope) {
        this.hasLocation = hasLocation;
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.eventChain = eventChain;
        this.location = location;
        this.scope = scope;
    }

    public boolean isHasLocation() {
        return hasLocation;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getEventChain() {
        return eventChain;
    }

    public String getLocation() {
        return location;
    }

    public Scope getScope() {
        return scope;
    }
}
