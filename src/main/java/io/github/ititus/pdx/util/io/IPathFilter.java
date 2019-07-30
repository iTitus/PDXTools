package io.github.ititus.pdx.util.io;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface IPathFilter extends Predicate<Path> {

    default IPathFilter and(IPathFilter filter) {
        Objects.requireNonNull(filter);
        return f -> test(f) && filter.test(f);
    }

    default IPathFilter or(IPathFilter filter) {
        Objects.requireNonNull(filter);
        return f -> test(f) || filter.test(f);
    }

    default IPathFilter xor(IPathFilter filter) {
        Objects.requireNonNull(filter);
        return f -> test(f) ^ filter.test(f);
    }
}
