package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Buildings {

    private final ImmutableIntObjectMap<Building> buildings;

    public Buildings(PdxScriptObject o) {
        this.buildings = o.getAsIntObjectMap(Building::of);
    }

    public Buildings(ImmutableIntObjectMap<Building> buildings) {
        this.buildings = buildings;
    }

    public ImmutableIntObjectMap<Building> getBuildings() {
        return buildings;
    }
}
