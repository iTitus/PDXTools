package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class BuildingConstructionQueueItem {

    private final long tile, sector;
    private final int time;
    private final String type;
    private final Building building;
    private final boolean start;
    private final Resources resources;
    private final double progress;

    public BuildingConstructionQueueItem(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.tile = o.getLong("tile");
        this.time = o.getInt("time");
        this.type = o.getString("type");
        if (this.type != null && this.type.equals("building")) {
            this.building = o.getObject("building").getAs(Building::new);
        } else {
            this.building = null;
        }
        this.start = o.getBoolean("start");
        this.sector = o.getLong("sector");
        this.resources = o.getObject("resources").getAs(Resources::new);
        this.progress = o.getDouble("progress");
    }

    public BuildingConstructionQueueItem(long tile, long sector, int time, String type, Building building, boolean start, Resources resources, double progress) {
        this.tile = tile;
        this.sector = sector;
        this.time = time;
        this.type = type;
        this.building = building;
        this.start = start;
        this.resources = resources;
        this.progress = progress;
    }

    public long getTile() {
        return tile;
    }

    public long getSector() {
        return sector;
    }

    public int getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public Building getBuilding() {
        return building;
    }

    public boolean isStart() {
        return start;
    }

    public Resources getResources() {
        return resources;
    }

    public double getProgress() {
        return progress;
    }
}
