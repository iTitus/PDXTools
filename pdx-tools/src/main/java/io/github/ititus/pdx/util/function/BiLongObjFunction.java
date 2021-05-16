package io.github.ititus.pdx.util.function;

import java.util.Objects;
import java.util.function.Function;

@Deprecated(forRemoval = true)
@FunctionalInterface
public interface BiLongObjFunction<T, R> {

    R apply(long l, T t);

    default <V> BiLongObjFunction<T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (l, t) -> after.apply(apply(l, t));
    }
}
