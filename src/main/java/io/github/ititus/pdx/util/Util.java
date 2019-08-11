package io.github.ititus.pdx.util;

import io.github.ititus.pdx.pdxscript.PdxConstants;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Util {

    private static final String[] INDENTS;

    static {
        INDENTS = new String[16];
        INDENTS[0] = PdxConstants.EMPTY;
        StringBuilder last = new StringBuilder((INDENTS.length - 1) * PdxConstants.INDENT.length());
        for (int i = 1; i < INDENTS.length; i++) {
            INDENTS[i] = last.append(PdxConstants.INDENT).toString();
        }
    }

    public static String indent(int indent) {
        if (indent < 0) {
            throw new IllegalArgumentException();
        } else if (indent < INDENTS.length) {
            return INDENTS[indent];
        }

        return IntStream.range(0, indent).mapToObj(i -> PdxConstants.INDENT).collect(Collectors.joining());
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
