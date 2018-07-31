package io.github.ititus.pdx.util.collection;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.primitive.MutableObjectIntMap;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.factory.primitive.ObjectIntMaps;

import java.util.*;
import java.util.function.Predicate;

public class CountingSet<E> extends AbstractSet<E> {

    private static final Comparator<Object> HASH_COMP = Comparator.comparingInt(Object::hashCode);

    private final Comparator<E> COMP = (o1, o2) -> {
        if (o1 == o2) {
            return 0;
        }
        if (o1 instanceof Comparable && o2 != null) {
            return ((Comparable<? super E>) o1).compareTo(o2);
        }
        return HASH_COMP.compare(o1, o2);
    };

    private final MutableObjectIntMap<E> map;

    public CountingSet() {
        this.map = ObjectIntMaps.mutable.empty();
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
        map.addToValue(e, 1);
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

    public ImmutableList<E> sortedList() {
        return map.keyValuesView().toSortedList(Comparator.comparingInt(ObjectIntPair<E>::getTwo).reversed().thenComparing(ObjectIntPair::getOne, COMP)).collect(ObjectIntPair::getOne).toImmutable();
    }
}
