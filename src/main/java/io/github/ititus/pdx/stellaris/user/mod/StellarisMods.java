package io.github.ititus.pdx.stellaris.user.mod;

import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;
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

    public StellarisMods(File userDataDir, File modsFolder) {
        if (userDataDir == null || !userDataDir.isDirectory() || modsFolder == null || !modsFolder.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.userDataDir = userDataDir;
        this.modsFolder = modsFolder;

        MutableMap<String, StellarisMod> map = Maps.mutable.empty();
        Arrays.stream(modsFolder.listFiles(MOD)).forEach(f -> map.put(IOUtil.getNameWithoutExtension(f), new StellarisMod(userDataDir, f)));
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
