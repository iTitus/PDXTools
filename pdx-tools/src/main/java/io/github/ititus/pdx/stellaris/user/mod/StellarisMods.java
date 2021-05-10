package io.github.ititus.pdx.stellaris.user.mod;

import io.github.ititus.data.mutable.MutableInt;
import io.github.ititus.io.FileExtensionFilter;
import io.github.ititus.io.PathFilter;
import io.github.ititus.io.PathUtil;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class StellarisMods {

    private static final PathFilter MOD = new FileExtensionFilter("mod");

    private final Path userDataDir, modsFolder;
    private final ImmutableMap<String, StellarisMod> mods;

    public StellarisMods(Path userDataDir, Path modsFolder, int index,
                         StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (userDataDir == null || !Files.isDirectory(userDataDir) || modsFolder == null || !Files.isDirectory(modsFolder)) {
            throw new IllegalArgumentException();
        }
        this.userDataDir = userDataDir;
        this.modsFolder = modsFolder;

        MutableMap<String, StellarisMod> map = Maps.mutable.empty();

        Path[] paths;
        try (Stream<Path> stream = Files.list(modsFolder)) {
            paths = stream
                    .filter(MOD)
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        int fileCount = paths.length;
        MutableInt progress = MutableInt.ofZero();

        Arrays.stream(paths).forEach(f -> {
            progressMessageUpdater.updateProgressMessage(index, true, progress.getAndIncrement(), fileCount, "Loading Mod " + PathUtil.getNameWithoutExtension(f));
            map.put(PathUtil.getNameWithoutExtension(f), new StellarisMod(userDataDir, f));
        });
        progressMessageUpdater.updateProgressMessage(index, false, fileCount, fileCount, "Done");
        this.mods = map.toImmutable();
    }

    public Path getUserDataDir() {
        return userDataDir;
    }

    public Path getModsFolder() {
        return modsFolder;
    }

    public ImmutableMap<String, StellarisMod> getMods() {
        return mods;
    }
}
