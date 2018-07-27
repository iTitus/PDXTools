package io.github.ititus.pdx.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Tuple3D {

    private final double d1, d2, d3;

    public Tuple3D(double d1, double d2, double d3) {
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    public static Tuple3D of(double d1, double d2, double d3) {
        return new Tuple3D(d1, d2, d3);
    }

    public static Tuple3D of(double[] array) {
        return new Tuple3D(array[0], array[1], array[2]);
    }

    public static Tuple3D of(List<Double> list) {
        return new Tuple3D(list.get(0), list.get(1), list.get(2));
    }

    public static Tuple3D of(Set<Double> set) {
        Iterator<Double> it = set.iterator();
        return new Tuple3D(it.next(), it.next(), it.next());
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
}
