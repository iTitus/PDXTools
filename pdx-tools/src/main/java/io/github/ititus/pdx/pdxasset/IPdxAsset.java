package io.github.ititus.pdx.pdxasset;

import io.github.ititus.io.PathUtil;

import java.nio.file.Path;
import java.util.Optional;

public interface IPdxAsset {

    static IPdxAsset load(Path path) {
        Optional<String> ext = PathUtil.getExtension(path);
        if (ext.isEmpty()) {
            throw new IllegalStateException("given file has no extension");
        }

        return switch (ext.get()) {
            case "mesh" -> new PdxMesh(path);
            case "anim" -> new PdxAnim(path);
            default -> throw new IllegalStateException("unknown asset file extension: " + ext.get());
        };
    }
}
