package io.github.ititus.pdx.stellaris.user.mod;

import com.koloboke.collect.map.hash.HashObjObjMaps;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
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

        File[] files = modsFolder.listFiles(MOD);
        this.mods = HashObjObjMaps.newImmutableMap(Arrays.stream(files).map(IOUtil::getNameWithoutExtension)::iterator, Arrays.stream(files).map(modFile -> new StellarisMod(userDataDir, modFile))::iterator);
    }

    public File getUserDataDir() {
        return userDataDir;
    }

    public File getModsFolder() {
        return modsFolder;
    }

    public Map<String, StellarisMod> getMods() {
        return mods;
    }
}
