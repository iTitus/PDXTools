package io.github.ititus.pdx.stellaris.user;

import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.stellaris.user.mod.StellarisMods;
import io.github.ititus.pdx.stellaris.user.save.StellarisSaves;
import io.github.ititus.pdx.util.CollectionUtil;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IFileFilter;

import java.io.File;
import java.util.Set;

public class StellarisUserData {

    private static final Set<String> BLACKLIST = CollectionUtil.setOf(
            // Not PDXScript
            "cache", "dumps", "pops_filestorage", "screenshots", "shadercache",
            // Handled separately
            "save games", "oos", "mod"
    );
    private static final IFileFilter FILTER = new FileExtensionFilter("txt", "mod");

    private final File dataDir;
    private final StellarisSaves saves;
    private final StellarisMods mods;
    private final PdxRawDataLoader rawDataLoader;

    public StellarisUserData(String dataDirPath) {
        this(new File(dataDirPath));
    }

    public StellarisUserData(File dataDir) {
        if (dataDir == null || !dataDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.dataDir = dataDir;

        this.saves = new StellarisSaves(new File(dataDir, "save games"));
        this.mods = new StellarisMods(new File(dataDir, "mod"));
        this.rawDataLoader = new PdxRawDataLoader(dataDir, BLACKLIST, FILTER);
    }

    public File getDataDir() {
        return dataDir;
    }

    public StellarisSaves getSaves() {
        return saves;
    }

    public PdxRawDataLoader getRawDataLoader() {
        return rawDataLoader;
    }
}
