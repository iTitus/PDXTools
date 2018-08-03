package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Objects;

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
        this.cost = o.getObject("cost").getAs(Resources::of);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuildQueueItem)) {
            return false;
        }
        BuildQueueItem that = (BuildQueueItem) o;
        return planet == that.planet && slot == that.slot && id == that.id && sector == that.sector && Double.compare(that.progress, progress) == 0 && Objects.equals(item, that.item) && Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planet, slot, id, sector, progress, item, cost);
    }
}
