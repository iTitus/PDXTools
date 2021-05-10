package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Coordinate {

    public final boolean randomized;
    public final int origin;
    public final double x, y;

    public Coordinate(PdxScriptObject o) {
        this.x = o.getDouble("x");
        this.y = o.getDouble("y");
        this.origin = o.getUnsignedInt("origin");
        this.randomized = o.getBoolean("randomized", false);
    }
}
