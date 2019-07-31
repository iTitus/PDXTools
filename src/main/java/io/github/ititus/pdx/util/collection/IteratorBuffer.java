package io.github.ititus.pdx.util.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class IteratorBuffer<T> {

    private final Iterator<T> iterator;
    private final T[] buffer;

    private int oldest, cursor, pos;

    @SuppressWarnings("unchecked")
    public IteratorBuffer(Iterator<T> iterator, int forward, int backward) {
        Objects.requireNonNull(iterator);
        this.iterator = iterator;
        this.buffer = (T[]) new Object[1 + forward + backward];
        this.cursor = backward;
        this.oldest = 0;

        for (int i = 0; i <= forward; i++) {
            next();
        }

        this.pos = 0;
    }

    public void next() {
        buffer[oldest] = iterator.hasNext() ? Objects.requireNonNull(iterator.next()) : null;
        oldest++;
        if (oldest >= buffer.length) {
            oldest -= buffer.length;
        }
        cursor++;
        if (cursor >= buffer.length) {
            cursor -= buffer.length;
        }
        pos++;
    }

    public boolean hasNext() {
        return buffer[cursor] != null;
    }

    public T get() {
        return get(0);
    }

    public T getNext() {
        next();
        return get();
    }

    public T get(int offset) {
        int i = cursor + offset;
        if (i >= buffer.length) {
            i -= buffer.length;
        } else if (i < 0) {
            i += buffer.length;
        }
        T t = buffer[i];
        if (t == null) {
            throw new NoSuchElementException();
        }
        return t;
    }

    public int getPos() {
        return pos;
    }
}
