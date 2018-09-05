package io.github.ititus.pdx.stellaris.user.mod;

import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.mutable.MutableInt;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

public class StellarisMods {

    private static final FileFilter MOD = new FileExtensionFilter("mod");

    private final File userDataDir, modsFolder;
    private final ImmutableMap<String, StellarisMod> mods;

    public StellarisMods(File userDataDir, File modsFolder, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (userDataDir == null || !userDataDir.isDirectory() || modsFolder == null || !modsFolder.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.userDataDir = userDataDir;
        this.modsFolder = modsFolder;

        MutableMap<String, StellarisMod> map = Maps.mutable.empty();
        File[] files = modsFolder.listFiles(MOD);
        int FILE_COUNT = files.length;
        MutableInt progress = new MutableInt();

        Arrays.stream(files).forEach(f -> {
            progressMessageUpdater.updateProgressMessage(index, true, progress.getAndIncrement(), FILE_COUNT, "Loading Mod " + IOUtil.getNameWithoutExtension(f));
            map.put(IOUtil.getNameWithoutExtension(f), new StellarisMod(userDataDir, f));
        });
        progressMessageUpdater.updateProgressMessage(index, false, FILE_COUNT, FILE_COUNT, "Done");
        this.mods = map.toImmutable();
    }

    public File getUserDataDir() {
        return userDataDir;
    }

    public File getModsFolder() {
        return modsFolder;
    }

    public ImmutableMap<String, StellarisMod> getMods() {
        return mods;
    }
}
