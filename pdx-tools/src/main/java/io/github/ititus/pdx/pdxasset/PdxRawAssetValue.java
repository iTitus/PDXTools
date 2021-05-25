package io.github.ititus.pdx.pdxasset;

import io.github.ititus.math.vector.*;
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

    public Vec2i expectVec2i() {
        if (value instanceof int[] arr && arr.length == 2) {
            return new Vec2i(arr[0], arr[1]);
        }

        throw new IllegalStateException("expected int[] of size 2 but got " + value);
    }

    public ImmutableIntList expectIntList() {
        if (value instanceof int[] arr) {
            return IntLists.immutable.of(arr);
        }

        throw new IllegalStateException("expected int[] but got " + value);
    }

    public ImmutableList<Vec3i> expectVec3iList() {
        if (value instanceof int[] arr) {
            if (arr.length % 3 != 0) {
                throw new RuntimeException("illegal size");
            }

            Vec3i[] groups = new Vec3i[arr.length / 3];
            for (int i = 0; i < arr.length; i += 3) {
                groups[i / 3] = new Vec3i(arr[i], arr[i + 1], arr[i + 2]);
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

    public Vec2f expectVec2f() {
        if (value instanceof float[] arr && arr.length == 2) {
            return new Vec2f(arr[0], arr[1]);
        }

        throw new IllegalStateException("expected float[] of size 2 but got " + value);
    }

    public Vec3f expectVec3f() {
        if (value instanceof float[] arr && arr.length == 3) {
            return new Vec3f(arr[0], arr[1], arr[2]);
        }

        throw new IllegalStateException("expected float[] of size 3 but got " + value);
    }

    public Vec4f expectVec4f() {
        if (value instanceof float[] arr && arr.length == 4) {
            return new Vec4f(arr[0], arr[1], arr[2], arr[3]);
        }

        throw new IllegalStateException("expected float[] of size 4 but got " + value);
    }

    public ImmutableFloatList expectFloatList() {
        if (value instanceof float[] arr) {
            return FloatLists.immutable.of(arr);
        }

        throw new IllegalStateException("expected float[] but got " + value);
    }

    public ImmutableList<Vec2f> expectVec2fList() {
        if (value instanceof float[] arr) {
            if (arr.length % 2 != 0) {
                throw new RuntimeException("illegal size");
            }

            Vec2f[] groups = new Vec2f[arr.length / 2];
            for (int i = 0; i < arr.length; i += 2) {
                groups[i / 2] = new Vec2f(arr[i], arr[i + 1]);
            }

            return Lists.immutable.of(groups);
        }

        throw new IllegalStateException("expected float[] but got " + value);
    }

    public ImmutableList<Vec3f> expectVec3fList() {
        if (value instanceof float[] arr) {
            if (arr.length % 3 != 0) {
                throw new RuntimeException("illegal size");
            }

            Vec3f[] groups = new Vec3f[arr.length / 3];
            for (int i = 0; i < arr.length; i += 3) {
                groups[i / 3] = new Vec3f(arr[i], arr[i + 1], arr[i + 2]);
            }

            return Lists.immutable.of(groups);
        }

        throw new IllegalStateException("expected float[] but got " + value);
    }

    public ImmutableList<Vec4f> expectVec4fList() {
        if (value instanceof float[] arr) {
            if (arr.length % 4 != 0) {
                throw new RuntimeException("illegal size");
            }

            Vec4f[] groups = new Vec4f[arr.length / 4];
            for (int i = 0; i < arr.length; i += 4) {
                groups[i / 4] = new Vec4f(arr[i], arr[i + 1], arr[i + 2], arr[i + 3]);
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
