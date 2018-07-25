package io.github.ititus.pdx.util.collection;

import java.util.*;
import java.util.stream.Stream;

public class CountingSet<E> extends AbstractSet<E> {

    private static final long serialVersionUID = 1;

    private final Map<E, Integer> map;

    public CountingSet() {
        this.map = new HashMap<>();
    }

    public CountingSet(Collection<? extends E> c) {
        this.map = new HashMap<>(Math.max((int) (c.size() / .75f) + 1, 16));
        addAll(c);
    }

    public CountingSet(int initialCapacity, float loadFactor) {
        this.map = new HashMap<>(initialCapacity, loadFactor);
    }

    public CountingSet(int initialCapacity) {
        this.map = new HashMap<>(initialCapacity);
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean add(E e) {
        map.put(e, map.getOrDefault(e, 0) + 1);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) != null;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Spliterator<E> spliterator() {
        return map.keySet().spliterator();
    }

    public Stream<E> sortedStream() {
        return map.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry<E, Integer>::getValue).reversed().thenComparing(Map.Entry::getKey, (Comparator<? super E>) Comparator.naturalOrder())).map(Map.Entry::getKey);
    }

    public Stream<E> sortedStream(Comparator<? super E> comparator) {
        return map.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry<E, Integer>::getValue).reversed().thenComparing(Map.Entry::getKey, comparator)).map(Map.Entry::getKey);
    }

    public Stream<E> sortedParallelStream() {
        return map.entrySet().parallelStream().sorted(Comparator.comparingInt(Map.Entry<E, Integer>::getValue).reversed().thenComparing(Map.Entry::getKey, (Comparator<? super E>) Comparator.naturalOrder())).map(Map.Entry::getKey);
    }

    public Stream<E> sortedParallelStream(Comparator<? super E> comparator) {
        return map.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry<E, Integer>::getValue).reversed().thenComparing(Map.Entry::getKey, comparator)).map(Map.Entry::getKey);
    }
}
