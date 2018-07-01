package io.github.ititus.pdx.util;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ThrowingBiConsumer<T, U, E extends Exception> extends BiConsumer<T, U> {

    @Override
    default void accept(T t, U u) {
        try {
            consume(t, u);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void consume(T t, U u) throws E;
}
