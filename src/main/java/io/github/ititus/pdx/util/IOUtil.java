package io.github.ititus.pdx.util;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class IOUtil {

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

    public static String getExtension(File file) {
        if (file == null) {
            throw new IllegalArgumentException();
        }
        if (!file.isFile()) {
            return "";
        }
        return getExtension(file.getName());
    }

    public static String getExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) {
            return fileName.substring(i + 1).toLowerCase(Locale.ENGLISH);
        }
        return "";
    }

    public static String getNameWithoutExtension(File file) {
        if (file == null) {
            throw new IllegalArgumentException();
        }
        if (!file.isFile()) {
            return file.getName();
        }
        return getNameWithoutExtension(file.getName());
    }

    public static String getNameWithoutExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 1 && i < fileName.length() - 1) {
            return fileName.substring(0, i);
        }
        return fileName;
    }

    public static IntStream getCharacterStream(Reader r) {
        return StreamSupport.intStream(Spliterators.spliteratorUnknownSize(new PrimitiveIterator.OfInt() {

            int next = -1;
            boolean hasNextCalled = false;

            @Override
            public int nextInt() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                hasNextCalled = false;
                return next;
            }

            @Override
            public boolean hasNext() {
                if (!hasNextCalled) {
                    try {
                        next = r.read();
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                    hasNextCalled = true;
                }
                return next != -1;
            }
        }, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE), false);
    }
}
