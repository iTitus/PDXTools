package io.github.ititus.pdx.pdxasset;

import org.eclipse.collections.api.list.primitive.ImmutableIntList;

import java.nio.file.Path;

public final class PdxAnim implements IPdxAsset {

    public final ImmutableIntList pdxasset;
    public final PdxRawAssetObject info;
    public final PdxRawAssetObject samples;

    public PdxAnim(Path path) {
        PdxRawAssetObject data = new PdxRawAsset(path).getData();
        this.pdxasset = data.get("pdxasset").expectValue().expectInt();
        this.info = data.get("info").expectObject();
        this.samples = data.get("samples").expectObject();
    }
}
