package io.github.ititus.pdx.util.collection;

import com.koloboke.collect.map.hash.HashObjIntMap;
import com.koloboke.collect.map.hash.HashObjIntMaps;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CountingSet<E> extends AbstractSet<E> {

    private final HashObjIntMap<E> map;

    public CountingSet() {
        this.map = HashObjIntMaps.newUpdatableMap();
    }

    public CountingSet(int initialCapacity) {
        this.map = HashObjIntMaps.newUpdatableMap(initialCapacity);
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
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
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
