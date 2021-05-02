package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Entry {

    public final String key;
    public final Object value;

    public Entry(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.key = o.getString("key");
        this.value = o.get("value").expectValue().getValue();
    }
}
