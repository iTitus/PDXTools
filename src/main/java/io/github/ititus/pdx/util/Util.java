package io.github.ititus.pdx.util;

import java.util.function.Function;

public class Util {

    @SuppressWarnings("unchecked")
    public static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }

}
