package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.*;
import io.github.ititus.pdx.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Tile {

    private final boolean active;
    private final int pop;
    private final String prevBuilding, blocker;
    private final List<String> deposits;
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
        this.blocker = o.getString("blocker");
        IPdxScript s1 = o.get("deposit");
        if (s1 instanceof PdxScriptList) {
            this.deposits = ((PdxScriptList) s1).getAsStringList();
            o.use("deposit", PdxConstants.LIST);
        } else if (s1 == null) {
            this.deposits = CollectionUtil.listOf();
        } else {
            this.deposits = CollectionUtil.listOf((String) ((PdxScriptValue) s1).getValue());
            o.use("deposit", PdxConstants.STRING);
        }
    }

    public Tile(boolean active, int pop, String prevBuilding, String blocker, Collection<String> deposits, Resources resources, Building building) {
        this.active = active;
        this.pop = pop;
        this.prevBuilding = prevBuilding;
        this.blocker = blocker;
        this.deposits = new ArrayList<>(deposits);
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

    public String getBlocker() {
        return blocker;
    }

    public List<String> getDeposits() {
        return new ArrayList<>(deposits);
    }

    public Resources getResources() {
        return resources;
    }

    public Building getBuilding() {
        return building;
    }
}
