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
        if (value instanceof int[] arr) {
            return IntLists.immutable.of(arr);
        }

        throw new IllegalStateException("expected int[] but got " + value);
    }

    public ImmutableList<ImmutableIntList> expectGroupedIntList(int stride) {
        if (value instanceof int[] arr) {
            if (arr.length % stride != 0) {
                throw new RuntimeException("illegal size");
            }

            ImmutableIntList[] groups = new ImmutableIntList[arr.length / stride];
            for (int i = 0; i < arr.length; i += stride) {
                int[] group = new int[stride];
                System.arraycopy(arr, i, group, 0, stride);
                groups[i / stride] = IntLists.immutable.of(group);
            }

            return Lists.immutable.of(groups);
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
        if (value instanceof float[] arr) {
            return FloatLists.immutable.of(arr);
        }

        throw new IllegalStateException("expected float[] but got " + value);
    }

    public ImmutableList<ImmutableFloatList> expectGroupedFloatList(int stride) {
        if (value instanceof float[] arr) {
            if (arr.length % stride != 0) {
                throw new RuntimeException("illegal size");
            }

            ImmutableFloatList[] groups = new ImmutableFloatList[arr.length / stride];
            for (int i = 0; i < arr.length; i += stride) {
                float[] group = new float[stride];
                System.arraycopy(arr, i, group, 0, stride);
                groups[i / stride] = FloatLists.immutable.of(group);
            }

            return Lists.immutable.of(groups);
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
