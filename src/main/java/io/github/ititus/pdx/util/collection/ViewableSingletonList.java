package io.github.ititus.pdx.util.collection;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ViewableSingletonList<E> extends AbstractList<E> implements ViewableList<E>, RandomAccess {

    private final E element;

    public ViewableSingletonList(E obj) {
        element = obj;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private boolean hasNext = true;

            public boolean hasNext() {
                return hasNext;
            }

            public E next() {
                if (hasNext) {
                    hasNext = false;
                    return element;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(Consumer<? super E> action) {
                Objects.requireNonNull(action);
                if (hasNext) {
                    action.accept(element);
                    hasNext = false;
                }
            }
        };
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean contains(Object obj) {
        return Objects.equals(obj, element);
    }

    @Override
    public E get(int index) {
        if (index != 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: 1");
        }
        return element;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof List) {
            List<?> other = (List<?>) o;
            return other.size() == 1 && Objects.equals(other.get(0), element);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(element);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        action.accept(element);
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sort(Comparator<? super E> c) {
    }

    @Override
    public Spliterator<E> spliterator() {
        return new Spliterator<E>() {

            long est = 1;

            @Override
            public Spliterator<E> trySplit() {
                return null;
            }

            @Override
            public boolean tryAdvance(Consumer<? super E> consumer) {
                Objects.requireNonNull(consumer);
                if (est > 0) {
                    est--;
                    consumer.accept(element);
                    return true;
                }
                return false;
            }

            @Override
            public void forEachRemaining(Consumer<? super E> consumer) {
                tryAdvance(consumer);
            }

            @Override
            public long estimateSize() {
                return est;
            }

            @Override
            public int characteristics() {
                return ((element != null) ? Spliterator.NONNULL : 0) | Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.IMMUTABLE | Spliterator.DISTINCT | Spliterator.ORDERED;
            }
        };
    }

    @Override
    public List<E> getView() {
        return this;
    }
}
