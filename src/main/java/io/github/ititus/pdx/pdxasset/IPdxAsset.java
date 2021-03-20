package io.github.ititus.pdx.pdxasset;

import io.github.ititus.pdx.util.io.IOUtil;

import java.nio.file.Path;

public interface IPdxAsset {

    static IPdxAsset load(Path path) {
        String extension = IOUtil.getExtension(path);
        return switch (extension) {
            case "mesh" -> new PdxMesh(path);
            case "anim" -> new PdxAnim(path);
            default -> throw new IllegalStateException("unknown asset file extension: " + extension);
        };
    }
}
