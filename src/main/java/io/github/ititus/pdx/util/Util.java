package io.github.ititus.pdx.util;

import java.util.function.Function;

public class Util {

    @SuppressWarnings("unchecked")
    public static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }

    public static double[] addArrays(double[] array1, double[] array2) {
        for (int i = 0; i < array1.length; i++) {
            array1[i] += array2[i];
        }
        return array1;
    }
}
