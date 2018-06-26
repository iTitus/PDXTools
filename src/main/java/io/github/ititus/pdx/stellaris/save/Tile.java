package io.github.ititus.pdx.stellaris.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Tile {

    private final boolean active;
    private final int pop;
    private final String prevBuilding, deposit;
    private final Resources resources;
    private final Building building;

    public Tile(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.active = o.getBoolean("active");
        this.pop = o.getInt("pop", -1);
        PdxScriptObject o1 = o.getObject("resources");
        this.resources = o1 != null ? o1.getAs(Resources::new) : new Resources();
        o1 = o.getObject("building");
        this.building = o1 != null ? o1.getAs(Building::new) : null;
        this.prevBuilding = o.getString("prev_building");
        this.deposit = o.getString("deposit");
    }

    public Tile(boolean active, int pop, String prevBuilding, String deposit, Resources resources, Building building) {
        this.active = active;
        this.pop = pop;
        this.prevBuilding = prevBuilding;
        this.deposit = deposit;
        this.resources = resources;
        this.building = building;
    }

    public boolean isActive() {
        return active;
    }

    public int getPop() {
        return pop;
    }

    public String getPrevBuilding() {
        return prevBuilding;
    }

    public String getDeposit() {
        return deposit;
    }

    public Resources getResources() {
        return resources;
    }

    public Building getBuilding() {
        return building;
    }
}
