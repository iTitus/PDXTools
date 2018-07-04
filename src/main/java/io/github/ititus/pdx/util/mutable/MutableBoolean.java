package io.github.ititus.pdx.util.mutable;

import java.util.function.Supplier;

public final class MutableBoolean {

    private boolean b;

    public MutableBoolean() {
        this(false);
    }

    public MutableBoolean(boolean b) {
        this.b = b;
    }

    public boolean get() {
        return b;
    }

    public MutableBoolean set(boolean b) {
        this.b = b;
        return this;
    }

    public MutableBoolean toggle() {
        this.b = !this.b;
        return this;
    }

    public MutableBoolean ifTrue(Runnable r) {
        if (b) {
            r.run();
        }
        return this;
    }

    public MutableBoolean ifFalse(Runnable r) {
        if (!b) {
            r.run();
        }
        return this;
    }

    public MutableBoolean ifElse(Runnable if_, Runnable else_) {
        if (b) {
            if_.run();
        } else {
            else_.run();
        }
        return this;
    }

    public <T> T ifElse(Supplier<T> if_, Supplier<T> else_) {
        if (b) {
            return if_.get();
        }
        return else_.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MutableBoolean)) {
            return false;
        }
        MutableBoolean that = (MutableBoolean) o;
        return b == that.b;
    }

    @Override
    public int hashCode() {
        return b ? 1 : 0;
    }

    @Override
    public String toString() {
        return Boolean.toString(b);
    }
}
