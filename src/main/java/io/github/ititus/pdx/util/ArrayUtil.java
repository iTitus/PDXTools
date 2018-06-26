package io.github.ititus.pdx.util;

public class ArrayUtil {
    public static float[] toFloatArray(double... doubleArray) {
        if (doubleArray == null) {
            return null;
        }
        float[] floatArray = new float[doubleArray.length];
        for (int i = 0; i < doubleArray.length; i++) {
            floatArray[i] = (float) doubleArray[i];
        }
        return floatArray;
    }
}
