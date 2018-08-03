package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Objects;

public class Tile {

    private static final Deduplicator<Tile> DEDUPLICATOR = new Deduplicator<>(t -> t.getPop() == -1);

    private final boolean active;
    private final int pop;
    private final String prevBuilding, blocker;
    private final ImmutableList<String> deposits;
    private final Resources resources;
    private final Building building;

    private Tile(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.active = o.getBoolean("active");
        this.pop = o.getInt("pop", -1);
        PdxScriptObject o1 = o.getObject("resources");
        this.resources = o1 != null ? o1.getAs(Resources::of) : null;
        o1 = o.getObject("building");
        this.building = o1 != null ? o1.getAs(Building::of) : null;
        this.prevBuilding = o.getString("prev_building");
        this.blocker = o.getString("blocker");
        this.deposits = o.getImplicitList("deposit").getAsStringList();
    }

    private Tile(boolean active, int pop, String prevBuilding, String blocker, ImmutableList<String> deposits, Resources resources, Building building) {
        this.active = active;
        this.pop = pop;
        this.prevBuilding = prevBuilding;
        this.blocker = blocker;
        this.deposits = deposits;
        this.resources = resources;
        this.building = building;
    }

    public static Tile of(IPdxScript s) {
        return DEDUPLICATOR.deduplicate(new Tile(s));
    }

    public static Tile of(boolean active, int pop, String prevBuilding, String blocker, ImmutableList<String> deposits, Resources resources, Building building) {
        return DEDUPLICATOR.deduplicate(new Tile(active, pop, prevBuilding, blocker, deposits, resources, building));
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

    public ImmutableList<String> getDeposits() {
        return deposits;
    }

    public Resources getResources() {
        return resources;
    }

    public Building getBuilding() {
        return building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tile)) {
            return false;
        }
        Tile tile = (Tile) o;
        return active == tile.active && pop == tile.pop && Objects.equals(prevBuilding, tile.prevBuilding) && Objects.equals(blocker, tile.blocker) && Objects.equals(deposits, tile.deposits) && Objects.equals(resources, tile.resources) && Objects.equals(building, tile.building);
    }

    @Override
    public int hashCode() {
        return Objects.hash(active, pop, prevBuilding, blocker, deposits, resources, building);
    }
}
