package io.github.ititus.pdx.stellaris.game.dlc;

import io.github.ititus.commons.io.PathUtil;
import io.github.ititus.pdx.shared.ProgressMessageUpdater;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class StellarisDLCs {

    private final Path installDir, dlcDir;
    private final ImmutableMap<String, StellarisDLC> dlcs;

    public StellarisDLCs(Path installDir, Path dlcDir, int index, ProgressMessageUpdater progressMessageUpdater) {
        if (installDir == null || !Files.isDirectory(installDir) || dlcDir == null || !Files.isDirectory(dlcDir)) {
            throw new IllegalArgumentException();
        }

        this.installDir = installDir;
        this.dlcDir = dlcDir;

        Path[] paths;
        try (Stream<Path> stream = Files.list(dlcDir)) {
            paths = stream
                    .filter(Files::isDirectory)
                    .sorted(PathUtil.ASCIIBETICAL)
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        int dirCount = paths.length;
        int progress = 0;
        MutableMap<String, StellarisDLC> map = Maps.mutable.empty();
        for (Path p : paths) {
            String name = p.getFileName().toString();
            progressMessageUpdater.updateProgressMessage(index, true, progress++, dirCount, "Loading " + name);
            map.put(name, new StellarisDLC(installDir, p));
        }

        this.dlcs = map.toImmutable();

        progressMessageUpdater.updateProgressMessage(index, false, dirCount, dirCount, "Done");
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
