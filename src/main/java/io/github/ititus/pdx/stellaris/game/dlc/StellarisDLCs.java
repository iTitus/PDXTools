package io.github.ititus.pdx.stellaris.game.dlc;

import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;

import java.io.File;
import java.util.Arrays;

public class StellarisDLCs {

    private final File installDir, dlcDir;
    private final ImmutableMap<String, StellarisDLC> dlcs;

    public StellarisDLCs(File installDir, File dlcDir) {
        if (installDir == null || !installDir.isDirectory() || dlcDir == null || !dlcDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;
        this.dlcDir = dlcDir;

        MutableMap<String, StellarisDLC> map = Maps.mutable.empty();
        Arrays.stream(dlcDir.listFiles(f -> f != null && f.isDirectory())).forEach(f -> map.put(f.getName(), new StellarisDLC(installDir, f)));
        this.dlcs = map.toImmutable();
    }

    public File getInstallDir() {
        return installDir;
    }

    public File getDLCDir() {
        return dlcDir;
    }

    public ImmutableMap<String, StellarisDLC> getDLCs() {
        return dlcs;
    }
}
