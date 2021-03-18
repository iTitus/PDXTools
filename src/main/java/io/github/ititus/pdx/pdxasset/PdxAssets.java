package io.github.ititus.pdx.pdxasset;

import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IPathFilter;
import io.github.ititus.pdx.util.mutable.MutableInt;
import org.eclipse.collections.api.map.ImmutableMap;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.eclipse.collections.impl.collector.Collectors2.toImmutableMap;

public final class PdxAssets {

    private static final IPathFilter FILTER = new FileExtensionFilter("mesh", "anim");

    private final ImmutableMap<String, IPdxAsset> assets;

    public PdxAssets(Path installDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        Path[] assetFiles;
        try (Stream<Path> stream = Files.walk(installDir)) {
            assetFiles = stream
                    .filter(Objects::nonNull)
                    .filter(Predicate.not(Files::isDirectory))
                    .filter(FILTER)
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        MutableInt progress = new MutableInt();
        this.assets = Arrays.stream(assetFiles)
                .peek(p -> {
                    if (progressMessageUpdater != null) {
                        progressMessageUpdater.updateProgressMessage(index, true, progress.getAndIncrement(), assetFiles.length,
                                "Loading Asset File " + installDir.relativize(p));
                    }
                })
                .collect(toImmutableMap(
                        p -> installDir.relativize(p).normalize().toString().replace(File.separatorChar, '/'),
                        IPdxAsset::load
                ));
        if (progressMessageUpdater != null) {
            progressMessageUpdater.updateProgressMessage(index, false, assetFiles.length, assetFiles.length, "Done");
        }
    }

    public ImmutableMap<String, IPdxAsset> getAssets() {
        return assets;
    }
}
