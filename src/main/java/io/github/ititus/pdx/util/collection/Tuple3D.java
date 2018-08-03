package io.github.ititus.pdx.util.collection;

import io.github.ititus.pdx.util.Deduplicator;
import io.github.ititus.pdx.util.Util;

public class Tuple3D {

    private static final Deduplicator<Tuple3D> DEDUPLICATOR = new Deduplicator<>();

    private final double d1, d2, d3;

    private Tuple3D(double d1, double d2, double d3) {
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    public static Tuple3D of() {
        return of(0, 0, 0);
    }

    public static Tuple3D of(double d1) {
        return of(d1, 0, 0);
    }

    public static Tuple3D of(double d1, double d2) {
        return of(d1, d2, 0);
    }

    public static Tuple3D of(double d1, double d2, double d3) {
        return DEDUPLICATOR.deduplicate(new Tuple3D(d1, d2, d3));
    }

    public static Tuple3D of(double[] array) {
        if (array == null || array.length == 0) {
            return of();
        } else if (array.length == 1) {
            return of(array[0]);
        } else if (array.length == 2) {
            return of(array[0], array[1]);
        }
        return of(array[0], array[1], array[2]);
    }

    public double getD1() {
        return d1;
    }

    public double getD2() {
        return d2;
    }

    public double getD3() {
        return d3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tuple3D)) {
            return false;
        }
        Tuple3D tuple3D = (Tuple3D) o;
        return Double.compare(tuple3D.d1, d1) == 0 && Double.compare(tuple3D.d2, d2) == 0 && Double.compare(tuple3D.d3, d3) == 0;
    }

    @Override
    public int hashCode() {
        return Util.hash(d1, d2, d3);
    }
}
