package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;

import java.util.Objects;

public class Coordinate {

    private static final Deduplicator<Coordinate> DEDUPLICATOR = new Deduplicator<>();

    private final boolean randomized;
    private final int origin;
    private final double x, y;

    private Coordinate(PdxScriptObject o) {
        this.x = o.getDouble("x");
        this.y = o.getDouble("y");
        this.origin = o.getUnsignedInt("origin");
        this.randomized = o.getBoolean("randomized");
    }

    private Coordinate(boolean randomized, int origin, double x, double y) {
        this.randomized = randomized;
        this.origin = origin;
        this.x = x;
        this.y = y;
    }

    public static Coordinate of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new Coordinate(o));
    }

    public static Coordinate of(boolean randomized, int origin, double x, double y) {
        return DEDUPLICATOR.deduplicate(new Coordinate(randomized, origin, x, y));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate that = (Coordinate) o;
        return randomized == that.randomized && origin == that.origin && Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(randomized, origin, x, y);
    }
}
