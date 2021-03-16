package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class SavedEventTarget {

    public final int id;
    public final String type, name;

    public SavedEventTarget(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.type = o.getString("type");
        this.id = o.getInt("id");
        this.name = o.getString("name");
    }
}
