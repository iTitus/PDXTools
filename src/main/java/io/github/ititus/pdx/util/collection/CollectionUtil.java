package io.github.ititus.pdx.util.collection;

import org.eclipse.collections.api.DoubleIterable;
import org.eclipse.collections.api.IntIterable;
import org.eclipse.collections.api.LongIterable;
import org.eclipse.collections.api.iterator.DoubleIterator;
import org.eclipse.collections.api.iterator.IntIterator;
import org.eclipse.collections.api.iterator.LongIterator;

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class CollectionUtil {

    private static final Iterable EMPTY_ITERABLE = new EmptyIterable<>();

    @SuppressWarnings("unchecked")
    public static <T> Iterable<T> emptyIterable() {
        return (Iterable<T>) EMPTY_ITERABLE;
    }

    public static IntStream stream(IntIterable iterable) {
        IntIterator it = iterable.intIterator();
        return StreamSupport.intStream(Spliterators.spliteratorUnknownSize(new PrimitiveIterator.OfInt() {

            @Override
            public int nextInt() {
                return it.next();
            }

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }
        }, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE), false);
    }

    public static LongStream stream(LongIterable iterable) {
        LongIterator it = iterable.longIterator();
        return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(new PrimitiveIterator.OfLong() {

            @Override
            public long nextLong() {
                return it.next();
            }

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }
        }, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE), false);
    }

    public static DoubleStream stream(DoubleIterable iterable) {
        DoubleIterator it = iterable.doubleIterator();
        return StreamSupport.doubleStream(Spliterators.spliteratorUnknownSize(new PrimitiveIterator.OfDouble() {

            @Override
            public double nextDouble() {
                return it.next();
            }

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }
        }, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE), false);
    }
}
