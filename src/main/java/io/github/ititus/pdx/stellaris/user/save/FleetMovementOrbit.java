package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FleetMovementOrbit {

    private final int planet, index;

    public FleetMovementOrbit(PdxScriptObject o) {
        this.planet = o.getUnsignedInt("planet");
        this.index = o.getInt("index", -1);
    }

    public FleetMovementOrbit(int planet, int index) {
        this.planet = planet;
        this.index = index;
    }

    public int getPlanet() {
        return planet;
    }

    public int getIndex() {
        return index;
    }
}
