package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class ParameterData {

    private final int id;
    private final String type;

    public ParameterData(PdxScriptObject o) {
        this.type = o.getString("type");
        this.id = o.getInt("id");
    }

    public ParameterData(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
