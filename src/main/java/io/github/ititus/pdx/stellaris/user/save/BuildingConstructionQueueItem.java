package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class BuildingConstructionQueueItem {

    private final boolean start;
    private final int time;
    private final long tile, sector;
    private final double progress;
    private final String type;
    private final Building building;
    private final Resources resources;

    public BuildingConstructionQueueItem(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.tile = o.getLong("tile");
        this.time = o.getInt("time");
        this.type = o.getString("type");
        PdxScriptObject o1 = o.getObject("building");
        this.building = o1 != null ? o1.getAs(Building::of) : null;
        this.start = o.getBoolean("start");
        this.sector = o.getLong("sector");
        this.resources = o.getObject("resources").getAs(Resources::of);
        this.progress = o.getDouble("progress");
    }

    public BuildingConstructionQueueItem(boolean start, int time, long tile, long sector, double progress, String type, Building building, Resources resources) {
        this.start = start;
        this.time = time;
        this.tile = tile;
        this.sector = sector;
        this.progress = progress;
        this.type = type;
        this.building = building;
        this.resources = resources;
    }

    public boolean isStart() {
        return start;
    }

    public int getTime() {
        return time;
    }

    public long getTile() {
        return tile;
    }

    public long getSector() {
        return sector;
    }

    public double getProgress() {
        return progress;
    }

    public String getType() {
        return type;
    }

    public Building getBuilding() {
        return building;
    }

    public Resources getResources() {
        return resources;
    }
}
