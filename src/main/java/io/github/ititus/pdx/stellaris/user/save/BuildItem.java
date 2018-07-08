package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class BuildItem {

    private final String type;
    private final int shipDesign;

    public BuildItem(PdxScriptObject o) {
        this.type = o.getString("type");
        this.shipDesign = o.getInt("ship_design");
    }

    public BuildItem(String type, int shipDesign) {
        this.type = type;
        this.shipDesign = shipDesign;
    }

    public String getType() {
        return type;
    }

    public int getShipDesign() {
        return shipDesign;
    }
}
