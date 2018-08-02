package io.github.ititus.pdx.util;

import java.util.Arrays;
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

    public static int hash(boolean... array) {
        return Arrays.hashCode(array);
    }

    public static int hash(byte... array) {
        return Arrays.hashCode(array);
    }

    public static int hash(short... array) {
        return Arrays.hashCode(array);
    }

    public static int hash(char... array) {
        return Arrays.hashCode(array);
    }

    public static int hash(int... array) {
        return Arrays.hashCode(array);
    }

    public static int hash(long... array) {
        return Arrays.hashCode(array);
    }

    public static int hash(float... array) {
        return Arrays.hashCode(array);
    }

    public static int hash(double... array) {
        return Arrays.hashCode(array);
    }
}
