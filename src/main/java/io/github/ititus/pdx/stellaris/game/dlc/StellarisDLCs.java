package io.github.ititus.pdx.stellaris.game.dlc;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StellarisDLCs {

    private final File installDir, dlcDir;
    private final Map<String, StellarisDLC> dlcs;

    public StellarisDLCs(File installDir, File dlcDir) {
        if (installDir == null || !installDir.isDirectory() || dlcDir == null || !dlcDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;
        this.dlcDir = dlcDir;
        this.dlcs = new HashMap<>();

        File[] files = dlcDir.listFiles();
        if (files != null) {
            for (File dlcFolder : files) {
                if (dlcFolder != null && dlcDir.isDirectory()) {
                    dlcs.put(dlcFolder.getName(), new StellarisDLC(installDir, dlcFolder));
                }
            }
        }
    }

    public File getInstallDir() {
        return installDir;
    }

    public File getDLCDir() {
        return dlcDir;
    }

    public Map<String, StellarisDLC> getDLCs() {
        return Collections.unmodifiableMap(dlcs);
    }
}
