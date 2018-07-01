package io.github.ititus.pdx.util;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;

public class FileUtil {


    public static Comparator<File> asciibetical(File dir) {
        try {
            return asciibetical(dir.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Comparator<File> asciibetical(String canonicalPath) {
        return Comparator.comparing(f -> (f.isDirectory() ? (char) 1 : (char) 0) + getRelativePath(canonicalPath, f));
    }

    public static String getRelativePath(String installDirPath, File f) {
        try {
            String canonical = f.getCanonicalPath();
            canonical = canonical.replace(installDirPath, "");
            canonical = canonical.replace("\\", "/");
            if (canonical.startsWith("/")) {
                canonical = canonical.substring(1);
            }
            return canonical;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
