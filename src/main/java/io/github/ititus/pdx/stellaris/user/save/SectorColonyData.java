package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class SectorColonyData {

    private final int planetConstruction, colony, popColony;

    public SectorColonyData(PdxScriptObject o) {
        this.planetConstruction = o.getUnsignedInt("planet_construction");
        this.colony = o.getUnsignedInt("colony");
        this.popColony = o.getUnsignedInt("pop_colony");
    }

    public SectorColonyData(int planetConstruction, int colony, int popColony) {
        this.planetConstruction = planetConstruction;
        this.colony = colony;
        this.popColony = popColony;
    }

    public int getPlanetConstruction() {
        return planetConstruction;
    }

    public int getColony() {
        return colony;
    }

    public int getPopColony() {
        return popColony;
    }
}
