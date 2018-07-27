package io.github.ititus.pdx.util.collection;

import com.koloboke.collect.Equivalence;
import com.koloboke.collect.ObjCursor;
import com.koloboke.collect.ObjIterator;
import com.koloboke.collect.map.ObjIntMap;
import com.koloboke.collect.map.hash.HashObjIntMaps;
import com.koloboke.collect.set.ObjSet;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CountingSet<E> extends AbstractSet<E> implements ObjSet<E> {

    private final ObjIntMap<E> map;

    public CountingSet() {
        this.map = HashObjIntMaps.newUpdatableMap();
    }

    @Override
    public Equivalence<E> equivalence() {
        return map.keyEquivalence();
    }

    @Override
    public boolean forEachWhile(Predicate<? super E> predicate) {
        return map.keySet().forEachWhile(predicate);
    }

    @Override
    public ObjCursor<E> cursor() {
        return map.keySet().cursor();
    }

    @Override
    public ObjIterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public long sizeAsLong() {
        return map.sizeAsLong();
    }

    @Override
    public boolean ensureCapacity(long l) {
        return map.ensureCapacity(l);
    }

    @Override
    public boolean shrink() {
        return map.shrink();
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
