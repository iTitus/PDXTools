package io.github.ititus.pdx.pdxasset;

import io.github.ititus.commons.data.Lazy;
import io.github.ititus.dds.DdsFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

public class PdxDds implements IPdxAsset {

    public final Path path;
    public final Lazy<DdsFile> dds;

    public PdxDds(Path path) {
        this.path = path;
        this.dds = Lazy.of(() -> {
            try {
                return DdsFile.load(path);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }
}
