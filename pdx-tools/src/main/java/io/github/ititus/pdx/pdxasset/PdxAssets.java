package io.github.ititus.pdx.pdxasset;

import io.github.ititus.commons.io.FileExtensionFilter;
import io.github.ititus.commons.io.PathFilter;
import io.github.ititus.commons.io.PathUtil;
import io.github.ititus.pdx.shared.ProgressMessageUpdater;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public final class PdxAssets {

    private static final PathFilter FILTER = new FileExtensionFilter("anim", "dds", "mesh");

    private final Map<String, IPdxAsset> assets;

    public PdxAssets(Path installDir, int index, ProgressMessageUpdater progressMessageUpdater) {
        Path[] assetFiles;
        try (Stream<Path> stream = Files.walk(installDir)) {
            assetFiles = stream
                    .filter(Files::isRegularFile)
                    .filter(FILTER)
                    .sorted(PathUtil.ASCIIBETICAL)
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        Map<String, IPdxAsset> assets = new LinkedHashMap<>();
        for (Path file : assetFiles) {
            String name = installDir.relativize(file).toString().replace(File.separatorChar, '/');
            assets.put(name, IPdxAsset.load(file));
        }
        this.assets = Collections.unmodifiableMap(assets);
    }

    public Map<String, IPdxAsset> getAssets() {
        return assets;
    }
}
