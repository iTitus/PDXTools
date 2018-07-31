package io.github.ititus.pdx.util.collection;

import java.util.*;
import java.util.function.Consumer;

public class EmptyIterable<T> implements Iterable<T> {

    @Override
    public Iterator<T> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Spliterators.emptySpliterator();
    }
}
