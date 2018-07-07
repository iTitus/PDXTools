package io.github.ititus.pdx.stellaris.user.mod;

import io.github.ititus.pdx.util.io.IOUtil;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StellarisMods {

    private final File modsFolder;
    private final Map<String, StellarisMod> mods;

    public StellarisMods(File modsFolder) {
        if (modsFolder == null || !modsFolder.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.modsFolder = modsFolder;
        this.mods = new HashMap<>();

        File[] files = modsFolder.listFiles();
        if (files != null) {
            for (File modFile : files) {
                if (modFile != null) {
                    if (modFile.isDirectory()) {
                        // TODO: Parse folder mods
                    } else if (IOUtil.getExtension(modFile).equals("mod")) {
                        mods.put(IOUtil.getNameWithoutExtension(modFile), new StellarisMod(modFile));
                    }
                }
            }
        }
    }

    public File getModsFolder() {
        return modsFolder;
    }

    public Map<String, StellarisMod> getMods() {
        return Collections.unmodifiableMap(mods);
    }
}
