package io.github.ititus.pdx.pdxasset;

import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;

import java.util.NoSuchElementException;

public final class PdxRawAssetObject implements IPdxRawAsset {

    public static final PdxRawAssetObject EMPTY = new PdxRawAssetObject(Maps.immutable.empty());

    private final ImmutableMap<String, IPdxRawAsset> data;

    private PdxRawAssetObject(ImmutableMap<String, IPdxRawAsset> data) {
        this.data = data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ImmutableMap<String, IPdxRawAsset> getData() {
        return data;
    }

    public int size() {
        return data.size();
    }

    public IPdxRawAsset get(String name) {
        IPdxRawAsset a = data.get(name);
        if (a == null) {
            throw new NoSuchElementException("no value for key " + name);
        }

        return a;
    }

    public IPdxRawAsset get(String name, IPdxRawAsset def) {
        IPdxRawAsset a = data.get(name);
        if (a == null) {
            return def;
        }

        return a;
    }

    public static final class Builder {

        private final MutableMap<String, Object> data;

        private Builder() {
            this.data = Maps.mutable.empty();
        }

        public Builder add(String name, Object value) {
            data.put(name, value);
            return this;
        }

        public PdxRawAssetObject build() {
            MutableMap<String, IPdxRawAsset> data = this.data.collectValues((k, v) -> {
                if (v instanceof Builder) {
                    return ((Builder) v).build();
                } else if (v instanceof IPdxRawAsset) {
                    return (IPdxRawAsset) v;
                }

                return PdxRawAssetValue.of(v);
            });
            if (data.isEmpty()) {
                return EMPTY;
            }

            return new PdxRawAssetObject(data.toImmutable());
        }
    }
}
