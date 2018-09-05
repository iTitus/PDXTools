package io.github.ititus.pdx.util.mutable;

public final class MutableInt {

    private int i;

    public MutableInt() {
        this(0);
    }

    public MutableInt(int i) {
        this.i = i;
    }

    public int get() {
        return i;
    }

    public int getAndIncrement() {
        return i++;
    }

    public int incrementAndGet() {
        return ++i;
    }

    public MutableInt set(int i) {
        this.i = i;
        return this;
    }

    public MutableInt increment() {
        i++;
        return this;
    }

    public MutableInt add(int i) {
        this.i += i;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MutableInt)) {
            return false;
        }
        MutableInt that = (MutableInt) o;
        return i == that.i;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(i);
    }

    @Override
    public String toString() {
        return Integer.toString(i);
    }
}
