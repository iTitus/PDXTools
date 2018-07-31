package io.github.ititus.pdx.stellaris.user;

import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
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
    private final StellarisSaves saves;
    private final StellarisMods mods;
    private final PdxRawDataLoader rawDataLoader;

    public StellarisUserData(String dataDirPath) {
        this(new File(dataDirPath));
    }

    public StellarisUserData(File userDataDir) {
        if (userDataDir == null || !userDataDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.userDataDir = userDataDir;

        this.saves = new StellarisSaves(new File(userDataDir, "save games"));
        this.mods = new StellarisMods(userDataDir, new File(userDataDir, "mod"));
        this.rawDataLoader = new PdxRawDataLoader(userDataDir, BLACKLIST, FILTER);
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
