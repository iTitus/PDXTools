package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class SectorPlannedProject {

    private final boolean building;
    private final int id;
    private final double energy, minerals;
    private final String target;

    public SectorPlannedProject(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.id = o.getInt("id");
        this.target = o.getString("target");
        this.building = o.getBoolean("building");
        this.energy = o.getDouble("energy");
        this.minerals = o.getDouble("minerals");
    }

    public SectorPlannedProject(boolean building, int id, double energy, double minerals, String target) {
        this.building = building;
        this.id = id;
        this.energy = energy;
        this.minerals = minerals;
        this.target = target;
    }

    public boolean isBuilding() {
        return building;
    }

    public int getId() {
        return id;
    }

    public double getEnergy() {
        return energy;
    }

    public double getMinerals() {
        return minerals;
    }

    public String getTarget() {
        return target;
    }
}
