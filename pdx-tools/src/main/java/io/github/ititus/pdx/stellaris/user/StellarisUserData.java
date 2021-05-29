package io.github.ititus.pdx.stellaris.user;

import io.github.ititus.io.FileExtensionFilter;
import io.github.ititus.io.PathFilter;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.stellaris.user.mod.StellarisMods;
import io.github.ititus.pdx.stellaris.user.save.StellarisSaves;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.nio.file.Files;
import java.nio.file.Path;

public class StellarisUserData {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of(
            // Not PDXScript
            ".launcher-cache", "cache", "crashes", "dumps", "exceptions", "logs", "pops_filestorage", "screenshots",
            "shadercache",
            // Handled separately
            "mod", "save games"
    );
    private static final PathFilter FILTER = new FileExtensionFilter("txt", "mod");

    private final Path userDataDir;
    private final StellarisMods mods;
    private final StellarisSaves saves;
    private final PdxRawDataLoader rawDataLoader;

    public StellarisUserData(Path userDataDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (userDataDir == null || !Files.isDirectory(userDataDir)) {
            throw new IllegalArgumentException();
        }
        this.userDataDir = userDataDir;

        int steps = 3;

        progressMessageUpdater.updateProgressMessage(index, true, 0, steps, "Loading Mods");
        // FIXME: disabled because it is slow
        this.mods = null; // new StellarisMods(userDataDir, userDataDir.resolve("mod"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 1, steps, "Loading Saves");
        this.saves = new StellarisSaves(userDataDir.resolve("save games"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 2, steps, "Loading Raw User Data");
        // FIXME: disabled because it is slow
        this.rawDataLoader = null; // new PdxRawDataLoader(userDataDir, BLACKLIST, FILTER, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, false, 3, steps, "Done");
    }

    public Path getUserDataDir() {
        return userDataDir;
    }

    public StellarisSaves getSaves() {
        return saves;
    }

    public StellarisMods getMods() {
        return mods;
    }

    public PdxRawDataLoader getRawDataLoader() {
        return rawDataLoader;
    }
}
