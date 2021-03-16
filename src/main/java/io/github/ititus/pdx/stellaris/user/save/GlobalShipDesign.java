package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class GlobalShipDesign {

    public final int shipDesign;
    public final String name;

    public GlobalShipDesign(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
        this.shipDesign = o.getInt("ship_design");
    }
}
