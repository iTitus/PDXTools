package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class NaturalWormhole {

    private final int bypass;
    private final Coordinate coordinate;

    public NaturalWormhole(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.coordinate = o.getObject("coordinate").getAs(Coordinate::of);
        this.bypass = o.getInt("bypass");
    }

    public NaturalWormhole(int bypass, Coordinate coordinate) {
        this.bypass = bypass;
        this.coordinate = coordinate;
    }

    public int getBypass() {
        return bypass;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
