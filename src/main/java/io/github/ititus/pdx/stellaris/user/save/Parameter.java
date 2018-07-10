package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Parameter {

    private final String key;
    private final ParameterData data;

    public Parameter(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.key = o.getString("key");
        this.data = o.getObject("data").getAs(ParameterData::new);
    }

    public Parameter(String key, ParameterData data) {
        this.key = key;
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public ParameterData getData() {
        return data;
    }
}
