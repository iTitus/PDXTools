package io.github.ititus.pdx.util.io;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public final class IOUtil {

    private IOUtil() {
    }

    public static Comparator<Path> asciibetical(Path root) {
        return Comparator.comparing(p -> (Files.isDirectory(p) ? (char) 1 : (char) 0) + root.relativize(p).toString());
    }

    public static String getExtension(Path p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return getExtension(p.getFileName().toString());
    }

    public static String getExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) {
            return fileName.substring(i + 1).toLowerCase(Locale.ROOT);
        }
        return "";
    }

    public static String getNameWithoutExtension(Path p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return getNameWithoutExtension(p.getFileName().toString());
    }

    public static String getNameWithoutExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) {
            return fileName.substring(0, i);
        }
        return fileName;
    }

    public static PrimitiveIterator.OfInt getCharacterIterator(Reader r) {
        return new PrimitiveIterator.OfInt() {

            int next = -1;
            boolean hasNextCalled = false;

            @Override
            public int nextInt() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int n = next;
                hasNextCalled = false;
                next = -1;
                return n;
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
        };
    }

    public static IntStream getCharacterStream(Reader r) {
        return StreamSupport.intStream(Spliterators.spliteratorUnknownSize(getCharacterIterator(r),
                Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE), false);
    }

    public static FileSystem openZip(Path zip) throws IOException {
        return openZip(zip, false);
    }

    public static FileSystem openZip(Path zip, boolean create) throws IOException {
        URI uri = URI.create("jar:file:" + zip.toUri().getRawPath());
        Map<String, Boolean> env = Collections.singletonMap("create", create);
        return FileSystems.newFileSystem(uri, env);
    }
}
