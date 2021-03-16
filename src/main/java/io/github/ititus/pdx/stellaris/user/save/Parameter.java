package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Parameter {

    public final String key;
    public final ParameterData data;

    public Parameter(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.key = o.getString("key");
        this.data = o.getObjectAs("data", ParameterData::new);
    }
}
