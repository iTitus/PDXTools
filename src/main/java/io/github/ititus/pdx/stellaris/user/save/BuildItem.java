package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class BuildItem {

    private final int shipDesign, pop;
    private final String type, starbaseBuilding, starbaseLevel, starbaseModule;

    public BuildItem(PdxScriptObject o) {
        this.type = o.getString("type");
        this.shipDesign = o.getInt("ship_design", -1);
        this.pop = o.getInt("pop", -1);
        this.starbaseBuilding = o.getString("starbase_building");
        this.starbaseLevel = o.getString("starbase_level");
        this.starbaseModule = o.getString("starbase_module");
    }

    public BuildItem(int shipDesign, int pop, String type, String starbaseBuilding, String starbaseLevel, String starbaseModule) {
        this.shipDesign = shipDesign;
        this.pop = pop;
        this.type = type;
        this.starbaseBuilding = starbaseBuilding;
        this.starbaseLevel = starbaseLevel;
        this.starbaseModule = starbaseModule;
    }

    public int getShipDesign() {
        return shipDesign;
    }

    public int getPop() {
        return pop;
    }

    public String getType() {
        return type;
    }

    public String getStarbaseBuilding() {
        return starbaseBuilding;
    }

    public String getStarbaseLevel() {
        return starbaseLevel;
    }

    public String getStarbaseModule() {
        return starbaseModule;
    }
}
