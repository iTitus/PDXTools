package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class LeaderMandate {

    private final String type;

    public LeaderMandate(PdxScriptObject o) {
        this.type = o.getString("type");
    }

    public LeaderMandate(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
