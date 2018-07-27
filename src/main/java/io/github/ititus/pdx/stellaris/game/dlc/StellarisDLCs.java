package io.github.ititus.pdx.stellaris.game.dlc;

import com.koloboke.collect.map.hash.HashObjObjMaps;

import java.io.File;
import java.util.Arrays;
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

        File[] files = dlcDir.listFiles(f -> f != null && f.isDirectory());
        this.dlcs = HashObjObjMaps.newImmutableMap(Arrays.stream(files).map(File::getName)::iterator, Arrays.stream(files).map(dlcFolder -> new StellarisDLC(installDir, dlcFolder))::iterator);
    }

    public File getInstallDir() {
        return installDir;
    }

    public File getDLCDir() {
        return dlcDir;
    }

    public Map<String, StellarisDLC> getDLCs() {
        return dlcs;
    }
}
