package io.github.ititus.pdx.pdxasset;

import org.eclipse.collections.api.list.primitive.ImmutableIntList;

import java.nio.file.Path;

public final class PdxMesh implements IPdxAsset {

    public final ImmutableIntList pdxasset;
    public final PdxRawAssetObject object;
    public final PdxRawAssetObject locator;

    public PdxMesh(Path path) {
        PdxRawAssetObject data = new PdxRawAsset(path).getData();
        this.pdxasset = data.get("pdxasset").expectValue().expectInt();
        this.object = data.get("object", PdxRawAssetObject.EMPTY).expectObject();
        this.locator = data.get("locator").expectObject();
    }
}
