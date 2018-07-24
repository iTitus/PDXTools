package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class AuraId {

    private final int priority;
    private final String id;

    public AuraId(PdxScriptObject o) {
        this.id = o.getString("id");
        this.priority = o.getInt("priority");
    }

    public AuraId(int priority, String id) {
        this.priority = priority;
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public String getId() {
        return id;
    }
}
