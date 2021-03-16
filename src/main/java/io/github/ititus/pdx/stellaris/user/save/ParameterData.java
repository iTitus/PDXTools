package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class ParameterData {

    public final int id;
    public final String type;

    public ParameterData(PdxScriptObject o) {
        this.type = o.getString("type");
        this.id = o.getInt("id");
    }
}
