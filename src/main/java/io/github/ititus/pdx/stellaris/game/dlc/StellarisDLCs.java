package io.github.ititus.pdx.stellaris.game.dlc;

import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.util.mutable.MutableInt;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;

import java.io.File;
import java.util.Arrays;

public class StellarisDLCs {

    private final File installDir, dlcDir;
    private final ImmutableMap<String, StellarisDLC> dlcs;

    public StellarisDLCs(File installDir, File dlcDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (installDir == null || !installDir.isDirectory() || dlcDir == null || !dlcDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;
        this.dlcDir = dlcDir;

        MutableMap<String, StellarisDLC> map = Maps.mutable.empty();
        File[] files = dlcDir.listFiles(f -> f != null && f.isDirectory());
        int FILE_COUNT = files.length;
        MutableInt progress = new MutableInt();

        Arrays.stream(files).forEach(f -> {
            progressMessageUpdater.updateProgressMessage(index, true, progress.getAndIncrement(), FILE_COUNT, "Loading DLC " + f.getName());
            map.put(f.getName(), new StellarisDLC(installDir, f));
        });
        progressMessageUpdater.updateProgressMessage(index, false, FILE_COUNT, FILE_COUNT, "Done");
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
