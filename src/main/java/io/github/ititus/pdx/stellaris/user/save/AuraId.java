package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class AuraId {

    public final int priority;
    public final String id;

    public AuraId(PdxScriptObject o) {
        this.id = o.getString("id");
        this.priority = o.getInt("priority");
    }
}
