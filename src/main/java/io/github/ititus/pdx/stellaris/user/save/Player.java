package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Player {

    public final int country;
    public final String name;

    public Player(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
        this.country = o.getInt("country");
    }
}
