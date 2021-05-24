package io.github.ititus.pdx.pdxasset;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableFloatList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.FloatLists;
import org.eclipse.collections.impl.factory.primitive.IntLists;

public final class PdxRawAssetValue implements IPdxRawAsset {

    private final Object value;

    private PdxRawAssetValue(Object value) {
        this.value = value;
    }

    public static PdxRawAssetValue of(Object value) {
        if (value instanceof int[] || value instanceof float[] || value instanceof String[]) {
            return new PdxRawAssetValue(value);
        }

        throw new IllegalArgumentException("illegal value " + value);
    }

    public int expectInt() {
        if (value instanceof int[] arr && arr.length == 1) {
            return arr[0];
        }

        throw new IllegalStateException("expected int[] of size 1 but got " + value);
    }

    public ImmutableIntList expectIntList() {
        if (value instanceof int[]) {
            return IntLists.immutable.of((int[]) value);
        }

        throw new IllegalStateException("expected int[] but got " + value);
    }

    public float expectFloat() {
        if (value instanceof float[] arr && arr.length == 1) {
            return arr[0];
        }

        throw new IllegalStateException("expected float[] of size 1 but got " + value);
    }

    public ImmutableFloatList expectFloatList() {
        if (value instanceof float[]) {
            return FloatLists.immutable.of((float[]) value);
        }

        throw new IllegalStateException("expected float[] but got " + value);
    }

    public String expectString() {
        if (value instanceof String[] arr && arr.length == 1) {
            return arr[0];
        }

        throw new IllegalStateException("expected String[] of size 1 but got " + value);
    }

    public ImmutableList<String> expectStringList() {
        if (value instanceof String[]) {
            return Lists.immutable.of((String[]) value);
        }

        throw new IllegalStateException("expected String[] but got " + value);
    }
}
