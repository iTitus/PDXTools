package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Type {

    private final String type;

    public Type(PdxScriptObject o) {
        this.type = o.getString("type");
    }

    public Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
