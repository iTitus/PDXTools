package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class GlobalShipDesign {

    private final int shipDesign;
    private final String name;

    public GlobalShipDesign(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        this.shipDesign = o.getInt("ship_design");
    }

    public GlobalShipDesign(int shipDesign, String name) {
        this.shipDesign = shipDesign;
        this.name = name;
    }

    public int getShipDesign() {
        return shipDesign;
    }

    public String getName() {
        return name;
    }
}
