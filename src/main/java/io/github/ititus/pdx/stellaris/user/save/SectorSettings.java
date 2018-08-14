package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class SectorSettings {

    private final boolean spaceConstruction, tileResources, redevelopment, robotic, colonize;

    public SectorSettings(PdxScriptObject o) {
        this.spaceConstruction = o.getBoolean("space_construction");
        this.tileResources = o.getBoolean("tile_resources");
        this.redevelopment = o.getBoolean("redevelopment");
        this.robotic = o.getBoolean("robotic");
        this.colonize = o.getBoolean("colonize");
    }

    public SectorSettings(boolean spaceConstruction, boolean tileResources, boolean redevelopment, boolean robotic, boolean colonize) {
        this.spaceConstruction = spaceConstruction;
        this.tileResources = tileResources;
        this.redevelopment = redevelopment;
        this.robotic = robotic;
        this.colonize = colonize;
    }

    public boolean isSpaceConstruction() {
        return spaceConstruction;
    }

    public boolean isTileResources() {
        return tileResources;
    }

    public boolean isRedevelopment() {
        return redevelopment;
    }

    public boolean isRobotic() {
        return robotic;
    }

    public boolean isColonize() {
        return colonize;
    }
}
