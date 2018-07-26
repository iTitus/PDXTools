package io.github.ititus.pdx.util.collection;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ViewableUnmodifiableArrayList<E> extends ArrayList<E> implements ViewableList<E> {

    public ViewableUnmodifiableArrayList(Collection<? extends E> c) {
        super(c);
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    @Override
    public void trimToSize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void ensureCapacity(int minCapacity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        return super.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
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
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sort(Comparator<? super E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return Collections.unmodifiableList(super.subList(fromIndex, toIndex));
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private final Iterator<? extends E> i = ViewableUnmodifiableArrayList.super.iterator();

            @Override
            public boolean hasNext() {
                return i.hasNext();
            }

            @Override
            public E next() {
                return i.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(Consumer<? super E> action) {
                i.forEachRemaining(action);
            }
        };
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    public ListIterator<E> listIterator(int index) {
        return new ListIterator<E>() {

            private final ListIterator<? extends E> i = ViewableUnmodifiableArrayList.super.listIterator(index);

            @Override
            public boolean hasNext() {
                return i.hasNext();
            }

            @Override
            public E next() {
                return i.next();
            }

            @Override
            public boolean hasPrevious() {
                return i.hasPrevious();
            }

            @Override
            public E previous() {
                return i.previous();
            }

            @Override
            public int nextIndex() {
                return i.nextIndex();
            }

            @Override
            public int previousIndex() {
                return i.previousIndex();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(E e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(E e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(Consumer<? super E> action) {
                i.forEachRemaining(action);
            }
        };
    }

    @Override
    public List<E> getView() {
        return this;
    }

    public static class Builder<E> {

        private final ViewableList<E> list;

        public Builder() {
            this.list = new ViewableArrayList<>();
        }

        public Builder<E> add(E e) {
            list.add(e);
            return this;
        }

        public Builder<E> addAll(E... e) {
            if (e != null && e.length > 0) {
                if (e.length == 1) {
                    return add(e[0]);
                }
                list.addAll(Arrays.asList(e));
            }
            return this;
        }

        public Builder<E> addAll(Collection<? extends E> c) {
            list.addAll(c);
            return this;
        }


        public Builder<E> addAll(Builder<? extends E> b) {
            list.addAll(b.list);
            return this;
        }

        public ViewableUnmodifiableArrayList<E> build() {
            return new ViewableUnmodifiableArrayList<>(list);
        }
    }
}
