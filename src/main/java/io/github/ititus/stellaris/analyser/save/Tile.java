package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

public class Tile {

    private final boolean active;
    private final int pop;
    private final Resources resources;
    private final Building building;
    private final String prevBuilding, deposit;


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

    public Tile(boolean active, int pop, Resources resources, Building building, String prevBuilding, String deposit) {
        this.active = active;
        this.pop = pop;
        this.resources = resources;
        this.building = building;
        this.prevBuilding = prevBuilding;
        this.deposit = deposit;
    }

    public boolean isActive() {
        return active;
    }

    public int getPop() {
        return pop;
    }

    public Resources getResources() {
        return resources;
    }

    public Building getBuilding() {
        return building;
    }

    public String getPrevBuilding() {
        return prevBuilding;
    }

    public String getDeposit() {
        return deposit;
    }
}
