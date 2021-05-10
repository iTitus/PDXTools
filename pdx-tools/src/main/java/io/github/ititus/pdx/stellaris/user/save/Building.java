package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Building {

    public final String type;
    public final int position;
    public final boolean ruined;

    public Building(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.type = o.getString("type");
        this.position = o.getInt("position");
        this.ruined = o.getBoolean("ruined", false);
    }
}
