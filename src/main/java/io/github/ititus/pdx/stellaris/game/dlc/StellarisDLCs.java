package io.github.ititus.pdx.stellaris.game.dlc;

import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.util.mutable.MutableInt;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class StellarisDLCs {

    private final Path installDir, dlcDir;
    private final ImmutableMap<String, StellarisDLC> dlcs;

    public StellarisDLCs(Path installDir, Path dlcDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (installDir == null || !Files.isDirectory(installDir) || dlcDir == null || !Files.isDirectory(dlcDir)) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;
        this.dlcDir = dlcDir;

        MutableMap<String, StellarisDLC> map = Maps.mutable.empty();

        Path[] paths;
        try (Stream<Path> stream = Files.list(dlcDir)) {
            paths = stream
                    .filter(Files::isDirectory)
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        int dirCount = paths.length;
        MutableInt progress = new MutableInt();

        Arrays.stream(paths).forEach(p -> {
            progressMessageUpdater.updateProgressMessage(index, true, progress.getAndIncrement(), dirCount, "Loading DLC " + p.getFileName());
            map.put(p.getFileName().toString(), new StellarisDLC(installDir, p));
        });
        progressMessageUpdater.updateProgressMessage(index, false, dirCount, dirCount, "Done");
        this.dlcs = map.toImmutable();
    }

    public Path getInstallDir() {
        return installDir;
    }

    public Path getDLCDir() {
        return dlcDir;
    }

    public ImmutableMap<String, StellarisDLC> getDLCs() {
        return dlcs;
    }
}
