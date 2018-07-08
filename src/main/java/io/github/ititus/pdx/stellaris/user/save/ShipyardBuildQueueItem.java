package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class ShipyardBuildQueueItem {

    private final BuildItem item;
    private final double progress;
    private final int planet, id;
    private final long sector;
    private final Resources cost;

    public ShipyardBuildQueueItem(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.item = o.getObject("item").getAs(BuildItem::new);
        this.progress = o.getDouble("progress");
        this.planet = o.getInt("planet");
        this.sector = o.getLong("sector");
        this.cost = o.getObject("cost").getAs(Resources::new);
        this.id = o.getInt("id");
    }

    public ShipyardBuildQueueItem(BuildItem item, double progress, int planet, int id, long sector, Resources cost) {
        this.item = item;
        this.progress = progress;
        this.planet = planet;
        this.id = id;
        this.sector = sector;
        this.cost = cost;
    }

    public BuildItem getItem() {
        return item;
    }

    public double getProgress() {
        return progress;
    }

    public int getPlanet() {
        return planet;
    }

    public int getId() {
        return id;
    }

    public long getSector() {
        return sector;
    }

    public Resources getCost() {
        return cost;
    }
}
