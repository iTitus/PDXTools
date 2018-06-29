package io.github.ititus.pdx.stellaris.game;

import io.github.ititus.pdx.pdxlocalisation.PDXLocalisation;
import io.github.ititus.pdx.pdxlocalisation.PdxLocalisationParser;
import io.github.ititus.pdx.pdxscript.*;
import io.github.ititus.pdx.util.CollectionUtil;
import io.github.ititus.pdx.util.FileExtensionFilter;
import io.github.ititus.pdx.util.Pair;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StellarisGame {

    private static final Set<String> BLACKLIST = CollectionUtil.setOf(
            "licenses", "ChangeLog.txt", "ChangeLogBlank.txt", "checksum_manifest.txt", "console_history.txt", "common/HOW_TO_MAKE_NEW_SHIPS.txt", "interface/reference.txt"
    );
    private static final FileFilter FILTER = new FileExtensionFilter("txt", "dlc", "asset", "gui", "gfx");

    private static final List<Pair<String, Exception>> errors = new ArrayList<>();

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

        String canonical;
        try {
            canonical = installDir.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            canonical = installDir.getAbsolutePath();
        }
        this.data = parseFolder(canonical, installDir);
    }

    private static PdxScriptObject parseFolder(String installDirPath, File dir) {
        if (dir != null && dir.isDirectory() && !BLACKLIST.contains(getRelativePath(installDirPath, dir))) {
            File[] files = dir.listFiles();
            if (files != null) {
                PdxScriptObject.Builder b = PdxScriptObject.builder();
                Arrays.stream(files).filter(Objects::nonNull).sorted(Comparator.comparing(f -> {
                    if (f.isDirectory()) {
                        return "1_" + f.getName();
                    }
                    return "0_" + f.getName();
                })).forEach(f -> {
                    if (f.isDirectory()) {
                        PdxScriptObject o = parseFolder(installDirPath, f);
                        if (o != null) {
                            b.add(f.getName(), o);
                        }
                    } else {
                        IPdxScript s = parseFile(installDirPath, f);
                        if (s != null) {
                            b.add(f.getName(), s);
                        }
                    }
                });
                PdxScriptObject o = b.build(PdxRelation.EQUALS);
                return o.size() > 0 ? o : null;
            }
        }
        return null;
    }

    private static IPdxScript parseFile(String installDirPath, File f) {
        if (f != null && f.isFile()) {
            String path = getRelativePath(installDirPath, f);
            if (!BLACKLIST.contains(path) && FILTER.accept(f)) {
                IPdxScript s;
                try {
                    s = PdxScriptParser.parse(f);
                } catch (Exception e) {
                    Throwable[] suppressed = e.getSuppressed();
                    Throwable cause = e.getCause();
                    System.out.println("Error while parsing " + path + ": " + e + (suppressed != null && suppressed.length > 0 ? ", Supressed: " + Arrays.toString(suppressed) : "") + (cause != null ? ", Caused By: " + cause : ""));
                    errors.add(Pair.of(path, e));
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
                        return s;
                    }
                }
            }
        }
        return null;
    }

    private static String getRelativePath(String installDirPath, File f) {
        String canonical;
        try {
            canonical = f.getCanonicalPath();
        } catch (IOException e1) {
            e1.printStackTrace();
            canonical = f.getAbsolutePath();
        }
        canonical = canonical.replace(installDirPath, "");
        canonical = canonical.replace("\\", "/");
        if (canonical.startsWith("/")) {
            canonical = canonical.substring(1);
        }
        return canonical;
    }

    public static List<Pair<String, Exception>> getErrors() {
        return Collections.unmodifiableList(errors.stream().sorted(Comparator.comparing((Pair<String, Exception> p) -> p.getValue().getMessage()).thenComparing(Pair::getKey)).collect(Collectors.toList()));
    }

    public File getInstallDir() {
        return installDir;
    }

    public PDXLocalisation getLocalisation() {
        return localisation;
    }

    public PdxScriptObject getData() {
        return data;
    }
}
