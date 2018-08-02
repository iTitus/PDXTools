package io.github.ititus.pdx.util;

import org.eclipse.collections.impl.block.factory.Predicates;

import java.util.function.Predicate;

public class Deduplicator<T> {

    private static final boolean ENABLED = true;

    private final DeduplicatingCountingSet<T> map;
    private final Predicate<? super T> allowDeduplication;

    public Deduplicator() {
        this(Predicates.alwaysTrue());
    }

    public Deduplicator(Predicate<? super T> allowDeduplication) {
        this.map = new DeduplicatingCountingSet<>();
        this.allowDeduplication = allowDeduplication;
    }

    public T deduplicate(T t) {
        if (!ENABLED || !allowDeduplication.test(t)) {
            return t;
        }
        return map.addDeduplicate(t);
    }
}
