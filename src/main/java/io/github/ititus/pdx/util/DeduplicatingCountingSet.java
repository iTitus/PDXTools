package io.github.ititus.pdx.util;

import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Predicate;

public class DeduplicatingCountingSet<E> extends AbstractSet<E> {

    private final MutableMap<E, ObjectIntPair<E>> map;

    public DeduplicatingCountingSet() {
        map = Maps.mutable.empty();
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
        addDeduplicate(e);
        return true;
    }

    public E addDeduplicate(E e) {
        ObjectIntPair<E> pair = map.get(e);
        E base = pair != null ? pair.getOne() : e;
        map.put(e, PrimitiveTuples.pair(base, pair != null ? pair.getTwo() + 1 : 1));
        return base;
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
}
