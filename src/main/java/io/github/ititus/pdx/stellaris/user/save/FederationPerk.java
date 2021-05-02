package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FederationPerk {

    public final int level;
    public final String type;

    public FederationPerk(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.level = o.getInt("level");
        this.type = o.getString("type");
    }
}
