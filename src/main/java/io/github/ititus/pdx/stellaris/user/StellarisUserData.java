package io.github.ititus.pdx.stellaris.user;

import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.stellaris.user.mod.StellarisMods;
import io.github.ititus.pdx.stellaris.user.save.StellarisSaves;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IFileFilter;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.File;

public class StellarisUserData {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of(
            // Not PDXScript
            "cache", "dumps", "pops_filestorage", "screenshots", "shadercache",
            // Handled separately
            "save games", "oos", "mod"
    );
    private static final IFileFilter FILTER = new FileExtensionFilter("txt", "mod");

    private final File userDataDir;
    private final StellarisMods mods;
    private final StellarisSaves saves;
    private final PdxRawDataLoader rawDataLoader;

    public StellarisUserData(String dataDirPath, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        this(new File(dataDirPath), index, progressMessageUpdater);
    }

    public StellarisUserData(File userDataDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (userDataDir == null || !userDataDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.userDataDir = userDataDir;

        int STEPS = 3;

        progressMessageUpdater.updateProgressMessage(index, true, 0, STEPS, "Loading Mods");
        this.mods = new StellarisMods(userDataDir, new File(userDataDir, "mod"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 1, STEPS, "Loading Saves");
        this.saves = new StellarisSaves(new File(userDataDir, "save games"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 2, STEPS, "Loading User Data");
        this.rawDataLoader = new PdxRawDataLoader(userDataDir, BLACKLIST, FILTER, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, false, 3, STEPS, "Done");
    }

    public File getUserDataDir() {
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
