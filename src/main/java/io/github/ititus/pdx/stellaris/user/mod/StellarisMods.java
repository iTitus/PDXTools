package io.github.ititus.pdx.stellaris.user.mod;

import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StellarisMods {

    private static final FileFilter MOD = new FileExtensionFilter("mod");

    private final File userDataDir, modsFolder;
    private final Map<String, StellarisMod> mods;

    public StellarisMods(File userDataDir, File modsFolder) {
        if (userDataDir == null || !userDataDir.isDirectory() || modsFolder == null || !modsFolder.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.userDataDir = userDataDir;
        this.modsFolder = modsFolder;
        this.mods = new HashMap<>();

        File[] files = modsFolder.listFiles(MOD);
        if (files != null) {
            for (File modFile : files) {
                mods.put(IOUtil.getNameWithoutExtension(modFile), new StellarisMod(userDataDir, modFile));
            }
        }
    }

    public File getUserDataDir() {
        return userDataDir;
    }

    public File getModsFolder() {
        return modsFolder;
    }

    public Map<String, StellarisMod> getMods() {
        return Collections.unmodifiableMap(mods);
    }
}
