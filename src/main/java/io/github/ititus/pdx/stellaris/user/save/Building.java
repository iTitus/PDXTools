package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Building {

    public final boolean ruined;
    public final String type;

    public Building(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.type = o.getString("type");
        this.ruined = o.getBoolean("ruined", false);
    }
}
