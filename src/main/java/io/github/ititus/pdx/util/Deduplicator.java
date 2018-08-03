package io.github.ititus.pdx.util;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Predicate;

public class Deduplicator<T> {

    private static final boolean DISABLED = false, RESPECT_DEDUPLICATION_CONDITION = false;

    private final Predicate<? super T> allowDeduplication;

    private Map<T, WeakReference<T>> map;

    public Deduplicator() {
        this(null);
    }

    public Deduplicator(Predicate<? super T> allowDeduplication) {
        this.allowDeduplication = allowDeduplication;
    }

    public T deduplicate(T t) {
        if (DISABLED || t == null || (RESPECT_DEDUPLICATION_CONDITION && allowDeduplication != null && !allowDeduplication.test(t))) {
            return t;
        }
        if (map == null) {
            map = new WeakHashMap<>();
        }
        return map.computeIfAbsent(t, WeakReference::new).get();
    }
}
