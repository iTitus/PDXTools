package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Property {

    public final int type;
    public final int id;

    public Property(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.type = o.getInt("type");
        this.id = o.getUnsignedInt("id");
    }
}
