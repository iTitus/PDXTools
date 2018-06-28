package io.github.ititus.pdx.stellaris.game;

import io.github.ititus.pdx.pdxlocalisation.PDXLocalisation;
import io.github.ititus.pdx.pdxlocalisation.PdxLocalisationParser;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.util.CollectionUtil;
import io.github.ititus.pdx.util.FileExtensionFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Set;

public class StellarisGame {

    private static final Set<String> BLACKLIST_ROOT_FOLDERS = CollectionUtil.setOf("_CommonRedist", "licenses");
    private static final Set<String> BLACKLIST_FILES = CollectionUtil.setOf();

    private static final FileFilter FILTER = new FileExtensionFilter("txt", "dlc", "asset", "gui", "gfx");

    private final File installDir;
    private final PDXLocalisation localisation;
    private final PdxScriptObject data;

    public StellarisGame(String installDirPath) {
        this(new File(installDirPath));
    }

    public StellarisGame(File installDir) {
        if (installDir == null || !installDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;

        this.localisation = PdxLocalisationParser.parse(installDir);

        PdxScriptObject.Builder b = PdxScriptObject.builder();
        File[] files = installDir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f != null) {
                    if (f.isDirectory()) {
                        if (!BLACKLIST_ROOT_FOLDERS.contains(f.getName())) {
                            PdxScriptObject o = parse(f);
                            if (o != null && o.size() > 0) {
                                b.add(f.getName(), o);
                            }
                        }
                    }
                }
            }
        }
        this.data = b.build();
    }

    private static PdxScriptObject parse(File dir) {
        PdxScriptObject.Builder b = PdxScriptObject.builder();
        if (dir != null && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f != null) {
                        if (f.isDirectory()) {
                            PdxScriptObject o = parse(f);
                            if (o != null && o.size() > 0) {
                                b.add(f.getName(), o);
                            }
                        } else if (!BLACKLIST_FILES.contains(f.getName()) && FILTER.accept(f)) {
                            IPdxScript s;
                            try {
                                s = PdxScriptParser.parse(f);
                            } catch (Exception e) {
                                Throwable[] suppressed = e.getSuppressed();
                                Throwable cause = e.getCause();
                                System.out.println("Error while parsing " + f.getName() + ": " + e + (suppressed != null && suppressed.length > 0 ? ", Supressed: " + Arrays.toString(suppressed) : "") + (cause != null ? ", Caused By: " + cause : ""));
                                s = null;
                            }
                            if (s != null) {
                                boolean success = false;
                                if (s instanceof PdxScriptObject) {
                                    if (((PdxScriptObject) s).size() > 0) {
                                        success = true;
                                    }
                                } else if (s instanceof PdxScriptList) {
                                    if (((PdxScriptList) s).size() > 0) {
                                        success = true;
                                    }
                                } else {
                                    throw new RuntimeException("Unexpected return value from parsing: " + s.getClass().getTypeName());
                                }
                                if (success) {
                                    b.add(f.getName(), s);
                                }
                            }
                        }
                    }
                }
            }
        }
        return b.build();
    }

    public File getInstallDir() {
        return installDir;
    }

    public PDXLocalisation getLocalisation() {
        return localisation;
    }
}
