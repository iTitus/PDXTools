package io.github.ititus.pdx.util.collection;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class EmptyViewableList<E> extends AbstractList<E> implements ViewableList<E>, RandomAccess {

    private static final long serialVersionUID = 1;

    @Override
    public Iterator<E> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return Collections.emptyListIterator();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean contains(Object obj) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.isEmpty();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length > 0) {
            a[0] = null;
        }
        return a;
    }

    @Override
    public E get(int index) {
        throw new IndexOutOfBoundsException("Index: " + index);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || (o instanceof List && ((List<?>) o).isEmpty());
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        return false;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
    }

    @Override
    public void sort(Comparator<? super E> c) {
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.emptySpliterator();
    }

    @Override
    public List<E> getView() {
        return this;
    }

    private Object readResolve() {
        return CollectionUtil.viewableListOf();
    }

}
