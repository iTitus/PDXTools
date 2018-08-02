package io.github.ititus.pdx.util.mutable;

import java.util.Objects;

public class MutableString {

    private String s;

    public MutableString() {
        this(null);
    }

    public MutableString(String s) {
        this.s = s;
    }

    public String get() {
        return s;
    }

    public MutableString set(String s) {
        this.s = s;
        return this;
    }

    public boolean isNull() {
        return s == null;
    }

    public boolean isNotNull() {
        return s != null;
    }

    public boolean isNullOrEmpty() {
        return s == null || s.isEmpty();
    }

    public boolean isNotNullOrEmpty() {
        return s != null && !s.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MutableBoolean)) {
            return false;
        }
        MutableString that = (MutableString) o;
        return Objects.equals(s, that.s);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(s);
    }

    @Override
    public String toString() {
        return Objects.toString(s);
    }
}
