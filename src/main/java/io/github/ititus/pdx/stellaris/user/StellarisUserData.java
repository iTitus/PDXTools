package io.github.ititus.pdx.stellaris.user;

import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.stellaris.user.save.StellarisSaves;
import io.github.ititus.pdx.util.CollectionUtil;
import io.github.ititus.pdx.util.FileExtensionFilter;
import io.github.ititus.pdx.util.IFileFilter;
import io.github.ititus.pdx.util.Pair;

import java.io.File;
import java.util.List;
import java.util.Set;

public class StellarisUserData {

    private static final Set<String> BLACKLIST = CollectionUtil.setOf(
            // Not PDXScript
            "cache", "dumps", "pops_filestorage", "screenshots", "shadercache",
            // Handled separately
            "save games", "oos"
    );
    private static final IFileFilter FILTER = new FileExtensionFilter("txt", "mod");

    private final File dataDir;
    private final StellarisSaves saves;
    private final PdxRawDataLoader rawDataLoader;

    public StellarisUserData(String dataDirPath) {
        this(new File(dataDirPath));
    }

    public StellarisUserData(File dataDir) {
        if (dataDir == null || !dataDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.dataDir = dataDir;

        this.saves = /*null; //*/ new StellarisSaves(new File(dataDir, "save games"));
        this.rawDataLoader = new PdxRawDataLoader(dataDir, BLACKLIST, FILTER).load();
    }

    public File getDataDir() {
        return dataDir;
    }

    public StellarisSaves getSaves() {
        return saves;
    }

    public PdxScriptObject getRawData() {
        return rawDataLoader.getRawData();
    }

    public List<Pair<String, Throwable>> getErrors() {
        return rawDataLoader.getErrors();
    }
}
