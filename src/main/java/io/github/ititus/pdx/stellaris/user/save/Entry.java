package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Entry {

    public final String key;
    public final String value;

    public Entry(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.key = o.getString("key");
        this.value = o.getString("value");
    }
}
