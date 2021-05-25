package io.github.ititus.pdx.pdxasset;

import java.util.*;

public final class PdxRawAssetObject implements IPdxRawAsset {

    public static final PdxRawAssetObject EMPTY = new PdxRawAssetObject(Map.of(), Map.of());

    private final Map<String, PdxRawAssetValue> properties;
    private final Map<String, List<PdxRawAssetObject>> children;

    private PdxRawAssetObject(Map<String, PdxRawAssetValue> properties, Map<String, List<PdxRawAssetObject>> children) {
        this.properties = properties;
        this.children = children;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, PdxRawAssetValue> getProperties() {
        return properties;
    }

    public Map<String, List<PdxRawAssetObject>> getChildren() {
        return children;
    }

    public boolean hasProperty(String name) {
        return properties.containsKey(name);
    }

    public PdxRawAssetValue getProperty(String name, PdxRawAssetValue def) {
        PdxRawAssetValue v = properties.get(name);
        if (v == null) {
            return def;
        }

        return v;
    }

    public PdxRawAssetValue getProperty(String name) {
        PdxRawAssetValue v = properties.get(name);
        if (v == null) {
            throw new NoSuchElementException("no property for key " + name);
        }

        return v;
    }

    public boolean hasChild(String name) {
        return children.containsKey(name);
    }

    public List<PdxRawAssetObject> getChildren(String name) {
        List<PdxRawAssetObject> os = children.get(name);
        return os != null ? os : List.of();
    }

    public PdxRawAssetObject getChild(String name) {
        List<PdxRawAssetObject> os = getChildren(name);
        int size = os.size();
        if (size == 1) {
            return os.get(0);
        }

        throw new NoSuchElementException("got " + size + " children but expected 1 for key " + name);
    }

    public PdxRawAssetObject getChildOrEmpty(String name) {
        List<PdxRawAssetObject> os = getChildren(name);
        int size = os.size();
        if (size == 0) {
            return EMPTY;
        } else if (size == 1) {
            return os.get(0);
        }

        throw new NoSuchElementException("got " + size + " children but expected 0 or 1 for key " + name);
    }

    public static final class Builder {

        private final Map<String, PdxRawAssetValue> properties;
        private final Map<String, List<Builder>> children;

        private Builder() {
            this.properties = new LinkedHashMap<>();
            this.children = new LinkedHashMap<>();
        }

        public Builder addProperty(String name, Object value) {
            if (properties.putIfAbsent(name, PdxRawAssetValue.of(value)) != null) {
                throw new IllegalArgumentException("property " + name + " already exists");
            }

            return this;
        }

        public Builder addChild(String name, Builder value) {
            children.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
            return this;
        }

        public PdxRawAssetObject build() {
            Map<String, List<PdxRawAssetObject>> children = new LinkedHashMap<>();
            this.children.forEach((k, v) -> children.put(k, v.stream().map(Builder::build).toList()));
            return properties.isEmpty() && children.isEmpty() ? EMPTY :
                    new PdxRawAssetObject(
                            properties.isEmpty() ? Map.of() : Collections.unmodifiableMap(properties),
                            children.isEmpty() ? Map.of() : Collections.unmodifiableMap(children)
                    );
        }
    }
}
