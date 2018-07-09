package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Coordinate {

    private final boolean randomized;
    private final int origin;
    private final double x, y;

    public Coordinate(PdxScriptObject o) {
        this.x = o.getDouble("x");
        this.y = o.getDouble("y");
        this.origin = o.getUnsignedInt("origin");
        this.randomized = o.getBoolean("randomized");
    }

    public Coordinate(boolean randomized, int origin, double x, double y) {
        this.randomized = randomized;
        this.origin = origin;
        this.x = x;
        this.y = y;
    }

    public boolean isRandomized() {
        return randomized;
    }

    public int getOrigin() {
        return origin;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
