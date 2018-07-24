package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Bypass {

    private final boolean active;
    private final int linkedTo;
    private final String type;
    private final Property owner;

    public Bypass(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.type = o.getString("type");
        this.active = o.getBoolean("active");
        this.linkedTo = o.getInt("linked_to", -1);
        this.owner = o.getObject("owner").getAs(Property::new);
    }

    public Bypass(boolean active, int linkedTo, String type, Property owner) {
        this.active = active;
        this.linkedTo = linkedTo;
        this.type = type;
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

    public Property getOwner() {
        return owner;
    }
}
