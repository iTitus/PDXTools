package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class BuildQueueItem {

    private final int planet, slot, id;
    private final long sector;
    private final double progress;
    private final BuildItem item;
    private final Resources cost;

    public BuildQueueItem(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.item = o.getObject("item").getAs(BuildItem::new);
        this.progress = o.getDouble("progress");
        this.planet = o.getInt("planet");
        this.slot = o.getInt("slot", -1);
        this.sector = o.getLong("sector");
        this.cost = o.getObject("cost").getAs(Resources::new);
        this.id = o.getInt("id");
    }

    public BuildQueueItem(int planet, int slot, int id, long sector, double progress, BuildItem item, Resources cost) {
        this.planet = planet;
        this.slot = slot;
        this.id = id;
        this.sector = sector;
        this.progress = progress;
        this.item = item;
        this.cost = cost;
    }

    public int getPlanet() {
        return planet;
    }

    public int getSlot() {
        return slot;
    }

    public int getId() {
        return id;
    }

    public long getSector() {
        return sector;
    }

    public double getProgress() {
        return progress;
    }

    public BuildItem getItem() {
        return item;
    }

    public Resources getCost() {
        return cost;
    }
}
