package io.github.ititus.pdx.stellaris.user;

import java.io.File;

public class StellarisUserData {

    private final File dataDir;

    public StellarisUserData(String dataDirPath) {
        this(new File(dataDirPath));
    }

    public StellarisUserData(File dataDir) {
        if (dataDir == null || !dataDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.dataDir = dataDir;
    }

    public File getDataDir() {
        return dataDir;
    }
}
