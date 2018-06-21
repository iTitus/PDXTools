package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

public class Coordinate {

    private final double x, y;
    private final long origin;
    private final boolean randomized;

    public Coordinate(PdxScriptObject o) {
        this.x = o.getDouble("x");
        this.y = o.getDouble("y");
        this.origin = o.getLong("origin");
        this.randomized = o.getBoolean("randomized");
    }

    public Coordinate(double x, double y, long origin, boolean randomized) {
        this.x = x;
        this.y = y;
        this.origin = origin;
        this.randomized = randomized;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public long getOrigin() {
        return origin;
    }

    public boolean isRandomized() {
        return randomized;
    }
}
