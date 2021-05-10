package io.github.ititus.pdx.pdxasset;

public interface IPdxRawAsset {

    default PdxRawAssetValue expectValue() {
        if (this instanceof PdxRawAssetValue) {
            return (PdxRawAssetValue) this;
        }

        throw new IllegalStateException("expected value but got " + this);
    }

    default PdxRawAssetObject expectObject() {
        if (this instanceof PdxRawAssetObject) {
            return (PdxRawAssetObject) this;
        }

        throw new IllegalStateException("expected object but got " + this);
    }
}
